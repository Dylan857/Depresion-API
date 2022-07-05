package com.depresion.security.dao;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.depresion.security.enums.RolNombre;
import com.depresion.security.model.Rol;

public interface RolDao extends JpaRepository<Rol, Integer> {
	
	public Optional<Rol> findByRolNombre(RolNombre rolNombre);
	
}
