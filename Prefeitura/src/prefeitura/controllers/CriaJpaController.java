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
import prefeitura.entities.Cria;
import prefeitura.entities.Processo;
import prefeitura.entities.Usuario;

/**
 *
 * @author lucia
 */
public class CriaJpaController implements Serializable {

    public CriaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Cria cria) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processo numeroProcesso = cria.getNumeroProcesso();
            if (numeroProcesso != null) {
                numeroProcesso = em.getReference(numeroProcesso.getClass(), numeroProcesso.getIdProcesso());
                cria.setNumeroProcesso(numeroProcesso);
            }
            Usuario idUsuario = cria.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                cria.setIdUsuario(idUsuario);
            }
            em.persist(cria);
            if (numeroProcesso != null) {
                numeroProcesso.getCriaList().add(cria);
                numeroProcesso = em.merge(numeroProcesso);
            }
            if (idUsuario != null) {
                idUsuario.getCriaList().add(cria);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Cria cria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Cria persistentCria = em.find(Cria.class, cria.getIdCria());
            Processo numeroProcessoOld = persistentCria.getNumeroProcesso();
            Processo numeroProcessoNew = cria.getNumeroProcesso();
            Usuario idUsuarioOld = persistentCria.getIdUsuario();
            Usuario idUsuarioNew = cria.getIdUsuario();
            if (numeroProcessoNew != null) {
                numeroProcessoNew = em.getReference(numeroProcessoNew.getClass(), numeroProcessoNew.getIdProcesso());
                cria.setNumeroProcesso(numeroProcessoNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                cria.setIdUsuario(idUsuarioNew);
            }
            cria = em.merge(cria);
            if (numeroProcessoOld != null && !numeroProcessoOld.equals(numeroProcessoNew)) {
                numeroProcessoOld.getCriaList().remove(cria);
                numeroProcessoOld = em.merge(numeroProcessoOld);
            }
            if (numeroProcessoNew != null && !numeroProcessoNew.equals(numeroProcessoOld)) {
                numeroProcessoNew.getCriaList().add(cria);
                numeroProcessoNew = em.merge(numeroProcessoNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getCriaList().remove(cria);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getCriaList().add(cria);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = cria.getIdCria();
                if (findCria(id) == null) {
                    throw new NonexistentEntityException("The cria with id " + id + " no longer exists.");
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
            Cria cria;
            try {
                cria = em.getReference(Cria.class, id);
                cria.getIdCria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The cria with id " + id + " no longer exists.", enfe);
            }
            Processo numeroProcesso = cria.getNumeroProcesso();
            if (numeroProcesso != null) {
                numeroProcesso.getCriaList().remove(cria);
                numeroProcesso = em.merge(numeroProcesso);
            }
            Usuario idUsuario = cria.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getCriaList().remove(cria);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(cria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Cria> findCriaEntities() {
        return findCriaEntities(true, -1, -1);
    }

    public List<Cria> findCriaEntities(int maxResults, int firstResult) {
        return findCriaEntities(false, maxResults, firstResult);
    }

    private List<Cria> findCriaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Cria.class));
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

    public Cria findCria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Cria.class, id);
        } finally {
            em.close();
        }
    }

    public int getCriaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Cria> rt = cq.from(Cria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
