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
import prefeitura.entities.Logsistema;
import prefeitura.entities.Usuario;

/**
 *
 * @author lucia
 */
public class LogsistemaJpaController implements Serializable {

    public LogsistemaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Logsistema logsistema) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = logsistema.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                logsistema.setIdUsuario(idUsuario);
            }
            em.persist(logsistema);
            if (idUsuario != null) {
                idUsuario.getLogsistemaList().add(logsistema);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Logsistema logsistema) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Logsistema persistentLogsistema = em.find(Logsistema.class, logsistema.getIdLog());
            Usuario idUsuarioOld = persistentLogsistema.getIdUsuario();
            Usuario idUsuarioNew = logsistema.getIdUsuario();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                logsistema.setIdUsuario(idUsuarioNew);
            }
            logsistema = em.merge(logsistema);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getLogsistemaList().remove(logsistema);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getLogsistemaList().add(logsistema);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = logsistema.getIdLog();
                if (findLogsistema(id) == null) {
                    throw new NonexistentEntityException("The logsistema with id " + id + " no longer exists.");
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
            Logsistema logsistema;
            try {
                logsistema = em.getReference(Logsistema.class, id);
                logsistema.getIdLog();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The logsistema with id " + id + " no longer exists.", enfe);
            }
            Usuario idUsuario = logsistema.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getLogsistemaList().remove(logsistema);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(logsistema);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Logsistema> findLogsistemaEntities() {
        return findLogsistemaEntities(true, -1, -1);
    }

    public List<Logsistema> findLogsistemaEntities(int maxResults, int firstResult) {
        return findLogsistemaEntities(false, maxResults, firstResult);
    }

    private List<Logsistema> findLogsistemaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Logsistema.class));
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

    public Logsistema findLogsistema(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Logsistema.class, id);
        } finally {
            em.close();
        }
    }

    public int getLogsistemaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Logsistema> rt = cq.from(Logsistema.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
