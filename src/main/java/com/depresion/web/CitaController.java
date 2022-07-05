package com.depresion.web;

import com.depresion.dto.CitaDto;
import com.depresion.model.Cita;
import com.depresion.model.UsuarioPsicologo;
import com.depresion.security.model.Usuario;
import com.depresion.security.servicio.UsuarioService;
import com.depresion.servicio.CitaService;
import com.depresion.servicio.MailService;
import com.depresion.servicio.UsuarioPsicologoService;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/citas")
@CrossOrigin(origins = "http://localhost:4200/")
public class CitaController {

	@Autowired
	private CitaService citaService;

	@Autowired
	private UsuarioPsicologoService usuarioPsicologoService;

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private MailService mailService;

	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/guardar")
	public ResponseEntity<Cita> generarCita(@RequestBody CitaDto citaDto) {
		Usuario usuario = usuarioService.findByNumeroDocumento(citaDto.getNumeroDocumentoUsuario());
		UsuarioPsicologo usuarioPsicologo = usuarioPsicologoService
				.findByNumeroDocumento(citaDto.getNumeroDocumentoPsicologo());
		Cita cita = new Cita(citaDto.getFecha(), citaDto.getHora(), citaDto.getPlataforma(), usuario, usuarioPsicologo);
		mailService.sendMail(usuario.getEmail(), "Cita programada",
				"A continiacion se mostrara la informacion de la cita : \n" + cita.toString());
		mailService.sendMail(usuarioPsicologo.getEmail(), "Cita programada",
				"A continuacion se mostrara la informacion de la cita : \n" + cita.toString());
		return new ResponseEntity<>(citaService.guardar(cita), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/listaCitas")
	public ResponseEntity<List<Cita>> listaCitas() {
		return new ResponseEntity<>(citaService.listarCitas(), HttpStatus.OK);
	}

	@GetMapping("/cita/{usuario}")
	public ResponseEntity<List<Cita>> encontrarCitaByUsuario(@PathVariable("usuario") String usuario) {
		Usuario usuarioEncontrado = usuarioService.findByUsuario(usuario);
		return new ResponseEntity<>(citaService.findCitaByUsuario(usuarioEncontrado), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/{idCita}")
	public ResponseEntity<Cita> encontrarCita(@PathVariable("idCita") Integer idCita) {
		return new ResponseEntity<>(citaService.citaFindById(idCita), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@PutMapping("/editar/{idCita}")
	public ResponseEntity<Cita> modificarCita(@PathVariable("idCita") Integer idCita, @RequestBody CitaDto citaDto) {
		Cita citaEncontrada = citaService.citaFindById(idCita);
		Usuario usuario = usuarioService.findByNumeroDocumento(citaDto.getNumeroDocumentoUsuario());
		UsuarioPsicologo usuarioPsicologo = usuarioPsicologoService
				.findByNumeroDocumento(citaDto.getNumeroDocumentoPsicologo());
		if (citaEncontrada == null) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			try {
				citaEncontrada.setFecha(citaDto.getFecha());
				citaEncontrada.setHora(citaDto.getHora());
				citaEncontrada.setPlataforma(citaDto.getPlataforma());
				citaEncontrada.setUsuario(usuario);
				citaEncontrada.setPsicologo(usuarioPsicologo);
				mailService.sendMail(usuario.getEmail(), "Cita modificada",
						"A continiacion se mostrara la informacion de la cita : \n" + citaEncontrada.toString());
				mailService.sendMail(usuarioPsicologo.getEmail(), "Cita modificada",
						"A continuacion se mostrara la informacion de la cita : \n" + citaEncontrada.toString());
				return new ResponseEntity<>(citaService.guardar(citaEncontrada), HttpStatus.OK);
			} catch (DataAccessException e) {
				return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
			}

		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/eliminar/{idCita}")
	public ResponseEntity<Boolean> eliminarCita(@PathVariable("idCita") Integer idCita) {
		boolean citaEliminada = citaService.eliminar(idCita);
		if (citaEliminada) {
			return new ResponseEntity<>(HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/usuariosPsicologos")
	public ResponseEntity<List<UsuarioPsicologo>> usuariosPsicologos() {
		return new ResponseEntity<>(usuarioPsicologoService.listarUsuarios(), HttpStatus.OK);
	}

	@PreAuthorize("hasRole('ADMIN')")
	@GetMapping("/usuarios")
	public ResponseEntity<List<Usuario>> usuarios() {
		return new ResponseEntity<>(usuarioService.listarUsuarios(), HttpStatus.OK);
	}

}
