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
import prefeitura.entities.Fornecedor;
import prefeitura.entities.Oficio;
import prefeitura.entities.Notafiscal;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Cria;
import prefeitura.entities.Processo;

/**
 *
 * @author lucia
 */
public class ProcessoJpaController implements Serializable {

    public ProcessoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Processo processo) {
        if (processo.getNotafiscalList() == null) {
            processo.setNotafiscalList(new ArrayList<Notafiscal>());
        }
        if (processo.getCriaList() == null) {
            processo.setCriaList(new ArrayList<Cria>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedor idFornecedor = processo.getIdFornecedor();
            if (idFornecedor != null) {
                idFornecedor = em.getReference(idFornecedor.getClass(), idFornecedor.getIdFornecedor());
                processo.setIdFornecedor(idFornecedor);
            }
            Oficio idOficio = processo.getIdOficio();
            if (idOficio != null) {
                idOficio = em.getReference(idOficio.getClass(), idOficio.getIdOficio());
                processo.setIdOficio(idOficio);
            }
            List<Notafiscal> attachedNotafiscalList = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNotafiscalToAttach : processo.getNotafiscalList()) {
                notafiscalListNotafiscalToAttach = em.getReference(notafiscalListNotafiscalToAttach.getClass(), notafiscalListNotafiscalToAttach.getIdNotaFiscal());
                attachedNotafiscalList.add(notafiscalListNotafiscalToAttach);
            }
            processo.setNotafiscalList(attachedNotafiscalList);
            List<Cria> attachedCriaList = new ArrayList<Cria>();
            for (Cria criaListCriaToAttach : processo.getCriaList()) {
                criaListCriaToAttach = em.getReference(criaListCriaToAttach.getClass(), criaListCriaToAttach.getIdCria());
                attachedCriaList.add(criaListCriaToAttach);
            }
            processo.setCriaList(attachedCriaList);
            em.persist(processo);
            if (idFornecedor != null) {
                idFornecedor.getProcessoList().add(processo);
                idFornecedor = em.merge(idFornecedor);
            }
            if (idOficio != null) {
                idOficio.getProcessoList().add(processo);
                idOficio = em.merge(idOficio);
            }
            for (Notafiscal notafiscalListNotafiscal : processo.getNotafiscalList()) {
                Processo oldIdProcessoOfNotafiscalListNotafiscal = notafiscalListNotafiscal.getIdProcesso();
                notafiscalListNotafiscal.setIdProcesso(processo);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
                if (oldIdProcessoOfNotafiscalListNotafiscal != null) {
                    oldIdProcessoOfNotafiscalListNotafiscal.getNotafiscalList().remove(notafiscalListNotafiscal);
                    oldIdProcessoOfNotafiscalListNotafiscal = em.merge(oldIdProcessoOfNotafiscalListNotafiscal);
                }
            }
            for (Cria criaListCria : processo.getCriaList()) {
                Processo oldNumeroProcessoOfCriaListCria = criaListCria.getNumeroProcesso();
                criaListCria.setNumeroProcesso(processo);
                criaListCria = em.merge(criaListCria);
                if (oldNumeroProcessoOfCriaListCria != null) {
                    oldNumeroProcessoOfCriaListCria.getCriaList().remove(criaListCria);
                    oldNumeroProcessoOfCriaListCria = em.merge(oldNumeroProcessoOfCriaListCria);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Processo processo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processo persistentProcesso = em.find(Processo.class, processo.getIdProcesso());
            Fornecedor idFornecedorOld = persistentProcesso.getIdFornecedor();
            Fornecedor idFornecedorNew = processo.getIdFornecedor();
            Oficio idOficioOld = persistentProcesso.getIdOficio();
            Oficio idOficioNew = processo.getIdOficio();
            List<Notafiscal> notafiscalListOld = persistentProcesso.getNotafiscalList();
            List<Notafiscal> notafiscalListNew = processo.getNotafiscalList();
            List<Cria> criaListOld = persistentProcesso.getCriaList();
            List<Cria> criaListNew = processo.getCriaList();
            if (idFornecedorNew != null) {
                idFornecedorNew = em.getReference(idFornecedorNew.getClass(), idFornecedorNew.getIdFornecedor());
                processo.setIdFornecedor(idFornecedorNew);
            }
            if (idOficioNew != null) {
                idOficioNew = em.getReference(idOficioNew.getClass(), idOficioNew.getIdOficio());
                processo.setIdOficio(idOficioNew);
            }
            List<Notafiscal> attachedNotafiscalListNew = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNewNotafiscalToAttach : notafiscalListNew) {
                notafiscalListNewNotafiscalToAttach = em.getReference(notafiscalListNewNotafiscalToAttach.getClass(), notafiscalListNewNotafiscalToAttach.getIdNotaFiscal());
                attachedNotafiscalListNew.add(notafiscalListNewNotafiscalToAttach);
            }
            notafiscalListNew = attachedNotafiscalListNew;
            processo.setNotafiscalList(notafiscalListNew);
            List<Cria> attachedCriaListNew = new ArrayList<Cria>();
            for (Cria criaListNewCriaToAttach : criaListNew) {
                criaListNewCriaToAttach = em.getReference(criaListNewCriaToAttach.getClass(), criaListNewCriaToAttach.getIdCria());
                attachedCriaListNew.add(criaListNewCriaToAttach);
            }
            criaListNew = attachedCriaListNew;
            processo.setCriaList(criaListNew);
            processo = em.merge(processo);
            if (idFornecedorOld != null && !idFornecedorOld.equals(idFornecedorNew)) {
                idFornecedorOld.getProcessoList().remove(processo);
                idFornecedorOld = em.merge(idFornecedorOld);
            }
            if (idFornecedorNew != null && !idFornecedorNew.equals(idFornecedorOld)) {
                idFornecedorNew.getProcessoList().add(processo);
                idFornecedorNew = em.merge(idFornecedorNew);
            }
            if (idOficioOld != null && !idOficioOld.equals(idOficioNew)) {
                idOficioOld.getProcessoList().remove(processo);
                idOficioOld = em.merge(idOficioOld);
            }
            if (idOficioNew != null && !idOficioNew.equals(idOficioOld)) {
                idOficioNew.getProcessoList().add(processo);
                idOficioNew = em.merge(idOficioNew);
            }
            for (Notafiscal notafiscalListOldNotafiscal : notafiscalListOld) {
                if (!notafiscalListNew.contains(notafiscalListOldNotafiscal)) {
                    notafiscalListOldNotafiscal.setIdProcesso(null);
                    notafiscalListOldNotafiscal = em.merge(notafiscalListOldNotafiscal);
                }
            }
            for (Notafiscal notafiscalListNewNotafiscal : notafiscalListNew) {
                if (!notafiscalListOld.contains(notafiscalListNewNotafiscal)) {
                    Processo oldIdProcessoOfNotafiscalListNewNotafiscal = notafiscalListNewNotafiscal.getIdProcesso();
                    notafiscalListNewNotafiscal.setIdProcesso(processo);
                    notafiscalListNewNotafiscal = em.merge(notafiscalListNewNotafiscal);
                    if (oldIdProcessoOfNotafiscalListNewNotafiscal != null && !oldIdProcessoOfNotafiscalListNewNotafiscal.equals(processo)) {
                        oldIdProcessoOfNotafiscalListNewNotafiscal.getNotafiscalList().remove(notafiscalListNewNotafiscal);
                        oldIdProcessoOfNotafiscalListNewNotafiscal = em.merge(oldIdProcessoOfNotafiscalListNewNotafiscal);
                    }
                }
            }
            for (Cria criaListOldCria : criaListOld) {
                if (!criaListNew.contains(criaListOldCria)) {
                    criaListOldCria.setNumeroProcesso(null);
                    criaListOldCria = em.merge(criaListOldCria);
                }
            }
            for (Cria criaListNewCria : criaListNew) {
                if (!criaListOld.contains(criaListNewCria)) {
                    Processo oldNumeroProcessoOfCriaListNewCria = criaListNewCria.getNumeroProcesso();
                    criaListNewCria.setNumeroProcesso(processo);
                    criaListNewCria = em.merge(criaListNewCria);
                    if (oldNumeroProcessoOfCriaListNewCria != null && !oldNumeroProcessoOfCriaListNewCria.equals(processo)) {
                        oldNumeroProcessoOfCriaListNewCria.getCriaList().remove(criaListNewCria);
                        oldNumeroProcessoOfCriaListNewCria = em.merge(oldNumeroProcessoOfCriaListNewCria);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = processo.getIdProcesso();
                if (findProcesso(id) == null) {
                    throw new NonexistentEntityException("The processo with id " + id + " no longer exists.");
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
            Processo processo;
            try {
                processo = em.getReference(Processo.class, id);
                processo.getIdProcesso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The processo with id " + id + " no longer exists.", enfe);
            }
            Fornecedor idFornecedor = processo.getIdFornecedor();
            if (idFornecedor != null) {
                idFornecedor.getProcessoList().remove(processo);
                idFornecedor = em.merge(idFornecedor);
            }
            Oficio idOficio = processo.getIdOficio();
            if (idOficio != null) {
                idOficio.getProcessoList().remove(processo);
                idOficio = em.merge(idOficio);
            }
            List<Notafiscal> notafiscalList = processo.getNotafiscalList();
            for (Notafiscal notafiscalListNotafiscal : notafiscalList) {
                notafiscalListNotafiscal.setIdProcesso(null);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
            }
            List<Cria> criaList = processo.getCriaList();
            for (Cria criaListCria : criaList) {
                criaListCria.setNumeroProcesso(null);
                criaListCria = em.merge(criaListCria);
            }
            em.remove(processo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Processo> findProcessoEntities() {
        return findProcessoEntities(true, -1, -1);
    }

    public List<Processo> findProcessoEntities(int maxResults, int firstResult) {
        return findProcessoEntities(false, maxResults, firstResult);
    }

    private List<Processo> findProcessoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Processo.class));
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

    public Processo findProcesso(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Processo.class, id);
        } finally {
            em.close();
        }
    }

    public int getProcessoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Processo> rt = cq.from(Processo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
