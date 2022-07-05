package com.depresion.servicio;

import com.depresion.model.Cita;
import com.depresion.security.model.Usuario;

import java.util.List;

public interface CitaService {
    
    public List<Cita> listarCitas();
    
    public Cita citaFindById(Integer idCita);
    
    public Cita guardar(Cita cita);
        
    public boolean eliminar(Integer idCita);
    
    public List<Cita> findCitaByUsuario(Usuario usuario);
}
