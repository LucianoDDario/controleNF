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
@Table(name = "envia1")
@NamedQueries({
    @NamedQuery(name = "Envia1.findAll", query = "SELECT e FROM Envia1 e"),
    @NamedQuery(name = "Envia1.findByIdEnvia1", query = "SELECT e FROM Envia1 e WHERE e.idEnvia1 = :idEnvia1")})
public class Envia1 implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdEnvia1")
    private Integer idEnvia1;
    @JoinColumn(name = "NumeroOficio", referencedColumnName = "NumeroOficio")
    @ManyToOne
    private Oficio numeroOficio;
    @JoinColumn(name = "IdSecretaria", referencedColumnName = "IdSecretaria")
    @ManyToOne
    private Secretaria idSecretaria;

    public Envia1() {
    }

    public Envia1(Integer idEnvia1) {
        this.idEnvia1 = idEnvia1;
    }

    public Integer getIdEnvia1() {
        return idEnvia1;
    }

    public void setIdEnvia1(Integer idEnvia1) {
        this.idEnvia1 = idEnvia1;
    }

    public Oficio getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(Oficio numeroOficio) {
        this.numeroOficio = numeroOficio;
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
        hash += (idEnvia1 != null ? idEnvia1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Envia1)) {
            return false;
        }
        Envia1 other = (Envia1) object;
        if ((this.idEnvia1 == null && other.idEnvia1 != null) || (this.idEnvia1 != null && !this.idEnvia1.equals(other.idEnvia1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Envia1[ idEnvia1=" + idEnvia1 + " ]";
    }
    
}
