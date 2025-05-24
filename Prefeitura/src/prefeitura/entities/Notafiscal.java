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
@Table(name = "notafiscal")
@NamedQueries({
    @NamedQuery(name = "Notafiscal.findAll", query = "SELECT n FROM Notafiscal n"),
    @NamedQuery(name = "Notafiscal.findByNumeroNota", query = "SELECT n FROM Notafiscal n WHERE n.numeroNota = :numeroNota"),
    @NamedQuery(name = "Notafiscal.findByDataEmissao", query = "SELECT n FROM Notafiscal n WHERE n.dataEmissao = :dataEmissao"),
    @NamedQuery(name = "Notafiscal.findByValorNota", query = "SELECT n FROM Notafiscal n WHERE n.valorNota = :valorNota")})
public class Notafiscal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "NumeroNota")
    private Integer numeroNota;
    @Column(name = "DataEmissao")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    @Column(name = "ValorNota")
    private Integer valorNota;
    @JoinTable(name = "envia", joinColumns = {
        @JoinColumn(name = "NumeroNota", referencedColumnName = "NumeroNota")}, inverseJoinColumns = {
        @JoinColumn(name = "IdFornecedor", referencedColumnName = "IdFornecedor")})
    @ManyToMany
    private List<Fornecedor> fornecedorList;
    @OneToMany(mappedBy = "numeroNota")
    private List<Protocolo> protocoloList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "notafiscal")
    private List<Possui> possuiList;
    @JoinColumn(name = "IdFornecedor", referencedColumnName = "IdFornecedor")
    @ManyToOne
    private Fornecedor idFornecedor;
    @JoinColumn(name = "NumeroProcesso", referencedColumnName = "NumeroProcesso")
    @ManyToOne
    private Processo numeroProcesso;
    @JoinColumn(name = "NumeroProtocolo", referencedColumnName = "NumeroProtocolo")
    @ManyToOne
    private Protocolo numeroProtocolo;

    public Notafiscal() {
    }

    public Notafiscal(Integer numeroNota) {
        this.numeroNota = numeroNota;
    }

    public Integer getNumeroNota() {
        return numeroNota;
    }

    public void setNumeroNota(Integer numeroNota) {
        this.numeroNota = numeroNota;
    }

    public Date getDataEmissao() {
        return dataEmissao;
    }

    public void setDataEmissao(Date dataEmissao) {
        this.dataEmissao = dataEmissao;
    }

    public Integer getValorNota() {
        return valorNota;
    }

    public void setValorNota(Integer valorNota) {
        this.valorNota = valorNota;
    }

    public List<Fornecedor> getFornecedorList() {
        return fornecedorList;
    }

    public void setFornecedorList(List<Fornecedor> fornecedorList) {
        this.fornecedorList = fornecedorList;
    }

    public List<Protocolo> getProtocoloList() {
        return protocoloList;
    }

    public void setProtocoloList(List<Protocolo> protocoloList) {
        this.protocoloList = protocoloList;
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

    public Processo getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(Processo numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Protocolo getNumeroProtocolo() {
        return numeroProtocolo;
    }

    public void setNumeroProtocolo(Protocolo numeroProtocolo) {
        this.numeroProtocolo = numeroProtocolo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (numeroNota != null ? numeroNota.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notafiscal)) {
            return false;
        }
        Notafiscal other = (Notafiscal) object;
        if ((this.numeroNota == null && other.numeroNota != null) || (this.numeroNota != null && !this.numeroNota.equals(other.numeroNota))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Notafiscal[ numeroNota=" + numeroNota + " ]";
    }
    
}
