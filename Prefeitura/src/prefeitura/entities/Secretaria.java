/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package prefeitura.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author lucia
 */
@Entity
@Table(name = "secretaria")
@NamedQueries({
    @NamedQuery(name = "Secretaria.findAll", query = "SELECT s FROM Secretaria s"),
    @NamedQuery(name = "Secretaria.findByIdSecretaria", query = "SELECT s FROM Secretaria s WHERE s.idSecretaria = :idSecretaria"),
    @NamedQuery(name = "Secretaria.findByNomeSecretaria", query = "SELECT s FROM Secretaria s WHERE s.nomeSecretaria = :nomeSecretaria")})
public class Secretaria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdSecretaria")
    private Integer idSecretaria;
    @Column(name = "NomeSecretaria")
    private String nomeSecretaria;
    @OneToMany(mappedBy = "idSecretaria")
    private List<Oficio> oficioList;
    @OneToMany(mappedBy = "idSecretaria")
    private List<Envia1> envia1List;

    public Secretaria() {
    }

    public Secretaria(Integer idSecretaria, String nomeSecretaria) {
        this.idSecretaria = idSecretaria;
        this.nomeSecretaria = nomeSecretaria;
    }

    public Secretaria(Integer idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public Integer getIdSecretaria() {
        return idSecretaria;
    }

    public void setIdSecretaria(Integer idSecretaria) {
        this.idSecretaria = idSecretaria;
    }

    public String getNomeSecretaria() {
        return nomeSecretaria;
    }

    public void setNomeSecretaria(String nomeSecretaria) {
        this.nomeSecretaria = nomeSecretaria;
    }

    public List<Oficio> getOficioList() {
        return oficioList;
    }

    public void setOficioList(List<Oficio> oficioList) {
        this.oficioList = oficioList;
    }

    public List<Envia1> getEnvia1List() {
        return envia1List;
    }

    public void setEnvia1List(List<Envia1> envia1List) {
        this.envia1List = envia1List;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idSecretaria != null ? idSecretaria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Secretaria)) {
            return false;
        }
        Secretaria other = (Secretaria) object;
        if ((this.idSecretaria == null && other.idSecretaria != null) || (this.idSecretaria != null && !this.idSecretaria.equals(other.idSecretaria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return idSecretaria + "-" + nomeSecretaria;
    }
    
}
