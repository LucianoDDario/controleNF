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
@Table(name = "recebe")
@NamedQueries({
    @NamedQuery(name = "Recebe.findAll", query = "SELECT r FROM Recebe r"),
    @NamedQuery(name = "Recebe.findByIdRecebe", query = "SELECT r FROM Recebe r WHERE r.idRecebe = :idRecebe")})
public class Recebe implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdRecebe")
    private Integer idRecebe;
    @JoinColumn(name = "NumeroOficio", referencedColumnName = "NumeroOficio")
    @ManyToOne
    private Oficio numeroOficio;
    @JoinColumn(name = "IdUsuario", referencedColumnName = "IdUsuario")
    @ManyToOne
    private Usuario idUsuario;

    public Recebe() {
    }

    public Recebe(Integer idRecebe) {
        this.idRecebe = idRecebe;
    }

    public Integer getIdRecebe() {
        return idRecebe;
    }

    public void setIdRecebe(Integer idRecebe) {
        this.idRecebe = idRecebe;
    }

    public Oficio getNumeroOficio() {
        return numeroOficio;
    }

    public void setNumeroOficio(Oficio numeroOficio) {
        this.numeroOficio = numeroOficio;
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
        hash += (idRecebe != null ? idRecebe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Recebe)) {
            return false;
        }
        Recebe other = (Recebe) object;
        if ((this.idRecebe == null && other.idRecebe != null) || (this.idRecebe != null && !this.idRecebe.equals(other.idRecebe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "prefeitura.entities.Recebe[ idRecebe=" + idRecebe + " ]";
    }
    
}
