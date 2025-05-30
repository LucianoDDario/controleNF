/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "notafiscal")
@NamedQueries({
    @NamedQuery(name = "Notafiscal.findAll", query = "SELECT n FROM Notafiscal n"),
    @NamedQuery(name = "Notafiscal.findByIdNotaFiscal", query = "SELECT n FROM Notafiscal n WHERE n.idNotaFiscal = :idNotaFiscal"),
    @NamedQuery(name = "Notafiscal.findByNumeroNota", query = "SELECT n FROM Notafiscal n WHERE n.numeroNota = :numeroNota"),
    @NamedQuery(name = "Notafiscal.findByDataEmissao", query = "SELECT n FROM Notafiscal n WHERE n.dataEmissao = :dataEmissao"),
    @NamedQuery(name = "Notafiscal.findByValorNota", query = "SELECT n FROM Notafiscal n WHERE n.valorNota = :valorNota")})
public class Notafiscal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdNotaFiscal")
    private Integer idNotaFiscal;
    @Column(name = "NumeroNota")
    private Integer numeroNota;
    @Column(name = "DataEmissao")
    @Temporal(TemporalType.DATE)
    private Date dataEmissao;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "ValorNota")
    private BigDecimal valorNota;
    @OneToMany(mappedBy = "numeroNota")
    private List<Protocolo> protocoloList;
    @OneToMany(mappedBy = "numeroNota")
    private List<Envia> enviaList;
    @JoinColumn(name = "IdFornecedor", referencedColumnName = "IdFornecedor")
    @ManyToOne
    private Fornecedor idFornecedor;
    @JoinColumn(name = "IdProcesso", referencedColumnName = "IdProcesso")
    @ManyToOne
    private Processo idProcesso;
    @JoinColumn(name = "IdProtocolo", referencedColumnName = "IdProtocolo")
    @ManyToOne
    private Protocolo idProtocolo;

    public Notafiscal() {
    }

    public Notafiscal(Integer idNotaFiscal) {
        this.idNotaFiscal = idNotaFiscal;
    }

    public Integer getIdNotaFiscal() {
        return idNotaFiscal;
    }

    public void setIdNotaFiscal(Integer idNotaFiscal) {
        this.idNotaFiscal = idNotaFiscal;
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

    public BigDecimal getValorNota() {
        return valorNota;
    }

    public void setValorNota(BigDecimal valorNota) {
        this.valorNota = valorNota;
    }

    public List<Protocolo> getProtocoloList() {
        return protocoloList;
    }

    public void setProtocoloList(List<Protocolo> protocoloList) {
        this.protocoloList = protocoloList;
    }

    public List<Envia> getEnviaList() {
        return enviaList;
    }

    public void setEnviaList(List<Envia> enviaList) {
        this.enviaList = enviaList;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
    }

    public Processo getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Processo idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Protocolo getIdProtocolo() {
        return idProtocolo;
    }

    public void setIdProtocolo(Protocolo idProtocolo) {
        this.idProtocolo = idProtocolo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idNotaFiscal != null ? idNotaFiscal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Notafiscal)) {
            return false;
        }
        Notafiscal other = (Notafiscal) object;
        if ((this.idNotaFiscal == null && other.idNotaFiscal != null) || (this.idNotaFiscal != null && !this.idNotaFiscal.equals(other.idNotaFiscal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Notafiscal[ idNotaFiscal=" + idNotaFiscal + " ]";
    }
    
}
