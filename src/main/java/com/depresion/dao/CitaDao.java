package com.depresion.dao;

import com.depresion.model.Cita;
import com.depresion.security.model.Usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CitaDao extends JpaRepository<Cita, Integer> {
    
    public List<Cita> findByUsuario(Usuario usuario);
    
}
