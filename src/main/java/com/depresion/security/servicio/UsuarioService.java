package com.depresion.security.servicio;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.depresion.security.dao.UsuarioDao;
import com.depresion.security.model.Usuario;

@Service
@Transactional
public class UsuarioService {
	
	@Autowired
	private UsuarioDao usuarioDao;
	
	public Optional<Usuario> getByUsuario(String usuario) {
		return usuarioDao.findByUsuario(usuario);
	}
	
	public Usuario findByUsuario(String usuario) {
		Optional<Usuario> usuarioOptional = usuarioDao.findByUsuario(usuario);
		if(usuarioOptional.isPresent()) {
			return usuarioOptional.get();
		} else {
			return null;
		}
	}
	
	public List<Usuario> listarUsuarios() {
		return usuarioDao.findAll();
	}
	
	public boolean existsByUsuario(String usuario) {
		return usuarioDao.existsByUsuario(usuario);
	}
	
	public boolean existsByNumeroDocumento(String numeroDocumento) {
		return usuarioDao.existsByNumeroDocumento(numeroDocumento);
	}
	
	public boolean existsByEmail(String email) {
		return usuarioDao.existsByEmail(email);
	}
	
	public void guardarUsuario(Usuario usuario) {
		usuarioDao.save(usuario);
	}
	
	public Usuario findByNumeroDocumento(String numeroDocumento) {
		Optional<Usuario> usuarioOptional = usuarioDao.findByNumeroDocumento(numeroDocumento);
		if(usuarioOptional.isPresent()) {
			return usuarioOptional.get();
		} else {
			return null;
		}
	}
	
}
