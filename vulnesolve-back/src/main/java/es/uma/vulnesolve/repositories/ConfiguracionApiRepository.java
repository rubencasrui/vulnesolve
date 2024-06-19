package es.uma.vulnesolve.repositories;

import es.uma.vulnesolve.models.dto.ConfiguracionApi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface ConfiguracionApiRepository extends JpaRepository<ConfiguracionApi, Long> {

    @Query("SELECT c FROM ConfiguracionApi c WHERE c.nombre LIKE ?1")
    Optional<ConfiguracionApi> buscarPorNombre(String nombre);

}
