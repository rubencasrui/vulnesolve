package es.uma.vulnesolve.controllers;

import es.uma.vulnesolve.models.dto.ConfiguracionApi;
import es.uma.vulnesolve.services.ConfiguracionApiService;
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
        Optional<ConfiguracionApi> confDada = configuracionApiService.buscarPorNombre(configuracionApi.getNombre());

        ResponseEntity<ConfiguracionApi> response;

        if (confDada.isPresent()) {
            response = new ResponseEntity<>(HttpStatus.CONFLICT);
        }
        else {
            try {
                ConfiguracionApi confCreada = configuracionApiService.crearConfiguracionApi(configuracionApi);

                response = new ResponseEntity<>(confCreada, HttpStatus.CREATED);
            }
            catch (Exception e) {
                System.out.println("Error al crear la configuración: " + e.getMessage());
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
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

    @PutMapping("/configuracionApi/{nombre}")
    public ResponseEntity<ConfiguracionApi> actualizarConfiguracionApi(@PathVariable String nombre, @RequestBody ConfiguracionApi configuracionApi) {
        configuracionApi.setNombre(nombre);
        Optional<ConfiguracionApi> configuracionActual = configuracionApiService.buscarPorNombre(configuracionApi.getNombre());

        ResponseEntity<ConfiguracionApi> response;

        if (configuracionActual.isPresent()) {
            try {
                configuracionApi.setId(configuracionActual.get().getId());
                ConfiguracionApi configuracionActualizada = configuracionApiService.actualizarConfiguracionApi(configuracionApi);

                response = new ResponseEntity<>(configuracionActualizada, HttpStatus.OK);
            }
            catch (Exception e) {
                System.out.println("Error al actualizar la configuración: " + e.getMessage());
                response = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

    // DELETE

    @DeleteMapping("/configuracionApi/{nombre}")
    public ResponseEntity<ConfiguracionApi> eliminarConfiguracionApi(@PathVariable String nombre) {
        Optional<ConfiguracionApi> conf = configuracionApiService.buscarPorNombre(nombre);

        ResponseEntity<ConfiguracionApi> response;

        if (conf.isPresent()) {
            configuracionApiService.eliminarConfiguracionApi(conf.get().getId());

            response = new ResponseEntity<>(HttpStatus.OK);
        } else {
            response = new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return response;
    }

}
