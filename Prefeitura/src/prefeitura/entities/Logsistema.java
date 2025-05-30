/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
import java.util.Date;
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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucia
 */
@Entity
@Table(name = "logsistema")
@NamedQueries({
    @NamedQuery(name = "Logsistema.findAll", query = "SELECT l FROM Logsistema l"),
    @NamedQuery(name = "Logsistema.findByIdLog", query = "SELECT l FROM Logsistema l WHERE l.idLog = :idLog"),
    @NamedQuery(name = "Logsistema.findByAcao", query = "SELECT l FROM Logsistema l WHERE l.acao = :acao"),
    @NamedQuery(name = "Logsistema.findByDataAcao", query = "SELECT l FROM Logsistema l WHERE l.dataAcao = :dataAcao"),
    @NamedQuery(name = "Logsistema.findByHoraAcao", query = "SELECT l FROM Logsistema l WHERE l.horaAcao = :horaAcao")})
public class Logsistema implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdLog")
    private Integer idLog;
    @Column(name = "Acao")
    private String acao;
    @Column(name = "DataAcao")
    @Temporal(TemporalType.DATE)
    private Date dataAcao;
    @Column(name = "HoraAcao")
    @Temporal(TemporalType.TIME)
    private Date horaAcao;
    @JoinColumn(name = "IdUsuario", referencedColumnName = "IdUsuario")
    @ManyToOne
    private Usuario idUsuario;

    public Logsistema() {
    }

    public Logsistema(Integer idLog) {
        this.idLog = idLog;
    }

    public Integer getIdLog() {
        return idLog;
    }

    public void setIdLog(Integer idLog) {
        this.idLog = idLog;
    }

    public String getAcao() {
        return acao;
    }

    public void setAcao(String acao) {
        this.acao = acao;
    }

    public Date getDataAcao() {
        return dataAcao;
    }

    public void setDataAcao(Date dataAcao) {
        this.dataAcao = dataAcao;
    }

    public Date getHoraAcao() {
        return horaAcao;
    }

    public void setHoraAcao(Date horaAcao) {
        this.horaAcao = horaAcao;
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
        hash += (idLog != null ? idLog.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Logsistema)) {
            return false;
        }
        Logsistema other = (Logsistema) object;
        if ((this.idLog == null && other.idLog != null) || (this.idLog != null && !this.idLog.equals(other.idLog))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Logsistema[ idLog=" + idLog + " ]";
    }
    
}
