/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.controllers;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.controllers.exceptions.PreexistingEntityException;
import prefeitura.entities.Notafiscal;
import prefeitura.entities.Possui;
import prefeitura.entities.PossuiPK;
import prefeitura.entities.Processo;
import prefeitura.entities.Protocolo;

/**
 *
 * @author lucia
 */
public class PossuiJpaController implements Serializable {

    public PossuiJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Possui possui) throws PreexistingEntityException, Exception {
        if (possui.getPossuiPK() == null) {
            possui.setPossuiPK(new PossuiPK());
        }
        possui.getPossuiPK().setNumeroProcesso(possui.getProcesso().getNumeroProcesso());
        possui.getPossuiPK().setNumeroNota(possui.getNotafiscal().getNumeroNota());
        possui.getPossuiPK().setNumeroProtocolo(possui.getProtocolo().getNumeroProtocolo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notafiscal notafiscal = possui.getNotafiscal();
            if (notafiscal != null) {
                notafiscal = em.getReference(notafiscal.getClass(), notafiscal.getNumeroNota());
                possui.setNotafiscal(notafiscal);
            }
            Processo processo = possui.getProcesso();
            if (processo != null) {
                processo = em.getReference(processo.getClass(), processo.getNumeroProcesso());
                possui.setProcesso(processo);
            }
            Protocolo protocolo = possui.getProtocolo();
            if (protocolo != null) {
                protocolo = em.getReference(protocolo.getClass(), protocolo.getNumeroProtocolo());
                possui.setProtocolo(protocolo);
            }
            em.persist(possui);
            if (notafiscal != null) {
                notafiscal.getPossuiList().add(possui);
                notafiscal = em.merge(notafiscal);
            }
            if (processo != null) {
                processo.getPossuiList().add(possui);
                processo = em.merge(processo);
            }
            if (protocolo != null) {
                protocolo.getPossuiList().add(possui);
                protocolo = em.merge(protocolo);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findPossui(possui.getPossuiPK()) != null) {
                throw new PreexistingEntityException("Possui " + possui + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Possui possui) throws NonexistentEntityException, Exception {
        possui.getPossuiPK().setNumeroProcesso(possui.getProcesso().getNumeroProcesso());
        possui.getPossuiPK().setNumeroNota(possui.getNotafiscal().getNumeroNota());
        possui.getPossuiPK().setNumeroProtocolo(possui.getProtocolo().getNumeroProtocolo());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Possui persistentPossui = em.find(Possui.class, possui.getPossuiPK());
            Notafiscal notafiscalOld = persistentPossui.getNotafiscal();
            Notafiscal notafiscalNew = possui.getNotafiscal();
            Processo processoOld = persistentPossui.getProcesso();
            Processo processoNew = possui.getProcesso();
            Protocolo protocoloOld = persistentPossui.getProtocolo();
            Protocolo protocoloNew = possui.getProtocolo();
            if (notafiscalNew != null) {
                notafiscalNew = em.getReference(notafiscalNew.getClass(), notafiscalNew.getNumeroNota());
                possui.setNotafiscal(notafiscalNew);
            }
            if (processoNew != null) {
                processoNew = em.getReference(processoNew.getClass(), processoNew.getNumeroProcesso());
                possui.setProcesso(processoNew);
            }
            if (protocoloNew != null) {
                protocoloNew = em.getReference(protocoloNew.getClass(), protocoloNew.getNumeroProtocolo());
                possui.setProtocolo(protocoloNew);
            }
            possui = em.merge(possui);
            if (notafiscalOld != null && !notafiscalOld.equals(notafiscalNew)) {
                notafiscalOld.getPossuiList().remove(possui);
                notafiscalOld = em.merge(notafiscalOld);
            }
            if (notafiscalNew != null && !notafiscalNew.equals(notafiscalOld)) {
                notafiscalNew.getPossuiList().add(possui);
                notafiscalNew = em.merge(notafiscalNew);
            }
            if (processoOld != null && !processoOld.equals(processoNew)) {
                processoOld.getPossuiList().remove(possui);
                processoOld = em.merge(processoOld);
            }
            if (processoNew != null && !processoNew.equals(processoOld)) {
                processoNew.getPossuiList().add(possui);
                processoNew = em.merge(processoNew);
            }
            if (protocoloOld != null && !protocoloOld.equals(protocoloNew)) {
                protocoloOld.getPossuiList().remove(possui);
                protocoloOld = em.merge(protocoloOld);
            }
            if (protocoloNew != null && !protocoloNew.equals(protocoloOld)) {
                protocoloNew.getPossuiList().add(possui);
                protocoloNew = em.merge(protocoloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                PossuiPK id = possui.getPossuiPK();
                if (findPossui(id) == null) {
                    throw new NonexistentEntityException("The possui with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(PossuiPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Possui possui;
            try {
                possui = em.getReference(Possui.class, id);
                possui.getPossuiPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The possui with id " + id + " no longer exists.", enfe);
            }
            Notafiscal notafiscal = possui.getNotafiscal();
            if (notafiscal != null) {
                notafiscal.getPossuiList().remove(possui);
                notafiscal = em.merge(notafiscal);
            }
            Processo processo = possui.getProcesso();
            if (processo != null) {
                processo.getPossuiList().remove(possui);
                processo = em.merge(processo);
            }
            Protocolo protocolo = possui.getProtocolo();
            if (protocolo != null) {
                protocolo.getPossuiList().remove(possui);
                protocolo = em.merge(protocolo);
            }
            em.remove(possui);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Possui> findPossuiEntities() {
        return findPossuiEntities(true, -1, -1);
    }

    public List<Possui> findPossuiEntities(int maxResults, int firstResult) {
        return findPossuiEntities(false, maxResults, firstResult);
    }

    private List<Possui> findPossuiEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Possui.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Possui findPossui(PossuiPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Possui.class, id);
        } finally {
            em.close();
        }
    }

    public int getPossuiCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Possui> rt = cq.from(Possui.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
