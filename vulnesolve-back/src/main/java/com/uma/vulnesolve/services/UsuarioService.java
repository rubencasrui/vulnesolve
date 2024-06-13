package com.uma.vulnesolve.services;

import com.uma.vulnesolve.models.dto.Usuario;
import com.uma.vulnesolve.repositories.UsuarioRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.uma.vulnesolve.security.TokenJwtConfig.SECRET_KEY;

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

    // DELETE

    // EXTRA

    public boolean tokenExpirado(String token) {
        boolean estaExpirado;

        try {
            Claims claims = Jwts.parser().verifyWith(SECRET_KEY).build().parseSignedClaims(token).getPayload();
            estaExpirado = claims.getExpiration().before(new Date());
        }
        catch (JwtException e) {
            estaExpirado = true;
        }

        return estaExpirado;
    }
}
