/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author lucia
 */
@Entity
@Table(name = "monitora")
@NamedQueries({
    @NamedQuery(name = "Monitora.findAll", query = "SELECT m FROM Monitora m"),
    @NamedQuery(name = "Monitora.findByIdMonitora", query = "SELECT m FROM Monitora m WHERE m.idMonitora = :idMonitora")})
public class Monitora implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdMonitora")
    private Integer idMonitora;
    @JoinColumn(name = "IdLog", referencedColumnName = "IdLog")
    @ManyToOne
    private Logsistema idLog;
    @JoinColumn(name = "IdUsuario", referencedColumnName = "IdUsuario")
    @ManyToOne
    private Usuario idUsuario;

    public Monitora() {
    }

    public Monitora(Integer idMonitora) {
        this.idMonitora = idMonitora;
    }

    public Integer getIdMonitora() {
        return idMonitora;
    }

    public void setIdMonitora(Integer idMonitora) {
        this.idMonitora = idMonitora;
    }

    public Logsistema getIdLog() {
        return idLog;
    }

    public void setIdLog(Logsistema idLog) {
        this.idLog = idLog;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idMonitora != null ? idMonitora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Monitora)) {
            return false;
        }
        Monitora other = (Monitora) object;
        if ((this.idMonitora == null && other.idMonitora != null) || (this.idMonitora != null && !this.idMonitora.equals(other.idMonitora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Monitora[ idMonitora=" + idMonitora + " ]";
    }
    
}
