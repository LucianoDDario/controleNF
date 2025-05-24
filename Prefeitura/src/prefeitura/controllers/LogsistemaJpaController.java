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
import prefeitura.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Logsistema;

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
        if (logsistema.getUsuarioList() == null) {
            logsistema.setUsuarioList(new ArrayList<Usuario>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario idUsuario = logsistema.getIdUsuario();
            if (idUsuario != null) {
                idUsuario = em.getReference(idUsuario.getClass(), idUsuario.getIdUsuario());
                logsistema.setIdUsuario(idUsuario);
            }
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : logsistema.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getIdUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            logsistema.setUsuarioList(attachedUsuarioList);
            em.persist(logsistema);
            if (idUsuario != null) {
                idUsuario.getLogsistemaList().add(logsistema);
                idUsuario = em.merge(idUsuario);
            }
            for (Usuario usuarioListUsuario : logsistema.getUsuarioList()) {
                usuarioListUsuario.getLogsistemaList().add(logsistema);
                usuarioListUsuario = em.merge(usuarioListUsuario);
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
            List<Usuario> usuarioListOld = persistentLogsistema.getUsuarioList();
            List<Usuario> usuarioListNew = logsistema.getUsuarioList();
            if (idUsuarioNew != null) {
                idUsuarioNew = em.getReference(idUsuarioNew.getClass(), idUsuarioNew.getIdUsuario());
                logsistema.setIdUsuario(idUsuarioNew);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            logsistema.setUsuarioList(usuarioListNew);
            logsistema = em.merge(logsistema);
            if (idUsuarioOld != null && !idUsuarioOld.equals(idUsuarioNew)) {
                idUsuarioOld.getLogsistemaList().remove(logsistema);
                idUsuarioOld = em.merge(idUsuarioOld);
            }
            if (idUsuarioNew != null && !idUsuarioNew.equals(idUsuarioOld)) {
                idUsuarioNew.getLogsistemaList().add(logsistema);
                idUsuarioNew = em.merge(idUsuarioNew);
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.getLogsistemaList().remove(logsistema);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    usuarioListNewUsuario.getLogsistemaList().add(logsistema);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                }
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
            List<Usuario> usuarioList = logsistema.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.getLogsistemaList().remove(logsistema);
                usuarioListUsuario = em.merge(usuarioListUsuario);
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
