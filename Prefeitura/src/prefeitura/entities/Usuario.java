/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author lucia
 */
@Entity
@Table(name = "usuario")
@NamedQueries({
    @NamedQuery(name = "Usuario.findAll", query = "SELECT u FROM Usuario u"),
    @NamedQuery(name = "Usuario.findByIdUsuario", query = "SELECT u FROM Usuario u WHERE u.idUsuario = :idUsuario"),
    @NamedQuery(name = "Usuario.findByLogin", query = "SELECT u FROM Usuario u WHERE u.login = :login"),
    @NamedQuery(name = "Usuario.findBySenha", query = "SELECT u FROM Usuario u WHERE u.senha = :senha"),
    @NamedQuery(name = "Usuario.findByTipoAcesso", query = "SELECT u FROM Usuario u WHERE u.tipoAcesso = :tipoAcesso")})
public class Usuario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdUsuario")
    private Integer idUsuario;
    @Column(name = "Login")
    private String login;
    @Column(name = "Senha")
    private String senha;
    @Column(name = "TipoAcesso")
    private String tipoAcesso;
    @OneToMany(mappedBy = "idUsuario")
    private List<Monitora> monitoraList;
    @OneToMany(mappedBy = "idUsuario")
    private List<Cria> criaList;
    @OneToMany(mappedBy = "idUsuario")
    private List<Logsistema> logsistemaList;
    @OneToMany(mappedBy = "idUsuario")
    private List<Recebe> recebeList;

    public Usuario() {
    }

    public Usuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTipoAcesso() {
        return tipoAcesso;
    }

    public void setTipoAcesso(String tipoAcesso) {
        this.tipoAcesso = tipoAcesso;
    }

    public List<Monitora> getMonitoraList() {
        return monitoraList;
    }

    public void setMonitoraList(List<Monitora> monitoraList) {
        this.monitoraList = monitoraList;
    }

    public List<Cria> getCriaList() {
        return criaList;
    }

    public void setCriaList(List<Cria> criaList) {
        this.criaList = criaList;
    }

    public List<Logsistema> getLogsistemaList() {
        return logsistemaList;
    }

    public void setLogsistemaList(List<Logsistema> logsistemaList) {
        this.logsistemaList = logsistemaList;
    }

    public List<Recebe> getRecebeList() {
        return recebeList;
    }

    public void setRecebeList(List<Recebe> recebeList) {
        this.recebeList = recebeList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idUsuario != null ? idUsuario.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Usuario)) {
            return false;
        }
        Usuario other = (Usuario) object;
        if ((this.idUsuario == null && other.idUsuario != null) || (this.idUsuario != null && !this.idUsuario.equals(other.idUsuario))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Usuario[ idUsuario=" + idUsuario + " ]";
    }
    
}
