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
import prefeitura.entities.Oficio;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Secretaria;

/**
 *
 * @author lucia
 */
public class SecretariaJpaController implements Serializable {

    public SecretariaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Secretaria secretaria) {
        if (secretaria.getOficioList() == null) {
            secretaria.setOficioList(new ArrayList<Oficio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Oficio> attachedOficioList = new ArrayList<Oficio>();
            for (Oficio oficioListOficioToAttach : secretaria.getOficioList()) {
                oficioListOficioToAttach = em.getReference(oficioListOficioToAttach.getClass(), oficioListOficioToAttach.getIdOficio());
                attachedOficioList.add(oficioListOficioToAttach);
            }
            secretaria.setOficioList(attachedOficioList);
            em.persist(secretaria);
            for (Oficio oficioListOficio : secretaria.getOficioList()) {
                Secretaria oldIdSecretariaOfOficioListOficio = oficioListOficio.getIdSecretaria();
                oficioListOficio.setIdSecretaria(secretaria);
                oficioListOficio = em.merge(oficioListOficio);
                if (oldIdSecretariaOfOficioListOficio != null) {
                    oldIdSecretariaOfOficioListOficio.getOficioList().remove(oficioListOficio);
                    oldIdSecretariaOfOficioListOficio = em.merge(oldIdSecretariaOfOficioListOficio);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Secretaria secretaria) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Secretaria persistentSecretaria = em.find(Secretaria.class, secretaria.getIdSecretaria());
            List<Oficio> oficioListOld = persistentSecretaria.getOficioList();
            List<Oficio> oficioListNew = secretaria.getOficioList();
            List<Oficio> attachedOficioListNew = new ArrayList<Oficio>();
            for (Oficio oficioListNewOficioToAttach : oficioListNew) {
                oficioListNewOficioToAttach = em.getReference(oficioListNewOficioToAttach.getClass(), oficioListNewOficioToAttach.getIdOficio());
                attachedOficioListNew.add(oficioListNewOficioToAttach);
            }
            oficioListNew = attachedOficioListNew;
            secretaria.setOficioList(oficioListNew);
            secretaria = em.merge(secretaria);
            for (Oficio oficioListOldOficio : oficioListOld) {
                if (!oficioListNew.contains(oficioListOldOficio)) {
                    oficioListOldOficio.setIdSecretaria(null);
                    oficioListOldOficio = em.merge(oficioListOldOficio);
                }
            }
            for (Oficio oficioListNewOficio : oficioListNew) {
                if (!oficioListOld.contains(oficioListNewOficio)) {
                    Secretaria oldIdSecretariaOfOficioListNewOficio = oficioListNewOficio.getIdSecretaria();
                    oficioListNewOficio.setIdSecretaria(secretaria);
                    oficioListNewOficio = em.merge(oficioListNewOficio);
                    if (oldIdSecretariaOfOficioListNewOficio != null && !oldIdSecretariaOfOficioListNewOficio.equals(secretaria)) {
                        oldIdSecretariaOfOficioListNewOficio.getOficioList().remove(oficioListNewOficio);
                        oldIdSecretariaOfOficioListNewOficio = em.merge(oldIdSecretariaOfOficioListNewOficio);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = secretaria.getIdSecretaria();
                if (findSecretaria(id) == null) {
                    throw new NonexistentEntityException("The secretaria with id " + id + " no longer exists.");
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
            Secretaria secretaria;
            try {
                secretaria = em.getReference(Secretaria.class, id);
                secretaria.getIdSecretaria();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The secretaria with id " + id + " no longer exists.", enfe);
            }
            List<Oficio> oficioList = secretaria.getOficioList();
            for (Oficio oficioListOficio : oficioList) {
                oficioListOficio.setIdSecretaria(null);
                oficioListOficio = em.merge(oficioListOficio);
            }
            em.remove(secretaria);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Secretaria> findSecretariaEntities() {
        return findSecretariaEntities(true, -1, -1);
    }

    public List<Secretaria> findSecretariaEntities(int maxResults, int firstResult) {
        return findSecretariaEntities(false, maxResults, firstResult);
    }

    private List<Secretaria> findSecretariaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Secretaria.class));
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

    public Secretaria findSecretaria(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Secretaria.class, id);
        } finally {
            em.close();
        }
    }

    public int getSecretariaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Secretaria> rt = cq.from(Secretaria.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
