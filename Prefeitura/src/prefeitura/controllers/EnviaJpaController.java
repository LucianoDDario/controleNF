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
import prefeitura.entities.Envia;
import prefeitura.entities.Fornecedor;
import prefeitura.entities.Notafiscal;

/**
 *
 * @author lucia
 */
public class EnviaJpaController implements Serializable {

    public EnviaJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Envia envia) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedor idFornecedor = envia.getIdFornecedor();
            if (idFornecedor != null) {
                idFornecedor = em.getReference(idFornecedor.getClass(), idFornecedor.getIdFornecedor());
                envia.setIdFornecedor(idFornecedor);
            }
            Notafiscal numeroNota = envia.getNumeroNota();
            if (numeroNota != null) {
                numeroNota = em.getReference(numeroNota.getClass(), numeroNota.getIdNotaFiscal());
                envia.setNumeroNota(numeroNota);
            }
            em.persist(envia);
            if (idFornecedor != null) {
                idFornecedor.getEnviaList().add(envia);
                idFornecedor = em.merge(idFornecedor);
            }
            if (numeroNota != null) {
                numeroNota.getEnviaList().add(envia);
                numeroNota = em.merge(numeroNota);
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Envia envia) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Envia persistentEnvia = em.find(Envia.class, envia.getIdEnvia());
            Fornecedor idFornecedorOld = persistentEnvia.getIdFornecedor();
            Fornecedor idFornecedorNew = envia.getIdFornecedor();
            Notafiscal numeroNotaOld = persistentEnvia.getNumeroNota();
            Notafiscal numeroNotaNew = envia.getNumeroNota();
            if (idFornecedorNew != null) {
                idFornecedorNew = em.getReference(idFornecedorNew.getClass(), idFornecedorNew.getIdFornecedor());
                envia.setIdFornecedor(idFornecedorNew);
            }
            if (numeroNotaNew != null) {
                numeroNotaNew = em.getReference(numeroNotaNew.getClass(), numeroNotaNew.getIdNotaFiscal());
                envia.setNumeroNota(numeroNotaNew);
            }
            envia = em.merge(envia);
            if (idFornecedorOld != null && !idFornecedorOld.equals(idFornecedorNew)) {
                idFornecedorOld.getEnviaList().remove(envia);
                idFornecedorOld = em.merge(idFornecedorOld);
            }
            if (idFornecedorNew != null && !idFornecedorNew.equals(idFornecedorOld)) {
                idFornecedorNew.getEnviaList().add(envia);
                idFornecedorNew = em.merge(idFornecedorNew);
            }
            if (numeroNotaOld != null && !numeroNotaOld.equals(numeroNotaNew)) {
                numeroNotaOld.getEnviaList().remove(envia);
                numeroNotaOld = em.merge(numeroNotaOld);
            }
            if (numeroNotaNew != null && !numeroNotaNew.equals(numeroNotaOld)) {
                numeroNotaNew.getEnviaList().add(envia);
                numeroNotaNew = em.merge(numeroNotaNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = envia.getIdEnvia();
                if (findEnvia(id) == null) {
                    throw new NonexistentEntityException("The envia with id " + id + " no longer exists.");
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
            Envia envia;
            try {
                envia = em.getReference(Envia.class, id);
                envia.getIdEnvia();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The envia with id " + id + " no longer exists.", enfe);
            }
            Fornecedor idFornecedor = envia.getIdFornecedor();
            if (idFornecedor != null) {
                idFornecedor.getEnviaList().remove(envia);
                idFornecedor = em.merge(idFornecedor);
            }
            Notafiscal numeroNota = envia.getNumeroNota();
            if (numeroNota != null) {
                numeroNota.getEnviaList().remove(envia);
                numeroNota = em.merge(numeroNota);
            }
            em.remove(envia);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Envia> findEnviaEntities() {
        return findEnviaEntities(true, -1, -1);
    }

    public List<Envia> findEnviaEntities(int maxResults, int firstResult) {
        return findEnviaEntities(false, maxResults, firstResult);
    }

    private List<Envia> findEnviaEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Envia.class));
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

    public Envia findEnvia(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Envia.class, id);
        } finally {
            em.close();
        }
    }

    public int getEnviaCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Envia> rt = cq.from(Envia.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
