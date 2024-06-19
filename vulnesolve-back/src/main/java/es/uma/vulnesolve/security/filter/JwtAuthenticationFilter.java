package es.uma.vulnesolve.security.filter;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import es.uma.vulnesolve.models.dto.Usuario;
import es.uma.vulnesolve.security.TokenJwtConfig;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager autenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager autenticationManager) {
        this.autenticationManager = autenticationManager;
    }

    // Obtener valores usuario y clave del cuerpo de la petición y probar a autenticar
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        Usuario user = null;
        String usuario = null;
        String clave = null;

        try {
            user = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);

            usuario = user.getUsuario();
            clave = user.getClave();
        } catch (StreamReadException e) {
            throw new RuntimeException(e);
        } catch (DatabindException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(usuario, clave);

        return autenticationManager.authenticate(authenticationToken);
    }

    // Generar token JWT si la autenticación es exitosa, sino devolver mensaje de error
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {

        User user = (User) authResult.getPrincipal();
        String usuario = user.getUsername();
        Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

        Claims claims = Jwts.claims()
            .add("authorities", new ObjectMapper().writeValueAsString(roles))
            .add("usuario", usuario)
            .build();

        String token = Jwts.builder()
           .subject(usuario)
           .claims(claims)
           .expiration(new Date(System.currentTimeMillis() + TokenJwtConfig.TIEMPO_EXPIRACION))
           .issuedAt(new Date())
           .signWith(TokenJwtConfig.SECRET_KEY)
           .compact();

        response.addHeader(TokenJwtConfig.HEADER_AUTHORIZATION, TokenJwtConfig.PREFIX_TOKEN + token);

        Map<String, String> body = new HashMap<>();
        body.put("usuario", usuario);
        body.put("rol", roles.toString());
        body.put("token", token);
        body.put("mensaje", "Autenticación exitosa");

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        response.setStatus(200);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {

        Map<String, String> body = new HashMap<>();
        body.put("mensaje", "Error de autenticación: usuario o clave incorrectos");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        response.setContentType(TokenJwtConfig.CONTENT_TYPE);
        response.setStatus(401);

    }
}
