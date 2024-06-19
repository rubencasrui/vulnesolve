package es.uma.vulnesolve.controllers;

import es.uma.vulnesolve.models.nmap.EscaneoNmap;
import es.uma.vulnesolve.services.StorageService;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

@RestController
@RequestMapping("/api/media")
public class MediaController {

    private StorageService storageService;

    public MediaController(StorageService storageService) {
        this.storageService = storageService;
    }

    @PostMapping("upload")
    public ResponseEntity<Void> handleFileUpload(@RequestParam("file") MultipartFile file) {

        ResponseEntity<Void> responseEntity = null;
        try {
            storageService.guardar(file);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @GetMapping("/files")
    public ResponseEntity<List<String>> listUploadedFiles() {
        List<String> archivos = new LinkedList<>();
        ResponseEntity<List<String>> responseEntity = null;

        try {
            storageService.cargarTodos().forEach(path -> {
                archivos.add(path.getFileName().toString());
            });
            responseEntity = ResponseEntity.ok(archivos);
        }
        catch (Exception e) {
            archivos.add("Error al listar los archivos.");
            archivos.add(e.getMessage());
            responseEntity = ResponseEntity.badRequest().body(archivos);
        }

        return responseEntity;
    }

    @GetMapping("/files/{filename:.+}")
    @ResponseBody
    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {

        Resource file = storageService.cargarComoRecurso(filename);

        if (file == null)
            return ResponseEntity.notFound().build();

        return ResponseEntity
                .ok()
                .header(
                        HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + file.getFilename() + "\""
                )
                .body(file);
    }

    @DeleteMapping("delete/{filename}")
    public ResponseEntity<Void> deleteFile(@PathVariable String filename) {

        ResponseEntity<Void> responseEntity = null;
        try {
            storageService.borrar(filename);
            responseEntity = new ResponseEntity<>(HttpStatus.OK);
        }
        catch (Exception e) {
            responseEntity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return responseEntity;
    }

    @GetMapping("/escaneo/{filename}")
    public ResponseEntity<EscaneoNmap> escaneo(@PathVariable String filename) {
        EscaneoNmap escaneoNmap = storageService.leerEscaneo(filename);

        return ResponseEntity.ok(escaneoNmap);
    }

    @PostMapping("escanear")
    public ResponseEntity<EscaneoNmap> updatedFile(@RequestParam("file") MultipartFile file) {
        ResponseEntity<EscaneoNmap> responseEntity = null;

        String nombre = null;

        try {
            nombre = storageService.guardar(file);

            EscaneoNmap escaneoNmap = storageService.leerEscaneo(nombre);

            responseEntity = ResponseEntity.ok(escaneoNmap);
        }
        catch (Exception e) {
            responseEntity = ResponseEntity.badRequest().build();
        }

        if(nombre != null){
            storageService.borrar(nombre);
        }

        return responseEntity;
    }
}
