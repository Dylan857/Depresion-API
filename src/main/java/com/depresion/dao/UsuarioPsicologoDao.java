package com.depresion.dao;

import com.depresion.model.UsuarioPsicologo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UsuarioPsicologoDao extends JpaRepository<UsuarioPsicologo, Integer> {
    
    public Optional<UsuarioPsicologo> findByNumeroDocumento(String numeroDocumento);
	
}
