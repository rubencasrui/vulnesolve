package es.uma.vulnesolve.repositories;

import es.uma.vulnesolve.models.dto.Puerto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PuertoRepository extends JpaRepository<Puerto, Integer> {

    @Query("SELECT p FROM Puerto p WHERE p.servicio LIKE %?1%")
    List<Puerto> findByServicio(String servicio);
}
