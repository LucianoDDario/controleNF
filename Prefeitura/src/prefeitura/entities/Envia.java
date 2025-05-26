/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
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
import javax.persistence.Table;

/**
 *
 * @author lucia
 */
@Entity
@Table(name = "envia")
@NamedQueries({
    @NamedQuery(name = "Envia.findAll", query = "SELECT e FROM Envia e"),
    @NamedQuery(name = "Envia.findByIdEnvia", query = "SELECT e FROM Envia e WHERE e.idEnvia = :idEnvia")})
public class Envia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdEnvia")
    private Integer idEnvia;
    @JoinColumn(name = "IdFornecedor", referencedColumnName = "IdFornecedor")
    @ManyToOne
    private Fornecedor idFornecedor;
    @JoinColumn(name = "NumeroNota", referencedColumnName = "NumeroNota")
    @ManyToOne
    private Notafiscal numeroNota;

    public Envia() {
    }

    public Envia(Integer idEnvia) {
        this.idEnvia = idEnvia;
    }

    public Integer getIdEnvia() {
        return idEnvia;
    }

    public void setIdEnvia(Integer idEnvia) {
        this.idEnvia = idEnvia;
    }

    public Fornecedor getIdFornecedor() {
        return idFornecedor;
    }

    public void setIdFornecedor(Fornecedor idFornecedor) {
        this.idFornecedor = idFornecedor;
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
        hash += (idEnvia != null ? idEnvia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Envia)) {
            return false;
        }
        Envia other = (Envia) object;
        if ((this.idEnvia == null && other.idEnvia != null) || (this.idEnvia != null && !this.idEnvia.equals(other.idEnvia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Envia[ idEnvia=" + idEnvia + " ]";
    }
    
}
