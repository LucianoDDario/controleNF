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
import prefeitura.entities.Monitora;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import prefeitura.controllers.exceptions.NonexistentEntityException;
import prefeitura.entities.Cria;
import prefeitura.entities.Logsistema;
import prefeitura.entities.Recebe;
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
        if (usuario.getMonitoraList() == null) {
            usuario.setMonitoraList(new ArrayList<Monitora>());
        }
        if (usuario.getCriaList() == null) {
            usuario.setCriaList(new ArrayList<Cria>());
        }
        if (usuario.getLogsistemaList() == null) {
            usuario.setLogsistemaList(new ArrayList<Logsistema>());
        }
        if (usuario.getRecebeList() == null) {
            usuario.setRecebeList(new ArrayList<Recebe>());
        }
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            List<Monitora> attachedMonitoraList = new ArrayList<Monitora>();
            for (Monitora monitoraListMonitoraToAttach : usuario.getMonitoraList()) {
                monitoraListMonitoraToAttach = em.getReference(monitoraListMonitoraToAttach.getClass(), monitoraListMonitoraToAttach.getIdMonitora());
                attachedMonitoraList.add(monitoraListMonitoraToAttach);
            }
            usuario.setMonitoraList(attachedMonitoraList);
            List<Cria> attachedCriaList = new ArrayList<Cria>();
            for (Cria criaListCriaToAttach : usuario.getCriaList()) {
                criaListCriaToAttach = em.getReference(criaListCriaToAttach.getClass(), criaListCriaToAttach.getIdCria());
                attachedCriaList.add(criaListCriaToAttach);
            }
            usuario.setCriaList(attachedCriaList);
            List<Logsistema> attachedLogsistemaList = new ArrayList<Logsistema>();
            for (Logsistema logsistemaListLogsistemaToAttach : usuario.getLogsistemaList()) {
                logsistemaListLogsistemaToAttach = em.getReference(logsistemaListLogsistemaToAttach.getClass(), logsistemaListLogsistemaToAttach.getIdLog());
                attachedLogsistemaList.add(logsistemaListLogsistemaToAttach);
            }
            usuario.setLogsistemaList(attachedLogsistemaList);
            List<Recebe> attachedRecebeList = new ArrayList<Recebe>();
            for (Recebe recebeListRecebeToAttach : usuario.getRecebeList()) {
                recebeListRecebeToAttach = em.getReference(recebeListRecebeToAttach.getClass(), recebeListRecebeToAttach.getIdRecebe());
                attachedRecebeList.add(recebeListRecebeToAttach);
            }
            usuario.setRecebeList(attachedRecebeList);
            em.persist(usuario);
            for (Monitora monitoraListMonitora : usuario.getMonitoraList()) {
                Usuario oldIdUsuarioOfMonitoraListMonitora = monitoraListMonitora.getIdUsuario();
                monitoraListMonitora.setIdUsuario(usuario);
                monitoraListMonitora = em.merge(monitoraListMonitora);
                if (oldIdUsuarioOfMonitoraListMonitora != null) {
                    oldIdUsuarioOfMonitoraListMonitora.getMonitoraList().remove(monitoraListMonitora);
                    oldIdUsuarioOfMonitoraListMonitora = em.merge(oldIdUsuarioOfMonitoraListMonitora);
                }
            }
            for (Cria criaListCria : usuario.getCriaList()) {
                Usuario oldIdUsuarioOfCriaListCria = criaListCria.getIdUsuario();
                criaListCria.setIdUsuario(usuario);
                criaListCria = em.merge(criaListCria);
                if (oldIdUsuarioOfCriaListCria != null) {
                    oldIdUsuarioOfCriaListCria.getCriaList().remove(criaListCria);
                    oldIdUsuarioOfCriaListCria = em.merge(oldIdUsuarioOfCriaListCria);
                }
            }
            for (Logsistema logsistemaListLogsistema : usuario.getLogsistemaList()) {
                Usuario oldIdUsuarioOfLogsistemaListLogsistema = logsistemaListLogsistema.getIdUsuario();
                logsistemaListLogsistema.setIdUsuario(usuario);
                logsistemaListLogsistema = em.merge(logsistemaListLogsistema);
                if (oldIdUsuarioOfLogsistemaListLogsistema != null) {
                    oldIdUsuarioOfLogsistemaListLogsistema.getLogsistemaList().remove(logsistemaListLogsistema);
                    oldIdUsuarioOfLogsistemaListLogsistema = em.merge(oldIdUsuarioOfLogsistemaListLogsistema);
                }
            }
            for (Recebe recebeListRecebe : usuario.getRecebeList()) {
                Usuario oldIdUsuarioOfRecebeListRecebe = recebeListRecebe.getIdUsuario();
                recebeListRecebe.setIdUsuario(usuario);
                recebeListRecebe = em.merge(recebeListRecebe);
                if (oldIdUsuarioOfRecebeListRecebe != null) {
                    oldIdUsuarioOfRecebeListRecebe.getRecebeList().remove(recebeListRecebe);
                    oldIdUsuarioOfRecebeListRecebe = em.merge(oldIdUsuarioOfRecebeListRecebe);
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
            List<Monitora> monitoraListOld = persistentUsuario.getMonitoraList();
            List<Monitora> monitoraListNew = usuario.getMonitoraList();
            List<Cria> criaListOld = persistentUsuario.getCriaList();
            List<Cria> criaListNew = usuario.getCriaList();
            List<Logsistema> logsistemaListOld = persistentUsuario.getLogsistemaList();
            List<Logsistema> logsistemaListNew = usuario.getLogsistemaList();
            List<Recebe> recebeListOld = persistentUsuario.getRecebeList();
            List<Recebe> recebeListNew = usuario.getRecebeList();
            List<Monitora> attachedMonitoraListNew = new ArrayList<Monitora>();
            for (Monitora monitoraListNewMonitoraToAttach : monitoraListNew) {
                monitoraListNewMonitoraToAttach = em.getReference(monitoraListNewMonitoraToAttach.getClass(), monitoraListNewMonitoraToAttach.getIdMonitora());
                attachedMonitoraListNew.add(monitoraListNewMonitoraToAttach);
            }
            monitoraListNew = attachedMonitoraListNew;
            usuario.setMonitoraList(monitoraListNew);
            List<Cria> attachedCriaListNew = new ArrayList<Cria>();
            for (Cria criaListNewCriaToAttach : criaListNew) {
                criaListNewCriaToAttach = em.getReference(criaListNewCriaToAttach.getClass(), criaListNewCriaToAttach.getIdCria());
                attachedCriaListNew.add(criaListNewCriaToAttach);
            }
            criaListNew = attachedCriaListNew;
            usuario.setCriaList(criaListNew);
            List<Logsistema> attachedLogsistemaListNew = new ArrayList<Logsistema>();
            for (Logsistema logsistemaListNewLogsistemaToAttach : logsistemaListNew) {
                logsistemaListNewLogsistemaToAttach = em.getReference(logsistemaListNewLogsistemaToAttach.getClass(), logsistemaListNewLogsistemaToAttach.getIdLog());
                attachedLogsistemaListNew.add(logsistemaListNewLogsistemaToAttach);
            }
            logsistemaListNew = attachedLogsistemaListNew;
            usuario.setLogsistemaList(logsistemaListNew);
            List<Recebe> attachedRecebeListNew = new ArrayList<Recebe>();
            for (Recebe recebeListNewRecebeToAttach : recebeListNew) {
                recebeListNewRecebeToAttach = em.getReference(recebeListNewRecebeToAttach.getClass(), recebeListNewRecebeToAttach.getIdRecebe());
                attachedRecebeListNew.add(recebeListNewRecebeToAttach);
            }
            recebeListNew = attachedRecebeListNew;
            usuario.setRecebeList(recebeListNew);
            usuario = em.merge(usuario);
            for (Monitora monitoraListOldMonitora : monitoraListOld) {
                if (!monitoraListNew.contains(monitoraListOldMonitora)) {
                    monitoraListOldMonitora.setIdUsuario(null);
                    monitoraListOldMonitora = em.merge(monitoraListOldMonitora);
                }
            }
            for (Monitora monitoraListNewMonitora : monitoraListNew) {
                if (!monitoraListOld.contains(monitoraListNewMonitora)) {
                    Usuario oldIdUsuarioOfMonitoraListNewMonitora = monitoraListNewMonitora.getIdUsuario();
                    monitoraListNewMonitora.setIdUsuario(usuario);
                    monitoraListNewMonitora = em.merge(monitoraListNewMonitora);
                    if (oldIdUsuarioOfMonitoraListNewMonitora != null && !oldIdUsuarioOfMonitoraListNewMonitora.equals(usuario)) {
                        oldIdUsuarioOfMonitoraListNewMonitora.getMonitoraList().remove(monitoraListNewMonitora);
                        oldIdUsuarioOfMonitoraListNewMonitora = em.merge(oldIdUsuarioOfMonitoraListNewMonitora);
                    }
                }
            }
            for (Cria criaListOldCria : criaListOld) {
                if (!criaListNew.contains(criaListOldCria)) {
                    criaListOldCria.setIdUsuario(null);
                    criaListOldCria = em.merge(criaListOldCria);
                }
            }
            for (Cria criaListNewCria : criaListNew) {
                if (!criaListOld.contains(criaListNewCria)) {
                    Usuario oldIdUsuarioOfCriaListNewCria = criaListNewCria.getIdUsuario();
                    criaListNewCria.setIdUsuario(usuario);
                    criaListNewCria = em.merge(criaListNewCria);
                    if (oldIdUsuarioOfCriaListNewCria != null && !oldIdUsuarioOfCriaListNewCria.equals(usuario)) {
                        oldIdUsuarioOfCriaListNewCria.getCriaList().remove(criaListNewCria);
                        oldIdUsuarioOfCriaListNewCria = em.merge(oldIdUsuarioOfCriaListNewCria);
                    }
                }
            }
            for (Logsistema logsistemaListOldLogsistema : logsistemaListOld) {
                if (!logsistemaListNew.contains(logsistemaListOldLogsistema)) {
                    logsistemaListOldLogsistema.setIdUsuario(null);
                    logsistemaListOldLogsistema = em.merge(logsistemaListOldLogsistema);
                }
            }
            for (Logsistema logsistemaListNewLogsistema : logsistemaListNew) {
                if (!logsistemaListOld.contains(logsistemaListNewLogsistema)) {
                    Usuario oldIdUsuarioOfLogsistemaListNewLogsistema = logsistemaListNewLogsistema.getIdUsuario();
                    logsistemaListNewLogsistema.setIdUsuario(usuario);
                    logsistemaListNewLogsistema = em.merge(logsistemaListNewLogsistema);
                    if (oldIdUsuarioOfLogsistemaListNewLogsistema != null && !oldIdUsuarioOfLogsistemaListNewLogsistema.equals(usuario)) {
                        oldIdUsuarioOfLogsistemaListNewLogsistema.getLogsistemaList().remove(logsistemaListNewLogsistema);
                        oldIdUsuarioOfLogsistemaListNewLogsistema = em.merge(oldIdUsuarioOfLogsistemaListNewLogsistema);
                    }
                }
            }
            for (Recebe recebeListOldRecebe : recebeListOld) {
                if (!recebeListNew.contains(recebeListOldRecebe)) {
                    recebeListOldRecebe.setIdUsuario(null);
                    recebeListOldRecebe = em.merge(recebeListOldRecebe);
                }
            }
            for (Recebe recebeListNewRecebe : recebeListNew) {
                if (!recebeListOld.contains(recebeListNewRecebe)) {
                    Usuario oldIdUsuarioOfRecebeListNewRecebe = recebeListNewRecebe.getIdUsuario();
                    recebeListNewRecebe.setIdUsuario(usuario);
                    recebeListNewRecebe = em.merge(recebeListNewRecebe);
                    if (oldIdUsuarioOfRecebeListNewRecebe != null && !oldIdUsuarioOfRecebeListNewRecebe.equals(usuario)) {
                        oldIdUsuarioOfRecebeListNewRecebe.getRecebeList().remove(recebeListNewRecebe);
                        oldIdUsuarioOfRecebeListNewRecebe = em.merge(oldIdUsuarioOfRecebeListNewRecebe);
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
            List<Monitora> monitoraList = usuario.getMonitoraList();
            for (Monitora monitoraListMonitora : monitoraList) {
                monitoraListMonitora.setIdUsuario(null);
                monitoraListMonitora = em.merge(monitoraListMonitora);
            }
            List<Cria> criaList = usuario.getCriaList();
            for (Cria criaListCria : criaList) {
                criaListCria.setIdUsuario(null);
                criaListCria = em.merge(criaListCria);
            }
            List<Logsistema> logsistemaList = usuario.getLogsistemaList();
            for (Logsistema logsistemaListLogsistema : logsistemaList) {
                logsistemaListLogsistema.setIdUsuario(null);
                logsistemaListLogsistema = em.merge(logsistemaListLogsistema);
            }
            List<Recebe> recebeList = usuario.getRecebeList();
            for (Recebe recebeListRecebe : recebeList) {
                recebeListRecebe.setIdUsuario(null);
                recebeListRecebe = em.merge(recebeListRecebe);
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
