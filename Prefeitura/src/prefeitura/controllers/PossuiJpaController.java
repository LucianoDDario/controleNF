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
import prefeitura.entities.Notafiscal;
import prefeitura.entities.Possui;
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

    public void create(Possui possui) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notafiscal numeroNota = possui.getNumeroNota();
            if (numeroNota != null) {
                numeroNota = em.getReference(numeroNota.getClass(), numeroNota.getIdNotaFiscal());
                possui.setNumeroNota(numeroNota);
            }
            Processo numeroProcesso = possui.getNumeroProcesso();
            if (numeroProcesso != null) {
                numeroProcesso = em.getReference(numeroProcesso.getClass(), numeroProcesso.getIdProcesso());
                possui.setNumeroProcesso(numeroProcesso);
            }
            Protocolo numeroProtocolo = possui.getNumeroProtocolo();
            if (numeroProtocolo != null) {
                numeroProtocolo = em.getReference(numeroProtocolo.getClass(), numeroProtocolo.getIdProtocolo());
                possui.setNumeroProtocolo(numeroProtocolo);
            }
            em.persist(possui);
            if (numeroNota != null) {
                numeroNota.getPossuiList().add(possui);
                numeroNota = em.merge(numeroNota);
            }
            if (numeroProcesso != null) {
                numeroProcesso.getPossuiList().add(possui);
                numeroProcesso = em.merge(numeroProcesso);
            }
            if (numeroProtocolo != null) {
                numeroProtocolo.getPossuiList().add(possui);
                numeroProtocolo = em.merge(numeroProtocolo);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Possui possui) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Possui persistentPossui = em.find(Possui.class, possui.getIdPossui());
            Notafiscal numeroNotaOld = persistentPossui.getNumeroNota();
            Notafiscal numeroNotaNew = possui.getNumeroNota();
            Processo numeroProcessoOld = persistentPossui.getNumeroProcesso();
            Processo numeroProcessoNew = possui.getNumeroProcesso();
            Protocolo numeroProtocoloOld = persistentPossui.getNumeroProtocolo();
            Protocolo numeroProtocoloNew = possui.getNumeroProtocolo();
            if (numeroNotaNew != null) {
                numeroNotaNew = em.getReference(numeroNotaNew.getClass(), numeroNotaNew.getIdNotaFiscal());
                possui.setNumeroNota(numeroNotaNew);
            }
            if (numeroProcessoNew != null) {
                numeroProcessoNew = em.getReference(numeroProcessoNew.getClass(), numeroProcessoNew.getIdProcesso());
                possui.setNumeroProcesso(numeroProcessoNew);
            }
            if (numeroProtocoloNew != null) {
                numeroProtocoloNew = em.getReference(numeroProtocoloNew.getClass(), numeroProtocoloNew.getIdProtocolo());
                possui.setNumeroProtocolo(numeroProtocoloNew);
            }
            possui = em.merge(possui);
            if (numeroNotaOld != null && !numeroNotaOld.equals(numeroNotaNew)) {
                numeroNotaOld.getPossuiList().remove(possui);
                numeroNotaOld = em.merge(numeroNotaOld);
            }
            if (numeroNotaNew != null && !numeroNotaNew.equals(numeroNotaOld)) {
                numeroNotaNew.getPossuiList().add(possui);
                numeroNotaNew = em.merge(numeroNotaNew);
            }
            if (numeroProcessoOld != null && !numeroProcessoOld.equals(numeroProcessoNew)) {
                numeroProcessoOld.getPossuiList().remove(possui);
                numeroProcessoOld = em.merge(numeroProcessoOld);
            }
            if (numeroProcessoNew != null && !numeroProcessoNew.equals(numeroProcessoOld)) {
                numeroProcessoNew.getPossuiList().add(possui);
                numeroProcessoNew = em.merge(numeroProcessoNew);
            }
            if (numeroProtocoloOld != null && !numeroProtocoloOld.equals(numeroProtocoloNew)) {
                numeroProtocoloOld.getPossuiList().remove(possui);
                numeroProtocoloOld = em.merge(numeroProtocoloOld);
            }
            if (numeroProtocoloNew != null && !numeroProtocoloNew.equals(numeroProtocoloOld)) {
                numeroProtocoloNew.getPossuiList().add(possui);
                numeroProtocoloNew = em.merge(numeroProtocoloNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = possui.getIdPossui();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Possui possui;
            try {
                possui = em.getReference(Possui.class, id);
                possui.getIdPossui();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The possui with id " + id + " no longer exists.", enfe);
            }
            Notafiscal numeroNota = possui.getNumeroNota();
            if (numeroNota != null) {
                numeroNota.getPossuiList().remove(possui);
                numeroNota = em.merge(numeroNota);
            }
            Processo numeroProcesso = possui.getNumeroProcesso();
            if (numeroProcesso != null) {
                numeroProcesso.getPossuiList().remove(possui);
                numeroProcesso = em.merge(numeroProcesso);
            }
            Protocolo numeroProtocolo = possui.getNumeroProtocolo();
            if (numeroProtocolo != null) {
                numeroProtocolo.getPossuiList().remove(possui);
                numeroProtocolo = em.merge(numeroProtocolo);
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

    public Possui findPossui(Integer id) {
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
