/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.controllers;

import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import prefeitura.entities.Notafiscal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Protocolo;

/**
 *
 * @author lucia
 */
public class ProtocoloJpaController implements Serializable {

    public ProtocoloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Protocolo protocolo) {
        if (protocolo.getNotafiscalList() == null) {
            protocolo.setNotafiscalList(new ArrayList<Notafiscal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notafiscal numeroNota = protocolo.getNumeroNota();
            if (numeroNota != null) {
                numeroNota = em.getReference(numeroNota.getClass(), numeroNota.getIdNotaFiscal());
                protocolo.setNumeroNota(numeroNota);
            }
            List<Notafiscal> attachedNotafiscalList = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNotafiscalToAttach : protocolo.getNotafiscalList()) {
                notafiscalListNotafiscalToAttach = em.getReference(notafiscalListNotafiscalToAttach.getClass(), notafiscalListNotafiscalToAttach.getIdNotaFiscal());
                attachedNotafiscalList.add(notafiscalListNotafiscalToAttach);
            }
            protocolo.setNotafiscalList(attachedNotafiscalList);
            em.persist(protocolo);
            if (numeroNota != null) {
                numeroNota.getProtocoloList().add(protocolo);
                numeroNota = em.merge(numeroNota);
            }
            for (Notafiscal notafiscalListNotafiscal : protocolo.getNotafiscalList()) {
                Protocolo oldIdProtocoloOfNotafiscalListNotafiscal = notafiscalListNotafiscal.getIdProtocolo();
                notafiscalListNotafiscal.setIdProtocolo(protocolo);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
                if (oldIdProtocoloOfNotafiscalListNotafiscal != null) {
                    oldIdProtocoloOfNotafiscalListNotafiscal.getNotafiscalList().remove(notafiscalListNotafiscal);
                    oldIdProtocoloOfNotafiscalListNotafiscal = em.merge(oldIdProtocoloOfNotafiscalListNotafiscal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Protocolo protocolo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Protocolo persistentProtocolo = em.find(Protocolo.class, protocolo.getIdProtocolo());
            Notafiscal numeroNotaOld = persistentProtocolo.getNumeroNota();
            Notafiscal numeroNotaNew = protocolo.getNumeroNota();
            List<Notafiscal> notafiscalListOld = persistentProtocolo.getNotafiscalList();
            List<Notafiscal> notafiscalListNew = protocolo.getNotafiscalList();
            if (numeroNotaNew != null) {
                numeroNotaNew = em.getReference(numeroNotaNew.getClass(), numeroNotaNew.getIdNotaFiscal());
                protocolo.setNumeroNota(numeroNotaNew);
            }
            List<Notafiscal> attachedNotafiscalListNew = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNewNotafiscalToAttach : notafiscalListNew) {
                notafiscalListNewNotafiscalToAttach = em.getReference(notafiscalListNewNotafiscalToAttach.getClass(), notafiscalListNewNotafiscalToAttach.getIdNotaFiscal());
                attachedNotafiscalListNew.add(notafiscalListNewNotafiscalToAttach);
            }
            notafiscalListNew = attachedNotafiscalListNew;
            protocolo.setNotafiscalList(notafiscalListNew);
            protocolo = em.merge(protocolo);
            if (numeroNotaOld != null && !numeroNotaOld.equals(numeroNotaNew)) {
                numeroNotaOld.getProtocoloList().remove(protocolo);
                numeroNotaOld = em.merge(numeroNotaOld);
            }
            if (numeroNotaNew != null && !numeroNotaNew.equals(numeroNotaOld)) {
                numeroNotaNew.getProtocoloList().add(protocolo);
                numeroNotaNew = em.merge(numeroNotaNew);
            }
            for (Notafiscal notafiscalListOldNotafiscal : notafiscalListOld) {
                if (!notafiscalListNew.contains(notafiscalListOldNotafiscal)) {
                    notafiscalListOldNotafiscal.setIdProtocolo(null);
                    notafiscalListOldNotafiscal = em.merge(notafiscalListOldNotafiscal);
                }
            }
            for (Notafiscal notafiscalListNewNotafiscal : notafiscalListNew) {
                if (!notafiscalListOld.contains(notafiscalListNewNotafiscal)) {
                    Protocolo oldIdProtocoloOfNotafiscalListNewNotafiscal = notafiscalListNewNotafiscal.getIdProtocolo();
                    notafiscalListNewNotafiscal.setIdProtocolo(protocolo);
                    notafiscalListNewNotafiscal = em.merge(notafiscalListNewNotafiscal);
                    if (oldIdProtocoloOfNotafiscalListNewNotafiscal != null && !oldIdProtocoloOfNotafiscalListNewNotafiscal.equals(protocolo)) {
                        oldIdProtocoloOfNotafiscalListNewNotafiscal.getNotafiscalList().remove(notafiscalListNewNotafiscal);
                        oldIdProtocoloOfNotafiscalListNewNotafiscal = em.merge(oldIdProtocoloOfNotafiscalListNewNotafiscal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = protocolo.getIdProtocolo();
                if (findProtocolo(id) == null) {
                    throw new NonexistentEntityException("The protocolo with id " + id + " no longer exists.");
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
            Protocolo protocolo;
            try {
                protocolo = em.getReference(Protocolo.class, id);
                protocolo.getIdProtocolo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The protocolo with id " + id + " no longer exists.", enfe);
            }
            Notafiscal numeroNota = protocolo.getNumeroNota();
            if (numeroNota != null) {
                numeroNota.getProtocoloList().remove(protocolo);
                numeroNota = em.merge(numeroNota);
            }
            List<Notafiscal> notafiscalList = protocolo.getNotafiscalList();
            for (Notafiscal notafiscalListNotafiscal : notafiscalList) {
                notafiscalListNotafiscal.setIdProtocolo(null);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
            }
            em.remove(protocolo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Protocolo> findProtocoloEntities() {
        return findProtocoloEntities(true, -1, -1);
    }

    public List<Protocolo> findProtocoloEntities(int maxResults, int firstResult) {
        return findProtocoloEntities(false, maxResults, firstResult);
    }

    private List<Protocolo> findProtocoloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Protocolo.class));
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

    public Protocolo findProtocolo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Protocolo.class, id);
        } finally {
            em.close();
        }
    }

    public int getProtocoloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Protocolo> rt = cq.from(Protocolo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
