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
import prefeitura.entities.Oficio;
import prefeitura.entities.Processo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Protocolo;

/**
 *
 * @author lucia
 */
public class ProtocoloJpaController implements Serializable {

    public ProtocoloJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Protocolo protocolo) {
        if (protocolo.getProcessoList() == null) {
            protocolo.setProcessoList(new ArrayList<Processo>());
        }
        if (protocolo.getNotafiscalList() == null) {
            protocolo.setNotafiscalList(new ArrayList<Notafiscal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notafiscal idNota = protocolo.getIdNota();
            if (idNota != null) {
                idNota = em.getReference(idNota.getClass(), idNota.getIdNotaFiscal());
                protocolo.setIdNota(idNota);
            }
            Oficio idOficio = protocolo.getIdOficio();
            if (idOficio != null) {
                idOficio = em.getReference(idOficio.getClass(), idOficio.getIdOficio());
                protocolo.setIdOficio(idOficio);
            }
            Processo idProcesso = protocolo.getIdProcesso();
            if (idProcesso != null) {
                idProcesso = em.getReference(idProcesso.getClass(), idProcesso.getIdProcesso());
                protocolo.setIdProcesso(idProcesso);
            }
            Oficio oficio = protocolo.getOficio();
            if (oficio != null) {
                oficio = em.getReference(oficio.getClass(), oficio.getIdOficio());
                protocolo.setOficio(oficio);
            }
            List<Processo> attachedProcessoList = new ArrayList<Processo>();
            for (Processo processoListProcessoToAttach : protocolo.getProcessoList()) {
                processoListProcessoToAttach = em.getReference(processoListProcessoToAttach.getClass(), processoListProcessoToAttach.getIdProcesso());
                attachedProcessoList.add(processoListProcessoToAttach);
            }
            protocolo.setProcessoList(attachedProcessoList);
            List<Notafiscal> attachedNotafiscalList = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNotafiscalToAttach : protocolo.getNotafiscalList()) {
                notafiscalListNotafiscalToAttach = em.getReference(notafiscalListNotafiscalToAttach.getClass(), notafiscalListNotafiscalToAttach.getIdNotaFiscal());
                attachedNotafiscalList.add(notafiscalListNotafiscalToAttach);
            }
            protocolo.setNotafiscalList(attachedNotafiscalList);
            em.persist(protocolo);
            if (idNota != null) {
                idNota.getProtocoloList().add(protocolo);
                idNota = em.merge(idNota);
            }
            if (idOficio != null) {
                idOficio.getProtocoloList().add(protocolo);
                idOficio = em.merge(idOficio);
            }
            if (idProcesso != null) {
                idProcesso.getProtocoloList().add(protocolo);
                idProcesso = em.merge(idProcesso);
            }
            if (oficio != null) {
                Protocolo oldIdProtocoloOfOficio = oficio.getIdProtocolo();
                if (oldIdProtocoloOfOficio != null) {
                    oldIdProtocoloOfOficio.setOficio(null);
                    oldIdProtocoloOfOficio = em.merge(oldIdProtocoloOfOficio);
                }
                oficio.setIdProtocolo(protocolo);
                oficio = em.merge(oficio);
            }
            for (Processo processoListProcesso : protocolo.getProcessoList()) {
                Protocolo oldIdProtocoloOfProcessoListProcesso = processoListProcesso.getIdProtocolo();
                processoListProcesso.setIdProtocolo(protocolo);
                processoListProcesso = em.merge(processoListProcesso);
                if (oldIdProtocoloOfProcessoListProcesso != null) {
                    oldIdProtocoloOfProcessoListProcesso.getProcessoList().remove(processoListProcesso);
                    oldIdProtocoloOfProcessoListProcesso = em.merge(oldIdProtocoloOfProcessoListProcesso);
                }
            }
            for (Notafiscal notafiscalListNotafiscal : protocolo.getNotafiscalList()) {
                Protocolo oldIdProtocoloOfNotafiscalListNotafiscal = notafiscalListNotafiscal.getIdProtocolo();
                notafiscalListNotafiscal.setIdProtocolo(protocolo);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
                if (oldIdProtocoloOfNotafiscalListNotafiscal != null) {
                    oldIdProtocoloOfNotafiscalListNotafiscal.getNotafiscalList().remove(notafiscalListNotafiscal);
                    oldIdProtocoloOfNotafiscalListNotafiscal = em.merge(oldIdProtocoloOfNotafiscalListNotafiscal);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Protocolo protocolo) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Protocolo persistentProtocolo = em.find(Protocolo.class, protocolo.getIdProtocolo());
            Notafiscal idNotaOld = persistentProtocolo.getIdNota();
            Notafiscal idNotaNew = protocolo.getIdNota();
            Oficio idOficioOld = persistentProtocolo.getIdOficio();
            Oficio idOficioNew = protocolo.getIdOficio();
            Processo idProcessoOld = persistentProtocolo.getIdProcesso();
            Processo idProcessoNew = protocolo.getIdProcesso();
            Oficio oficioOld = persistentProtocolo.getOficio();
            Oficio oficioNew = protocolo.getOficio();
            List<Processo> processoListOld = persistentProtocolo.getProcessoList();
            List<Processo> processoListNew = protocolo.getProcessoList();
            List<Notafiscal> notafiscalListOld = persistentProtocolo.getNotafiscalList();
            List<Notafiscal> notafiscalListNew = protocolo.getNotafiscalList();
            if (idNotaNew != null) {
                idNotaNew = em.getReference(idNotaNew.getClass(), idNotaNew.getIdNotaFiscal());
                protocolo.setIdNota(idNotaNew);
            }
            if (idOficioNew != null) {
                idOficioNew = em.getReference(idOficioNew.getClass(), idOficioNew.getIdOficio());
                protocolo.setIdOficio(idOficioNew);
            }
            if (idProcessoNew != null) {
                idProcessoNew = em.getReference(idProcessoNew.getClass(), idProcessoNew.getIdProcesso());
                protocolo.setIdProcesso(idProcessoNew);
            }
            if (oficioNew != null) {
                oficioNew = em.getReference(oficioNew.getClass(), oficioNew.getIdOficio());
                protocolo.setOficio(oficioNew);
            }
            List<Processo> attachedProcessoListNew = new ArrayList<Processo>();
            for (Processo processoListNewProcessoToAttach : processoListNew) {
                processoListNewProcessoToAttach = em.getReference(processoListNewProcessoToAttach.getClass(), processoListNewProcessoToAttach.getIdProcesso());
                attachedProcessoListNew.add(processoListNewProcessoToAttach);
            }
            processoListNew = attachedProcessoListNew;
            protocolo.setProcessoList(processoListNew);
            List<Notafiscal> attachedNotafiscalListNew = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNewNotafiscalToAttach : notafiscalListNew) {
                notafiscalListNewNotafiscalToAttach = em.getReference(notafiscalListNewNotafiscalToAttach.getClass(), notafiscalListNewNotafiscalToAttach.getIdNotaFiscal());
                attachedNotafiscalListNew.add(notafiscalListNewNotafiscalToAttach);
            }
            notafiscalListNew = attachedNotafiscalListNew;
            protocolo.setNotafiscalList(notafiscalListNew);
            protocolo = em.merge(protocolo);
            if (idNotaOld != null && !idNotaOld.equals(idNotaNew)) {
                idNotaOld.getProtocoloList().remove(protocolo);
                idNotaOld = em.merge(idNotaOld);
            }
            if (idNotaNew != null && !idNotaNew.equals(idNotaOld)) {
                idNotaNew.getProtocoloList().add(protocolo);
                idNotaNew = em.merge(idNotaNew);
            }
            if (idOficioOld != null && !idOficioOld.equals(idOficioNew)) {
                idOficioOld.getProtocoloList().remove(protocolo);
                idOficioOld = em.merge(idOficioOld);
            }
            if (idOficioNew != null && !idOficioNew.equals(idOficioOld)) {
                idOficioNew.getProtocoloList().add(protocolo);
                idOficioNew = em.merge(idOficioNew);
            }
            if (idProcessoOld != null && !idProcessoOld.equals(idProcessoNew)) {
                idProcessoOld.getProtocoloList().remove(protocolo);
                idProcessoOld = em.merge(idProcessoOld);
            }
            if (idProcessoNew != null && !idProcessoNew.equals(idProcessoOld)) {
                idProcessoNew.getProtocoloList().add(protocolo);
                idProcessoNew = em.merge(idProcessoNew);
            }
            if (oficioOld != null && !oficioOld.equals(oficioNew)) {
                oficioOld.setIdProtocolo(null);
                oficioOld = em.merge(oficioOld);
            }
            if (oficioNew != null && !oficioNew.equals(oficioOld)) {
                Protocolo oldIdProtocoloOfOficio = oficioNew.getIdProtocolo();
                if (oldIdProtocoloOfOficio != null) {
                    oldIdProtocoloOfOficio.setOficio(null);
                    oldIdProtocoloOfOficio = em.merge(oldIdProtocoloOfOficio);
                }
                oficioNew.setIdProtocolo(protocolo);
                oficioNew = em.merge(oficioNew);
            }
            for (Processo processoListOldProcesso : processoListOld) {
                if (!processoListNew.contains(processoListOldProcesso)) {
                    processoListOldProcesso.setIdProtocolo(null);
                    processoListOldProcesso = em.merge(processoListOldProcesso);
                }
            }
            for (Processo processoListNewProcesso : processoListNew) {
                if (!processoListOld.contains(processoListNewProcesso)) {
                    Protocolo oldIdProtocoloOfProcessoListNewProcesso = processoListNewProcesso.getIdProtocolo();
                    processoListNewProcesso.setIdProtocolo(protocolo);
                    processoListNewProcesso = em.merge(processoListNewProcesso);
                    if (oldIdProtocoloOfProcessoListNewProcesso != null && !oldIdProtocoloOfProcessoListNewProcesso.equals(protocolo)) {
                        oldIdProtocoloOfProcessoListNewProcesso.getProcessoList().remove(processoListNewProcesso);
                        oldIdProtocoloOfProcessoListNewProcesso = em.merge(oldIdProtocoloOfProcessoListNewProcesso);
                    }
                }
            }
            for (Notafiscal notafiscalListOldNotafiscal : notafiscalListOld) {
                if (!notafiscalListNew.contains(notafiscalListOldNotafiscal)) {
                    notafiscalListOldNotafiscal.setIdProtocolo(null);
                    notafiscalListOldNotafiscal = em.merge(notafiscalListOldNotafiscal);
                }
            }
            for (Notafiscal notafiscalListNewNotafiscal : notafiscalListNew) {
                if (!notafiscalListOld.contains(notafiscalListNewNotafiscal)) {
                    Protocolo oldIdProtocoloOfNotafiscalListNewNotafiscal = notafiscalListNewNotafiscal.getIdProtocolo();
                    notafiscalListNewNotafiscal.setIdProtocolo(protocolo);
                    notafiscalListNewNotafiscal = em.merge(notafiscalListNewNotafiscal);
                    if (oldIdProtocoloOfNotafiscalListNewNotafiscal != null && !oldIdProtocoloOfNotafiscalListNewNotafiscal.equals(protocolo)) {
                        oldIdProtocoloOfNotafiscalListNewNotafiscal.getNotafiscalList().remove(notafiscalListNewNotafiscal);
                        oldIdProtocoloOfNotafiscalListNewNotafiscal = em.merge(oldIdProtocoloOfNotafiscalListNewNotafiscal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = protocolo.getIdProtocolo();
                if (findProtocolo(id) == null) {
                    throw new NonexistentEntityException("The protocolo with id " + id + " no longer exists.");
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
            Protocolo protocolo;
            try {
                protocolo = em.getReference(Protocolo.class, id);
                protocolo.getIdProtocolo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The protocolo with id " + id + " no longer exists.", enfe);
            }
            Notafiscal idNota = protocolo.getIdNota();
            if (idNota != null) {
                idNota.getProtocoloList().remove(protocolo);
                idNota = em.merge(idNota);
            }
            Oficio idOficio = protocolo.getIdOficio();
            if (idOficio != null) {
                idOficio.getProtocoloList().remove(protocolo);
                idOficio = em.merge(idOficio);
            }
            Processo idProcesso = protocolo.getIdProcesso();
            if (idProcesso != null) {
                idProcesso.getProtocoloList().remove(protocolo);
                idProcesso = em.merge(idProcesso);
            }
            Oficio oficio = protocolo.getOficio();
            if (oficio != null) {
                oficio.setIdProtocolo(null);
                oficio = em.merge(oficio);
            }
            List<Processo> processoList = protocolo.getProcessoList();
            for (Processo processoListProcesso : processoList) {
                processoListProcesso.setIdProtocolo(null);
                processoListProcesso = em.merge(processoListProcesso);
            }
            List<Notafiscal> notafiscalList = protocolo.getNotafiscalList();
            for (Notafiscal notafiscalListNotafiscal : notafiscalList) {
                notafiscalListNotafiscal.setIdProtocolo(null);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
            }
            em.remove(protocolo);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Protocolo> findProtocoloEntities() {
        return findProtocoloEntities(true, -1, -1);
    }

    public List<Protocolo> findProtocoloEntities(int maxResults, int firstResult) {
        return findProtocoloEntities(false, maxResults, firstResult);
    }

    private List<Protocolo> findProtocoloEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Protocolo.class));
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

    public Protocolo findProtocolo(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Protocolo.class, id);
        } finally {
            em.close();
        }
    }

    public int getProtocoloCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Protocolo> rt = cq.from(Protocolo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
