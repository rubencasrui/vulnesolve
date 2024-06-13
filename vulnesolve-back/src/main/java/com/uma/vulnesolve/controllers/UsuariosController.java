package com.uma.vulnesolve.controllers;

import com.uma.vulnesolve.models.dto.Usuario;
import com.uma.vulnesolve.services.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UsuariosController {

    private UsuarioService usuarioService;

    public UsuariosController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // CREATE

    @PostMapping("/usuario")
    public ResponseEntity<Usuario> crearUsuario(@RequestBody Usuario usuario) {
        Optional<Usuario> usuarioEntregado = usuarioService.buscarPorNombre(usuario.getUsuario());

        ResponseEntity<Usuario> response;

        if(usuarioEntregado.isPresent()) {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else{
            Usuario usuarioCreado = usuarioService.crearUsuario(usuario);
            response = new ResponseEntity<>(usuarioCreado, HttpStatus.CREATED);
        }

        return response;
    }

    // READ

    @GetMapping("/usuarios")
    public ResponseEntity<List<Usuario>> buscarTodos() {
        List<Usuario> usuarios = usuarioService.buscarTodos();

        ResponseEntity<List<Usuario>> response = new ResponseEntity<>(usuarios, HttpStatus.OK);

        return response;
    }

    @GetMapping("/usuarios/admin")
    public ResponseEntity<List<Usuario>> usuariosAdmin() {
        List<Usuario> usuarios = usuarioService.usuariosAdmin();

        ResponseEntity<List<Usuario>> response = new ResponseEntity<>(usuarios, HttpStatus.OK);

        return response;
    }

    @GetMapping("/usuarios/noadmin")
    public ResponseEntity<List<Usuario>> usuariosNoAdmin() {
        List<Usuario> usuarios = usuarioService.usuariosNoAdmin();

        ResponseEntity<List<Usuario>> response = new ResponseEntity<>(usuarios, HttpStatus.OK);

        return response;
    }

    @GetMapping("/usuario/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        Optional<Usuario> usuario = usuarioService.buscarPorId(id);

        ResponseEntity<Usuario> response;

        if (usuario.isPresent()) {
            response = new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/usuario/nombre/{nombre}")
    public ResponseEntity<Usuario> buscarPorNombre(@PathVariable String nombre) {
        Optional<Usuario> usuario = usuarioService.buscarPorNombre(nombre);

        ResponseEntity<Usuario> response;

        if (usuario.isPresent()) {
            response = new ResponseEntity<>(usuario.get(), HttpStatus.OK);
        }
        else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    // UPDATE

    // DELETE

    // EXTRA

    @GetMapping("/usuario/tokenExpirado/{token}")
    public ResponseEntity<Map<String, Boolean>> tokenExpirado(@PathVariable String token) {
        boolean estaExpirado = usuarioService.tokenExpirado(token);

        ResponseEntity<Map<String, Boolean>> response = new ResponseEntity<>(Map.of("expirado", estaExpirado), HttpStatus.OK);

        return response;
    }

}
