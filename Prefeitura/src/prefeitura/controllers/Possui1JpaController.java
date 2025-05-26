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
import prefeitura.entities.Oficio;
import prefeitura.entities.Possui1;
import prefeitura.entities.Processo;

/**
 *
 * @author lucia
 */
public class Possui1JpaController implements Serializable {

    public Possui1JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Possui1 possui1) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oficio numeroOficio = possui1.getNumeroOficio();
            if (numeroOficio != null) {
                numeroOficio = em.getReference(numeroOficio.getClass(), numeroOficio.getIdOficio());
                possui1.setNumeroOficio(numeroOficio);
            }
            Processo numeroProcesso = possui1.getNumeroProcesso();
            if (numeroProcesso != null) {
                numeroProcesso = em.getReference(numeroProcesso.getClass(), numeroProcesso.getIdProcesso());
                possui1.setNumeroProcesso(numeroProcesso);
            }
            em.persist(possui1);
            if (numeroOficio != null) {
                numeroOficio.getPossui1List().add(possui1);
                numeroOficio = em.merge(numeroOficio);
            }
            if (numeroProcesso != null) {
                numeroProcesso.getPossui1List().add(possui1);
                numeroProcesso = em.merge(numeroProcesso);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Possui1 possui1) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Possui1 persistentPossui1 = em.find(Possui1.class, possui1.getIdPossui1());
            Oficio numeroOficioOld = persistentPossui1.getNumeroOficio();
            Oficio numeroOficioNew = possui1.getNumeroOficio();
            Processo numeroProcessoOld = persistentPossui1.getNumeroProcesso();
            Processo numeroProcessoNew = possui1.getNumeroProcesso();
            if (numeroOficioNew != null) {
                numeroOficioNew = em.getReference(numeroOficioNew.getClass(), numeroOficioNew.getIdOficio());
                possui1.setNumeroOficio(numeroOficioNew);
            }
            if (numeroProcessoNew != null) {
                numeroProcessoNew = em.getReference(numeroProcessoNew.getClass(), numeroProcessoNew.getIdProcesso());
                possui1.setNumeroProcesso(numeroProcessoNew);
            }
            possui1 = em.merge(possui1);
            if (numeroOficioOld != null && !numeroOficioOld.equals(numeroOficioNew)) {
                numeroOficioOld.getPossui1List().remove(possui1);
                numeroOficioOld = em.merge(numeroOficioOld);
            }
            if (numeroOficioNew != null && !numeroOficioNew.equals(numeroOficioOld)) {
                numeroOficioNew.getPossui1List().add(possui1);
                numeroOficioNew = em.merge(numeroOficioNew);
            }
            if (numeroProcessoOld != null && !numeroProcessoOld.equals(numeroProcessoNew)) {
                numeroProcessoOld.getPossui1List().remove(possui1);
                numeroProcessoOld = em.merge(numeroProcessoOld);
            }
            if (numeroProcessoNew != null && !numeroProcessoNew.equals(numeroProcessoOld)) {
                numeroProcessoNew.getPossui1List().add(possui1);
                numeroProcessoNew = em.merge(numeroProcessoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = possui1.getIdPossui1();
                if (findPossui1(id) == null) {
                    throw new NonexistentEntityException("The possui1 with id " + id + " no longer exists.");
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
            Possui1 possui1;
            try {
                possui1 = em.getReference(Possui1.class, id);
                possui1.getIdPossui1();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The possui1 with id " + id + " no longer exists.", enfe);
            }
            Oficio numeroOficio = possui1.getNumeroOficio();
            if (numeroOficio != null) {
                numeroOficio.getPossui1List().remove(possui1);
                numeroOficio = em.merge(numeroOficio);
            }
            Processo numeroProcesso = possui1.getNumeroProcesso();
            if (numeroProcesso != null) {
                numeroProcesso.getPossui1List().remove(possui1);
                numeroProcesso = em.merge(numeroProcesso);
            }
            em.remove(possui1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Possui1> findPossui1Entities() {
        return findPossui1Entities(true, -1, -1);
    }

    public List<Possui1> findPossui1Entities(int maxResults, int firstResult) {
        return findPossui1Entities(false, maxResults, firstResult);
    }

    private List<Possui1> findPossui1Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Possui1.class));
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

    public Possui1 findPossui1(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Possui1.class, id);
        } finally {
            em.close();
        }
    }

    public int getPossui1Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Possui1> rt = cq.from(Possui1.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
