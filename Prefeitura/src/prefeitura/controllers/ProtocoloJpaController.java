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
import prefeitura.entities.Possui;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.IllegalOrphanException;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.controllers.exceptions.PreexistingEntityException;
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

    public void create(Protocolo protocolo) throws PreexistingEntityException, Exception {
        if (protocolo.getPossuiList() == null) {
            protocolo.setPossuiList(new ArrayList<Possui>());
        }
        if (protocolo.getNotafiscalList() == null) {
            protocolo.setNotafiscalList(new ArrayList<Notafiscal>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Notafiscal numeroNota = protocolo.getNumeroNota();
            if (numeroNota != null) {
                numeroNota = em.getReference(numeroNota.getClass(), numeroNota.getNumeroNota());
                protocolo.setNumeroNota(numeroNota);
            }
            List<Possui> attachedPossuiList = new ArrayList<Possui>();
            for (Possui possuiListPossuiToAttach : protocolo.getPossuiList()) {
                possuiListPossuiToAttach = em.getReference(possuiListPossuiToAttach.getClass(), possuiListPossuiToAttach.getPossuiPK());
                attachedPossuiList.add(possuiListPossuiToAttach);
            }
            protocolo.setPossuiList(attachedPossuiList);
            List<Notafiscal> attachedNotafiscalList = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNotafiscalToAttach : protocolo.getNotafiscalList()) {
                notafiscalListNotafiscalToAttach = em.getReference(notafiscalListNotafiscalToAttach.getClass(), notafiscalListNotafiscalToAttach.getNumeroNota());
                attachedNotafiscalList.add(notafiscalListNotafiscalToAttach);
            }
            protocolo.setNotafiscalList(attachedNotafiscalList);
            em.persist(protocolo);
            if (numeroNota != null) {
                numeroNota.getProtocoloList().add(protocolo);
                numeroNota = em.merge(numeroNota);
            }
            for (Possui possuiListPossui : protocolo.getPossuiList()) {
                Protocolo oldProtocoloOfPossuiListPossui = possuiListPossui.getProtocolo();
                possuiListPossui.setProtocolo(protocolo);
                possuiListPossui = em.merge(possuiListPossui);
                if (oldProtocoloOfPossuiListPossui != null) {
                    oldProtocoloOfPossuiListPossui.getPossuiList().remove(possuiListPossui);
                    oldProtocoloOfPossuiListPossui = em.merge(oldProtocoloOfPossuiListPossui);
                }
            }
            for (Notafiscal notafiscalListNotafiscal : protocolo.getNotafiscalList()) {
                Protocolo oldNumeroProtocoloOfNotafiscalListNotafiscal = notafiscalListNotafiscal.getNumeroProtocolo();
                notafiscalListNotafiscal.setNumeroProtocolo(protocolo);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
                if (oldNumeroProtocoloOfNotafiscalListNotafiscal != null) {
                    oldNumeroProtocoloOfNotafiscalListNotafiscal.getNotafiscalList().remove(notafiscalListNotafiscal);
                    oldNumeroProtocoloOfNotafiscalListNotafiscal = em.merge(oldNumeroProtocoloOfNotafiscalListNotafiscal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProtocolo(protocolo.getNumeroProtocolo()) != null) {
                throw new PreexistingEntityException("Protocolo " + protocolo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Protocolo protocolo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Protocolo persistentProtocolo = em.find(Protocolo.class, protocolo.getNumeroProtocolo());
            Notafiscal numeroNotaOld = persistentProtocolo.getNumeroNota();
            Notafiscal numeroNotaNew = protocolo.getNumeroNota();
            List<Possui> possuiListOld = persistentProtocolo.getPossuiList();
            List<Possui> possuiListNew = protocolo.getPossuiList();
            List<Notafiscal> notafiscalListOld = persistentProtocolo.getNotafiscalList();
            List<Notafiscal> notafiscalListNew = protocolo.getNotafiscalList();
            List<String> illegalOrphanMessages = null;
            for (Possui possuiListOldPossui : possuiListOld) {
                if (!possuiListNew.contains(possuiListOldPossui)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Possui " + possuiListOldPossui + " since its protocolo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (numeroNotaNew != null) {
                numeroNotaNew = em.getReference(numeroNotaNew.getClass(), numeroNotaNew.getNumeroNota());
                protocolo.setNumeroNota(numeroNotaNew);
            }
            List<Possui> attachedPossuiListNew = new ArrayList<Possui>();
            for (Possui possuiListNewPossuiToAttach : possuiListNew) {
                possuiListNewPossuiToAttach = em.getReference(possuiListNewPossuiToAttach.getClass(), possuiListNewPossuiToAttach.getPossuiPK());
                attachedPossuiListNew.add(possuiListNewPossuiToAttach);
            }
            possuiListNew = attachedPossuiListNew;
            protocolo.setPossuiList(possuiListNew);
            List<Notafiscal> attachedNotafiscalListNew = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNewNotafiscalToAttach : notafiscalListNew) {
                notafiscalListNewNotafiscalToAttach = em.getReference(notafiscalListNewNotafiscalToAttach.getClass(), notafiscalListNewNotafiscalToAttach.getNumeroNota());
                attachedNotafiscalListNew.add(notafiscalListNewNotafiscalToAttach);
            }
            notafiscalListNew = attachedNotafiscalListNew;
            protocolo.setNotafiscalList(notafiscalListNew);
            protocolo = em.merge(protocolo);
            if (numeroNotaOld != null && !numeroNotaOld.equals(numeroNotaNew)) {
                numeroNotaOld.getProtocoloList().remove(protocolo);
                numeroNotaOld = em.merge(numeroNotaOld);
            }
            if (numeroNotaNew != null && !numeroNotaNew.equals(numeroNotaOld)) {
                numeroNotaNew.getProtocoloList().add(protocolo);
                numeroNotaNew = em.merge(numeroNotaNew);
            }
            for (Possui possuiListNewPossui : possuiListNew) {
                if (!possuiListOld.contains(possuiListNewPossui)) {
                    Protocolo oldProtocoloOfPossuiListNewPossui = possuiListNewPossui.getProtocolo();
                    possuiListNewPossui.setProtocolo(protocolo);
                    possuiListNewPossui = em.merge(possuiListNewPossui);
                    if (oldProtocoloOfPossuiListNewPossui != null && !oldProtocoloOfPossuiListNewPossui.equals(protocolo)) {
                        oldProtocoloOfPossuiListNewPossui.getPossuiList().remove(possuiListNewPossui);
                        oldProtocoloOfPossuiListNewPossui = em.merge(oldProtocoloOfPossuiListNewPossui);
                    }
                }
            }
            for (Notafiscal notafiscalListOldNotafiscal : notafiscalListOld) {
                if (!notafiscalListNew.contains(notafiscalListOldNotafiscal)) {
                    notafiscalListOldNotafiscal.setNumeroProtocolo(null);
                    notafiscalListOldNotafiscal = em.merge(notafiscalListOldNotafiscal);
                }
            }
            for (Notafiscal notafiscalListNewNotafiscal : notafiscalListNew) {
                if (!notafiscalListOld.contains(notafiscalListNewNotafiscal)) {
                    Protocolo oldNumeroProtocoloOfNotafiscalListNewNotafiscal = notafiscalListNewNotafiscal.getNumeroProtocolo();
                    notafiscalListNewNotafiscal.setNumeroProtocolo(protocolo);
                    notafiscalListNewNotafiscal = em.merge(notafiscalListNewNotafiscal);
                    if (oldNumeroProtocoloOfNotafiscalListNewNotafiscal != null && !oldNumeroProtocoloOfNotafiscalListNewNotafiscal.equals(protocolo)) {
                        oldNumeroProtocoloOfNotafiscalListNewNotafiscal.getNotafiscalList().remove(notafiscalListNewNotafiscal);
                        oldNumeroProtocoloOfNotafiscalListNewNotafiscal = em.merge(oldNumeroProtocoloOfNotafiscalListNewNotafiscal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = protocolo.getNumeroProtocolo();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Protocolo protocolo;
            try {
                protocolo = em.getReference(Protocolo.class, id);
                protocolo.getNumeroProtocolo();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The protocolo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Possui> possuiListOrphanCheck = protocolo.getPossuiList();
            for (Possui possuiListOrphanCheckPossui : possuiListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Protocolo (" + protocolo + ") cannot be destroyed since the Possui " + possuiListOrphanCheckPossui + " in its possuiList field has a non-nullable protocolo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Notafiscal numeroNota = protocolo.getNumeroNota();
            if (numeroNota != null) {
                numeroNota.getProtocoloList().remove(protocolo);
                numeroNota = em.merge(numeroNota);
            }
            List<Notafiscal> notafiscalList = protocolo.getNotafiscalList();
            for (Notafiscal notafiscalListNotafiscal : notafiscalList) {
                notafiscalListNotafiscal.setNumeroProtocolo(null);
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
