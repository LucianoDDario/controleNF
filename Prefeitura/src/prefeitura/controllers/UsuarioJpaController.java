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
import prefeitura.entities.Logsistema;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Processo;
import prefeitura.entities.Oficio;
import prefeitura.entities.Usuario;

/**
 *
 * @author lucia
 */
public class UsuarioJpaController implements Serializable {

    public UsuarioJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Usuario usuario) {
        if (usuario.getLogsistemaList() == null) {
            usuario.setLogsistemaList(new ArrayList<Logsistema>());
        }
        if (usuario.getProcessoList() == null) {
            usuario.setProcessoList(new ArrayList<Processo>());
        }
        if (usuario.getOficioList() == null) {
            usuario.setOficioList(new ArrayList<Oficio>());
        }
        if (usuario.getLogsistemaList1() == null) {
            usuario.setLogsistemaList1(new ArrayList<Logsistema>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Logsistema> attachedLogsistemaList = new ArrayList<Logsistema>();
            for (Logsistema logsistemaListLogsistemaToAttach : usuario.getLogsistemaList()) {
                logsistemaListLogsistemaToAttach = em.getReference(logsistemaListLogsistemaToAttach.getClass(), logsistemaListLogsistemaToAttach.getIdLog());
                attachedLogsistemaList.add(logsistemaListLogsistemaToAttach);
            }
            usuario.setLogsistemaList(attachedLogsistemaList);
            List<Processo> attachedProcessoList = new ArrayList<Processo>();
            for (Processo processoListProcessoToAttach : usuario.getProcessoList()) {
                processoListProcessoToAttach = em.getReference(processoListProcessoToAttach.getClass(), processoListProcessoToAttach.getNumeroProcesso());
                attachedProcessoList.add(processoListProcessoToAttach);
            }
            usuario.setProcessoList(attachedProcessoList);
            List<Oficio> attachedOficioList = new ArrayList<Oficio>();
            for (Oficio oficioListOficioToAttach : usuario.getOficioList()) {
                oficioListOficioToAttach = em.getReference(oficioListOficioToAttach.getClass(), oficioListOficioToAttach.getNumeroOficio());
                attachedOficioList.add(oficioListOficioToAttach);
            }
            usuario.setOficioList(attachedOficioList);
            List<Logsistema> attachedLogsistemaList1 = new ArrayList<Logsistema>();
            for (Logsistema logsistemaList1LogsistemaToAttach : usuario.getLogsistemaList1()) {
                logsistemaList1LogsistemaToAttach = em.getReference(logsistemaList1LogsistemaToAttach.getClass(), logsistemaList1LogsistemaToAttach.getIdLog());
                attachedLogsistemaList1.add(logsistemaList1LogsistemaToAttach);
            }
            usuario.setLogsistemaList1(attachedLogsistemaList1);
            em.persist(usuario);
            for (Logsistema logsistemaListLogsistema : usuario.getLogsistemaList()) {
                logsistemaListLogsistema.getUsuarioList().add(usuario);
                logsistemaListLogsistema = em.merge(logsistemaListLogsistema);
            }
            for (Processo processoListProcesso : usuario.getProcessoList()) {
                processoListProcesso.getUsuarioList().add(usuario);
                processoListProcesso = em.merge(processoListProcesso);
            }
            for (Oficio oficioListOficio : usuario.getOficioList()) {
                oficioListOficio.getUsuarioList().add(usuario);
                oficioListOficio = em.merge(oficioListOficio);
            }
            for (Logsistema logsistemaList1Logsistema : usuario.getLogsistemaList1()) {
                Usuario oldIdUsuarioOfLogsistemaList1Logsistema = logsistemaList1Logsistema.getIdUsuario();
                logsistemaList1Logsistema.setIdUsuario(usuario);
                logsistemaList1Logsistema = em.merge(logsistemaList1Logsistema);
                if (oldIdUsuarioOfLogsistemaList1Logsistema != null) {
                    oldIdUsuarioOfLogsistemaList1Logsistema.getLogsistemaList1().remove(logsistemaList1Logsistema);
                    oldIdUsuarioOfLogsistemaList1Logsistema = em.merge(oldIdUsuarioOfLogsistemaList1Logsistema);
                }
            }
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Usuario usuario) throws NonexistentEntityException, Exception {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Usuario persistentUsuario = em.find(Usuario.class, usuario.getIdUsuario());
            List<Logsistema> logsistemaListOld = persistentUsuario.getLogsistemaList();
            List<Logsistema> logsistemaListNew = usuario.getLogsistemaList();
            List<Processo> processoListOld = persistentUsuario.getProcessoList();
            List<Processo> processoListNew = usuario.getProcessoList();
            List<Oficio> oficioListOld = persistentUsuario.getOficioList();
            List<Oficio> oficioListNew = usuario.getOficioList();
            List<Logsistema> logsistemaList1Old = persistentUsuario.getLogsistemaList1();
            List<Logsistema> logsistemaList1New = usuario.getLogsistemaList1();
            List<Logsistema> attachedLogsistemaListNew = new ArrayList<Logsistema>();
            for (Logsistema logsistemaListNewLogsistemaToAttach : logsistemaListNew) {
                logsistemaListNewLogsistemaToAttach = em.getReference(logsistemaListNewLogsistemaToAttach.getClass(), logsistemaListNewLogsistemaToAttach.getIdLog());
                attachedLogsistemaListNew.add(logsistemaListNewLogsistemaToAttach);
            }
            logsistemaListNew = attachedLogsistemaListNew;
            usuario.setLogsistemaList(logsistemaListNew);
            List<Processo> attachedProcessoListNew = new ArrayList<Processo>();
            for (Processo processoListNewProcessoToAttach : processoListNew) {
                processoListNewProcessoToAttach = em.getReference(processoListNewProcessoToAttach.getClass(), processoListNewProcessoToAttach.getNumeroProcesso());
                attachedProcessoListNew.add(processoListNewProcessoToAttach);
            }
            processoListNew = attachedProcessoListNew;
            usuario.setProcessoList(processoListNew);
            List<Oficio> attachedOficioListNew = new ArrayList<Oficio>();
            for (Oficio oficioListNewOficioToAttach : oficioListNew) {
                oficioListNewOficioToAttach = em.getReference(oficioListNewOficioToAttach.getClass(), oficioListNewOficioToAttach.getNumeroOficio());
                attachedOficioListNew.add(oficioListNewOficioToAttach);
            }
            oficioListNew = attachedOficioListNew;
            usuario.setOficioList(oficioListNew);
            List<Logsistema> attachedLogsistemaList1New = new ArrayList<Logsistema>();
            for (Logsistema logsistemaList1NewLogsistemaToAttach : logsistemaList1New) {
                logsistemaList1NewLogsistemaToAttach = em.getReference(logsistemaList1NewLogsistemaToAttach.getClass(), logsistemaList1NewLogsistemaToAttach.getIdLog());
                attachedLogsistemaList1New.add(logsistemaList1NewLogsistemaToAttach);
            }
            logsistemaList1New = attachedLogsistemaList1New;
            usuario.setLogsistemaList1(logsistemaList1New);
            usuario = em.merge(usuario);
            for (Logsistema logsistemaListOldLogsistema : logsistemaListOld) {
                if (!logsistemaListNew.contains(logsistemaListOldLogsistema)) {
                    logsistemaListOldLogsistema.getUsuarioList().remove(usuario);
                    logsistemaListOldLogsistema = em.merge(logsistemaListOldLogsistema);
                }
            }
            for (Logsistema logsistemaListNewLogsistema : logsistemaListNew) {
                if (!logsistemaListOld.contains(logsistemaListNewLogsistema)) {
                    logsistemaListNewLogsistema.getUsuarioList().add(usuario);
                    logsistemaListNewLogsistema = em.merge(logsistemaListNewLogsistema);
                }
            }
            for (Processo processoListOldProcesso : processoListOld) {
                if (!processoListNew.contains(processoListOldProcesso)) {
                    processoListOldProcesso.getUsuarioList().remove(usuario);
                    processoListOldProcesso = em.merge(processoListOldProcesso);
                }
            }
            for (Processo processoListNewProcesso : processoListNew) {
                if (!processoListOld.contains(processoListNewProcesso)) {
                    processoListNewProcesso.getUsuarioList().add(usuario);
                    processoListNewProcesso = em.merge(processoListNewProcesso);
                }
            }
            for (Oficio oficioListOldOficio : oficioListOld) {
                if (!oficioListNew.contains(oficioListOldOficio)) {
                    oficioListOldOficio.getUsuarioList().remove(usuario);
                    oficioListOldOficio = em.merge(oficioListOldOficio);
                }
            }
            for (Oficio oficioListNewOficio : oficioListNew) {
                if (!oficioListOld.contains(oficioListNewOficio)) {
                    oficioListNewOficio.getUsuarioList().add(usuario);
                    oficioListNewOficio = em.merge(oficioListNewOficio);
                }
            }
            for (Logsistema logsistemaList1OldLogsistema : logsistemaList1Old) {
                if (!logsistemaList1New.contains(logsistemaList1OldLogsistema)) {
                    logsistemaList1OldLogsistema.setIdUsuario(null);
                    logsistemaList1OldLogsistema = em.merge(logsistemaList1OldLogsistema);
                }
            }
            for (Logsistema logsistemaList1NewLogsistema : logsistemaList1New) {
                if (!logsistemaList1Old.contains(logsistemaList1NewLogsistema)) {
                    Usuario oldIdUsuarioOfLogsistemaList1NewLogsistema = logsistemaList1NewLogsistema.getIdUsuario();
                    logsistemaList1NewLogsistema.setIdUsuario(usuario);
                    logsistemaList1NewLogsistema = em.merge(logsistemaList1NewLogsistema);
                    if (oldIdUsuarioOfLogsistemaList1NewLogsistema != null && !oldIdUsuarioOfLogsistemaList1NewLogsistema.equals(usuario)) {
                        oldIdUsuarioOfLogsistemaList1NewLogsistema.getLogsistemaList1().remove(logsistemaList1NewLogsistema);
                        oldIdUsuarioOfLogsistemaList1NewLogsistema = em.merge(oldIdUsuarioOfLogsistemaList1NewLogsistema);
                    }
                }
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Integer id = usuario.getIdUsuario();
                if (findUsuario(id) == null) {
                    throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.");
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
            Usuario usuario;
            try {
                usuario = em.getReference(Usuario.class, id);
                usuario.getIdUsuario();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The usuario with id " + id + " no longer exists.", enfe);
            }
            List<Logsistema> logsistemaList = usuario.getLogsistemaList();
            for (Logsistema logsistemaListLogsistema : logsistemaList) {
                logsistemaListLogsistema.getUsuarioList().remove(usuario);
                logsistemaListLogsistema = em.merge(logsistemaListLogsistema);
            }
            List<Processo> processoList = usuario.getProcessoList();
            for (Processo processoListProcesso : processoList) {
                processoListProcesso.getUsuarioList().remove(usuario);
                processoListProcesso = em.merge(processoListProcesso);
            }
            List<Oficio> oficioList = usuario.getOficioList();
            for (Oficio oficioListOficio : oficioList) {
                oficioListOficio.getUsuarioList().remove(usuario);
                oficioListOficio = em.merge(oficioListOficio);
            }
            List<Logsistema> logsistemaList1 = usuario.getLogsistemaList1();
            for (Logsistema logsistemaList1Logsistema : logsistemaList1) {
                logsistemaList1Logsistema.setIdUsuario(null);
                logsistemaList1Logsistema = em.merge(logsistemaList1Logsistema);
            }
            em.remove(usuario);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Usuario> findUsuarioEntities() {
        return findUsuarioEntities(true, -1, -1);
    }

    public List<Usuario> findUsuarioEntities(int maxResults, int firstResult) {
        return findUsuarioEntities(false, maxResults, firstResult);
    }

    private List<Usuario> findUsuarioEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Usuario.class));
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

    public Usuario findUsuario(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Usuario.class, id);
        } finally {
            em.close();
        }
    }

    public int getUsuarioCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Usuario> rt = cq.from(Usuario.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
