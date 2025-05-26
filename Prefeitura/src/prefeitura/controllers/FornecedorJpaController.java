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
import prefeitura.entities.Envia;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Fornecedor;
import prefeitura.entities.Notafiscal;
import prefeitura.entities.Processo;

/**
 *
 * @author lucia
 */
public class FornecedorJpaController implements Serializable {

    public FornecedorJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Fornecedor fornecedor) {
        if (fornecedor.getEnviaList() == null) {
            fornecedor.setEnviaList(new ArrayList<Envia>());
        }
        if (fornecedor.getNotafiscalList() == null) {
            fornecedor.setNotafiscalList(new ArrayList<Notafiscal>());
        }
        if (fornecedor.getProcessoList() == null) {
            fornecedor.setProcessoList(new ArrayList<Processo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Envia> attachedEnviaList = new ArrayList<Envia>();
            for (Envia enviaListEnviaToAttach : fornecedor.getEnviaList()) {
                enviaListEnviaToAttach = em.getReference(enviaListEnviaToAttach.getClass(), enviaListEnviaToAttach.getIdEnvia());
                attachedEnviaList.add(enviaListEnviaToAttach);
            }
            fornecedor.setEnviaList(attachedEnviaList);
            List<Notafiscal> attachedNotafiscalList = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNotafiscalToAttach : fornecedor.getNotafiscalList()) {
                notafiscalListNotafiscalToAttach = em.getReference(notafiscalListNotafiscalToAttach.getClass(), notafiscalListNotafiscalToAttach.getIdNotaFiscal());
                attachedNotafiscalList.add(notafiscalListNotafiscalToAttach);
            }
            fornecedor.setNotafiscalList(attachedNotafiscalList);
            List<Processo> attachedProcessoList = new ArrayList<Processo>();
            for (Processo processoListProcessoToAttach : fornecedor.getProcessoList()) {
                processoListProcessoToAttach = em.getReference(processoListProcessoToAttach.getClass(), processoListProcessoToAttach.getIdProcesso());
                attachedProcessoList.add(processoListProcessoToAttach);
            }
            fornecedor.setProcessoList(attachedProcessoList);
            em.persist(fornecedor);
            for (Envia enviaListEnvia : fornecedor.getEnviaList()) {
                Fornecedor oldIdFornecedorOfEnviaListEnvia = enviaListEnvia.getIdFornecedor();
                enviaListEnvia.setIdFornecedor(fornecedor);
                enviaListEnvia = em.merge(enviaListEnvia);
                if (oldIdFornecedorOfEnviaListEnvia != null) {
                    oldIdFornecedorOfEnviaListEnvia.getEnviaList().remove(enviaListEnvia);
                    oldIdFornecedorOfEnviaListEnvia = em.merge(oldIdFornecedorOfEnviaListEnvia);
                }
            }
            for (Notafiscal notafiscalListNotafiscal : fornecedor.getNotafiscalList()) {
                Fornecedor oldIdFornecedorOfNotafiscalListNotafiscal = notafiscalListNotafiscal.getIdFornecedor();
                notafiscalListNotafiscal.setIdFornecedor(fornecedor);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
                if (oldIdFornecedorOfNotafiscalListNotafiscal != null) {
                    oldIdFornecedorOfNotafiscalListNotafiscal.getNotafiscalList().remove(notafiscalListNotafiscal);
                    oldIdFornecedorOfNotafiscalListNotafiscal = em.merge(oldIdFornecedorOfNotafiscalListNotafiscal);
                }
            }
            for (Processo processoListProcesso : fornecedor.getProcessoList()) {
                Fornecedor oldIdFornecedorOfProcessoListProcesso = processoListProcesso.getIdFornecedor();
                processoListProcesso.setIdFornecedor(fornecedor);
                processoListProcesso = em.merge(processoListProcesso);
                if (oldIdFornecedorOfProcessoListProcesso != null) {
                    oldIdFornecedorOfProcessoListProcesso.getProcessoList().remove(processoListProcesso);
                    oldIdFornecedorOfProcessoListProcesso = em.merge(oldIdFornecedorOfProcessoListProcesso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Fornecedor fornecedor) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedor persistentFornecedor = em.find(Fornecedor.class, fornecedor.getIdFornecedor());
            List<Envia> enviaListOld = persistentFornecedor.getEnviaList();
            List<Envia> enviaListNew = fornecedor.getEnviaList();
            List<Notafiscal> notafiscalListOld = persistentFornecedor.getNotafiscalList();
            List<Notafiscal> notafiscalListNew = fornecedor.getNotafiscalList();
            List<Processo> processoListOld = persistentFornecedor.getProcessoList();
            List<Processo> processoListNew = fornecedor.getProcessoList();
            List<Envia> attachedEnviaListNew = new ArrayList<Envia>();
            for (Envia enviaListNewEnviaToAttach : enviaListNew) {
                enviaListNewEnviaToAttach = em.getReference(enviaListNewEnviaToAttach.getClass(), enviaListNewEnviaToAttach.getIdEnvia());
                attachedEnviaListNew.add(enviaListNewEnviaToAttach);
            }
            enviaListNew = attachedEnviaListNew;
            fornecedor.setEnviaList(enviaListNew);
            List<Notafiscal> attachedNotafiscalListNew = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNewNotafiscalToAttach : notafiscalListNew) {
                notafiscalListNewNotafiscalToAttach = em.getReference(notafiscalListNewNotafiscalToAttach.getClass(), notafiscalListNewNotafiscalToAttach.getIdNotaFiscal());
                attachedNotafiscalListNew.add(notafiscalListNewNotafiscalToAttach);
            }
            notafiscalListNew = attachedNotafiscalListNew;
            fornecedor.setNotafiscalList(notafiscalListNew);
            List<Processo> attachedProcessoListNew = new ArrayList<Processo>();
            for (Processo processoListNewProcessoToAttach : processoListNew) {
                processoListNewProcessoToAttach = em.getReference(processoListNewProcessoToAttach.getClass(), processoListNewProcessoToAttach.getIdProcesso());
                attachedProcessoListNew.add(processoListNewProcessoToAttach);
            }
            processoListNew = attachedProcessoListNew;
            fornecedor.setProcessoList(processoListNew);
            fornecedor = em.merge(fornecedor);
            for (Envia enviaListOldEnvia : enviaListOld) {
                if (!enviaListNew.contains(enviaListOldEnvia)) {
                    enviaListOldEnvia.setIdFornecedor(null);
                    enviaListOldEnvia = em.merge(enviaListOldEnvia);
                }
            }
            for (Envia enviaListNewEnvia : enviaListNew) {
                if (!enviaListOld.contains(enviaListNewEnvia)) {
                    Fornecedor oldIdFornecedorOfEnviaListNewEnvia = enviaListNewEnvia.getIdFornecedor();
                    enviaListNewEnvia.setIdFornecedor(fornecedor);
                    enviaListNewEnvia = em.merge(enviaListNewEnvia);
                    if (oldIdFornecedorOfEnviaListNewEnvia != null && !oldIdFornecedorOfEnviaListNewEnvia.equals(fornecedor)) {
                        oldIdFornecedorOfEnviaListNewEnvia.getEnviaList().remove(enviaListNewEnvia);
                        oldIdFornecedorOfEnviaListNewEnvia = em.merge(oldIdFornecedorOfEnviaListNewEnvia);
                    }
                }
            }
            for (Notafiscal notafiscalListOldNotafiscal : notafiscalListOld) {
                if (!notafiscalListNew.contains(notafiscalListOldNotafiscal)) {
                    notafiscalListOldNotafiscal.setIdFornecedor(null);
                    notafiscalListOldNotafiscal = em.merge(notafiscalListOldNotafiscal);
                }
            }
            for (Notafiscal notafiscalListNewNotafiscal : notafiscalListNew) {
                if (!notafiscalListOld.contains(notafiscalListNewNotafiscal)) {
                    Fornecedor oldIdFornecedorOfNotafiscalListNewNotafiscal = notafiscalListNewNotafiscal.getIdFornecedor();
                    notafiscalListNewNotafiscal.setIdFornecedor(fornecedor);
                    notafiscalListNewNotafiscal = em.merge(notafiscalListNewNotafiscal);
                    if (oldIdFornecedorOfNotafiscalListNewNotafiscal != null && !oldIdFornecedorOfNotafiscalListNewNotafiscal.equals(fornecedor)) {
                        oldIdFornecedorOfNotafiscalListNewNotafiscal.getNotafiscalList().remove(notafiscalListNewNotafiscal);
                        oldIdFornecedorOfNotafiscalListNewNotafiscal = em.merge(oldIdFornecedorOfNotafiscalListNewNotafiscal);
                    }
                }
            }
            for (Processo processoListOldProcesso : processoListOld) {
                if (!processoListNew.contains(processoListOldProcesso)) {
                    processoListOldProcesso.setIdFornecedor(null);
                    processoListOldProcesso = em.merge(processoListOldProcesso);
                }
            }
            for (Processo processoListNewProcesso : processoListNew) {
                if (!processoListOld.contains(processoListNewProcesso)) {
                    Fornecedor oldIdFornecedorOfProcessoListNewProcesso = processoListNewProcesso.getIdFornecedor();
                    processoListNewProcesso.setIdFornecedor(fornecedor);
                    processoListNewProcesso = em.merge(processoListNewProcesso);
                    if (oldIdFornecedorOfProcessoListNewProcesso != null && !oldIdFornecedorOfProcessoListNewProcesso.equals(fornecedor)) {
                        oldIdFornecedorOfProcessoListNewProcesso.getProcessoList().remove(processoListNewProcesso);
                        oldIdFornecedorOfProcessoListNewProcesso = em.merge(oldIdFornecedorOfProcessoListNewProcesso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = fornecedor.getIdFornecedor();
                if (findFornecedor(id) == null) {
                    throw new NonexistentEntityException("The fornecedor with id " + id + " no longer exists.");
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
            Fornecedor fornecedor;
            try {
                fornecedor = em.getReference(Fornecedor.class, id);
                fornecedor.getIdFornecedor();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The fornecedor with id " + id + " no longer exists.", enfe);
            }
            List<Envia> enviaList = fornecedor.getEnviaList();
            for (Envia enviaListEnvia : enviaList) {
                enviaListEnvia.setIdFornecedor(null);
                enviaListEnvia = em.merge(enviaListEnvia);
            }
            List<Notafiscal> notafiscalList = fornecedor.getNotafiscalList();
            for (Notafiscal notafiscalListNotafiscal : notafiscalList) {
                notafiscalListNotafiscal.setIdFornecedor(null);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
            }
            List<Processo> processoList = fornecedor.getProcessoList();
            for (Processo processoListProcesso : processoList) {
                processoListProcesso.setIdFornecedor(null);
                processoListProcesso = em.merge(processoListProcesso);
            }
            em.remove(fornecedor);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Fornecedor> findFornecedorEntities() {
        return findFornecedorEntities(true, -1, -1);
    }

    public List<Fornecedor> findFornecedorEntities(int maxResults, int firstResult) {
        return findFornecedorEntities(false, maxResults, firstResult);
    }

    private List<Fornecedor> findFornecedorEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Fornecedor.class));
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

    public Fornecedor findFornecedor(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Fornecedor.class, id);
        } finally {
            em.close();
        }
    }

    public int getFornecedorCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Fornecedor> rt = cq.from(Fornecedor.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
