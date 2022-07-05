package com.depresion.security.servicio;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.depresion.security.dao.RolDao;
import com.depresion.security.enums.RolNombre;
import com.depresion.security.model.Rol;

@Service
public class RolService {

	@Autowired
	private RolDao rolDao;
	
	public Optional<Rol> getRolNombre(RolNombre rolNombre){
		return rolDao.findByRolNombre(rolNombre);
	}
	
}
