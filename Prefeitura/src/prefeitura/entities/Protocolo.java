/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
import java.util.Collection;
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
import javax.persistence.OneToOne;
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

    @JoinColumn(name = "IdOficio", referencedColumnName = "IdOficio")
    @ManyToOne
    private Oficio idOficio;
    @JoinColumn(name = "IdProcesso", referencedColumnName = "IdProcesso")
    @ManyToOne
    private Processo idProcesso;
    @OneToOne(mappedBy = "idProtocolo")
    private Oficio oficio;

    @OneToMany(mappedBy = "idProtocolo")
    private Collection<Processo> processoCollection;
    @OneToMany(mappedBy = "idProtocolo")
    private Collection<Oficio> oficioCollection;

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdProtocolo")
    private Integer idProtocolo;
    @Basic(optional = false)
    @Column(name = "NumeroProtocolo")
    private int numeroProtocolo;
    @Column(name = "DataProtocolo")
    @Temporal(TemporalType.DATE)
    private Date dataProtocolo;
    @JoinColumn(name = "NumeroNota", referencedColumnName = "NumeroNota")
    @ManyToOne
    private Notafiscal numeroNota;
    @OneToMany(mappedBy = "idProtocolo")
    private List<Notafiscal> notafiscalList;

    public Protocolo() {
    }

    public Protocolo(Oficio idOficio, Processo idProcesso) {
        this.idOficio = idOficio;
        this.idProcesso = idProcesso;
    }

    public Protocolo(int numeroProtocolo, Date dataProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
        this.dataProtocolo = dataProtocolo;
    }

    public Protocolo(Integer idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public Protocolo(Integer idProtocolo, int numeroProtocolo) {
        this.idProtocolo = idProtocolo;
        this.numeroProtocolo = numeroProtocolo;
    }

    public Integer getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(Integer idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public int getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(int numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    public Date getDataProtocolo() {
        return dataProtocolo;
    }

    public void setDataProtocolo(Date dataProtocolo) {
        this.dataProtocolo = dataProtocolo;
    }

    public Notafiscal getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(Notafiscal numeroNota) {
        this.numeroNota = numeroNota;
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
        return String.valueOf(idProtocolo);
    }

    public Collection<Processo> getProcessoCollection() {
        return processoCollection;
    }

    public void setProcessoCollection(Collection<Processo> processoCollection) {
        this.processoCollection = processoCollection;
    }

    public Collection<Oficio> getOficioCollection() {
        return oficioCollection;
    }

    public void setOficioCollection(Collection<Oficio> oficioCollection) {
        this.oficioCollection = oficioCollection;
    }

    public Oficio getIdOficio() {
        return idOficio;
    }

    public void setIdOficio(Oficio idOficio) {
        this.idOficio = idOficio;
    }

    public Processo getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Processo idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Oficio getOficio() {
        return oficio;
    }

    public void setOficio(Oficio oficio) {
        this.oficio = oficio;
    }
    
}
