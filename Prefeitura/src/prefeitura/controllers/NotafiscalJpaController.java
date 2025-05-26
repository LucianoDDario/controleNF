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
import prefeitura.entities.Possui;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Envia;
import prefeitura.entities.Notafiscal;

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

    public void create(Notafiscal notafiscal) {
        if (notafiscal.getPossuiList() == null) {
            notafiscal.setPossuiList(new ArrayList<Possui>());
        }
        if (notafiscal.getEnviaList() == null) {
            notafiscal.setEnviaList(new ArrayList<Envia>());
        }
        if (notafiscal.getProtocoloList() == null) {
            notafiscal.setProtocoloList(new ArrayList<Protocolo>());
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
            Processo idProcesso = notafiscal.getIdProcesso();
            if (idProcesso != null) {
                idProcesso = em.getReference(idProcesso.getClass(), idProcesso.getIdProcesso());
                notafiscal.setIdProcesso(idProcesso);
            }
            Protocolo idProtocolo = notafiscal.getIdProtocolo();
            if (idProtocolo != null) {
                idProtocolo = em.getReference(idProtocolo.getClass(), idProtocolo.getIdProtocolo());
                notafiscal.setIdProtocolo(idProtocolo);
            }
            List<Possui> attachedPossuiList = new ArrayList<Possui>();
            for (Possui possuiListPossuiToAttach : notafiscal.getPossuiList()) {
                possuiListPossuiToAttach = em.getReference(possuiListPossuiToAttach.getClass(), possuiListPossuiToAttach.getIdPossui());
                attachedPossuiList.add(possuiListPossuiToAttach);
            }
            notafiscal.setPossuiList(attachedPossuiList);
            List<Envia> attachedEnviaList = new ArrayList<Envia>();
            for (Envia enviaListEnviaToAttach : notafiscal.getEnviaList()) {
                enviaListEnviaToAttach = em.getReference(enviaListEnviaToAttach.getClass(), enviaListEnviaToAttach.getIdEnvia());
                attachedEnviaList.add(enviaListEnviaToAttach);
            }
            notafiscal.setEnviaList(attachedEnviaList);
            List<Protocolo> attachedProtocoloList = new ArrayList<Protocolo>();
            for (Protocolo protocoloListProtocoloToAttach : notafiscal.getProtocoloList()) {
                protocoloListProtocoloToAttach = em.getReference(protocoloListProtocoloToAttach.getClass(), protocoloListProtocoloToAttach.getIdProtocolo());
                attachedProtocoloList.add(protocoloListProtocoloToAttach);
            }
            notafiscal.setProtocoloList(attachedProtocoloList);
            em.persist(notafiscal);
            if (idFornecedor != null) {
                idFornecedor.getNotafiscalList().add(notafiscal);
                idFornecedor = em.merge(idFornecedor);
            }
            if (idProcesso != null) {
                idProcesso.getNotafiscalList().add(notafiscal);
                idProcesso = em.merge(idProcesso);
            }
            if (idProtocolo != null) {
                idProtocolo.getNotafiscalList().add(notafiscal);
                idProtocolo = em.merge(idProtocolo);
            }
            for (Possui possuiListPossui : notafiscal.getPossuiList()) {
                Notafiscal oldNumeroNotaOfPossuiListPossui = possuiListPossui.getNumeroNota();
                possuiListPossui.setNumeroNota(notafiscal);
                possuiListPossui = em.merge(possuiListPossui);
                if (oldNumeroNotaOfPossuiListPossui != null) {
                    oldNumeroNotaOfPossuiListPossui.getPossuiList().remove(possuiListPossui);
                    oldNumeroNotaOfPossuiListPossui = em.merge(oldNumeroNotaOfPossuiListPossui);
                }
            }
            for (Envia enviaListEnvia : notafiscal.getEnviaList()) {
                Notafiscal oldNumeroNotaOfEnviaListEnvia = enviaListEnvia.getNumeroNota();
                enviaListEnvia.setNumeroNota(notafiscal);
                enviaListEnvia = em.merge(enviaListEnvia);
                if (oldNumeroNotaOfEnviaListEnvia != null) {
                    oldNumeroNotaOfEnviaListEnvia.getEnviaList().remove(enviaListEnvia);
                    oldNumeroNotaOfEnviaListEnvia = em.merge(oldNumeroNotaOfEnviaListEnvia);
                }
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
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Notafiscal notafiscal) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notafiscal persistentNotafiscal = em.find(Notafiscal.class, notafiscal.getIdNotaFiscal());
            Fornecedor idFornecedorOld = persistentNotafiscal.getIdFornecedor();
            Fornecedor idFornecedorNew = notafiscal.getIdFornecedor();
            Processo idProcessoOld = persistentNotafiscal.getIdProcesso();
            Processo idProcessoNew = notafiscal.getIdProcesso();
            Protocolo idProtocoloOld = persistentNotafiscal.getIdProtocolo();
            Protocolo idProtocoloNew = notafiscal.getIdProtocolo();
            List<Possui> possuiListOld = persistentNotafiscal.getPossuiList();
            List<Possui> possuiListNew = notafiscal.getPossuiList();
            List<Envia> enviaListOld = persistentNotafiscal.getEnviaList();
            List<Envia> enviaListNew = notafiscal.getEnviaList();
            List<Protocolo> protocoloListOld = persistentNotafiscal.getProtocoloList();
            List<Protocolo> protocoloListNew = notafiscal.getProtocoloList();
            if (idFornecedorNew != null) {
                idFornecedorNew = em.getReference(idFornecedorNew.getClass(), idFornecedorNew.getIdFornecedor());
                notafiscal.setIdFornecedor(idFornecedorNew);
            }
            if (idProcessoNew != null) {
                idProcessoNew = em.getReference(idProcessoNew.getClass(), idProcessoNew.getIdProcesso());
                notafiscal.setIdProcesso(idProcessoNew);
            }
            if (idProtocoloNew != null) {
                idProtocoloNew = em.getReference(idProtocoloNew.getClass(), idProtocoloNew.getIdProtocolo());
                notafiscal.setIdProtocolo(idProtocoloNew);
            }
            List<Possui> attachedPossuiListNew = new ArrayList<Possui>();
            for (Possui possuiListNewPossuiToAttach : possuiListNew) {
                possuiListNewPossuiToAttach = em.getReference(possuiListNewPossuiToAttach.getClass(), possuiListNewPossuiToAttach.getIdPossui());
                attachedPossuiListNew.add(possuiListNewPossuiToAttach);
            }
            possuiListNew = attachedPossuiListNew;
            notafiscal.setPossuiList(possuiListNew);
            List<Envia> attachedEnviaListNew = new ArrayList<Envia>();
            for (Envia enviaListNewEnviaToAttach : enviaListNew) {
                enviaListNewEnviaToAttach = em.getReference(enviaListNewEnviaToAttach.getClass(), enviaListNewEnviaToAttach.getIdEnvia());
                attachedEnviaListNew.add(enviaListNewEnviaToAttach);
            }
            enviaListNew = attachedEnviaListNew;
            notafiscal.setEnviaList(enviaListNew);
            List<Protocolo> attachedProtocoloListNew = new ArrayList<Protocolo>();
            for (Protocolo protocoloListNewProtocoloToAttach : protocoloListNew) {
                protocoloListNewProtocoloToAttach = em.getReference(protocoloListNewProtocoloToAttach.getClass(), protocoloListNewProtocoloToAttach.getIdProtocolo());
                attachedProtocoloListNew.add(protocoloListNewProtocoloToAttach);
            }
            protocoloListNew = attachedProtocoloListNew;
            notafiscal.setProtocoloList(protocoloListNew);
            notafiscal = em.merge(notafiscal);
            if (idFornecedorOld != null && !idFornecedorOld.equals(idFornecedorNew)) {
                idFornecedorOld.getNotafiscalList().remove(notafiscal);
                idFornecedorOld = em.merge(idFornecedorOld);
            }
            if (idFornecedorNew != null && !idFornecedorNew.equals(idFornecedorOld)) {
                idFornecedorNew.getNotafiscalList().add(notafiscal);
                idFornecedorNew = em.merge(idFornecedorNew);
            }
            if (idProcessoOld != null && !idProcessoOld.equals(idProcessoNew)) {
                idProcessoOld.getNotafiscalList().remove(notafiscal);
                idProcessoOld = em.merge(idProcessoOld);
            }
            if (idProcessoNew != null && !idProcessoNew.equals(idProcessoOld)) {
                idProcessoNew.getNotafiscalList().add(notafiscal);
                idProcessoNew = em.merge(idProcessoNew);
            }
            if (idProtocoloOld != null && !idProtocoloOld.equals(idProtocoloNew)) {
                idProtocoloOld.getNotafiscalList().remove(notafiscal);
                idProtocoloOld = em.merge(idProtocoloOld);
            }
            if (idProtocoloNew != null && !idProtocoloNew.equals(idProtocoloOld)) {
                idProtocoloNew.getNotafiscalList().add(notafiscal);
                idProtocoloNew = em.merge(idProtocoloNew);
            }
            for (Possui possuiListOldPossui : possuiListOld) {
                if (!possuiListNew.contains(possuiListOldPossui)) {
                    possuiListOldPossui.setNumeroNota(null);
                    possuiListOldPossui = em.merge(possuiListOldPossui);
                }
            }
            for (Possui possuiListNewPossui : possuiListNew) {
                if (!possuiListOld.contains(possuiListNewPossui)) {
                    Notafiscal oldNumeroNotaOfPossuiListNewPossui = possuiListNewPossui.getNumeroNota();
                    possuiListNewPossui.setNumeroNota(notafiscal);
                    possuiListNewPossui = em.merge(possuiListNewPossui);
                    if (oldNumeroNotaOfPossuiListNewPossui != null && !oldNumeroNotaOfPossuiListNewPossui.equals(notafiscal)) {
                        oldNumeroNotaOfPossuiListNewPossui.getPossuiList().remove(possuiListNewPossui);
                        oldNumeroNotaOfPossuiListNewPossui = em.merge(oldNumeroNotaOfPossuiListNewPossui);
                    }
                }
            }
            for (Envia enviaListOldEnvia : enviaListOld) {
                if (!enviaListNew.contains(enviaListOldEnvia)) {
                    enviaListOldEnvia.setNumeroNota(null);
                    enviaListOldEnvia = em.merge(enviaListOldEnvia);
                }
            }
            for (Envia enviaListNewEnvia : enviaListNew) {
                if (!enviaListOld.contains(enviaListNewEnvia)) {
                    Notafiscal oldNumeroNotaOfEnviaListNewEnvia = enviaListNewEnvia.getNumeroNota();
                    enviaListNewEnvia.setNumeroNota(notafiscal);
                    enviaListNewEnvia = em.merge(enviaListNewEnvia);
                    if (oldNumeroNotaOfEnviaListNewEnvia != null && !oldNumeroNotaOfEnviaListNewEnvia.equals(notafiscal)) {
                        oldNumeroNotaOfEnviaListNewEnvia.getEnviaList().remove(enviaListNewEnvia);
                        oldNumeroNotaOfEnviaListNewEnvia = em.merge(oldNumeroNotaOfEnviaListNewEnvia);
                    }
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
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = notafiscal.getIdNotaFiscal();
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

    public void destroy(Integer id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notafiscal notafiscal;
            try {
                notafiscal = em.getReference(Notafiscal.class, id);
                notafiscal.getIdNotaFiscal();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The notafiscal with id " + id + " no longer exists.", enfe);
            }
            Fornecedor idFornecedor = notafiscal.getIdFornecedor();
            if (idFornecedor != null) {
                idFornecedor.getNotafiscalList().remove(notafiscal);
                idFornecedor = em.merge(idFornecedor);
            }
            Processo idProcesso = notafiscal.getIdProcesso();
            if (idProcesso != null) {
                idProcesso.getNotafiscalList().remove(notafiscal);
                idProcesso = em.merge(idProcesso);
            }
            Protocolo idProtocolo = notafiscal.getIdProtocolo();
            if (idProtocolo != null) {
                idProtocolo.getNotafiscalList().remove(notafiscal);
                idProtocolo = em.merge(idProtocolo);
            }
            List<Possui> possuiList = notafiscal.getPossuiList();
            for (Possui possuiListPossui : possuiList) {
                possuiListPossui.setNumeroNota(null);
                possuiListPossui = em.merge(possuiListPossui);
            }
            List<Envia> enviaList = notafiscal.getEnviaList();
            for (Envia enviaListEnvia : enviaList) {
                enviaListEnvia.setNumeroNota(null);
                enviaListEnvia = em.merge(enviaListEnvia);
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
