package com.depresion.security.controlador;

import java.text.ParseException;
import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.depresion.security.jwt.JwtProvider;
import com.depresion.security.model.Rol;
import com.depresion.security.model.Usuario;
import com.depresion.security.servicio.RolService;
import com.depresion.security.servicio.UsuarioService;
import com.depresion.servicio.MailService;
import com.depresion.dto.Mensaje;
import com.depresion.security.dto.JwtDto;
import com.depresion.security.dto.LoginUsuario;
import com.depresion.security.dto.NuevoUsuario;
import com.depresion.security.enums.RolNombre;

@RestController
@RequestMapping("/auth")
@CrossOrigin
public class AuthController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private RolService rolService;

	@Autowired
	private JwtProvider jwtProvider;
	
	@Autowired
	private MailService mailService;

	@PostMapping("/nuevo")
	public ResponseEntity<?> nuevoUsuario(@Valid @RequestBody NuevoUsuario nuevoUsuario, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResponseEntity<>(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
		} else if(usuarioService.existsByNumeroDocumento(nuevoUsuario.getNumeroDocumento())) {
			return new ResponseEntity<>(new Mensaje("Numero de documento " + "'" + nuevoUsuario.getNumeroDocumento() + "'" + " ya esta en uso"), HttpStatus.BAD_REQUEST);
		} else if(usuarioService.existsByEmail(nuevoUsuario.getEmail())) {
			return new ResponseEntity<>(new Mensaje("Email " + "'" + nuevoUsuario.getEmail() + "'" + " ya esta en uso"), HttpStatus.BAD_REQUEST);
		} else if(usuarioService.existsByUsuario(nuevoUsuario.getUsuario())) {
			return new ResponseEntity<>(new Mensaje("Email " + "'" + nuevoUsuario.getUsuario() + "'" + " ya esta en uso"), HttpStatus.BAD_REQUEST);
		}
		Usuario usuario = new Usuario(nuevoUsuario.getNombre(), nuevoUsuario.getTipoDocumento(), nuevoUsuario.getNumeroDocumento(),nuevoUsuario.getEmail(),nuevoUsuario.getUsuario(), passwordEncoder.encode(nuevoUsuario.getPassword()));
		Set<Rol> roles = new HashSet<>();
		roles.add(rolService.getRolNombre(RolNombre.ROLE_USER).get());
		if(nuevoUsuario.getRoles().contains("admin")) {
			roles.add(rolService.getRolNombre(RolNombre.ROLE_ADMIN).get());
		}
		usuario.setRoles(roles);
		usuarioService.guardarUsuario(usuario);
		mailService.sendMail(usuario.getEmail(), "Registro exitoso", "Bienvenido a nuestra pagina que busca ayudarte contra la depresion" + 
		usuario.toString());
		return new ResponseEntity<>(new Mensaje("Usuario creado con exito"), HttpStatus.OK);
	}
	
	@PostMapping("/login")
	public ResponseEntity<JwtDto> login(@Valid @RequestBody LoginUsuario loginUsuario, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return new ResponseEntity(new Mensaje("Campos mal puestos"), HttpStatus.BAD_REQUEST);
		}
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUsuario.getUsuario(), loginUsuario.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtProvider.generateToken(authentication);
		JwtDto jwtDto = new JwtDto(jwt);
		return new ResponseEntity<>(jwtDto, HttpStatus.OK);
	}

	@PostMapping("/refresh")
	public ResponseEntity<JwtDto> refresh(@RequestBody JwtDto jwtDto) throws ParseException {
		String token = jwtProvider.refreshToken(jwtDto);
		JwtDto refreshToken = new JwtDto(token);
		return new ResponseEntity<>(refreshToken, HttpStatus.OK);
	}
}
