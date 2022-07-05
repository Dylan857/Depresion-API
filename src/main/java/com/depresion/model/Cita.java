package com.depresion.model;

import java.io.Serializable;
import java.util.Date;
import java.util.Objects;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import com.depresion.security.model.Usuario;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "cita")
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id_cita")
    private Integer idCita;
    @Column(name = "fecha")
    @Temporal(TemporalType.DATE)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha;
    @Column(name = "hora")
    @Temporal(TemporalType.TIME)
    @JsonFormat(pattern = "HH:mm")
    private Date hora;
    @Column(name = "plataforma")
    private String plataforma;
    @JoinColumn(name = "numero_documento_usuario", referencedColumnName = "numero_documento")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario usuario;
    @JoinColumn(name = "numero_documento_psicologo", referencedColumnName = "numero_documento")
    @ManyToOne (fetch = FetchType.LAZY)
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private UsuarioPsicologo psicologo;

    public Cita() {
    }

    public Cita(Date fecha, Date hora, String plataforma, Usuario usuario, UsuarioPsicologo psicologo) {
		this.fecha = fecha;
		this.hora = hora;
		this.plataforma = plataforma;
		this.usuario = usuario;
		this.psicologo = psicologo;
	}

	public Cita(Integer idCita) {
        this.idCita = idCita;
    }

    public Integer getIdCita() {
        return idCita;
    }

    public void setIdCita(Integer idCita) {
        this.idCita = idCita;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Date getHora() {
        return hora;
    }

    public void setHora(Date hora) {
        this.hora = hora;
    }

    public String getPlataforma() {
        return plataforma;
    }

    public void setPlataforma(String plataforma) {
        this.plataforma = plataforma;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public UsuarioPsicologo getPsicologo() {
        return psicologo;
    }

    public void setPsicologo(UsuarioPsicologo psicologo) {
        this.psicologo = psicologo;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.idCita);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Cita other = (Cita) obj;
        if (!Objects.equals(this.idCita, other.idCita)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  "Fecha:" + fecha + "\n Hora:" + hora + "\n Plataforma=" + plataforma + "\n Numero documento usuario=" + usuario.getNumeroDocumento() + "\n Numero documento psicologo=" + psicologo.getNumeroDocumento();
    }
    
    
    
}
