package com.uma.vulnesolve.services;

import com.uma.vulnesolve.models.dto.Puerto;
import com.uma.vulnesolve.repositories.PuertoRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PuertoService {

    private PuertoRepository puertoRepository;

    public PuertoService(PuertoRepository puertoRepository) {
        this.puertoRepository = puertoRepository;
    }

    // CREATE

    public Puerto crearPuerto(Puerto puerto) {
        return puertoRepository.save(puerto);
    }

    // READ

    public List<Puerto> buscarPuertos() {
        return puertoRepository.findAll();
    }

    public Optional<Puerto> buscarPorId(Long id) {
        return puertoRepository.findById(id);
    }

    public List<Puerto> buscarPorNumero(int numero) {
        return puertoRepository.buscarPorNumero(numero);
    }

    public List<Puerto> buscarPorNombre(String nombre) {
        return puertoRepository.buscarPorNombre(nombre);
    }

    // UPDATE

    public Puerto actualizarPuerto(Puerto puerto) {
        return puertoRepository.save(puerto);
    }

    // DELETE

    public void eliminarPuerto(Long id) {
        puertoRepository.deleteById(id);
    }

}
