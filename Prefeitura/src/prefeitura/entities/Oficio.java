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
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "oficio")
@NamedQueries({
    @NamedQuery(name = "Oficio.findAll", query = "SELECT o FROM Oficio o"),
    @NamedQuery(name = "Oficio.findByNumeroOficio", query = "SELECT o FROM Oficio o WHERE o.numeroOficio = :numeroOficio"),
    @NamedQuery(name = "Oficio.findByDescricao", query = "SELECT o FROM Oficio o WHERE o.descricao = :descricao"),
    @NamedQuery(name = "Oficio.findByDataOficio", query = "SELECT o FROM Oficio o WHERE o.dataOficio = :dataOficio")})
public class Oficio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NumeroOficio")
    private Integer numeroOficio;
    @Column(name = "Descricao")
    private String descricao;
    @Column(name = "DataOficio")
    @Temporal(TemporalType.DATE)
    private Date dataOficio;
    @ManyToMany(mappedBy = "oficioList")
    private List<Processo> processoList;
    @JoinTable(name = "envia1", joinColumns = {
        @JoinColumn(name = "NumeroOficio", referencedColumnName = "NumeroOficio")}, inverseJoinColumns = {
        @JoinColumn(name = "IdSecretaria", referencedColumnName = "IdSecretaria")})
    @ManyToMany
    private List<Secretaria> secretariaList;
    @JoinTable(name = "recebe", joinColumns = {
        @JoinColumn(name = "NumeroOficio", referencedColumnName = "NumeroOficio")}, inverseJoinColumns = {
        @JoinColumn(name = "IdUsuario", referencedColumnName = "IdUsuario")})
    @ManyToMany
    private List<Usuario> usuarioList;
    @OneToMany(mappedBy = "numeroOficio")
    private List<Processo> processoList1;
    @JoinColumn(name = "IdSecretaria", referencedColumnName = "IdSecretaria")
    @ManyToOne
    private Secretaria idSecretaria;

    public Oficio() {
    }

    public Oficio(Integer numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    public Integer getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(Integer numeroOficio) {
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

    public List<Processo> getProcessoList() {
        return processoList;
    }

    public void setProcessoList(List<Processo> processoList) {
        this.processoList = processoList;
    }

    public List<Secretaria> getSecretariaList() {
        return secretariaList;
    }

    public void setSecretariaList(List<Secretaria> secretariaList) {
        this.secretariaList = secretariaList;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public List<Processo> getProcessoList1() {
        return processoList1;
    }

    public void setProcessoList1(List<Processo> processoList1) {
        this.processoList1 = processoList1;
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
        hash += (numeroOficio != null ? numeroOficio.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Oficio)) {
            return false;
        }
        Oficio other = (Oficio) object;
        if ((this.numeroOficio == null && other.numeroOficio != null) || (this.numeroOficio != null && !this.numeroOficio.equals(other.numeroOficio))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Oficio[ numeroOficio=" + numeroOficio + " ]";
    }
    
}
