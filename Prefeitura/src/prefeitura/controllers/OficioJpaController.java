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
import prefeitura.entities.Protocolo;
import prefeitura.entities.Secretaria;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Oficio;
import prefeitura.entities.Processo;

/**
 *
 * @author lucia
 */
public class OficioJpaController implements Serializable {

    public OficioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Oficio oficio) {
        if (oficio.getProtocoloList() == null) {
            oficio.setProtocoloList(new ArrayList<Protocolo>());
        }
        if (oficio.getProcessoList() == null) {
            oficio.setProcessoList(new ArrayList<Processo>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Protocolo idProtocolo = oficio.getIdProtocolo();
            if (idProtocolo != null) {
                idProtocolo = em.getReference(idProtocolo.getClass(), idProtocolo.getIdProtocolo());
                oficio.setIdProtocolo(idProtocolo);
            }
            Secretaria idSecretaria = oficio.getIdSecretaria();
            if (idSecretaria != null) {
                idSecretaria = em.getReference(idSecretaria.getClass(), idSecretaria.getIdSecretaria());
                oficio.setIdSecretaria(idSecretaria);
            }
            List<Protocolo> attachedProtocoloList = new ArrayList<Protocolo>();
            for (Protocolo protocoloListProtocoloToAttach : oficio.getProtocoloList()) {
                protocoloListProtocoloToAttach = em.getReference(protocoloListProtocoloToAttach.getClass(), protocoloListProtocoloToAttach.getIdProtocolo());
                attachedProtocoloList.add(protocoloListProtocoloToAttach);
            }
            oficio.setProtocoloList(attachedProtocoloList);
            List<Processo> attachedProcessoList = new ArrayList<Processo>();
            for (Processo processoListProcessoToAttach : oficio.getProcessoList()) {
                processoListProcessoToAttach = em.getReference(processoListProcessoToAttach.getClass(), processoListProcessoToAttach.getIdProcesso());
                attachedProcessoList.add(processoListProcessoToAttach);
            }
            oficio.setProcessoList(attachedProcessoList);
            em.persist(oficio);
            if (idProtocolo != null) {
                Oficio oldIdOficioOfIdProtocolo = idProtocolo.getIdOficio();
                if (oldIdOficioOfIdProtocolo != null) {
                    oldIdOficioOfIdProtocolo.setIdProtocolo(null);
                    oldIdOficioOfIdProtocolo = em.merge(oldIdOficioOfIdProtocolo);
                }
                idProtocolo.setIdOficio(oficio);
                idProtocolo = em.merge(idProtocolo);
            }
            if (idSecretaria != null) {
                idSecretaria.getOficioList().add(oficio);
                idSecretaria = em.merge(idSecretaria);
            }
            for (Protocolo protocoloListProtocolo : oficio.getProtocoloList()) {
                Oficio oldIdOficioOfProtocoloListProtocolo = protocoloListProtocolo.getIdOficio();
                protocoloListProtocolo.setIdOficio(oficio);
                protocoloListProtocolo = em.merge(protocoloListProtocolo);
                if (oldIdOficioOfProtocoloListProtocolo != null) {
                    oldIdOficioOfProtocoloListProtocolo.getProtocoloList().remove(protocoloListProtocolo);
                    oldIdOficioOfProtocoloListProtocolo = em.merge(oldIdOficioOfProtocoloListProtocolo);
                }
            }
            for (Processo processoListProcesso : oficio.getProcessoList()) {
                Oficio oldIdOficioOfProcessoListProcesso = processoListProcesso.getIdOficio();
                processoListProcesso.setIdOficio(oficio);
                processoListProcesso = em.merge(processoListProcesso);
                if (oldIdOficioOfProcessoListProcesso != null) {
                    oldIdOficioOfProcessoListProcesso.getProcessoList().remove(processoListProcesso);
                    oldIdOficioOfProcessoListProcesso = em.merge(oldIdOficioOfProcessoListProcesso);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Oficio oficio) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Oficio persistentOficio = em.find(Oficio.class, oficio.getIdOficio());
            Protocolo idProtocoloOld = persistentOficio.getIdProtocolo();
            Protocolo idProtocoloNew = oficio.getIdProtocolo();
            Secretaria idSecretariaOld = persistentOficio.getIdSecretaria();
            Secretaria idSecretariaNew = oficio.getIdSecretaria();
            List<Protocolo> protocoloListOld = persistentOficio.getProtocoloList();
            List<Protocolo> protocoloListNew = oficio.getProtocoloList();
            List<Processo> processoListOld = persistentOficio.getProcessoList();
            List<Processo> processoListNew = oficio.getProcessoList();
            if (idProtocoloNew != null) {
                idProtocoloNew = em.getReference(idProtocoloNew.getClass(), idProtocoloNew.getIdProtocolo());
                oficio.setIdProtocolo(idProtocoloNew);
            }
            if (idSecretariaNew != null) {
                idSecretariaNew = em.getReference(idSecretariaNew.getClass(), idSecretariaNew.getIdSecretaria());
                oficio.setIdSecretaria(idSecretariaNew);
            }
            List<Protocolo> attachedProtocoloListNew = new ArrayList<Protocolo>();
            for (Protocolo protocoloListNewProtocoloToAttach : protocoloListNew) {
                protocoloListNewProtocoloToAttach = em.getReference(protocoloListNewProtocoloToAttach.getClass(), protocoloListNewProtocoloToAttach.getIdProtocolo());
                attachedProtocoloListNew.add(protocoloListNewProtocoloToAttach);
            }
            protocoloListNew = attachedProtocoloListNew;
            oficio.setProtocoloList(protocoloListNew);
            List<Processo> attachedProcessoListNew = new ArrayList<Processo>();
            for (Processo processoListNewProcessoToAttach : processoListNew) {
                processoListNewProcessoToAttach = em.getReference(processoListNewProcessoToAttach.getClass(), processoListNewProcessoToAttach.getIdProcesso());
                attachedProcessoListNew.add(processoListNewProcessoToAttach);
            }
            processoListNew = attachedProcessoListNew;
            oficio.setProcessoList(processoListNew);
            oficio = em.merge(oficio);
            if (idProtocoloOld != null && !idProtocoloOld.equals(idProtocoloNew)) {
                idProtocoloOld.setIdOficio(null);
                idProtocoloOld = em.merge(idProtocoloOld);
            }
            if (idProtocoloNew != null && !idProtocoloNew.equals(idProtocoloOld)) {
                Oficio oldIdOficioOfIdProtocolo = idProtocoloNew.getIdOficio();
                if (oldIdOficioOfIdProtocolo != null) {
                    oldIdOficioOfIdProtocolo.setIdProtocolo(null);
                    oldIdOficioOfIdProtocolo = em.merge(oldIdOficioOfIdProtocolo);
                }
                idProtocoloNew.setIdOficio(oficio);
                idProtocoloNew = em.merge(idProtocoloNew);
            }
            if (idSecretariaOld != null && !idSecretariaOld.equals(idSecretariaNew)) {
                idSecretariaOld.getOficioList().remove(oficio);
                idSecretariaOld = em.merge(idSecretariaOld);
            }
            if (idSecretariaNew != null && !idSecretariaNew.equals(idSecretariaOld)) {
                idSecretariaNew.getOficioList().add(oficio);
                idSecretariaNew = em.merge(idSecretariaNew);
            }
            for (Protocolo protocoloListOldProtocolo : protocoloListOld) {
                if (!protocoloListNew.contains(protocoloListOldProtocolo)) {
                    protocoloListOldProtocolo.setIdOficio(null);
                    protocoloListOldProtocolo = em.merge(protocoloListOldProtocolo);
                }
            }
            for (Protocolo protocoloListNewProtocolo : protocoloListNew) {
                if (!protocoloListOld.contains(protocoloListNewProtocolo)) {
                    Oficio oldIdOficioOfProtocoloListNewProtocolo = protocoloListNewProtocolo.getIdOficio();
                    protocoloListNewProtocolo.setIdOficio(oficio);
                    protocoloListNewProtocolo = em.merge(protocoloListNewProtocolo);
                    if (oldIdOficioOfProtocoloListNewProtocolo != null && !oldIdOficioOfProtocoloListNewProtocolo.equals(oficio)) {
                        oldIdOficioOfProtocoloListNewProtocolo.getProtocoloList().remove(protocoloListNewProtocolo);
                        oldIdOficioOfProtocoloListNewProtocolo = em.merge(oldIdOficioOfProtocoloListNewProtocolo);
                    }
                }
            }
            for (Processo processoListOldProcesso : processoListOld) {
                if (!processoListNew.contains(processoListOldProcesso)) {
                    processoListOldProcesso.setIdOficio(null);
                    processoListOldProcesso = em.merge(processoListOldProcesso);
                }
            }
            for (Processo processoListNewProcesso : processoListNew) {
                if (!processoListOld.contains(processoListNewProcesso)) {
                    Oficio oldIdOficioOfProcessoListNewProcesso = processoListNewProcesso.getIdOficio();
                    processoListNewProcesso.setIdOficio(oficio);
                    processoListNewProcesso = em.merge(processoListNewProcesso);
                    if (oldIdOficioOfProcessoListNewProcesso != null && !oldIdOficioOfProcessoListNewProcesso.equals(oficio)) {
                        oldIdOficioOfProcessoListNewProcesso.getProcessoList().remove(processoListNewProcesso);
                        oldIdOficioOfProcessoListNewProcesso = em.merge(oldIdOficioOfProcessoListNewProcesso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = oficio.getIdOficio();
                if (findOficio(id) == null) {
                    throw new NonexistentEntityException("The oficio with id " + id + " no longer exists.");
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
            Oficio oficio;
            try {
                oficio = em.getReference(Oficio.class, id);
                oficio.getIdOficio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The oficio with id " + id + " no longer exists.", enfe);
            }
            Protocolo idProtocolo = oficio.getIdProtocolo();
            if (idProtocolo != null) {
                idProtocolo.setIdOficio(null);
                idProtocolo = em.merge(idProtocolo);
            }
            Secretaria idSecretaria = oficio.getIdSecretaria();
            if (idSecretaria != null) {
                idSecretaria.getOficioList().remove(oficio);
                idSecretaria = em.merge(idSecretaria);
            }
            List<Protocolo> protocoloList = oficio.getProtocoloList();
            for (Protocolo protocoloListProtocolo : protocoloList) {
                protocoloListProtocolo.setIdOficio(null);
                protocoloListProtocolo = em.merge(protocoloListProtocolo);
            }
            List<Processo> processoList = oficio.getProcessoList();
            for (Processo processoListProcesso : processoList) {
                processoListProcesso.setIdOficio(null);
                processoListProcesso = em.merge(processoListProcesso);
            }
            em.remove(oficio);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Oficio> findOficioEntities() {
        return findOficioEntities(true, -1, -1);
    }

    public List<Oficio> findOficioEntities(int maxResults, int firstResult) {
        return findOficioEntities(false, maxResults, firstResult);
    }

    private List<Oficio> findOficioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Oficio.class));
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

    public Oficio findOficio(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Oficio.class, id);
        } finally {
            em.close();
        }
    }

    public int getOficioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Oficio> rt = cq.from(Oficio.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
