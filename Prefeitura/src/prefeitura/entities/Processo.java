/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
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
@Table(name = "processo")
@NamedQueries({
    @NamedQuery(name = "Processo.findAll", query = "SELECT p FROM Processo p"),
    @NamedQuery(name = "Processo.findByNumeroProcesso", query = "SELECT p FROM Processo p WHERE p.numeroProcesso = :numeroProcesso"),
    @NamedQuery(name = "Processo.findByTipoProcesso", query = "SELECT p FROM Processo p WHERE p.tipoProcesso = :tipoProcesso"),
    @NamedQuery(name = "Processo.findByDataSaidaCompras", query = "SELECT p FROM Processo p WHERE p.dataSaidaCompras = :dataSaidaCompras")})
public class Processo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NumeroProcesso")
    private Integer numeroProcesso;
    @Column(name = "TipoProcesso")
    private String tipoProcesso;
    @Column(name = "DataSaidaCompras")
    @Temporal(TemporalType.DATE)
    private Date dataSaidaCompras;
    @JoinTable(name = "cria", joinColumns = {
        @JoinColumn(name = "NumeroProcesso", referencedColumnName = "NumeroProcesso")}, inverseJoinColumns = {
        @JoinColumn(name = "IdUsuario", referencedColumnName = "IdUsuario")})
    @ManyToMany
    private List<Usuario> usuarioList;
    @JoinTable(name = "possui1", joinColumns = {
        @JoinColumn(name = "NumeroProcesso", referencedColumnName = "NumeroProcesso")}, inverseJoinColumns = {
        @JoinColumn(name = "NumeroOficio", referencedColumnName = "NumeroOficio")})
    @ManyToMany
    private List<Oficio> oficioList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "processo")
    private List<Possui> possuiList;
    @JoinColumn(name = "IdFornecedor", referencedColumnName = "IdFornecedor")
    @ManyToOne
    private Fornecedor idFornecedor;
    @JoinColumn(name = "NumeroOficio", referencedColumnName = "NumeroOficio")
    @ManyToOne
    private Oficio numeroOficio;
    @OneToMany(mappedBy = "numeroProcesso")
    private List<Notafiscal> notafiscalList;

    public Processo() {
    }

    public Processo(Integer numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Integer getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(Integer numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public String getTipoProcesso() {
        return tipoProcesso;
    }

    public void setTipoProcesso(String tipoProcesso) {
        this.tipoProcesso = tipoProcesso;
    }

    public Date getDataSaidaCompras() {
        return dataSaidaCompras;
    }

    public void setDataSaidaCompras(Date dataSaidaCompras) {
        this.dataSaidaCompras = dataSaidaCompras;
    }

    public List<Usuario> getUsuarioList() {
        return usuarioList;
    }

    public void setUsuarioList(List<Usuario> usuarioList) {
        this.usuarioList = usuarioList;
    }

    public List<Oficio> getOficioList() {
        return oficioList;
    }

    public void setOficioList(List<Oficio> oficioList) {
        this.oficioList = oficioList;
    }

    public List<Possui> getPossuiList() {
        return possuiList;
    }

    public void setPossuiList(List<Possui> possuiList) {
        this.possuiList = possuiList;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public Oficio getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(Oficio numeroOficio) {
        this.numeroOficio = numeroOficio;
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
        hash += (numeroProcesso != null ? numeroProcesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Processo)) {
            return false;
        }
        Processo other = (Processo) object;
        if ((this.numeroProcesso == null && other.numeroProcesso != null) || (this.numeroProcesso != null && !this.numeroProcesso.equals(other.numeroProcesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Processo[ numeroProcesso=" + numeroProcesso + " ]";
    }
    
}
