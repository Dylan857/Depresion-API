package com.depresion.security.jwt;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import com.depresion.security.dto.JwtDto;
import com.depresion.security.model.UsuarioPrincipal;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Component
public class JwtProvider {

	private final static Logger logger = LoggerFactory.getLogger(JwtProvider.class);

	@Value("${jwt.secret}")
	private String secret;

	@Value("${jwt.expiration}")
	private int expiration;

	public String generateToken(Authentication authentication) {
		Date fechaInicio = new Date();
		Date fechaExpiracion = new Date(fechaInicio.getTime() + expiration);

		UsuarioPrincipal usuarioPrincipal = (UsuarioPrincipal) authentication.getPrincipal();
		List<String> roles = usuarioPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
				.collect((Collectors.toList()));
		return Jwts.builder().setSubject(usuarioPrincipal.getUsername()).claim("roles", roles).setIssuedAt(fechaInicio)
				.setExpiration(fechaExpiracion).signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
	}

	public String getTokenFromNombre(String token) {
		return Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token).getBody().getSubject();
	}

	public boolean validateToken(String token) {
		try {
			Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(token);
			return true;
		} catch (MalformedJwtException e) {
			logger.error("Token mal generado");
		} catch (UnsupportedJwtException e) {
			logger.error("Token no soportado");
		} catch (IllegalArgumentException e) {
			logger.error("Token vacio");
		} catch (SignatureException e) {
			logger.error("Token mal generado");
		}

		return false;
	}

	public String refreshToken(JwtDto jwtDto) throws ParseException {
		try {
			Jwts.parser().setSigningKey(secret.getBytes()).parseClaimsJws(jwtDto.getToken());
		} catch (ExpiredJwtException e) {
			JWT jwt = JWTParser.parse(jwtDto.getToken());
			JWTClaimsSet claims = jwt.getJWTClaimsSet();
			String nombreUsuario = claims.getSubject();
			@SuppressWarnings("unchecked")
			List<String> roles = (List<String>) claims.getClaim("roles");
			Date fechaInicio = new Date();
			Date fechaExpiracion = new Date(fechaInicio.getTime() + expiration);
			return Jwts.builder().setSubject(nombreUsuario).claim("roles", roles).setIssuedAt(fechaInicio)
					.setExpiration(fechaExpiracion).signWith(SignatureAlgorithm.HS512, secret.getBytes()).compact();
		}
		return null;
	}

}
