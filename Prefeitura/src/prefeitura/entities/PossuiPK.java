/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author lucia
 */
@Embeddable
public class PossuiPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "NumeroProtocolo")
    private int numeroProtocolo;
    @Basic(optional = false)
    @Column(name = "NumeroNota")
    private int numeroNota;
    @Basic(optional = false)
    @Column(name = "NumeroProcesso")
    private int numeroProcesso;

    public PossuiPK() {
    }

    public PossuiPK(int numeroProtocolo, int numeroNota, int numeroProcesso) {
        this.numeroProtocolo = numeroProtocolo;
        this.numeroNota = numeroNota;
        this.numeroProcesso = numeroProcesso;
    }

    public int getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(int numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public int getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(int numeroNota) {
        this.numeroNota = numeroNota;
    }

    public int getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(int numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) numeroProtocolo;
        hash += (int) numeroNota;
        hash += (int) numeroProcesso;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PossuiPK)) {
            return false;
        }
        PossuiPK other = (PossuiPK) object;
        if (this.numeroProtocolo != other.numeroProtocolo) {
            return false;
        }
        if (this.numeroNota != other.numeroNota) {
            return false;
        }
        if (this.numeroProcesso != other.numeroProcesso) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.PossuiPK[ numeroProtocolo=" + numeroProtocolo + ", numeroNota=" + numeroNota + ", numeroProcesso=" + numeroProcesso + " ]";
    }
    
}
