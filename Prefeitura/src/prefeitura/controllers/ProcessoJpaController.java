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
import prefeitura.entities.Usuario;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.IllegalOrphanException;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.controllers.exceptions.PreexistingEntityException;
import prefeitura.entities.Possui;
import prefeitura.entities.Notafiscal;
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

    public void create(Processo processo) throws PreexistingEntityException, Exception {
        if (processo.getUsuarioList() == null) {
            processo.setUsuarioList(new ArrayList<Usuario>());
        }
        if (processo.getOficioList() == null) {
            processo.setOficioList(new ArrayList<Oficio>());
        }
        if (processo.getPossuiList() == null) {
            processo.setPossuiList(new ArrayList<Possui>());
        }
        if (processo.getNotafiscalList() == null) {
            processo.setNotafiscalList(new ArrayList<Notafiscal>());
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
                numeroOficio = em.getReference(numeroOficio.getClass(), numeroOficio.getNumeroOficio());
                processo.setNumeroOficio(numeroOficio);
            }
            List<Usuario> attachedUsuarioList = new ArrayList<Usuario>();
            for (Usuario usuarioListUsuarioToAttach : processo.getUsuarioList()) {
                usuarioListUsuarioToAttach = em.getReference(usuarioListUsuarioToAttach.getClass(), usuarioListUsuarioToAttach.getIdUsuario());
                attachedUsuarioList.add(usuarioListUsuarioToAttach);
            }
            processo.setUsuarioList(attachedUsuarioList);
            List<Oficio> attachedOficioList = new ArrayList<Oficio>();
            for (Oficio oficioListOficioToAttach : processo.getOficioList()) {
                oficioListOficioToAttach = em.getReference(oficioListOficioToAttach.getClass(), oficioListOficioToAttach.getNumeroOficio());
                attachedOficioList.add(oficioListOficioToAttach);
            }
            processo.setOficioList(attachedOficioList);
            List<Possui> attachedPossuiList = new ArrayList<Possui>();
            for (Possui possuiListPossuiToAttach : processo.getPossuiList()) {
                possuiListPossuiToAttach = em.getReference(possuiListPossuiToAttach.getClass(), possuiListPossuiToAttach.getPossuiPK());
                attachedPossuiList.add(possuiListPossuiToAttach);
            }
            processo.setPossuiList(attachedPossuiList);
            List<Notafiscal> attachedNotafiscalList = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNotafiscalToAttach : processo.getNotafiscalList()) {
                notafiscalListNotafiscalToAttach = em.getReference(notafiscalListNotafiscalToAttach.getClass(), notafiscalListNotafiscalToAttach.getNumeroNota());
                attachedNotafiscalList.add(notafiscalListNotafiscalToAttach);
            }
            processo.setNotafiscalList(attachedNotafiscalList);
            em.persist(processo);
            if (idFornecedor != null) {
                idFornecedor.getProcessoList().add(processo);
                idFornecedor = em.merge(idFornecedor);
            }
            if (numeroOficio != null) {
                numeroOficio.getProcessoList().add(processo);
                numeroOficio = em.merge(numeroOficio);
            }
            for (Usuario usuarioListUsuario : processo.getUsuarioList()) {
                usuarioListUsuario.getProcessoList().add(processo);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            for (Oficio oficioListOficio : processo.getOficioList()) {
                oficioListOficio.getProcessoList().add(processo);
                oficioListOficio = em.merge(oficioListOficio);
            }
            for (Possui possuiListPossui : processo.getPossuiList()) {
                Processo oldProcessoOfPossuiListPossui = possuiListPossui.getProcesso();
                possuiListPossui.setProcesso(processo);
                possuiListPossui = em.merge(possuiListPossui);
                if (oldProcessoOfPossuiListPossui != null) {
                    oldProcessoOfPossuiListPossui.getPossuiList().remove(possuiListPossui);
                    oldProcessoOfPossuiListPossui = em.merge(oldProcessoOfPossuiListPossui);
                }
            }
            for (Notafiscal notafiscalListNotafiscal : processo.getNotafiscalList()) {
                Processo oldNumeroProcessoOfNotafiscalListNotafiscal = notafiscalListNotafiscal.getNumeroProcesso();
                notafiscalListNotafiscal.setNumeroProcesso(processo);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
                if (oldNumeroProcessoOfNotafiscalListNotafiscal != null) {
                    oldNumeroProcessoOfNotafiscalListNotafiscal.getNotafiscalList().remove(notafiscalListNotafiscal);
                    oldNumeroProcessoOfNotafiscalListNotafiscal = em.merge(oldNumeroProcessoOfNotafiscalListNotafiscal);
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findProcesso(processo.getNumeroProcesso()) != null) {
                throw new PreexistingEntityException("Processo " + processo + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Processo processo) throws IllegalOrphanException, NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processo persistentProcesso = em.find(Processo.class, processo.getNumeroProcesso());
            Fornecedor idFornecedorOld = persistentProcesso.getIdFornecedor();
            Fornecedor idFornecedorNew = processo.getIdFornecedor();
            Oficio numeroOficioOld = persistentProcesso.getNumeroOficio();
            Oficio numeroOficioNew = processo.getNumeroOficio();
            List<Usuario> usuarioListOld = persistentProcesso.getUsuarioList();
            List<Usuario> usuarioListNew = processo.getUsuarioList();
            List<Oficio> oficioListOld = persistentProcesso.getOficioList();
            List<Oficio> oficioListNew = processo.getOficioList();
            List<Possui> possuiListOld = persistentProcesso.getPossuiList();
            List<Possui> possuiListNew = processo.getPossuiList();
            List<Notafiscal> notafiscalListOld = persistentProcesso.getNotafiscalList();
            List<Notafiscal> notafiscalListNew = processo.getNotafiscalList();
            List<String> illegalOrphanMessages = null;
            for (Possui possuiListOldPossui : possuiListOld) {
                if (!possuiListNew.contains(possuiListOldPossui)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Possui " + possuiListOldPossui + " since its processo field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (idFornecedorNew != null) {
                idFornecedorNew = em.getReference(idFornecedorNew.getClass(), idFornecedorNew.getIdFornecedor());
                processo.setIdFornecedor(idFornecedorNew);
            }
            if (numeroOficioNew != null) {
                numeroOficioNew = em.getReference(numeroOficioNew.getClass(), numeroOficioNew.getNumeroOficio());
                processo.setNumeroOficio(numeroOficioNew);
            }
            List<Usuario> attachedUsuarioListNew = new ArrayList<Usuario>();
            for (Usuario usuarioListNewUsuarioToAttach : usuarioListNew) {
                usuarioListNewUsuarioToAttach = em.getReference(usuarioListNewUsuarioToAttach.getClass(), usuarioListNewUsuarioToAttach.getIdUsuario());
                attachedUsuarioListNew.add(usuarioListNewUsuarioToAttach);
            }
            usuarioListNew = attachedUsuarioListNew;
            processo.setUsuarioList(usuarioListNew);
            List<Oficio> attachedOficioListNew = new ArrayList<Oficio>();
            for (Oficio oficioListNewOficioToAttach : oficioListNew) {
                oficioListNewOficioToAttach = em.getReference(oficioListNewOficioToAttach.getClass(), oficioListNewOficioToAttach.getNumeroOficio());
                attachedOficioListNew.add(oficioListNewOficioToAttach);
            }
            oficioListNew = attachedOficioListNew;
            processo.setOficioList(oficioListNew);
            List<Possui> attachedPossuiListNew = new ArrayList<Possui>();
            for (Possui possuiListNewPossuiToAttach : possuiListNew) {
                possuiListNewPossuiToAttach = em.getReference(possuiListNewPossuiToAttach.getClass(), possuiListNewPossuiToAttach.getPossuiPK());
                attachedPossuiListNew.add(possuiListNewPossuiToAttach);
            }
            possuiListNew = attachedPossuiListNew;
            processo.setPossuiList(possuiListNew);
            List<Notafiscal> attachedNotafiscalListNew = new ArrayList<Notafiscal>();
            for (Notafiscal notafiscalListNewNotafiscalToAttach : notafiscalListNew) {
                notafiscalListNewNotafiscalToAttach = em.getReference(notafiscalListNewNotafiscalToAttach.getClass(), notafiscalListNewNotafiscalToAttach.getNumeroNota());
                attachedNotafiscalListNew.add(notafiscalListNewNotafiscalToAttach);
            }
            notafiscalListNew = attachedNotafiscalListNew;
            processo.setNotafiscalList(notafiscalListNew);
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
            for (Usuario usuarioListOldUsuario : usuarioListOld) {
                if (!usuarioListNew.contains(usuarioListOldUsuario)) {
                    usuarioListOldUsuario.getProcessoList().remove(processo);
                    usuarioListOldUsuario = em.merge(usuarioListOldUsuario);
                }
            }
            for (Usuario usuarioListNewUsuario : usuarioListNew) {
                if (!usuarioListOld.contains(usuarioListNewUsuario)) {
                    usuarioListNewUsuario.getProcessoList().add(processo);
                    usuarioListNewUsuario = em.merge(usuarioListNewUsuario);
                }
            }
            for (Oficio oficioListOldOficio : oficioListOld) {
                if (!oficioListNew.contains(oficioListOldOficio)) {
                    oficioListOldOficio.getProcessoList().remove(processo);
                    oficioListOldOficio = em.merge(oficioListOldOficio);
                }
            }
            for (Oficio oficioListNewOficio : oficioListNew) {
                if (!oficioListOld.contains(oficioListNewOficio)) {
                    oficioListNewOficio.getProcessoList().add(processo);
                    oficioListNewOficio = em.merge(oficioListNewOficio);
                }
            }
            for (Possui possuiListNewPossui : possuiListNew) {
                if (!possuiListOld.contains(possuiListNewPossui)) {
                    Processo oldProcessoOfPossuiListNewPossui = possuiListNewPossui.getProcesso();
                    possuiListNewPossui.setProcesso(processo);
                    possuiListNewPossui = em.merge(possuiListNewPossui);
                    if (oldProcessoOfPossuiListNewPossui != null && !oldProcessoOfPossuiListNewPossui.equals(processo)) {
                        oldProcessoOfPossuiListNewPossui.getPossuiList().remove(possuiListNewPossui);
                        oldProcessoOfPossuiListNewPossui = em.merge(oldProcessoOfPossuiListNewPossui);
                    }
                }
            }
            for (Notafiscal notafiscalListOldNotafiscal : notafiscalListOld) {
                if (!notafiscalListNew.contains(notafiscalListOldNotafiscal)) {
                    notafiscalListOldNotafiscal.setNumeroProcesso(null);
                    notafiscalListOldNotafiscal = em.merge(notafiscalListOldNotafiscal);
                }
            }
            for (Notafiscal notafiscalListNewNotafiscal : notafiscalListNew) {
                if (!notafiscalListOld.contains(notafiscalListNewNotafiscal)) {
                    Processo oldNumeroProcessoOfNotafiscalListNewNotafiscal = notafiscalListNewNotafiscal.getNumeroProcesso();
                    notafiscalListNewNotafiscal.setNumeroProcesso(processo);
                    notafiscalListNewNotafiscal = em.merge(notafiscalListNewNotafiscal);
                    if (oldNumeroProcessoOfNotafiscalListNewNotafiscal != null && !oldNumeroProcessoOfNotafiscalListNewNotafiscal.equals(processo)) {
                        oldNumeroProcessoOfNotafiscalListNewNotafiscal.getNotafiscalList().remove(notafiscalListNewNotafiscal);
                        oldNumeroProcessoOfNotafiscalListNewNotafiscal = em.merge(oldNumeroProcessoOfNotafiscalListNewNotafiscal);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = processo.getNumeroProcesso();
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

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Processo processo;
            try {
                processo = em.getReference(Processo.class, id);
                processo.getNumeroProcesso();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The processo with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            List<Possui> possuiListOrphanCheck = processo.getPossuiList();
            for (Possui possuiListOrphanCheckPossui : possuiListOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Processo (" + processo + ") cannot be destroyed since the Possui " + possuiListOrphanCheckPossui + " in its possuiList field has a non-nullable processo field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
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
            List<Usuario> usuarioList = processo.getUsuarioList();
            for (Usuario usuarioListUsuario : usuarioList) {
                usuarioListUsuario.getProcessoList().remove(processo);
                usuarioListUsuario = em.merge(usuarioListUsuario);
            }
            List<Oficio> oficioList = processo.getOficioList();
            for (Oficio oficioListOficio : oficioList) {
                oficioListOficio.getProcessoList().remove(processo);
                oficioListOficio = em.merge(oficioListOficio);
            }
            List<Notafiscal> notafiscalList = processo.getNotafiscalList();
            for (Notafiscal notafiscalListNotafiscal : notafiscalList) {
                notafiscalListNotafiscal.setNumeroProcesso(null);
                notafiscalListNotafiscal = em.merge(notafiscalListNotafiscal);
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
