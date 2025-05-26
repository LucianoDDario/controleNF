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
import prefeitura.entities.Recebe;
import prefeitura.entities.Usuario;

/**
 *
 * @author lucia
 */
public class RecebeJpaController implements Serializable {

    public RecebeJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recebe recebe) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oficio numeroOficio = recebe.getNumeroOficio();
            if (numeroOficio != null) {
                numeroOficio = em.getReference(numeroOficio.getClass(), numeroOficio.getIdOficio());
                recebe.setNumeroOficio(numeroOficio);
            }
            Usuario idUsuario = recebe.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                recebe.setIdUsuario(idUsuario);
            }
            em.persist(recebe);
            if (numeroOficio != null) {
                numeroOficio.getRecebeList().add(recebe);
                numeroOficio = em.merge(numeroOficio);
            }
            if (idUsuario != null) {
                idUsuario.getRecebeList().add(recebe);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Recebe recebe) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Recebe persistentRecebe = em.find(Recebe.class, recebe.getIdRecebe());
            Oficio numeroOficioOld = persistentRecebe.getNumeroOficio();
            Oficio numeroOficioNew = recebe.getNumeroOficio();
            Usuario idUsuarioOld = persistentRecebe.getIdUsuario();
            Usuario idUsuarioNew = recebe.getIdUsuario();
            if (numeroOficioNew != null) {
                numeroOficioNew = em.getReference(numeroOficioNew.getClass(), numeroOficioNew.getIdOficio());
                recebe.setNumeroOficio(numeroOficioNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                recebe.setIdUsuario(idUsuarioNew);
            }
            recebe = em.merge(recebe);
            if (numeroOficioOld != null && !numeroOficioOld.equals(numeroOficioNew)) {
                numeroOficioOld.getRecebeList().remove(recebe);
                numeroOficioOld = em.merge(numeroOficioOld);
            }
            if (numeroOficioNew != null && !numeroOficioNew.equals(numeroOficioOld)) {
                numeroOficioNew.getRecebeList().add(recebe);
                numeroOficioNew = em.merge(numeroOficioNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getRecebeList().remove(recebe);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getRecebeList().add(recebe);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = recebe.getIdRecebe();
                if (findRecebe(id) == null) {
                    throw new NonexistentEntityException("The recebe with id " + id + " no longer exists.");
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
            Recebe recebe;
            try {
                recebe = em.getReference(Recebe.class, id);
                recebe.getIdRecebe();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recebe with id " + id + " no longer exists.", enfe);
            }
            Oficio numeroOficio = recebe.getNumeroOficio();
            if (numeroOficio != null) {
                numeroOficio.getRecebeList().remove(recebe);
                numeroOficio = em.merge(numeroOficio);
            }
            Usuario idUsuario = recebe.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getRecebeList().remove(recebe);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(recebe);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Recebe> findRecebeEntities() {
        return findRecebeEntities(true, -1, -1);
    }

    public List<Recebe> findRecebeEntities(int maxResults, int firstResult) {
        return findRecebeEntities(false, maxResults, firstResult);
    }

    private List<Recebe> findRecebeEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Recebe.class));
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

    public Recebe findRecebe(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recebe.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecebeCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Recebe> rt = cq.from(Recebe.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
