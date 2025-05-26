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
@Table(name = "cria")
@NamedQueries({
    @NamedQuery(name = "Cria.findAll", query = "SELECT c FROM Cria c"),
    @NamedQuery(name = "Cria.findByIdCria", query = "SELECT c FROM Cria c WHERE c.idCria = :idCria")})
public class Cria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdCria")
    private Integer idCria;
    @JoinColumn(name = "NumeroProcesso", referencedColumnName = "NumeroProcesso")
    @ManyToOne
    private Processo numeroProcesso;
    @JoinColumn(name = "IdUsuario", referencedColumnName = "IdUsuario")
    @ManyToOne
    private Usuario idUsuario;

    public Cria() {
    }

    public Cria(Integer idCria) {
        this.idCria = idCria;
    }

    public Integer getIdCria() {
        return idCria;
    }

    public void setIdCria(Integer idCria) {
        this.idCria = idCria;
    }

    public Processo getNumeroProcesso() {
        return numeroProcesso;
    }

    public void setNumeroProcesso(Processo numeroProcesso) {
        this.numeroProcesso = numeroProcesso;
    }

    public Usuario getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Usuario idUsuario) {
        this.idUsuario = idUsuario;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idCria != null ? idCria.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cria)) {
            return false;
        }
        Cria other = (Cria) object;
        if ((this.idCria == null && other.idCria != null) || (this.idCria != null && !this.idCria.equals(other.idCria))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Cria[ idCria=" + idCria + " ]";
    }
    
}
