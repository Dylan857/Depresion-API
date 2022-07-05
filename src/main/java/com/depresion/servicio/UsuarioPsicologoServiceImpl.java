package com.depresion.servicio;

import com.depresion.dao.UsuarioPsicologoDao;
import com.depresion.model.UsuarioPsicologo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UsuarioPsicologoServiceImpl implements UsuarioPsicologoService {

    @Autowired
    private UsuarioPsicologoDao usuarioPsicologoDao;

	@Override
	public UsuarioPsicologo findByNumeroDocumento(String numeroDocumento) {
		Optional<UsuarioPsicologo> psicologoOptional = usuarioPsicologoDao.findByNumeroDocumento(numeroDocumento);
		if(psicologoOptional.isPresent()) {
			return psicologoOptional.get();
		} else {
			return null;
		}
	}

	@Override
	public List<UsuarioPsicologo> listarUsuarios() {
		return usuarioPsicologoDao.findAll();
	}
    
}
