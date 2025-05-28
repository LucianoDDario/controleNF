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
import prefeitura.entities.Secretaria;
import prefeitura.entities.Possui1;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.controllers.exceptions.PreexistingEntityException;
import prefeitura.entities.Envia1;
import prefeitura.entities.Oficio;
import prefeitura.entities.Processo;
import prefeitura.entities.Recebe;

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

    public void create(Oficio oficio) throws PreexistingEntityException, Exception {
        if (oficio.getPossui1List() == null) {
            oficio.setPossui1List(new ArrayList<Possui1>());
        }
        if (oficio.getEnvia1List() == null) {
            oficio.setEnvia1List(new ArrayList<Envia1>());
        }
        if (oficio.getProcessoList() == null) {
            oficio.setProcessoList(new ArrayList<Processo>());
        }
        if (oficio.getRecebeList() == null) {
            oficio.setRecebeList(new ArrayList<Recebe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Secretaria idSecretaria = oficio.getIdSecretaria();
            if (idSecretaria != null) {
                idSecretaria = em.getReference(idSecretaria.getClass(), idSecretaria.getIdSecretaria());
                oficio.setIdSecretaria(idSecretaria);
            }
            List<Possui1> attachedPossui1List = new ArrayList<Possui1>();
            for (Possui1 possui1ListPossui1ToAttach : oficio.getPossui1List()) {
                possui1ListPossui1ToAttach = em.getReference(possui1ListPossui1ToAttach.getClass(), possui1ListPossui1ToAttach.getIdPossui1());
                attachedPossui1List.add(possui1ListPossui1ToAttach);
            }
            oficio.setPossui1List(attachedPossui1List);
            List<Envia1> attachedEnvia1List = new ArrayList<Envia1>();
            for (Envia1 envia1ListEnvia1ToAttach : oficio.getEnvia1List()) {
                envia1ListEnvia1ToAttach = em.getReference(envia1ListEnvia1ToAttach.getClass(), envia1ListEnvia1ToAttach.getIdEnvia1());
                attachedEnvia1List.add(envia1ListEnvia1ToAttach);
            }
            oficio.setEnvia1List(attachedEnvia1List);
            List<Processo> attachedProcessoList = new ArrayList<Processo>();
            for (Processo processoListProcessoToAttach : oficio.getProcessoList()) {
                processoListProcessoToAttach = em.getReference(processoListProcessoToAttach.getClass(), processoListProcessoToAttach.getIdProcesso());
                attachedProcessoList.add(processoListProcessoToAttach);
            }
            oficio.setProcessoList(attachedProcessoList);
            List<Recebe> attachedRecebeList = new ArrayList<Recebe>();
            for (Recebe recebeListRecebeToAttach : oficio.getRecebeList()) {
                recebeListRecebeToAttach = em.getReference(recebeListRecebeToAttach.getClass(), recebeListRecebeToAttach.getIdRecebe());
                attachedRecebeList.add(recebeListRecebeToAttach);
            }
            oficio.setRecebeList(attachedRecebeList);
            em.persist(oficio);
            if (idSecretaria != null) {
                idSecretaria.getOficioList().add(oficio);
                idSecretaria = em.merge(idSecretaria);
            }
            for (Possui1 possui1ListPossui1 : oficio.getPossui1List()) {
                Oficio oldNumeroOficioOfPossui1ListPossui1 = possui1ListPossui1.getNumeroOficio();
                possui1ListPossui1.setNumeroOficio(oficio);
                possui1ListPossui1 = em.merge(possui1ListPossui1);
                if (oldNumeroOficioOfPossui1ListPossui1 != null) {
                    oldNumeroOficioOfPossui1ListPossui1.getPossui1List().remove(possui1ListPossui1);
                    oldNumeroOficioOfPossui1ListPossui1 = em.merge(oldNumeroOficioOfPossui1ListPossui1);
                }
            }
            for (Envia1 envia1ListEnvia1 : oficio.getEnvia1List()) {
                Oficio oldNumeroOficioOfEnvia1ListEnvia1 = envia1ListEnvia1.getNumeroOficio();
                envia1ListEnvia1.setNumeroOficio(oficio);
                envia1ListEnvia1 = em.merge(envia1ListEnvia1);
                if (oldNumeroOficioOfEnvia1ListEnvia1 != null) {
                    oldNumeroOficioOfEnvia1ListEnvia1.getEnvia1List().remove(envia1ListEnvia1);
                    oldNumeroOficioOfEnvia1ListEnvia1 = em.merge(oldNumeroOficioOfEnvia1ListEnvia1);
                }
            }
            for (Processo processoListProcesso : oficio.getProcessoList()) {
                Oficio oldNumeroOficioOfProcessoListProcesso = processoListProcesso.getNumeroOficio();
                processoListProcesso.setNumeroOficio(oficio);
                processoListProcesso = em.merge(processoListProcesso);
                if (oldNumeroOficioOfProcessoListProcesso != null) {
                    oldNumeroOficioOfProcessoListProcesso.getProcessoList().remove(processoListProcesso);
                    oldNumeroOficioOfProcessoListProcesso = em.merge(oldNumeroOficioOfProcessoListProcesso);
                }
            }
            for (Recebe recebeListRecebe : oficio.getRecebeList()) {
                Oficio oldNumeroOficioOfRecebeListRecebe = recebeListRecebe.getNumeroOficio();
                recebeListRecebe.setNumeroOficio(oficio);
                recebeListRecebe = em.merge(recebeListRecebe);
                if (oldNumeroOficioOfRecebeListRecebe != null) {
                    oldNumeroOficioOfRecebeListRecebe.getRecebeList().remove(recebeListRecebe);
                    oldNumeroOficioOfRecebeListRecebe = em.merge(oldNumeroOficioOfRecebeListRecebe);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOficio(oficio.getIdOficio()) != null) {
                throw new PreexistingEntityException("Oficio " + oficio + " already exists.", ex);
            }
            throw ex;
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
            Secretaria idSecretariaOld = persistentOficio.getIdSecretaria();
            Secretaria idSecretariaNew = oficio.getIdSecretaria();
            List<Possui1> possui1ListOld = persistentOficio.getPossui1List();
            List<Possui1> possui1ListNew = oficio.getPossui1List();
            List<Envia1> envia1ListOld = persistentOficio.getEnvia1List();
            List<Envia1> envia1ListNew = oficio.getEnvia1List();
            List<Processo> processoListOld = persistentOficio.getProcessoList();
            List<Processo> processoListNew = oficio.getProcessoList();
            List<Recebe> recebeListOld = persistentOficio.getRecebeList();
            List<Recebe> recebeListNew = oficio.getRecebeList();
            if (idSecretariaNew != null) {
                idSecretariaNew = em.getReference(idSecretariaNew.getClass(), idSecretariaNew.getIdSecretaria());
                oficio.setIdSecretaria(idSecretariaNew);
            }
            List<Possui1> attachedPossui1ListNew = new ArrayList<Possui1>();
            for (Possui1 possui1ListNewPossui1ToAttach : possui1ListNew) {
                possui1ListNewPossui1ToAttach = em.getReference(possui1ListNewPossui1ToAttach.getClass(), possui1ListNewPossui1ToAttach.getIdPossui1());
                attachedPossui1ListNew.add(possui1ListNewPossui1ToAttach);
            }
            possui1ListNew = attachedPossui1ListNew;
            oficio.setPossui1List(possui1ListNew);
            List<Envia1> attachedEnvia1ListNew = new ArrayList<Envia1>();
            for (Envia1 envia1ListNewEnvia1ToAttach : envia1ListNew) {
                envia1ListNewEnvia1ToAttach = em.getReference(envia1ListNewEnvia1ToAttach.getClass(), envia1ListNewEnvia1ToAttach.getIdEnvia1());
                attachedEnvia1ListNew.add(envia1ListNewEnvia1ToAttach);
            }
            envia1ListNew = attachedEnvia1ListNew;
            oficio.setEnvia1List(envia1ListNew);
            List<Processo> attachedProcessoListNew = new ArrayList<Processo>();
            for (Processo processoListNewProcessoToAttach : processoListNew) {
                processoListNewProcessoToAttach = em.getReference(processoListNewProcessoToAttach.getClass(), processoListNewProcessoToAttach.getIdProcesso());
                attachedProcessoListNew.add(processoListNewProcessoToAttach);
            }
            processoListNew = attachedProcessoListNew;
            oficio.setProcessoList(processoListNew);
            List<Recebe> attachedRecebeListNew = new ArrayList<Recebe>();
            for (Recebe recebeListNewRecebeToAttach : recebeListNew) {
                recebeListNewRecebeToAttach = em.getReference(recebeListNewRecebeToAttach.getClass(), recebeListNewRecebeToAttach.getIdRecebe());
                attachedRecebeListNew.add(recebeListNewRecebeToAttach);
            }
            recebeListNew = attachedRecebeListNew;
            oficio.setRecebeList(recebeListNew);
            oficio = em.merge(oficio);
            if (idSecretariaOld != null && !idSecretariaOld.equals(idSecretariaNew)) {
                idSecretariaOld.getOficioList().remove(oficio);
                idSecretariaOld = em.merge(idSecretariaOld);
            }
            if (idSecretariaNew != null && !idSecretariaNew.equals(idSecretariaOld)) {
                idSecretariaNew.getOficioList().add(oficio);
                idSecretariaNew = em.merge(idSecretariaNew);
            }
            for (Possui1 possui1ListOldPossui1 : possui1ListOld) {
                if (!possui1ListNew.contains(possui1ListOldPossui1)) {
                    possui1ListOldPossui1.setNumeroOficio(null);
                    possui1ListOldPossui1 = em.merge(possui1ListOldPossui1);
                }
            }
            for (Possui1 possui1ListNewPossui1 : possui1ListNew) {
                if (!possui1ListOld.contains(possui1ListNewPossui1)) {
                    Oficio oldNumeroOficioOfPossui1ListNewPossui1 = possui1ListNewPossui1.getNumeroOficio();
                    possui1ListNewPossui1.setNumeroOficio(oficio);
                    possui1ListNewPossui1 = em.merge(possui1ListNewPossui1);
                    if (oldNumeroOficioOfPossui1ListNewPossui1 != null && !oldNumeroOficioOfPossui1ListNewPossui1.equals(oficio)) {
                        oldNumeroOficioOfPossui1ListNewPossui1.getPossui1List().remove(possui1ListNewPossui1);
                        oldNumeroOficioOfPossui1ListNewPossui1 = em.merge(oldNumeroOficioOfPossui1ListNewPossui1);
                    }
                }
            }
            for (Envia1 envia1ListOldEnvia1 : envia1ListOld) {
                if (!envia1ListNew.contains(envia1ListOldEnvia1)) {
                    envia1ListOldEnvia1.setNumeroOficio(null);
                    envia1ListOldEnvia1 = em.merge(envia1ListOldEnvia1);
                }
            }
            for (Envia1 envia1ListNewEnvia1 : envia1ListNew) {
                if (!envia1ListOld.contains(envia1ListNewEnvia1)) {
                    Oficio oldNumeroOficioOfEnvia1ListNewEnvia1 = envia1ListNewEnvia1.getNumeroOficio();
                    envia1ListNewEnvia1.setNumeroOficio(oficio);
                    envia1ListNewEnvia1 = em.merge(envia1ListNewEnvia1);
                    if (oldNumeroOficioOfEnvia1ListNewEnvia1 != null && !oldNumeroOficioOfEnvia1ListNewEnvia1.equals(oficio)) {
                        oldNumeroOficioOfEnvia1ListNewEnvia1.getEnvia1List().remove(envia1ListNewEnvia1);
                        oldNumeroOficioOfEnvia1ListNewEnvia1 = em.merge(oldNumeroOficioOfEnvia1ListNewEnvia1);
                    }
                }
            }
            for (Processo processoListOldProcesso : processoListOld) {
                if (!processoListNew.contains(processoListOldProcesso)) {
                    processoListOldProcesso.setNumeroOficio(null);
                    processoListOldProcesso = em.merge(processoListOldProcesso);
                }
            }
            for (Processo processoListNewProcesso : processoListNew) {
                if (!processoListOld.contains(processoListNewProcesso)) {
                    Oficio oldNumeroOficioOfProcessoListNewProcesso = processoListNewProcesso.getNumeroOficio();
                    processoListNewProcesso.setNumeroOficio(oficio);
                    processoListNewProcesso = em.merge(processoListNewProcesso);
                    if (oldNumeroOficioOfProcessoListNewProcesso != null && !oldNumeroOficioOfProcessoListNewProcesso.equals(oficio)) {
                        oldNumeroOficioOfProcessoListNewProcesso.getProcessoList().remove(processoListNewProcesso);
                        oldNumeroOficioOfProcessoListNewProcesso = em.merge(oldNumeroOficioOfProcessoListNewProcesso);
                    }
                }
            }
            for (Recebe recebeListOldRecebe : recebeListOld) {
                if (!recebeListNew.contains(recebeListOldRecebe)) {
                    recebeListOldRecebe.setNumeroOficio(null);
                    recebeListOldRecebe = em.merge(recebeListOldRecebe);
                }
            }
            for (Recebe recebeListNewRecebe : recebeListNew) {
                if (!recebeListOld.contains(recebeListNewRecebe)) {
                    Oficio oldNumeroOficioOfRecebeListNewRecebe = recebeListNewRecebe.getNumeroOficio();
                    recebeListNewRecebe.setNumeroOficio(oficio);
                    recebeListNewRecebe = em.merge(recebeListNewRecebe);
                    if (oldNumeroOficioOfRecebeListNewRecebe != null && !oldNumeroOficioOfRecebeListNewRecebe.equals(oficio)) {
                        oldNumeroOficioOfRecebeListNewRecebe.getRecebeList().remove(recebeListNewRecebe);
                        oldNumeroOficioOfRecebeListNewRecebe = em.merge(oldNumeroOficioOfRecebeListNewRecebe);
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
            Secretaria idSecretaria = oficio.getIdSecretaria();
            if (idSecretaria != null) {
                idSecretaria.getOficioList().remove(oficio);
                idSecretaria = em.merge(idSecretaria);
            }
            List<Possui1> possui1List = oficio.getPossui1List();
            for (Possui1 possui1ListPossui1 : possui1List) {
                possui1ListPossui1.setNumeroOficio(null);
                possui1ListPossui1 = em.merge(possui1ListPossui1);
            }
            List<Envia1> envia1List = oficio.getEnvia1List();
            for (Envia1 envia1ListEnvia1 : envia1List) {
                envia1ListEnvia1.setNumeroOficio(null);
                envia1ListEnvia1 = em.merge(envia1ListEnvia1);
            }
            List<Processo> processoList = oficio.getProcessoList();
            for (Processo processoListProcesso : processoList) {
                processoListProcesso.setNumeroOficio(null);
                processoListProcesso = em.merge(processoListProcesso);
            }
            List<Recebe> recebeList = oficio.getRecebeList();
            for (Recebe recebeListRecebe : recebeList) {
                recebeListRecebe.setNumeroOficio(null);
                recebeListRecebe = em.merge(recebeListRecebe);
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
