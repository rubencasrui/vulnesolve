package com.uma.vulnesolve.controllers;

import com.uma.vulnesolve.models.dto.Puerto;
import com.uma.vulnesolve.services.PuertoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class PuertoController {

    private PuertoService puertoService;

    public PuertoController(PuertoService puertoService) {
        this.puertoService = puertoService;
    }

    // CREATE

    @PostMapping("/puerto")
    public ResponseEntity<Puerto> crearPuerto(@RequestBody Puerto puerto) {
        Puerto puertoCreado = puertoService.crearPuerto(puerto);

        ResponseEntity<Puerto> respuesta = new ResponseEntity<>(puertoCreado, HttpStatus.CREATED);

        return respuesta;
    }

    // READ

    @GetMapping("/puertos")
    public ResponseEntity<List<Puerto>> buscarPuertos() {
        List<Puerto> puertos = puertoService.buscarPuertos();

        ResponseEntity<List<Puerto>> respuesta = new ResponseEntity<>(puertos, HttpStatus.OK);

        return respuesta;
    }

    @GetMapping("/puerto/{id}")
    public ResponseEntity<Puerto> buscarPorId(@PathVariable Long id) {
        Optional<Puerto> puerto = puertoService.buscarPorId(id);

        ResponseEntity<Puerto> respuesta;

        if(puerto.isPresent()) {
            respuesta = new ResponseEntity<>(puerto.get(), HttpStatus.OK);
        }
        else {
            respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return respuesta;
    }

    @GetMapping("/puertos/numero/{numero}")
    public ResponseEntity<List<Puerto>> buscarPorNumero(@PathVariable int numero) {
        List<Puerto> puertos = puertoService.buscarPorNumero(numero);

        ResponseEntity<List<Puerto>> respuesta = new ResponseEntity<>(puertos, HttpStatus.OK);

        return respuesta;
    }

    @GetMapping("/puertos/nombre/{nombre}")
    public ResponseEntity<List<Puerto>> buscarPorNombre(@PathVariable String nombre) {
        List<Puerto> puertos = puertoService.buscarPorNombre(nombre);

        ResponseEntity<List<Puerto>> respuesta = new ResponseEntity<>(puertos, HttpStatus.OK);

        return respuesta;
    }

    // UPDATE

    @PutMapping("/puerto/{id}")
    public ResponseEntity<Puerto> actualizarPuerto(@PathVariable Long id, @RequestBody Puerto puerto) {
        Optional<Puerto> puertoActual = puertoService.buscarPorId(id);

        ResponseEntity<Puerto> respuesta;

        if(puertoActual.isPresent()) {
            puerto.setId(id);
            Puerto puertoActualizado = puertoService.actualizarPuerto(puerto);

            respuesta = new ResponseEntity<>(puertoActualizado, HttpStatus.OK);
        }
        else {
            respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return respuesta;
    }

    // DELETE

    @DeleteMapping("/puerto/{id}")
    public ResponseEntity<Void> eliminarPuerto(@PathVariable Long id) {
        Optional<Puerto> puerto = puertoService.buscarPorId(id);

        ResponseEntity<Void> respuesta;

        if(puerto.isPresent()) {
            puertoService.eliminarPuerto(id);

            respuesta = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return respuesta;
    }

}
