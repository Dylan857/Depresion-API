package com.depresion.security.model;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class UsuarioPrincipal implements UserDetails {
	
	private static final long serialVersionUID = 1L;
	
	private String nombre;
	private String tipoDocumento;
	private String numeroDocumento;
	private String email;
	private String usuario;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	
	

	public UsuarioPrincipal(String nombre, String tipoDocumento, String numeroDocumento, String email, String usuario,
			String password, Collection<? extends GrantedAuthority> authorities) {
		this.nombre = nombre;
		this.tipoDocumento = tipoDocumento;
		this.numeroDocumento = numeroDocumento;
		this.email = email;
		this.usuario = usuario;
		this.password = password;
		this.authorities = authorities;
	}

	public static UsuarioPrincipal build(Usuario usuario) {
		List<GrantedAuthority> authorities = usuario.getRoles().stream().map(rol -> new SimpleGrantedAuthority(rol.getRolNombre().name()
				)).collect(Collectors.toList());
		return new UsuarioPrincipal(usuario.getNombre(), usuario.getTipoDocumento(), usuario.getNumeroDocumento(), usuario.getEmail(), usuario.getUsuario(), usuario.getClave(), authorities);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.usuario;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public String getNombre() {
		return this.nombre;
	}
	
	public String getTipoDocumento() {
		return this.tipoDocumento;
	}
	
	public String getNumeroDocumento() {
		return this.numeroDocumento;
	}
	
	public String email() {
		return this.email;
	}
	
}
