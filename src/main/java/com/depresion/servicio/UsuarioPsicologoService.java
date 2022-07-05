package com.depresion.servicio;

import java.util.List;

import com.depresion.model.UsuarioPsicologo;

public interface UsuarioPsicologoService {
    
    public UsuarioPsicologo findByNumeroDocumento(String numeroDocumento);
    
    public List<UsuarioPsicologo> listarUsuarios();
    
}
