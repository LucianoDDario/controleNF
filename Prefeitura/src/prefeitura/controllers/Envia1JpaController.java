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
import prefeitura.entities.Envia1;
import prefeitura.entities.Oficio;
import prefeitura.entities.Secretaria;

/**
 *
 * @author lucia
 */
public class Envia1JpaController implements Serializable {

    public Envia1JpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Envia1 envia1) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oficio numeroOficio = envia1.getNumeroOficio();
            if (numeroOficio != null) {
                numeroOficio = em.getReference(numeroOficio.getClass(), numeroOficio.getIdOficio());
                envia1.setNumeroOficio(numeroOficio);
            }
            Secretaria idSecretaria = envia1.getIdSecretaria();
            if (idSecretaria != null) {
                idSecretaria = em.getReference(idSecretaria.getClass(), idSecretaria.getIdSecretaria());
                envia1.setIdSecretaria(idSecretaria);
            }
            em.persist(envia1);
            if (numeroOficio != null) {
                numeroOficio.getEnvia1List().add(envia1);
                numeroOficio = em.merge(numeroOficio);
            }
            if (idSecretaria != null) {
                idSecretaria.getEnvia1List().add(envia1);
                idSecretaria = em.merge(idSecretaria);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Envia1 envia1) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Envia1 persistentEnvia1 = em.find(Envia1.class, envia1.getIdEnvia1());
            Oficio numeroOficioOld = persistentEnvia1.getNumeroOficio();
            Oficio numeroOficioNew = envia1.getNumeroOficio();
            Secretaria idSecretariaOld = persistentEnvia1.getIdSecretaria();
            Secretaria idSecretariaNew = envia1.getIdSecretaria();
            if (numeroOficioNew != null) {
                numeroOficioNew = em.getReference(numeroOficioNew.getClass(), numeroOficioNew.getIdOficio());
                envia1.setNumeroOficio(numeroOficioNew);
            }
            if (idSecretariaNew != null) {
                idSecretariaNew = em.getReference(idSecretariaNew.getClass(), idSecretariaNew.getIdSecretaria());
                envia1.setIdSecretaria(idSecretariaNew);
            }
            envia1 = em.merge(envia1);
            if (numeroOficioOld != null && !numeroOficioOld.equals(numeroOficioNew)) {
                numeroOficioOld.getEnvia1List().remove(envia1);
                numeroOficioOld = em.merge(numeroOficioOld);
            }
            if (numeroOficioNew != null && !numeroOficioNew.equals(numeroOficioOld)) {
                numeroOficioNew.getEnvia1List().add(envia1);
                numeroOficioNew = em.merge(numeroOficioNew);
            }
            if (idSecretariaOld != null && !idSecretariaOld.equals(idSecretariaNew)) {
                idSecretariaOld.getEnvia1List().remove(envia1);
                idSecretariaOld = em.merge(idSecretariaOld);
            }
            if (idSecretariaNew != null && !idSecretariaNew.equals(idSecretariaOld)) {
                idSecretariaNew.getEnvia1List().add(envia1);
                idSecretariaNew = em.merge(idSecretariaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = envia1.getIdEnvia1();
                if (findEnvia1(id) == null) {
                    throw new NonexistentEntityException("The envia1 with id " + id + " no longer exists.");
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
            Envia1 envia1;
            try {
                envia1 = em.getReference(Envia1.class, id);
                envia1.getIdEnvia1();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The envia1 with id " + id + " no longer exists.", enfe);
            }
            Oficio numeroOficio = envia1.getNumeroOficio();
            if (numeroOficio != null) {
                numeroOficio.getEnvia1List().remove(envia1);
                numeroOficio = em.merge(numeroOficio);
            }
            Secretaria idSecretaria = envia1.getIdSecretaria();
            if (idSecretaria != null) {
                idSecretaria.getEnvia1List().remove(envia1);
                idSecretaria = em.merge(idSecretaria);
            }
            em.remove(envia1);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Envia1> findEnvia1Entities() {
        return findEnvia1Entities(true, -1, -1);
    }

    public List<Envia1> findEnvia1Entities(int maxResults, int firstResult) {
        return findEnvia1Entities(false, maxResults, firstResult);
    }

    private List<Envia1> findEnvia1Entities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Envia1.class));
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

    public Envia1 findEnvia1(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Envia1.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnvia1Count() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Envia1> rt = cq.from(Envia1.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
