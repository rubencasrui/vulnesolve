package com.uma.vulnesolve.services;

import com.uma.vulnesolve.models.dto.Usuario;
import com.uma.vulnesolve.repositories.UsuarioRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class JpaUserDatilsServices implements UserDetailsService {

    private UsuarioRepository userRepository;

    public JpaUserDatilsServices(UsuarioRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = userRepository.buscarPorNombre(username);

        if (usuarioOptional.isEmpty()) {
            throw new UsernameNotFoundException("Usuario '" + username + "' no encontrado");
        }

        Usuario usuario = usuarioOptional.orElseThrow();

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (usuario.getEsAdmin()) {
            authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        } else {
            authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        }

        return new User(
            usuario.getUsuario(),
            usuario.getClave(),
            true,
            true,
            true,
            true,
            authorities
        );
    }
}
