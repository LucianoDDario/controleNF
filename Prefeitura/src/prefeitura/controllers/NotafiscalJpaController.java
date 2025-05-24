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
import prefeitura.entities.Processo;
import prefeitura.entities.Protocolo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.IllegalOrphanException;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.controllers.exceptions.PreexistingEntityException;
import prefeitura.entities.Notafiscal;
import prefeitura.entities.Possui;

/**
 *
 * @author lucia
 */
public class NotafiscalJpaController implements Serializable {

    public NotafiscalJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Notafiscal notafiscal) throws PreexistingEntityException, Exception {
        if (notafiscal.getFornecedorList() == null) {
            notafiscal.setFornecedorList(new ArrayList<Fornecedor>());
        }
        if (notafiscal.getProtocoloList() == null) {
            notafiscal.setProtocoloList(new ArrayList<Protocolo>());
        }
        if (notafiscal.getPossuiList() == null) {
            notafiscal.setPossuiList(new ArrayList<Possui>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Fornecedor idFornecedor = notafiscal.getIdFornecedor();
            if (idFornecedor != null) {
                idFornecedor = em.getReference(idFornecedor.getClass(), idFornecedor.getIdFornecedor());
                notafiscal.setIdFornecedor(idFornecedor);
            }
            Processo numeroProcesso = notafiscal.getNumeroProcesso();
            if (numeroProcesso != null) {
                numeroProcesso = em.getReference(numeroProcesso.getClass(), numeroProcesso.getNumeroProcesso());
                notafiscal.setNumeroProcesso(numeroProcesso);
            }
            Protocolo numeroProtocolo = notafiscal.getNumeroProtocolo();
            if (numeroProtocolo != null) {
                numeroProtocolo = em.getReference(numeroProtocolo.getClass(), numeroProtocolo.getNumeroProtocolo());
                notafiscal.setNumeroProtocolo(numeroProtocolo);
            }
            List<Fornecedor> attachedFornecedorList = new ArrayList<Fornecedor>();
            for (Fornecedor fornecedorListFornecedorToAttach : notafiscal.getFornecedorList()) {
                fornecedorListFornecedorToAttach = em.getReference(fornecedorListFornecedorToAttach.getClass(), fornecedorListFornecedorToAttach.getIdFornecedor());
                attachedFornecedorList.add(fornecedorListFornecedorToAttach);
            }
            notafiscal.setFornecedorList(attachedFornecedorList);
            List<Protocolo> attachedProtocoloList = new ArrayList<Protocolo>();
            for (Protocolo protocoloListProtocoloToAttach : notafiscal.getProtocoloList()) {
                protocoloListProtocoloToAttach = em.getReference(protocoloListProtocoloToAttach.getClass(), protocoloListProtocoloToAttach.getNumeroProtocolo());
                attachedProtocoloList.add(protocoloListProtocoloToAttach);
            }
            notafiscal.setProtocoloList(attachedProtocoloList);
            List<Possui> attachedPossuiList = new ArrayList<Possui>();
            for (Possui possuiListPossuiToAttach : notafiscal.getPossuiList()) {
                possuiListPossuiToAttach = em.getReference(possuiListPossuiToAttach.getClass(), possuiListPossuiToAttach.getPossuiPK());
                attachedPossuiList.add(possuiListPossuiToAttach);
            }
            notafiscal.setPossuiList(attachedPossuiList);
            em.persist(notafiscal);
            if (idFornecedor != null) {
                idFornecedor.getNotafiscalList().add(notafiscal);
                idFornecedor = em.merge(idFornecedor);
            }
            if (numeroProcesso != null) {
                numeroProcesso.getNotafiscalList().add(notafiscal);
                numeroProcesso = em.merge(numeroProcesso);
            }
            if (numeroProtocolo != null) {
                Notafiscal oldNumeroNotaOfNumeroProtocolo = numeroProtocolo.getNumeroNota();
                if (oldNumeroNotaOfNumeroProtocolo != null) {
                    oldNumeroNotaOfNumeroProtocolo.setNumeroProtocolo(null);
                    oldNumeroNotaOfNumeroProtocolo = em.merge(oldNumeroNotaOfNumeroProtocolo);
                }
                numeroProtocolo.setNumeroNota(notafiscal);
                numeroProtocolo = em.merge(numeroProtocolo);
            }
            for (Fornecedor fornecedorListFornecedor : notafiscal.getFornecedorList()) {
                fornecedorListFornecedor.getNotafiscalList().add(notafiscal);
                fornecedorListFornecedor = em.merge(fornecedorListFornecedor);
            }
            for (Protocolo protocoloListProtocolo : notafiscal.getProtocoloList()) {
                Notafiscal oldNumeroNotaOfProtocoloListProtocolo = protocoloListProtocolo.getNumeroNota();
                protocoloListProtocolo.setNumeroNota(notafiscal);
                protocoloListProtocolo = em.merge(protocoloListProtocolo);
                if (oldNumeroNotaOfProtocoloListProtocolo != null) {
                    oldNumeroNotaOfProtocoloListProtocolo.getProtocoloList().remove(protocoloListProtocolo);
                    oldNumeroNotaOfProtocoloListProtocolo = em.merge(oldNumeroNotaOfProtocoloListProtocolo);
                }
            }
            for (Possui possuiListPossui : notafiscal.getPossuiList()) {
                Notafiscal oldNotafiscalOfPossuiListPossui = possuiListPossui.getNotafiscal();
                possuiListPossui.setNotafiscal(notafiscal);
                possuiListPossui = em.merge(possuiListPossui);
                if (oldNotafiscalOfPossuiListPossui != null) {
                    oldNotafiscalOfPossuiListPossui.getPossuiList().remove(possuiListPossui);
                    oldNotafiscalOfPossuiListPossui = em.merge(oldNotafiscalOfPossuiListPossui);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findNotafiscal(notafiscal.getNumeroNota()) != null) {
                throw new PreexistingEntityException("Notafiscal " + notafiscal + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notafiscal notafiscal) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notafiscal persistentNotafiscal = em.find(Notafiscal.class, notafiscal.getNumeroNota());
            Fornecedor idFornecedorOld = persistentNotafiscal.getIdFornecedor();
            Fornecedor idFornecedorNew = notafiscal.getIdFornecedor();
            Processo numeroProcessoOld = persistentNotafiscal.getNumeroProcesso();
            Processo numeroProcessoNew = notafiscal.getNumeroProcesso();
            Protocolo numeroProtocoloOld = persistentNotafiscal.getNumeroProtocolo();
            Protocolo numeroProtocoloNew = notafiscal.getNumeroProtocolo();
            List<Fornecedor> fornecedorListOld = persistentNotafiscal.getFornecedorList();
            List<Fornecedor> fornecedorListNew = notafiscal.getFornecedorList();
            List<Protocolo> protocoloListOld = persistentNotafiscal.getProtocoloList();
            List<Protocolo> protocoloListNew = notafiscal.getProtocoloList();
            List<Possui> possuiListOld = persistentNotafiscal.getPossuiList();
            List<Possui> possuiListNew = notafiscal.getPossuiList();
            List<String> illegalOrphanMessages = null;
            for (Possui possuiListOldPossui : possuiListOld) {
                if (!possuiListNew.contains(possuiListOldPossui)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Possui " + possuiListOldPossui + " since its notafiscal field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idFornecedorNew != null) {
                idFornecedorNew = em.getReference(idFornecedorNew.getClass(), idFornecedorNew.getIdFornecedor());
                notafiscal.setIdFornecedor(idFornecedorNew);
            }
            if (numeroProcessoNew != null) {
                numeroProcessoNew = em.getReference(numeroProcessoNew.getClass(), numeroProcessoNew.getNumeroProcesso());
                notafiscal.setNumeroProcesso(numeroProcessoNew);
            }
            if (numeroProtocoloNew != null) {
                numeroProtocoloNew = em.getReference(numeroProtocoloNew.getClass(), numeroProtocoloNew.getNumeroProtocolo());
                notafiscal.setNumeroProtocolo(numeroProtocoloNew);
            }
            List<Fornecedor> attachedFornecedorListNew = new ArrayList<Fornecedor>();
            for (Fornecedor fornecedorListNewFornecedorToAttach : fornecedorListNew) {
                fornecedorListNewFornecedorToAttach = em.getReference(fornecedorListNewFornecedorToAttach.getClass(), fornecedorListNewFornecedorToAttach.getIdFornecedor());
                attachedFornecedorListNew.add(fornecedorListNewFornecedorToAttach);
            }
            fornecedorListNew = attachedFornecedorListNew;
            notafiscal.setFornecedorList(fornecedorListNew);
            List<Protocolo> attachedProtocoloListNew = new ArrayList<Protocolo>();
            for (Protocolo protocoloListNewProtocoloToAttach : protocoloListNew) {
                protocoloListNewProtocoloToAttach = em.getReference(protocoloListNewProtocoloToAttach.getClass(), protocoloListNewProtocoloToAttach.getNumeroProtocolo());
                attachedProtocoloListNew.add(protocoloListNewProtocoloToAttach);
            }
            protocoloListNew = attachedProtocoloListNew;
            notafiscal.setProtocoloList(protocoloListNew);
            List<Possui> attachedPossuiListNew = new ArrayList<Possui>();
            for (Possui possuiListNewPossuiToAttach : possuiListNew) {
                possuiListNewPossuiToAttach = em.getReference(possuiListNewPossuiToAttach.getClass(), possuiListNewPossuiToAttach.getPossuiPK());
                attachedPossuiListNew.add(possuiListNewPossuiToAttach);
            }
            possuiListNew = attachedPossuiListNew;
            notafiscal.setPossuiList(possuiListNew);
            notafiscal = em.merge(notafiscal);
            if (idFornecedorOld != null && !idFornecedorOld.equals(idFornecedorNew)) {
                idFornecedorOld.getNotafiscalList().remove(notafiscal);
                idFornecedorOld = em.merge(idFornecedorOld);
            }
            if (idFornecedorNew != null && !idFornecedorNew.equals(idFornecedorOld)) {
                idFornecedorNew.getNotafiscalList().add(notafiscal);
                idFornecedorNew = em.merge(idFornecedorNew);
            }
            if (numeroProcessoOld != null && !numeroProcessoOld.equals(numeroProcessoNew)) {
                numeroProcessoOld.getNotafiscalList().remove(notafiscal);
                numeroProcessoOld = em.merge(numeroProcessoOld);
            }
            if (numeroProcessoNew != null && !numeroProcessoNew.equals(numeroProcessoOld)) {
                numeroProcessoNew.getNotafiscalList().add(notafiscal);
                numeroProcessoNew = em.merge(numeroProcessoNew);
            }
            if (numeroProtocoloOld != null && !numeroProtocoloOld.equals(numeroProtocoloNew)) {
                numeroProtocoloOld.setNumeroNota(null);
                numeroProtocoloOld = em.merge(numeroProtocoloOld);
            }
            if (numeroProtocoloNew != null && !numeroProtocoloNew.equals(numeroProtocoloOld)) {
                Notafiscal oldNumeroNotaOfNumeroProtocolo = numeroProtocoloNew.getNumeroNota();
                if (oldNumeroNotaOfNumeroProtocolo != null) {
                    oldNumeroNotaOfNumeroProtocolo.setNumeroProtocolo(null);
                    oldNumeroNotaOfNumeroProtocolo = em.merge(oldNumeroNotaOfNumeroProtocolo);
                }
                numeroProtocoloNew.setNumeroNota(notafiscal);
                numeroProtocoloNew = em.merge(numeroProtocoloNew);
            }
            for (Fornecedor fornecedorListOldFornecedor : fornecedorListOld) {
                if (!fornecedorListNew.contains(fornecedorListOldFornecedor)) {
                    fornecedorListOldFornecedor.getNotafiscalList().remove(notafiscal);
                    fornecedorListOldFornecedor = em.merge(fornecedorListOldFornecedor);
                }
            }
            for (Fornecedor fornecedorListNewFornecedor : fornecedorListNew) {
                if (!fornecedorListOld.contains(fornecedorListNewFornecedor)) {
                    fornecedorListNewFornecedor.getNotafiscalList().add(notafiscal);
                    fornecedorListNewFornecedor = em.merge(fornecedorListNewFornecedor);
                }
            }
            for (Protocolo protocoloListOldProtocolo : protocoloListOld) {
                if (!protocoloListNew.contains(protocoloListOldProtocolo)) {
                    protocoloListOldProtocolo.setNumeroNota(null);
                    protocoloListOldProtocolo = em.merge(protocoloListOldProtocolo);
                }
            }
            for (Protocolo protocoloListNewProtocolo : protocoloListNew) {
                if (!protocoloListOld.contains(protocoloListNewProtocolo)) {
                    Notafiscal oldNumeroNotaOfProtocoloListNewProtocolo = protocoloListNewProtocolo.getNumeroNota();
                    protocoloListNewProtocolo.setNumeroNota(notafiscal);
                    protocoloListNewProtocolo = em.merge(protocoloListNewProtocolo);
                    if (oldNumeroNotaOfProtocoloListNewProtocolo != null && !oldNumeroNotaOfProtocoloListNewProtocolo.equals(notafiscal)) {
                        oldNumeroNotaOfProtocoloListNewProtocolo.getProtocoloList().remove(protocoloListNewProtocolo);
                        oldNumeroNotaOfProtocoloListNewProtocolo = em.merge(oldNumeroNotaOfProtocoloListNewProtocolo);
                    }
                }
            }
            for (Possui possuiListNewPossui : possuiListNew) {
                if (!possuiListOld.contains(possuiListNewPossui)) {
                    Notafiscal oldNotafiscalOfPossuiListNewPossui = possuiListNewPossui.getNotafiscal();
                    possuiListNewPossui.setNotafiscal(notafiscal);
                    possuiListNewPossui = em.merge(possuiListNewPossui);
                    if (oldNotafiscalOfPossuiListNewPossui != null && !oldNotafiscalOfPossuiListNewPossui.equals(notafiscal)) {
                        oldNotafiscalOfPossuiListNewPossui.getPossuiList().remove(possuiListNewPossui);
                        oldNotafiscalOfPossuiListNewPossui = em.merge(oldNotafiscalOfPossuiListNewPossui);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notafiscal.getNumeroNota();
                if (findNotafiscal(id) == null) {
                    throw new NonexistentEntityException("The notafiscal with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notafiscal notafiscal;
            try {
                notafiscal = em.getReference(Notafiscal.class, id);
                notafiscal.getNumeroNota();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notafiscal with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Possui> possuiListOrphanCheck = notafiscal.getPossuiList();
            for (Possui possuiListOrphanCheckPossui : possuiListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Notafiscal (" + notafiscal + ") cannot be destroyed since the Possui " + possuiListOrphanCheckPossui + " in its possuiList field has a non-nullable notafiscal field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Fornecedor idFornecedor = notafiscal.getIdFornecedor();
            if (idFornecedor != null) {
                idFornecedor.getNotafiscalList().remove(notafiscal);
                idFornecedor = em.merge(idFornecedor);
            }
            Processo numeroProcesso = notafiscal.getNumeroProcesso();
            if (numeroProcesso != null) {
                numeroProcesso.getNotafiscalList().remove(notafiscal);
                numeroProcesso = em.merge(numeroProcesso);
            }
            Protocolo numeroProtocolo = notafiscal.getNumeroProtocolo();
            if (numeroProtocolo != null) {
                numeroProtocolo.setNumeroNota(null);
                numeroProtocolo = em.merge(numeroProtocolo);
            }
            List<Fornecedor> fornecedorList = notafiscal.getFornecedorList();
            for (Fornecedor fornecedorListFornecedor : fornecedorList) {
                fornecedorListFornecedor.getNotafiscalList().remove(notafiscal);
                fornecedorListFornecedor = em.merge(fornecedorListFornecedor);
            }
            List<Protocolo> protocoloList = notafiscal.getProtocoloList();
            for (Protocolo protocoloListProtocolo : protocoloList) {
                protocoloListProtocolo.setNumeroNota(null);
                protocoloListProtocolo = em.merge(protocoloListProtocolo);
            }
            em.remove(notafiscal);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Notafiscal> findNotafiscalEntities() {
        return findNotafiscalEntities(true, -1, -1);
    }

    public List<Notafiscal> findNotafiscalEntities(int maxResults, int firstResult) {
        return findNotafiscalEntities(false, maxResults, firstResult);
    }

    private List<Notafiscal> findNotafiscalEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Notafiscal.class));
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

    public Notafiscal findNotafiscal(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Notafiscal.class, id);
        } finally {
            em.close();
        }
    }

    public int getNotafiscalCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Notafiscal> rt = cq.from(Notafiscal.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
