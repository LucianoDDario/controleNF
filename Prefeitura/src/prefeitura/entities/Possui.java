/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "possui")
@NamedQueries({
    @NamedQuery(name = "Possui.findAll", query = "SELECT p FROM Possui p"),
    @NamedQuery(name = "Possui.findByNumeroProtocolo", query = "SELECT p FROM Possui p WHERE p.possuiPK.numeroProtocolo = :numeroProtocolo"),
    @NamedQuery(name = "Possui.findByNumeroNota", query = "SELECT p FROM Possui p WHERE p.possuiPK.numeroNota = :numeroNota"),
    @NamedQuery(name = "Possui.findByNumeroProcesso", query = "SELECT p FROM Possui p WHERE p.possuiPK.numeroProcesso = :numeroProcesso")})
public class Possui implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PossuiPK possuiPK;
    @JoinColumn(name = "NumeroNota", referencedColumnName = "NumeroNota", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Notafiscal notafiscal;
    @JoinColumn(name = "NumeroProcesso", referencedColumnName = "NumeroProcesso", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Processo processo;
    @JoinColumn(name = "NumeroProtocolo", referencedColumnName = "NumeroProtocolo", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Protocolo protocolo;

    public Possui() {
    }

    public Possui(PossuiPK possuiPK) {
        this.possuiPK = possuiPK;
    }

    public Possui(int numeroProtocolo, int numeroNota, int numeroProcesso) {
        this.possuiPK = new PossuiPK(numeroProtocolo, numeroNota, numeroProcesso);
    }

    public PossuiPK getPossuiPK() {
        return possuiPK;
    }

    public void setPossuiPK(PossuiPK possuiPK) {
        this.possuiPK = possuiPK;
    }

    public Notafiscal getNotafiscal() {
        return notafiscal;
    }

    public void setNotafiscal(Notafiscal notafiscal) {
        this.notafiscal = notafiscal;
    }

    public Processo getProcesso() {
        return processo;
    }

    public void setProcesso(Processo processo) {
        this.processo = processo;
    }

    public Protocolo getProtocolo() {
        return protocolo;
    }

    public void setProtocolo(Protocolo protocolo) {
        this.protocolo = protocolo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (possuiPK != null ? possuiPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Possui)) {
            return false;
        }
        Possui other = (Possui) object;
        if ((this.possuiPK == null && other.possuiPK != null) || (this.possuiPK != null && !this.possuiPK.equals(other.possuiPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Possui[ possuiPK=" + possuiPK + " ]";
    }
    
}
