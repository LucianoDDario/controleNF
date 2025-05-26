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
import prefeitura.entities.Envia1;
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
        if (secretaria.getEnvia1List() == null) {
            secretaria.setEnvia1List(new ArrayList<Envia1>());
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
            List<Envia1> attachedEnvia1List = new ArrayList<Envia1>();
            for (Envia1 envia1ListEnvia1ToAttach : secretaria.getEnvia1List()) {
                envia1ListEnvia1ToAttach = em.getReference(envia1ListEnvia1ToAttach.getClass(), envia1ListEnvia1ToAttach.getIdEnvia1());
                attachedEnvia1List.add(envia1ListEnvia1ToAttach);
            }
            secretaria.setEnvia1List(attachedEnvia1List);
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
            for (Envia1 envia1ListEnvia1 : secretaria.getEnvia1List()) {
                Secretaria oldIdSecretariaOfEnvia1ListEnvia1 = envia1ListEnvia1.getIdSecretaria();
                envia1ListEnvia1.setIdSecretaria(secretaria);
                envia1ListEnvia1 = em.merge(envia1ListEnvia1);
                if (oldIdSecretariaOfEnvia1ListEnvia1 != null) {
                    oldIdSecretariaOfEnvia1ListEnvia1.getEnvia1List().remove(envia1ListEnvia1);
                    oldIdSecretariaOfEnvia1ListEnvia1 = em.merge(oldIdSecretariaOfEnvia1ListEnvia1);
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
            List<Envia1> envia1ListOld = persistentSecretaria.getEnvia1List();
            List<Envia1> envia1ListNew = secretaria.getEnvia1List();
            List<Oficio> attachedOficioListNew = new ArrayList<Oficio>();
            for (Oficio oficioListNewOficioToAttach : oficioListNew) {
                oficioListNewOficioToAttach = em.getReference(oficioListNewOficioToAttach.getClass(), oficioListNewOficioToAttach.getIdOficio());
                attachedOficioListNew.add(oficioListNewOficioToAttach);
            }
            oficioListNew = attachedOficioListNew;
            secretaria.setOficioList(oficioListNew);
            List<Envia1> attachedEnvia1ListNew = new ArrayList<Envia1>();
            for (Envia1 envia1ListNewEnvia1ToAttach : envia1ListNew) {
                envia1ListNewEnvia1ToAttach = em.getReference(envia1ListNewEnvia1ToAttach.getClass(), envia1ListNewEnvia1ToAttach.getIdEnvia1());
                attachedEnvia1ListNew.add(envia1ListNewEnvia1ToAttach);
            }
            envia1ListNew = attachedEnvia1ListNew;
            secretaria.setEnvia1List(envia1ListNew);
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
            for (Envia1 envia1ListOldEnvia1 : envia1ListOld) {
                if (!envia1ListNew.contains(envia1ListOldEnvia1)) {
                    envia1ListOldEnvia1.setIdSecretaria(null);
                    envia1ListOldEnvia1 = em.merge(envia1ListOldEnvia1);
                }
            }
            for (Envia1 envia1ListNewEnvia1 : envia1ListNew) {
                if (!envia1ListOld.contains(envia1ListNewEnvia1)) {
                    Secretaria oldIdSecretariaOfEnvia1ListNewEnvia1 = envia1ListNewEnvia1.getIdSecretaria();
                    envia1ListNewEnvia1.setIdSecretaria(secretaria);
                    envia1ListNewEnvia1 = em.merge(envia1ListNewEnvia1);
                    if (oldIdSecretariaOfEnvia1ListNewEnvia1 != null && !oldIdSecretariaOfEnvia1ListNewEnvia1.equals(secretaria)) {
                        oldIdSecretariaOfEnvia1ListNewEnvia1.getEnvia1List().remove(envia1ListNewEnvia1);
                        oldIdSecretariaOfEnvia1ListNewEnvia1 = em.merge(oldIdSecretariaOfEnvia1ListNewEnvia1);
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
            List<Envia1> envia1List = secretaria.getEnvia1List();
            for (Envia1 envia1ListEnvia1 : envia1List) {
                envia1ListEnvia1.setIdSecretaria(null);
                envia1ListEnvia1 = em.merge(envia1ListEnvia1);
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
