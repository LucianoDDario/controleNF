/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucia
 */
@Entity
@Table(name = "protocolo")
@NamedQueries({
    @NamedQuery(name = "Protocolo.findAll", query = "SELECT p FROM Protocolo p"),
    @NamedQuery(name = "Protocolo.findByIdProtocolo", query = "SELECT p FROM Protocolo p WHERE p.idProtocolo = :idProtocolo"),
    @NamedQuery(name = "Protocolo.findByNumeroProtocolo", query = "SELECT p FROM Protocolo p WHERE p.numeroProtocolo = :numeroProtocolo"),
    @NamedQuery(name = "Protocolo.findByDataProtocolo", query = "SELECT p FROM Protocolo p WHERE p.dataProtocolo = :dataProtocolo")})
public class Protocolo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdProtocolo")
    private Integer idProtocolo;
    @Column(name = "NumeroProtocolo")
    private Integer numeroProtocolo;
    @Column(name = "DataProtocolo")
    @Temporal(TemporalType.DATE)
    private Date dataProtocolo;
    @OneToMany(mappedBy = "numeroProtocolo")
    private List<Possui> possuiList;
    @OneToMany(mappedBy = "idProtocolo")
    private List<Notafiscal> notafiscalList;
    @JoinColumn(name = "NumeroNota", referencedColumnName = "NumeroNota")
    @ManyToOne
    private Notafiscal numeroNota;

    public Protocolo() {
    }

    public Protocolo(Integer idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public Integer getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(Integer idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public Integer getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(Integer numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public Date getDataProtocolo() {
        return dataProtocolo;
    }

    public void setDataProtocolo(Date dataProtocolo) {
        this.dataProtocolo = dataProtocolo;
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

    public Notafiscal getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(Notafiscal numeroNota) {
        this.numeroNota = numeroNota;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProtocolo != null ? idProtocolo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Protocolo)) {
            return false;
        }
        Protocolo other = (Protocolo) object;
        if ((this.idProtocolo == null && other.idProtocolo != null) || (this.idProtocolo != null && !this.idProtocolo.equals(other.idProtocolo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Protocolo[ idProtocolo=" + idProtocolo + " ]";
    }
    
}
