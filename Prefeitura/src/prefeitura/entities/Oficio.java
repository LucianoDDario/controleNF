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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author lucia
 */
@Entity
@Table(name = "oficio")
@NamedQueries({
    @NamedQuery(name = "Oficio.findAll", query = "SELECT o FROM Oficio o"),
    @NamedQuery(name = "Oficio.findByIdOficio", query = "SELECT o FROM Oficio o WHERE o.idOficio = :idOficio"),
    @NamedQuery(name = "Oficio.findByNumeroOficio", query = "SELECT o FROM Oficio o WHERE o.numeroOficio = :numeroOficio"),
    @NamedQuery(name = "Oficio.findByDescricao", query = "SELECT o FROM Oficio o WHERE o.descricao = :descricao"),
    @NamedQuery(name = "Oficio.findByDataOficio", query = "SELECT o FROM Oficio o WHERE o.dataOficio = :dataOficio")})
public class Oficio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdOficio")
    private Integer idOficio;
    @Basic(optional = false)
    @Column(name = "NumeroOficio")
    private int numeroOficio;
    @Column(name = "Descricao")
    private String descricao;
    @Column(name = "DataOficio")
    @Temporal(TemporalType.DATE)
    private Date dataOficio;
    @OneToMany(mappedBy = "idOficio")
    private List<Protocolo> protocoloList;
    @OneToMany(mappedBy = "idOficio")
    private List<Processo> processoList;
    @JoinColumn(name = "IdProtocolo", referencedColumnName = "IdProtocolo")
    @OneToOne
    private Protocolo idProtocolo;
    @JoinColumn(name = "IdSecretaria", referencedColumnName = "IdSecretaria")
    @ManyToOne
    private Secretaria idSecretaria;

    public Oficio() {
    }

    public Oficio(Integer idOficio) {
        this.idOficio = idOficio;
    }

    public Oficio(int numeroOficio, String descricao, Date dataOficio, Protocolo idProtocolo, Secretaria idSecretaria) {
        this.numeroOficio = numeroOficio;
        this.descricao = descricao;
        this.dataOficio = dataOficio;
        this.idProtocolo = idProtocolo;
        this.idSecretaria = idSecretaria;
    }

    
    public Integer getIdOficio() {
        return idOficio;
    }

    public void setIdOficio(Integer idOficio) {
        this.idOficio = idOficio;
    }

    public int getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(int numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Date getDataOficio() {
        return dataOficio;
    }

    public void setDataOficio(Date dataOficio) {
        this.dataOficio = dataOficio;
    }

    public List<Protocolo> getProtocoloList() {
        return protocoloList;
    }

    public void setProtocoloList(List<Protocolo> protocoloList) {
        this.protocoloList = protocoloList;
    }

    public List<Processo> getProcessoList() {
        return processoList;
    }

    public void setProcessoList(List<Processo> processoList) {
        this.processoList = processoList;
    }

    public Protocolo getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(Protocolo idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    public Secretaria getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Secretaria idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idOficio != null ? idOficio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oficio)) {
            return false;
        }
        Oficio other = (Oficio) object;
        if ((this.idOficio == null && other.idOficio != null) || (this.idOficio != null && !this.idOficio.equals(other.idOficio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Oficio[ idOficio=" + idOficio + " ]";
    }
    
}
