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
@Table(name = "processo")
@NamedQueries({
    @NamedQuery(name = "Processo.findAll", query = "SELECT p FROM Processo p"),
    @NamedQuery(name = "Processo.findByIdProcesso", query = "SELECT p FROM Processo p WHERE p.idProcesso = :idProcesso"),
    @NamedQuery(name = "Processo.findByNumeroProcesso", query = "SELECT p FROM Processo p WHERE p.numeroProcesso = :numeroProcesso"),
    @NamedQuery(name = "Processo.findByTipoProcesso", query = "SELECT p FROM Processo p WHERE p.tipoProcesso = :tipoProcesso"),
    @NamedQuery(name = "Processo.findByDataSaidaCompras", query = "SELECT p FROM Processo p WHERE p.dataSaidaCompras = :dataSaidaCompras")})
public class Processo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdProcesso")
    private Integer idProcesso;
    @Column(name = "NumeroProcesso")
    private Integer numeroProcesso;
    @Column(name = "TipoProcesso")
    private String tipoProcesso;
    @Column(name = "DataSaidaCompras")
    @Temporal(TemporalType.DATE)
    private Date dataSaidaCompras;
    @OneToMany(mappedBy = "numeroProcesso")
    private List<Possui> possuiList;
    @OneToMany(mappedBy = "idProcesso")
    private List<Notafiscal> notafiscalList;
    @OneToMany(mappedBy = "numeroProcesso")
    private List<Cria> criaList;
    @OneToMany(mappedBy = "numeroProcesso")
    private List<Possui1> possui1List;
    @JoinColumn(name = "IdFornecedor", referencedColumnName = "IdFornecedor")
    @ManyToOne
    private Fornecedor idFornecedor;
    @JoinColumn(name = "NumeroOficio", referencedColumnName = "NumeroOficio")
    @ManyToOne
    private Oficio numeroOficio;

    public Processo() {
    }

    public Processo(Integer idProcesso, Integer numeroProcesso, String tipoProcesso, Date dataSaidaCompras, Fornecedor idFornecedor, Oficio numeroOficio) {
        this.idProcesso = idProcesso;
        this.numeroProcesso = numeroProcesso;
        this.tipoProcesso = tipoProcesso;
        this.dataSaidaCompras = dataSaidaCompras;
        this.idFornecedor = idFornecedor;
        this.numeroOficio = numeroOficio;
    }

    public Processo(Integer idProcesso) {
        this.idProcesso = idProcesso;
    }

    public Integer getIdProcesso() {
        return idProcesso;
    }

    public void setIdProcesso(Integer idProcesso) {
        this.idProcesso = idProcesso;
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

    public List<Cria> getCriaList() {
        return criaList;
    }

    public void setCriaList(List<Cria> criaList) {
        this.criaList = criaList;
    }

    public List<Possui1> getPossui1List() {
        return possui1List;
    }

    public void setPossui1List(List<Possui1> possui1List) {
        this.possui1List = possui1List;
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

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idProcesso != null ? idProcesso.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Processo)) {
            return false;
        }
        Processo other = (Processo) object;
        if ((this.idProcesso == null && other.idProcesso != null) || (this.idProcesso != null && !this.idProcesso.equals(other.idProcesso))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Processo[ idProcesso=" + idProcesso + " ]";
    }
    
}
