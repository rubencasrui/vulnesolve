package es.uma.vulnesolve.repositories;

import es.uma.vulnesolve.models.dto.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    @Query("SELECT u FROM Usuario u WHERE u.usuario LIKE %?1%")
    public Optional<Usuario> buscarPorNombre(String usuario);

    @Query("SELECT u FROM Usuario u WHERE u.esAdmin = true")
    public List<Usuario> usuariosAdmin();

    @Query("SELECT u FROM Usuario u WHERE u.esAdmin = false")
    public List<Usuario> usuariosNoAdmin();

}
