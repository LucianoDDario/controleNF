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
import prefeitura.entities.Processo;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.controllers.exceptions.PreexistingEntityException;
import prefeitura.entities.Oficio;
import prefeitura.entities.Usuario;

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
        if (oficio.getProcessoList() == null) {
            oficio.setProcessoList(new ArrayList<Processo>());
        }
        if (oficio.getSecretariaList() == null) {
            oficio.setSecretariaList(new ArrayList<Secretaria>());
        }
        if (oficio.getUsuarioList() == null) {
            oficio.setUsuarioList(new ArrayList<Usuario>());
        }
        if (oficio.getProcessoList1() == null) {
            oficio.setProcessoList1(new ArrayList<Processo>());
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
            List<Processo> attachedProcessoList = new ArrayList<Processo>();
            for (Processo processoListProcessoToAttach : oficio.getProcessoList()) {
                processoListProcessoToAttach = em.getReference(processoListProcessoToAttach.getClass(), processoListProcessoToAttach.getNumeroProcesso());
                attachedProcessoList.add(processoListProcessoToAttach);
            }
            oficio.setProcessoList(attachedProcessoList);
            List<Secretaria> attachedSecretariaList = new ArrayList<Secretaria>();
            for (Secretaria secretariaListSecretariaToAttach : oficio.getSecretariaList()) {
                secretariaListSecretariaToAttach = em.getReference(secretariaListSecretariaToAttach.getClass(), secretariaListSecretariaToAttach.getIdSecretaria());
                attachedSecretariaList.add(secretariaListSecretariaToAttach);
            }
            oficio.setSecretariaList(attachedSecretariaList);
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : oficio.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getIdUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            oficio.setUsuarioList(attachedUsuarioList);
            List<Processo> attachedProcessoList1 = new ArrayList<Processo>();
            for (Processo processoList1ProcessoToAttach : oficio.getProcessoList1()) {
                processoList1ProcessoToAttach = em.getReference(processoList1ProcessoToAttach.getClass(), processoList1ProcessoToAttach.getNumeroProcesso());
                attachedProcessoList1.add(processoList1ProcessoToAttach);
            }
            oficio.setProcessoList1(attachedProcessoList1);
            em.persist(oficio);
            if (idSecretaria != null) {
                idSecretaria.getOficioList().add(oficio);
                idSecretaria = em.merge(idSecretaria);
            }
            for (Processo processoListProcesso : oficio.getProcessoList()) {
                processoListProcesso.getOficioList().add(oficio);
                processoListProcesso = em.merge(processoListProcesso);
            }
            for (Secretaria secretariaListSecretaria : oficio.getSecretariaList()) {
                secretariaListSecretaria.getOficioList().add(oficio);
                secretariaListSecretaria = em.merge(secretariaListSecretaria);
            }
            for (Usuario usuarioListUsuario : oficio.getUsuarioList()) {
                usuarioListUsuario.getOficioList().add(oficio);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            for (Processo processoList1Processo : oficio.getProcessoList1()) {
                Oficio oldNumeroOficioOfProcessoList1Processo = processoList1Processo.getNumeroOficio();
                processoList1Processo.setNumeroOficio(oficio);
                processoList1Processo = em.merge(processoList1Processo);
                if (oldNumeroOficioOfProcessoList1Processo != null) {
                    oldNumeroOficioOfProcessoList1Processo.getProcessoList1().remove(processoList1Processo);
                    oldNumeroOficioOfProcessoList1Processo = em.merge(oldNumeroOficioOfProcessoList1Processo);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findOficio(oficio.getNumeroOficio()) != null) {
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
            Oficio persistentOficio = em.find(Oficio.class, oficio.getNumeroOficio());
            Secretaria idSecretariaOld = persistentOficio.getIdSecretaria();
            Secretaria idSecretariaNew = oficio.getIdSecretaria();
            List<Processo> processoListOld = persistentOficio.getProcessoList();
            List<Processo> processoListNew = oficio.getProcessoList();
            List<Secretaria> secretariaListOld = persistentOficio.getSecretariaList();
            List<Secretaria> secretariaListNew = oficio.getSecretariaList();
            List<Usuario> usuarioListOld = persistentOficio.getUsuarioList();
            List<Usuario> usuarioListNew = oficio.getUsuarioList();
            List<Processo> processoList1Old = persistentOficio.getProcessoList1();
            List<Processo> processoList1New = oficio.getProcessoList1();
            if (idSecretariaNew != null) {
                idSecretariaNew = em.getReference(idSecretariaNew.getClass(), idSecretariaNew.getIdSecretaria());
                oficio.setIdSecretaria(idSecretariaNew);
            }
            List<Processo> attachedProcessoListNew = new ArrayList<Processo>();
            for (Processo processoListNewProcessoToAttach : processoListNew) {
                processoListNewProcessoToAttach = em.getReference(processoListNewProcessoToAttach.getClass(), processoListNewProcessoToAttach.getNumeroProcesso());
                attachedProcessoListNew.add(processoListNewProcessoToAttach);
            }
            processoListNew = attachedProcessoListNew;
            oficio.setProcessoList(processoListNew);
            List<Secretaria> attachedSecretariaListNew = new ArrayList<Secretaria>();
            for (Secretaria secretariaListNewSecretariaToAttach : secretariaListNew) {
                secretariaListNewSecretariaToAttach = em.getReference(secretariaListNewSecretariaToAttach.getClass(), secretariaListNewSecretariaToAttach.getIdSecretaria());
                attachedSecretariaListNew.add(secretariaListNewSecretariaToAttach);
            }
            secretariaListNew = attachedSecretariaListNew;
            oficio.setSecretariaList(secretariaListNew);
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            oficio.setUsuarioList(usuarioListNew);
            List<Processo> attachedProcessoList1New = new ArrayList<Processo>();
            for (Processo processoList1NewProcessoToAttach : processoList1New) {
                processoList1NewProcessoToAttach = em.getReference(processoList1NewProcessoToAttach.getClass(), processoList1NewProcessoToAttach.getNumeroProcesso());
                attachedProcessoList1New.add(processoList1NewProcessoToAttach);
            }
            processoList1New = attachedProcessoList1New;
            oficio.setProcessoList1(processoList1New);
            oficio = em.merge(oficio);
            if (idSecretariaOld != null && !idSecretariaOld.equals(idSecretariaNew)) {
                idSecretariaOld.getOficioList().remove(oficio);
                idSecretariaOld = em.merge(idSecretariaOld);
            }
            if (idSecretariaNew != null && !idSecretariaNew.equals(idSecretariaOld)) {
                idSecretariaNew.getOficioList().add(oficio);
                idSecretariaNew = em.merge(idSecretariaNew);
            }
            for (Processo processoListOldProcesso : processoListOld) {
                if (!processoListNew.contains(processoListOldProcesso)) {
                    processoListOldProcesso.getOficioList().remove(oficio);
                    processoListOldProcesso = em.merge(processoListOldProcesso);
                }
            }
            for (Processo processoListNewProcesso : processoListNew) {
                if (!processoListOld.contains(processoListNewProcesso)) {
                    processoListNewProcesso.getOficioList().add(oficio);
                    processoListNewProcesso = em.merge(processoListNewProcesso);
                }
            }
            for (Secretaria secretariaListOldSecretaria : secretariaListOld) {
                if (!secretariaListNew.contains(secretariaListOldSecretaria)) {
                    secretariaListOldSecretaria.getOficioList().remove(oficio);
                    secretariaListOldSecretaria = em.merge(secretariaListOldSecretaria);
                }
            }
            for (Secretaria secretariaListNewSecretaria : secretariaListNew) {
                if (!secretariaListOld.contains(secretariaListNewSecretaria)) {
                    secretariaListNewSecretaria.getOficioList().add(oficio);
                    secretariaListNewSecretaria = em.merge(secretariaListNewSecretaria);
                }
            }
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.getOficioList().remove(oficio);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    usuarioListNewUsuario.getOficioList().add(oficio);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                }
            }
            for (Processo processoList1OldProcesso : processoList1Old) {
                if (!processoList1New.contains(processoList1OldProcesso)) {
                    processoList1OldProcesso.setNumeroOficio(null);
                    processoList1OldProcesso = em.merge(processoList1OldProcesso);
                }
            }
            for (Processo processoList1NewProcesso : processoList1New) {
                if (!processoList1Old.contains(processoList1NewProcesso)) {
                    Oficio oldNumeroOficioOfProcessoList1NewProcesso = processoList1NewProcesso.getNumeroOficio();
                    processoList1NewProcesso.setNumeroOficio(oficio);
                    processoList1NewProcesso = em.merge(processoList1NewProcesso);
                    if (oldNumeroOficioOfProcessoList1NewProcesso != null && !oldNumeroOficioOfProcessoList1NewProcesso.equals(oficio)) {
                        oldNumeroOficioOfProcessoList1NewProcesso.getProcessoList1().remove(processoList1NewProcesso);
                        oldNumeroOficioOfProcessoList1NewProcesso = em.merge(oldNumeroOficioOfProcessoList1NewProcesso);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = oficio.getNumeroOficio();
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
                oficio.getNumeroOficio();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The oficio with id " + id + " no longer exists.", enfe);
            }
            Secretaria idSecretaria = oficio.getIdSecretaria();
            if (idSecretaria != null) {
                idSecretaria.getOficioList().remove(oficio);
                idSecretaria = em.merge(idSecretaria);
            }
            List<Processo> processoList = oficio.getProcessoList();
            for (Processo processoListProcesso : processoList) {
                processoListProcesso.getOficioList().remove(oficio);
                processoListProcesso = em.merge(processoListProcesso);
            }
            List<Secretaria> secretariaList = oficio.getSecretariaList();
            for (Secretaria secretariaListSecretaria : secretariaList) {
                secretariaListSecretaria.getOficioList().remove(oficio);
                secretariaListSecretaria = em.merge(secretariaListSecretaria);
            }
            List<Usuario> usuarioList = oficio.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.getOficioList().remove(oficio);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            List<Processo> processoList1 = oficio.getProcessoList1();
            for (Processo processoList1Processo : processoList1) {
                processoList1Processo.setNumeroOficio(null);
                processoList1Processo = em.merge(processoList1Processo);
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
