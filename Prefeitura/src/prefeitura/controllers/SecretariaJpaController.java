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
        if (secretaria.getOficioList1() == null) {
            secretaria.setOficioList1(new ArrayList<Oficio>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Oficio> attachedOficioList = new ArrayList<Oficio>();
            for (Oficio oficioListOficioToAttach : secretaria.getOficioList()) {
                oficioListOficioToAttach = em.getReference(oficioListOficioToAttach.getClass(), oficioListOficioToAttach.getNumeroOficio());
                attachedOficioList.add(oficioListOficioToAttach);
            }
            secretaria.setOficioList(attachedOficioList);
            List<Oficio> attachedOficioList1 = new ArrayList<Oficio>();
            for (Oficio oficioList1OficioToAttach : secretaria.getOficioList1()) {
                oficioList1OficioToAttach = em.getReference(oficioList1OficioToAttach.getClass(), oficioList1OficioToAttach.getNumeroOficio());
                attachedOficioList1.add(oficioList1OficioToAttach);
            }
            secretaria.setOficioList1(attachedOficioList1);
            em.persist(secretaria);
            for (Oficio oficioListOficio : secretaria.getOficioList()) {
                oficioListOficio.getSecretariaList().add(secretaria);
                oficioListOficio = em.merge(oficioListOficio);
            }
            for (Oficio oficioList1Oficio : secretaria.getOficioList1()) {
                Secretaria oldIdSecretariaOfOficioList1Oficio = oficioList1Oficio.getIdSecretaria();
                oficioList1Oficio.setIdSecretaria(secretaria);
                oficioList1Oficio = em.merge(oficioList1Oficio);
                if (oldIdSecretariaOfOficioList1Oficio != null) {
                    oldIdSecretariaOfOficioList1Oficio.getOficioList1().remove(oficioList1Oficio);
                    oldIdSecretariaOfOficioList1Oficio = em.merge(oldIdSecretariaOfOficioList1Oficio);
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
            List<Oficio> oficioList1Old = persistentSecretaria.getOficioList1();
            List<Oficio> oficioList1New = secretaria.getOficioList1();
            List<Oficio> attachedOficioListNew = new ArrayList<Oficio>();
            for (Oficio oficioListNewOficioToAttach : oficioListNew) {
                oficioListNewOficioToAttach = em.getReference(oficioListNewOficioToAttach.getClass(), oficioListNewOficioToAttach.getNumeroOficio());
                attachedOficioListNew.add(oficioListNewOficioToAttach);
            }
            oficioListNew = attachedOficioListNew;
            secretaria.setOficioList(oficioListNew);
            List<Oficio> attachedOficioList1New = new ArrayList<Oficio>();
            for (Oficio oficioList1NewOficioToAttach : oficioList1New) {
                oficioList1NewOficioToAttach = em.getReference(oficioList1NewOficioToAttach.getClass(), oficioList1NewOficioToAttach.getNumeroOficio());
                attachedOficioList1New.add(oficioList1NewOficioToAttach);
            }
            oficioList1New = attachedOficioList1New;
            secretaria.setOficioList1(oficioList1New);
            secretaria = em.merge(secretaria);
            for (Oficio oficioListOldOficio : oficioListOld) {
                if (!oficioListNew.contains(oficioListOldOficio)) {
                    oficioListOldOficio.getSecretariaList().remove(secretaria);
                    oficioListOldOficio = em.merge(oficioListOldOficio);
                }
            }
            for (Oficio oficioListNewOficio : oficioListNew) {
                if (!oficioListOld.contains(oficioListNewOficio)) {
                    oficioListNewOficio.getSecretariaList().add(secretaria);
                    oficioListNewOficio = em.merge(oficioListNewOficio);
                }
            }
            for (Oficio oficioList1OldOficio : oficioList1Old) {
                if (!oficioList1New.contains(oficioList1OldOficio)) {
                    oficioList1OldOficio.setIdSecretaria(null);
                    oficioList1OldOficio = em.merge(oficioList1OldOficio);
                }
            }
            for (Oficio oficioList1NewOficio : oficioList1New) {
                if (!oficioList1Old.contains(oficioList1NewOficio)) {
                    Secretaria oldIdSecretariaOfOficioList1NewOficio = oficioList1NewOficio.getIdSecretaria();
                    oficioList1NewOficio.setIdSecretaria(secretaria);
                    oficioList1NewOficio = em.merge(oficioList1NewOficio);
                    if (oldIdSecretariaOfOficioList1NewOficio != null && !oldIdSecretariaOfOficioList1NewOficio.equals(secretaria)) {
                        oldIdSecretariaOfOficioList1NewOficio.getOficioList1().remove(oficioList1NewOficio);
                        oldIdSecretariaOfOficioList1NewOficio = em.merge(oldIdSecretariaOfOficioList1NewOficio);
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
                oficioListOficio.getSecretariaList().remove(secretaria);
                oficioListOficio = em.merge(oficioListOficio);
            }
            List<Oficio> oficioList1 = secretaria.getOficioList1();
            for (Oficio oficioList1Oficio : oficioList1) {
                oficioList1Oficio.setIdSecretaria(null);
                oficioList1Oficio = em.merge(oficioList1Oficio);
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
