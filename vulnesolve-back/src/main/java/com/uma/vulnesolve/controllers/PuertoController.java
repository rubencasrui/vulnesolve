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
        Optional<Puerto> puertoDado = puertoService.buscarPorNumero(puerto.getNumero());

        ResponseEntity<Puerto> respuesta;

        if(puertoDado.isPresent()) {
            respuesta = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else {
            try {
                Puerto puertoCreado = puertoService.crearPuerto(puerto);
                respuesta = new ResponseEntity<>(puertoCreado, HttpStatus.CREATED);
            }
            catch(Exception e) {
                System.out.println("Error al crear el puerto: " + e.getMessage());
                respuesta = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

        }

        return respuesta;
    }

    // READ

    @GetMapping("/puertos")
    public ResponseEntity<List<Puerto>> buscarPuertos() {
        List<Puerto> puertos = puertoService.buscarPuertos();

        ResponseEntity<List<Puerto>> respuesta = new ResponseEntity<>(puertos, HttpStatus.OK);

        return respuesta;
    }

    @GetMapping("/puerto/{numero}")
    public ResponseEntity<Puerto> buscarPorNumero(@PathVariable int numero) {
        Optional<Puerto> puertos = puertoService.buscarPorNumero(numero);

        ResponseEntity<Puerto> respuesta;

        if(puertos.isPresent()) {
            respuesta = new ResponseEntity<>(puertos.get(), HttpStatus.OK);
        }
        else {
            respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return respuesta;
    }

    @GetMapping("/puertos/{servicio}")
    public ResponseEntity<List<Puerto>> buscarPorServicio(@PathVariable String servicio) {
        List<Puerto> puertos = puertoService.buscarPorServicio(servicio);

        ResponseEntity<List<Puerto>> respuesta = new ResponseEntity<>(puertos, HttpStatus.OK);

        return respuesta;
    }

    // UPDATE

    @PutMapping("/puerto/{numero}")
    public ResponseEntity<Puerto> actualizarPuerto(@PathVariable int numero, @RequestBody Puerto puerto) {
        puerto.setNumero(numero);
        Optional<Puerto> puertoDado = puertoService.buscarPorNumero(puerto.getNumero());

        ResponseEntity<Puerto> respuesta;

        if(puertoDado.isPresent()) {
            try {
                Puerto puertoActualizado = puertoService.actualizarPuerto(puerto);

                respuesta = new ResponseEntity<>(puertoActualizado, HttpStatus.CREATED);
            }
            catch(Exception e) {
                System.out.println("Error al actualizar el puerto: " + e.getMessage());
                respuesta = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        }
        else {
            respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return respuesta;
    }

    // DELETE

    @DeleteMapping("/puerto/{numero}")
    public ResponseEntity<Void> eliminarPuerto(@PathVariable int numero) {
        Optional<Puerto> puertoDado = puertoService.buscarPorNumero(numero);

        ResponseEntity<Void> respuesta;

        if(puertoDado.isPresent()) {
            puertoService.eliminarPuerto(numero);

            respuesta = new ResponseEntity<>(HttpStatus.OK);
        }
        else {
            respuesta = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return respuesta;
    }

}
