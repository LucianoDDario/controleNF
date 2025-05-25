/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author lucia
 */
@Entity
@Table(name = "protocolo")
@NamedQueries({
    @NamedQuery(name = "Protocolo.findAll", query = "SELECT p FROM Protocolo p"),
    @NamedQuery(name = "Protocolo.findByNumeroProtocolo", query = "SELECT p FROM Protocolo p WHERE p.numeroProtocolo = :numeroProtocolo"),
    @NamedQuery(name = "Protocolo.findByDataProtocolo", query = "SELECT p FROM Protocolo p WHERE p.dataProtocolo = :dataProtocolo")})
public class Protocolo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NumeroProtocolo")
    private Integer numeroProtocolo;
    @Column(name = "DataProtocolo")
    private String dataProtocolo;
    @JoinColumn(name = "NumeroNota", referencedColumnName = "NumeroNota")
    @ManyToOne
    private Notafiscal numeroNota;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "protocolo")
    private List<Possui> possuiList;
    @OneToMany(mappedBy = "numeroProtocolo")
    private List<Notafiscal> notafiscalList;

    public Protocolo() {
    }

    public Protocolo(Integer numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public Integer getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(Integer numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public String getDataProtocolo() {
        return dataProtocolo;
    }

    public void setDataProtocolo(String dataProtocolo) {
        this.dataProtocolo = dataProtocolo;
    }

    public Notafiscal getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(Notafiscal numeroNota) {
        this.numeroNota = numeroNota;
    }

    public List<Possui> getPossuiList() {
        return possuiList;
    }

    public void setPossuiList(List<Possui> possuiList) {
        this.possuiList = possuiList;
    }

    public List<Notafiscal> getNotafiscalList() {
        return notafiscalList;
    }

    public void setNotafiscalList(List<Notafiscal> notafiscalList) {
        this.notafiscalList = notafiscalList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroProtocolo != null ? numeroProtocolo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Protocolo)) {
            return false;
        }
        Protocolo other = (Protocolo) object;
        if ((this.numeroProtocolo == null && other.numeroProtocolo != null) || (this.numeroProtocolo != null && !this.numeroProtocolo.equals(other.numeroProtocolo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Protocolo[ numeroProtocolo=" + numeroProtocolo + " ]";
    }
    
}
