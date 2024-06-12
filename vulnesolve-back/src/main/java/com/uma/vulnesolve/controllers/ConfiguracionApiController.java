package com.uma.vulnesolve.controllers;

import com.uma.vulnesolve.models.dto.ConfiguracionApi;
import com.uma.vulnesolve.services.ConfiguracionApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ConfiguracionApiController {

    private ConfiguracionApiService configuracionApiService;

    public ConfiguracionApiController(ConfiguracionApiService configuracionApiService) {
        this.configuracionApiService = configuracionApiService;
    }

    // CREATE

    @PostMapping("/configuracionApi")
    public ResponseEntity<ConfiguracionApi> crearConfiguracionApi(@RequestBody ConfiguracionApi configuracionApi) {
        Optional<ConfiguracionApi> conf = configuracionApiService.buscarPorNombre(configuracionApi.getNombre());

        ResponseEntity<ConfiguracionApi> response;

        if (conf.isPresent()) {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else {
            ConfiguracionApi confCreada = configuracionApiService.crearConfiguracionApi(configuracionApi);

            response = new ResponseEntity<>(confCreada, HttpStatus.CREATED);
        }

        return response;
    }

    // READ

    @GetMapping("/configuracionApi")
    public ResponseEntity<List<ConfiguracionApi>> buscarTodos() {
        List<ConfiguracionApi> confs = configuracionApiService.buscarTodos();

        ResponseEntity<List<ConfiguracionApi>> response = new ResponseEntity<>(confs, HttpStatus.OK);

        return response;
    }

    @GetMapping("/configuracionApi/{id}")
    public ResponseEntity<ConfiguracionApi> buscarPorId(@PathVariable Long id) {
        Optional<ConfiguracionApi> conf = configuracionApiService.buscarPorId(id);

        ResponseEntity<ConfiguracionApi> response;

        if (conf.isPresent()) {
            response = new ResponseEntity<>(conf.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    @GetMapping("/configuracionApi/nombre/{nombre}")
    public ResponseEntity<ConfiguracionApi> buscarPorNombre(@PathVariable String nombre) {
        Optional<ConfiguracionApi> confs = configuracionApiService.buscarPorNombre(nombre);

        ResponseEntity<ConfiguracionApi> response;

        if (confs.isPresent()) {
            response = new ResponseEntity<>(confs.get(), HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    // UPDATE

    @PutMapping("/configuracionApi/{id}")
    public ResponseEntity<ConfiguracionApi> actualizarConfiguracionApi(@PathVariable Long id, @RequestBody ConfiguracionApi configuracionApi) {
        Optional<ConfiguracionApi> configuracionActual = configuracionApiService.buscarPorId(id);

        ResponseEntity<ConfiguracionApi> response;

        if (configuracionActual.isPresent()) {
            configuracionApi.setId(id);
            ConfiguracionApi configuracionActualizada = configuracionApiService.actualizarConfiguracionApi(configuracionApi);

            response = new ResponseEntity<>(configuracionActualizada, HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    // DELETE

    @DeleteMapping("/configuracionApi/{id}")
    public ResponseEntity<ConfiguracionApi> eliminarConfiguracionApi(@PathVariable Long id) {
        Optional<ConfiguracionApi> conf = configuracionApiService.buscarPorId(id);

        ResponseEntity<ConfiguracionApi> response;

        if (conf.isPresent()) {
            configuracionApiService.eliminarConfiguracionApi(id);

            response = new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

}
