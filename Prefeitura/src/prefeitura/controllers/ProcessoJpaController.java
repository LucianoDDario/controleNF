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
import prefeitura.entities.Possui;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Notafiscal;
import prefeitura.entities.Cria;
import prefeitura.entities.Possui1;
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
        if (processo.getPossuiList() == null) {
            processo.setPossuiList(new ArrayList<Possui>());
        }
        if (processo.getNotafiscalList() == null) {
            processo.setNotafiscalList(new ArrayList<Notafiscal>());
        }
        if (processo.getCriaList() == null) {
            processo.setCriaList(new ArrayList<Cria>());
        }
        if (processo.getPossui1List() == null) {
            processo.setPossui1List(new ArrayList<Possui1>());
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
            Oficio numeroOficio = processo.getNumeroOficio();
            if (numeroOficio != null) {
                numeroOficio = em.getReference(numeroOficio.getClass(), numeroOficio.getIdOficio());
                processo.setNumeroOficio(numeroOficio);
            }
            List<Possui> attachedPossuiList = new ArrayList<Possui>();
            for (Possui possuiListPossuiToAttach : processo.getPossuiList()) {
                possuiListPossuiToAttach = em.getReference(possuiListPossuiToAttach.getClass(), possuiListPossuiToAttach.getIdPossui());
                attachedPossuiList.add(possuiListPossuiToAttach);
            }
            processo.setPossuiList(attachedPossuiList);
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
            List<Possui1> attachedPossui1List = new ArrayList<Possui1>();
            for (Possui1 possui1ListPossui1ToAttach : processo.getPossui1List()) {
                possui1ListPossui1ToAttach = em.getReference(possui1ListPossui1ToAttach.getClass(), possui1ListPossui1ToAttach.getIdPossui1());
                attachedPossui1List.add(possui1ListPossui1ToAttach);
            }
            processo.setPossui1List(attachedPossui1List);
            em.persist(processo);
            if (idFornecedor != null) {
                idFornecedor.getProcessoList().add(processo);
                idFornecedor = em.merge(idFornecedor);
            }
            if (numeroOficio != null) {
                numeroOficio.getProcessoList().add(processo);
                numeroOficio = em.merge(numeroOficio);
            }
            for (Possui possuiListPossui : processo.getPossuiList()) {
                Processo oldNumeroProcessoOfPossuiListPossui = possuiListPossui.getNumeroProcesso();
                possuiListPossui.setNumeroProcesso(processo);
                possuiListPossui = em.merge(possuiListPossui);
                if (oldNumeroProcessoOfPossuiListPossui != null) {
                    oldNumeroProcessoOfPossuiListPossui.getPossuiList().remove(possuiListPossui);
                    oldNumeroProcessoOfPossuiListPossui = em.merge(oldNumeroProcessoOfPossuiListPossui);
                }
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
            for (Possui1 possui1ListPossui1 : processo.getPossui1List()) {
                Processo oldNumeroProcessoOfPossui1ListPossui1 = possui1ListPossui1.getNumeroProcesso();
                possui1ListPossui1.setNumeroProcesso(processo);
                possui1ListPossui1 = em.merge(possui1ListPossui1);
                if (oldNumeroProcessoOfPossui1ListPossui1 != null) {
                    oldNumeroProcessoOfPossui1ListPossui1.getPossui1List().remove(possui1ListPossui1);
                    oldNumeroProcessoOfPossui1ListPossui1 = em.merge(oldNumeroProcessoOfPossui1ListPossui1);
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
            Oficio numeroOficioOld = persistentProcesso.getNumeroOficio();
            Oficio numeroOficioNew = processo.getNumeroOficio();
            List<Possui> possuiListOld = persistentProcesso.getPossuiList();
            List<Possui> possuiListNew = processo.getPossuiList();
            List<Notafiscal> notafiscalListOld = persistentProcesso.getNotafiscalList();
            List<Notafiscal> notafiscalListNew = processo.getNotafiscalList();
            List<Cria> criaListOld = persistentProcesso.getCriaList();
            List<Cria> criaListNew = processo.getCriaList();
            List<Possui1> possui1ListOld = persistentProcesso.getPossui1List();
            List<Possui1> possui1ListNew = processo.getPossui1List();
            if (idFornecedorNew != null) {
                idFornecedorNew = em.getReference(idFornecedorNew.getClass(), idFornecedorNew.getIdFornecedor());
                processo.setIdFornecedor(idFornecedorNew);
            }
            if (numeroOficioNew != null) {
                numeroOficioNew = em.getReference(numeroOficioNew.getClass(), numeroOficioNew.getIdOficio());
                processo.setNumeroOficio(numeroOficioNew);
            }
            List<Possui> attachedPossuiListNew = new ArrayList<Possui>();
            for (Possui possuiListNewPossuiToAttach : possuiListNew) {
                possuiListNewPossuiToAttach = em.getReference(possuiListNewPossuiToAttach.getClass(), possuiListNewPossuiToAttach.getIdPossui());
                attachedPossuiListNew.add(possuiListNewPossuiToAttach);
            }
            possuiListNew = attachedPossuiListNew;
            processo.setPossuiList(possuiListNew);
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
            List<Possui1> attachedPossui1ListNew = new ArrayList<Possui1>();
            for (Possui1 possui1ListNewPossui1ToAttach : possui1ListNew) {
                possui1ListNewPossui1ToAttach = em.getReference(possui1ListNewPossui1ToAttach.getClass(), possui1ListNewPossui1ToAttach.getIdPossui1());
                attachedPossui1ListNew.add(possui1ListNewPossui1ToAttach);
            }
            possui1ListNew = attachedPossui1ListNew;
            processo.setPossui1List(possui1ListNew);
            processo = em.merge(processo);
            if (idFornecedorOld != null && !idFornecedorOld.equals(idFornecedorNew)) {
                idFornecedorOld.getProcessoList().remove(processo);
                idFornecedorOld = em.merge(idFornecedorOld);
            }
            if (idFornecedorNew != null && !idFornecedorNew.equals(idFornecedorOld)) {
                idFornecedorNew.getProcessoList().add(processo);
                idFornecedorNew = em.merge(idFornecedorNew);
            }
            if (numeroOficioOld != null && !numeroOficioOld.equals(numeroOficioNew)) {
                numeroOficioOld.getProcessoList().remove(processo);
                numeroOficioOld = em.merge(numeroOficioOld);
            }
            if (numeroOficioNew != null && !numeroOficioNew.equals(numeroOficioOld)) {
                numeroOficioNew.getProcessoList().add(processo);
                numeroOficioNew = em.merge(numeroOficioNew);
            }
            for (Possui possuiListOldPossui : possuiListOld) {
                if (!possuiListNew.contains(possuiListOldPossui)) {
                    possuiListOldPossui.setNumeroProcesso(null);
                    possuiListOldPossui = em.merge(possuiListOldPossui);
                }
            }
            for (Possui possuiListNewPossui : possuiListNew) {
                if (!possuiListOld.contains(possuiListNewPossui)) {
                    Processo oldNumeroProcessoOfPossuiListNewPossui = possuiListNewPossui.getNumeroProcesso();
                    possuiListNewPossui.setNumeroProcesso(processo);
                    possuiListNewPossui = em.merge(possuiListNewPossui);
                    if (oldNumeroProcessoOfPossuiListNewPossui != null && !oldNumeroProcessoOfPossuiListNewPossui.equals(processo)) {
                        oldNumeroProcessoOfPossuiListNewPossui.getPossuiList().remove(possuiListNewPossui);
                        oldNumeroProcessoOfPossuiListNewPossui = em.merge(oldNumeroProcessoOfPossuiListNewPossui);
                    }
                }
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
            for (Possui1 possui1ListOldPossui1 : possui1ListOld) {
                if (!possui1ListNew.contains(possui1ListOldPossui1)) {
                    possui1ListOldPossui1.setNumeroProcesso(null);
                    possui1ListOldPossui1 = em.merge(possui1ListOldPossui1);
                }
            }
            for (Possui1 possui1ListNewPossui1 : possui1ListNew) {
                if (!possui1ListOld.contains(possui1ListNewPossui1)) {
                    Processo oldNumeroProcessoOfPossui1ListNewPossui1 = possui1ListNewPossui1.getNumeroProcesso();
                    possui1ListNewPossui1.setNumeroProcesso(processo);
                    possui1ListNewPossui1 = em.merge(possui1ListNewPossui1);
                    if (oldNumeroProcessoOfPossui1ListNewPossui1 != null && !oldNumeroProcessoOfPossui1ListNewPossui1.equals(processo)) {
                        oldNumeroProcessoOfPossui1ListNewPossui1.getPossui1List().remove(possui1ListNewPossui1);
                        oldNumeroProcessoOfPossui1ListNewPossui1 = em.merge(oldNumeroProcessoOfPossui1ListNewPossui1);
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
            Oficio numeroOficio = processo.getNumeroOficio();
            if (numeroOficio != null) {
                numeroOficio.getProcessoList().remove(processo);
                numeroOficio = em.merge(numeroOficio);
            }
            List<Possui> possuiList = processo.getPossuiList();
            for (Possui possuiListPossui : possuiList) {
                possuiListPossui.setNumeroProcesso(null);
                possuiListPossui = em.merge(possuiListPossui);
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
            List<Possui1> possui1List = processo.getPossui1List();
            for (Possui1 possui1ListPossui1 : possui1List) {
                possui1ListPossui1.setNumeroProcesso(null);
                possui1ListPossui1 = em.merge(possui1ListPossui1);
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
