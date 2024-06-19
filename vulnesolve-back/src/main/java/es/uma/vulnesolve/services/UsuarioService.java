package es.uma.vulnesolve.services;

import es.uma.vulnesolve.models.dto.Usuario;
import es.uma.vulnesolve.repositories.UsuarioRepository;
import es.uma.vulnesolve.security.TokenJwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    private UsuarioRepository usuarioRepository;

    private PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // CREATE

    public Usuario crearUsuario(Usuario usuario) {
        usuario.setClave(passwordEncoder.encode(usuario.getClave()));
        return usuarioRepository.save(usuario);
    }

    // READ

    public List<Usuario> buscarTodos(){
        return usuarioRepository.findAll();
    }

    public List<Usuario> usuariosAdmin(){
        return usuarioRepository.usuariosAdmin();
    }

    public List<Usuario> usuariosNoAdmin(){
        return usuarioRepository.usuariosNoAdmin();
    }

    public Optional<Usuario> buscarPorId(Long id){
        return usuarioRepository.findById(id);
    }

    public Optional<Usuario> buscarPorNombre(String nombre){
        return usuarioRepository.buscarPorNombre(nombre);
    }

    // UPDATE

    public Usuario actualizarUsuario(Usuario usuario){
        return usuarioRepository.save(usuario);
    }

    public Usuario hacerAdmin(Usuario usuario){
        usuario.setEsAdmin(true);
        return usuarioRepository.save(usuario);
    }

    // DELETE

    // EXTRA

    public boolean tokenExpirado(String token) {
        boolean estaExpirado;

        try {
            Claims claims = Jwts.parser().verifyWith(TokenJwtConfig.SECRET_KEY).build().parseSignedClaims(token).getPayload();

            // Si la fecha de expiración es anterior a la fecha actual, el token está expirado
            // Sino no está expirado
            estaExpirado = claims.getExpiration().before(new Date());
        }
        catch (JwtException e) {
            // Si hay un error en la validación del token, se considera expirado
            estaExpirado = true;
        }

        return estaExpirado;
    }

    public boolean esUsuarioAdmin (String token) {
        boolean esAdmin = false;

        try {
            Claims claims = Jwts.parser().verifyWith(TokenJwtConfig.SECRET_KEY).build().parseSignedClaims(token).getPayload();
            String usuario = claims.getSubject();

            Optional<Usuario> usuarioOptional = usuarioRepository.buscarPorNombre(usuario);

            if (usuarioOptional.isPresent()) {
                Usuario usuarioEncontrado = usuarioOptional.get();
                esAdmin = usuarioEncontrado.getEsAdmin();
            }
        }
        catch (JwtException e) {
            esAdmin = false;
        }

        return esAdmin;
    }
}
