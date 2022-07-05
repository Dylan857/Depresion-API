package com.depresion.servicio;

import com.depresion.dao.CitaDao;
import com.depresion.model.Cita;
import com.depresion.security.model.Usuario;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CitaServiceImpl implements CitaService {

    @Autowired
    private CitaDao citaDao;
    
    @Override
    @Transactional(readOnly = true)
    public List<Cita> listarCitas() {
        return citaDao.findAll();
    }

    @Override
    @Transactional
    public Cita guardar(Cita cita) {
        return citaDao.save(cita);
    }

    @Override
    @Transactional
    public boolean eliminar(Integer idCita) {
    	try {
    		citaDao.deleteById(idCita);
    		return true;
    	} catch (Exception e) {
    		return false;
    	}
    }

    @Override
    @Transactional(readOnly = true)
    public Cita citaFindById(Integer idCita) {
        return citaDao.findById(idCita).orElse(null);
    }

	@Override
	public List<Cita> findCitaByUsuario(Usuario usuario) {
		return citaDao.findByUsuario(usuario);
	}

    
}
