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
import prefeitura.entities.Notafiscal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Fornecedor;
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
        if (fornecedor.getNotafiscalList() == null) {
            fornecedor.setNotafiscalList(new ArrayList<Notafiscal>());
        }
        if (fornecedor.getProcessoList() == null) {
            fornecedor.setProcessoList(new ArrayList<Processo>());
        }
        if (fornecedor.getNotafiscalList1() == null) {
            fornecedor.setNotafiscalList1(new ArrayList<Notafiscal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Notafiscal> attachedNotafiscalList = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNotafiscalToAttach : fornecedor.getNotafiscalList()) {
                notafiscalListNotafiscalToAttach = em.getReference(notafiscalListNotafiscalToAttach.getClass(), notafiscalListNotafiscalToAttach.getNumeroNota());
                attachedNotafiscalList.add(notafiscalListNotafiscalToAttach);
            }
            fornecedor.setNotafiscalList(attachedNotafiscalList);
            List<Processo> attachedProcessoList = new ArrayList<Processo>();
            for (Processo processoListProcessoToAttach : fornecedor.getProcessoList()) {
                processoListProcessoToAttach = em.getReference(processoListProcessoToAttach.getClass(), processoListProcessoToAttach.getNumeroProcesso());
                attachedProcessoList.add(processoListProcessoToAttach);
            }
            fornecedor.setProcessoList(attachedProcessoList);
            List<Notafiscal> attachedNotafiscalList1 = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalList1NotafiscalToAttach : fornecedor.getNotafiscalList1()) {
                notafiscalList1NotafiscalToAttach = em.getReference(notafiscalList1NotafiscalToAttach.getClass(), notafiscalList1NotafiscalToAttach.getNumeroNota());
                attachedNotafiscalList1.add(notafiscalList1NotafiscalToAttach);
            }
            fornecedor.setNotafiscalList1(attachedNotafiscalList1);
            em.persist(fornecedor);
            for (Notafiscal notafiscalListNotafiscal : fornecedor.getNotafiscalList()) {
                notafiscalListNotafiscal.getFornecedorList().add(fornecedor);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
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
            for (Notafiscal notafiscalList1Notafiscal : fornecedor.getNotafiscalList1()) {
                Fornecedor oldIdFornecedorOfNotafiscalList1Notafiscal = notafiscalList1Notafiscal.getIdFornecedor();
                notafiscalList1Notafiscal.setIdFornecedor(fornecedor);
                notafiscalList1Notafiscal = em.merge(notafiscalList1Notafiscal);
                if (oldIdFornecedorOfNotafiscalList1Notafiscal != null) {
                    oldIdFornecedorOfNotafiscalList1Notafiscal.getNotafiscalList1().remove(notafiscalList1Notafiscal);
                    oldIdFornecedorOfNotafiscalList1Notafiscal = em.merge(oldIdFornecedorOfNotafiscalList1Notafiscal);
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
            List<Notafiscal> notafiscalListOld = persistentFornecedor.getNotafiscalList();
            List<Notafiscal> notafiscalListNew = fornecedor.getNotafiscalList();
            List<Processo> processoListOld = persistentFornecedor.getProcessoList();
            List<Processo> processoListNew = fornecedor.getProcessoList();
            List<Notafiscal> notafiscalList1Old = persistentFornecedor.getNotafiscalList1();
            List<Notafiscal> notafiscalList1New = fornecedor.getNotafiscalList1();
            List<Notafiscal> attachedNotafiscalListNew = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNewNotafiscalToAttach : notafiscalListNew) {
                notafiscalListNewNotafiscalToAttach = em.getReference(notafiscalListNewNotafiscalToAttach.getClass(), notafiscalListNewNotafiscalToAttach.getNumeroNota());
                attachedNotafiscalListNew.add(notafiscalListNewNotafiscalToAttach);
            }
            notafiscalListNew = attachedNotafiscalListNew;
            fornecedor.setNotafiscalList(notafiscalListNew);
            List<Processo> attachedProcessoListNew = new ArrayList<Processo>();
            for (Processo processoListNewProcessoToAttach : processoListNew) {
                processoListNewProcessoToAttach = em.getReference(processoListNewProcessoToAttach.getClass(), processoListNewProcessoToAttach.getNumeroProcesso());
                attachedProcessoListNew.add(processoListNewProcessoToAttach);
            }
            processoListNew = attachedProcessoListNew;
            fornecedor.setProcessoList(processoListNew);
            List<Notafiscal> attachedNotafiscalList1New = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalList1NewNotafiscalToAttach : notafiscalList1New) {
                notafiscalList1NewNotafiscalToAttach = em.getReference(notafiscalList1NewNotafiscalToAttach.getClass(), notafiscalList1NewNotafiscalToAttach.getNumeroNota());
                attachedNotafiscalList1New.add(notafiscalList1NewNotafiscalToAttach);
            }
            notafiscalList1New = attachedNotafiscalList1New;
            fornecedor.setNotafiscalList1(notafiscalList1New);
            fornecedor = em.merge(fornecedor);
            for (Notafiscal notafiscalListOldNotafiscal : notafiscalListOld) {
                if (!notafiscalListNew.contains(notafiscalListOldNotafiscal)) {
                    notafiscalListOldNotafiscal.getFornecedorList().remove(fornecedor);
                    notafiscalListOldNotafiscal = em.merge(notafiscalListOldNotafiscal);
                }
            }
            for (Notafiscal notafiscalListNewNotafiscal : notafiscalListNew) {
                if (!notafiscalListOld.contains(notafiscalListNewNotafiscal)) {
                    notafiscalListNewNotafiscal.getFornecedorList().add(fornecedor);
                    notafiscalListNewNotafiscal = em.merge(notafiscalListNewNotafiscal);
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
            for (Notafiscal notafiscalList1OldNotafiscal : notafiscalList1Old) {
                if (!notafiscalList1New.contains(notafiscalList1OldNotafiscal)) {
                    notafiscalList1OldNotafiscal.setIdFornecedor(null);
                    notafiscalList1OldNotafiscal = em.merge(notafiscalList1OldNotafiscal);
                }
            }
            for (Notafiscal notafiscalList1NewNotafiscal : notafiscalList1New) {
                if (!notafiscalList1Old.contains(notafiscalList1NewNotafiscal)) {
                    Fornecedor oldIdFornecedorOfNotafiscalList1NewNotafiscal = notafiscalList1NewNotafiscal.getIdFornecedor();
                    notafiscalList1NewNotafiscal.setIdFornecedor(fornecedor);
                    notafiscalList1NewNotafiscal = em.merge(notafiscalList1NewNotafiscal);
                    if (oldIdFornecedorOfNotafiscalList1NewNotafiscal != null && !oldIdFornecedorOfNotafiscalList1NewNotafiscal.equals(fornecedor)) {
                        oldIdFornecedorOfNotafiscalList1NewNotafiscal.getNotafiscalList1().remove(notafiscalList1NewNotafiscal);
                        oldIdFornecedorOfNotafiscalList1NewNotafiscal = em.merge(oldIdFornecedorOfNotafiscalList1NewNotafiscal);
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
            List<Notafiscal> notafiscalList = fornecedor.getNotafiscalList();
            for (Notafiscal notafiscalListNotafiscal : notafiscalList) {
                notafiscalListNotafiscal.getFornecedorList().remove(fornecedor);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
            }
            List<Processo> processoList = fornecedor.getProcessoList();
            for (Processo processoListProcesso : processoList) {
                processoListProcesso.setIdFornecedor(null);
                processoListProcesso = em.merge(processoListProcesso);
            }
            List<Notafiscal> notafiscalList1 = fornecedor.getNotafiscalList1();
            for (Notafiscal notafiscalList1Notafiscal : notafiscalList1) {
                notafiscalList1Notafiscal.setIdFornecedor(null);
                notafiscalList1Notafiscal = em.merge(notafiscalList1Notafiscal);
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
