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
import prefeitura.entities.Monitora;
import prefeitura.entities.Usuario;

/**
 *
 * @author lucia
 */
public class MonitoraJpaController implements Serializable {

    public MonitoraJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Monitora monitora) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Logsistema idLog = monitora.getIdLog();
            if (idLog != null) {
                idLog = em.getReference(idLog.getClass(), idLog.getIdLog());
                monitora.setIdLog(idLog);
            }
            Usuario idUsuario = monitora.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                monitora.setIdUsuario(idUsuario);
            }
            em.persist(monitora);
            if (idLog != null) {
                idLog.getMonitoraList().add(monitora);
                idLog = em.merge(idLog);
            }
            if (idUsuario != null) {
                idUsuario.getMonitoraList().add(monitora);
                idUsuario = em.merge(idUsuario);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Monitora monitora) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Monitora persistentMonitora = em.find(Monitora.class, monitora.getIdMonitora());
            Logsistema idLogOld = persistentMonitora.getIdLog();
            Logsistema idLogNew = monitora.getIdLog();
            Usuario idUsuarioOld = persistentMonitora.getIdUsuario();
            Usuario idUsuarioNew = monitora.getIdUsuario();
            if (idLogNew != null) {
                idLogNew = em.getReference(idLogNew.getClass(), idLogNew.getIdLog());
                monitora.setIdLog(idLogNew);
            }
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                monitora.setIdUsuario(idUsuarioNew);
            }
            monitora = em.merge(monitora);
            if (idLogOld != null && !idLogOld.equals(idLogNew)) {
                idLogOld.getMonitoraList().remove(monitora);
                idLogOld = em.merge(idLogOld);
            }
            if (idLogNew != null && !idLogNew.equals(idLogOld)) {
                idLogNew.getMonitoraList().add(monitora);
                idLogNew = em.merge(idLogNew);
            }
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getMonitoraList().remove(monitora);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getMonitoraList().add(monitora);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = monitora.getIdMonitora();
                if (findMonitora(id) == null) {
                    throw new NonexistentEntityException("The monitora with id " + id + " no longer exists.");
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
            Monitora monitora;
            try {
                monitora = em.getReference(Monitora.class, id);
                monitora.getIdMonitora();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The monitora with id " + id + " no longer exists.", enfe);
            }
            Logsistema idLog = monitora.getIdLog();
            if (idLog != null) {
                idLog.getMonitoraList().remove(monitora);
                idLog = em.merge(idLog);
            }
            Usuario idUsuario = monitora.getIdUsuario();
            if (idUsuario != null) {
                idUsuario.getMonitoraList().remove(monitora);
                idUsuario = em.merge(idUsuario);
            }
            em.remove(monitora);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Monitora> findMonitoraEntities() {
        return findMonitoraEntities(true, -1, -1);
    }

    public List<Monitora> findMonitoraEntities(int maxResults, int firstResult) {
        return findMonitoraEntities(false, maxResults, firstResult);
    }

    private List<Monitora> findMonitoraEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Monitora.class));
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

    public Monitora findMonitora(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Monitora.class, id);
        } finally {
            em.close();
        }
    }

    public int getMonitoraCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Monitora> rt = cq.from(Monitora.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
