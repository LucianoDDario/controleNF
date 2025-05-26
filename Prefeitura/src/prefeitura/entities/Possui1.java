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
@Table(name = "possui1")
@NamedQueries({
    @NamedQuery(name = "Possui1.findAll", query = "SELECT p FROM Possui1 p"),
    @NamedQuery(name = "Possui1.findByIdPossui1", query = "SELECT p FROM Possui1 p WHERE p.idPossui1 = :idPossui1")})
public class Possui1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdPossui1")
    private Integer idPossui1;
    @JoinColumn(name = "NumeroOficio", referencedColumnName = "NumeroOficio")
    @ManyToOne
    private Oficio numeroOficio;
    @JoinColumn(name = "NumeroProcesso", referencedColumnName = "NumeroProcesso")
    @ManyToOne
    private Processo numeroProcesso;

    public Possui1() {
    }

    public Possui1(Integer idPossui1) {
        this.idPossui1 = idPossui1;
    }

    public Integer getIdPossui1() {
        return idPossui1;
    }

    public void setIdPossui1(Integer idPossui1) {
        this.idPossui1 = idPossui1;
    }

    public Oficio getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(Oficio numeroOficio) {
        this.numeroOficio = numeroOficio;
    }

    public Processo getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(Processo numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPossui1 != null ? idPossui1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Possui1)) {
            return false;
        }
        Possui1 other = (Possui1) object;
        if ((this.idPossui1 == null && other.idPossui1 != null) || (this.idPossui1 != null && !this.idPossui1.equals(other.idPossui1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Possui1[ idPossui1=" + idPossui1 + " ]";
    }
    
}
