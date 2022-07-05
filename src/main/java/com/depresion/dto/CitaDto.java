package com.depresion.dto;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class CitaDto implements Serializable {

	private static final long serialVersionUID = 1L;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fecha;
	
	@JsonFormat(pattern = "HH:mm")
	private Date hora;
	
	private String plataforma;
	
	private String numeroDocumentoUsuario;
	
	private String numeroDocumentoPsicologo;

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

	public String getNumeroDocumentoUsuario() {
		return numeroDocumentoUsuario;
	}

	public void setNumeroDocumentoUsuario(String numeroDocumentoUsuario) {
		this.numeroDocumentoUsuario = numeroDocumentoUsuario;
	}

	public String getNumeroDocumentoPsicologo() {
		return numeroDocumentoPsicologo;
	}

	public void setNumeroDocumentoPsicologo(String numeroDocumentoPsicologo) {
		this.numeroDocumentoPsicologo = numeroDocumentoPsicologo;
	}
	
	
	
}
