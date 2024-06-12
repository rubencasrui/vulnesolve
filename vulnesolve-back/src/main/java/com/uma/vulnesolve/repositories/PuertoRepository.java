package com.uma.vulnesolve.repositories;

import com.uma.vulnesolve.models.dto.Puerto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PuertoRepository extends JpaRepository<Puerto, Long> {

    @Query("SELECT p FROM Puerto p WHERE p.numero = ?1")
    List<Puerto> buscarPorNumero(int numero);

    @Query("SELECT p FROM Puerto p WHERE p.nombre LIKE %?1%")
    List<Puerto> buscarPorNombre(String nombre);
}
