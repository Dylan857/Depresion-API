package com.depresion.security.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depresion.security.model.Usuario;

public interface UsuarioDao extends JpaRepository<Usuario, Integer> {
    
	public Optional<Usuario> findByUsuario(String usuario);
	
	public Optional<Usuario> findByNumeroDocumento(String numeroDocumento);
	
	public boolean existsByUsuario(String usuario);
	
	public boolean existsByNumeroDocumento(String numeroDocumento);
	
	public boolean existsByEmail(String email);
}
