package com.uma.vulnesolve.controllers;

import com.uma.vulnesolve.models.Escaneo;
import com.uma.vulnesolve.services.StorageService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Date;

@RestController
@RequestMapping("media")
public class MediaController {

    private StorageService storageService;
    private HttpServletRequest request;

    public MediaController(StorageService storageService, HttpServletRequest request) {
        this.storageService = storageService;
        this.request = request;
    }

    @GetMapping("hola")
    public String hola() {
        return "Hola";
    }


    @PostMapping("upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
        String respuesta = "";

        try {
            storageService.guardar(file);
            respuesta = "Archivo subido";
        }
        catch (Exception e) {
            respuesta = "Error al subir el archivo. " + e.getMessage();
        }

        return respuesta;
    }

    @GetMapping("/files")
    public String listUploadedFiles() {
        StringBuilder respuesta = new StringBuilder();

        try {
            storageService.cargarTodos().forEach(path -> {
                respuesta.append(path.getFileName() + "\n");
            });
        }
        catch (Exception e) {
            respuesta.append("Error al listar los archivos. ");
            respuesta.append(e.getMessage());
        }

        return respuesta.toString();
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
    public String deleteFile(@PathVariable String filename) {
        String respuesta = "";

        try {
            storageService.borrar(filename);
            respuesta = "Archivo eliminado";
        }
        catch (Exception e) {
            respuesta = "Error al eliminar el archivo. " + e.getMessage();
        }

        return respuesta;
    }

    @GetMapping("/escaneo/{filename}")
    public ResponseEntity<Escaneo> escaneo(@PathVariable String filename) {
        Escaneo escaneo = storageService.leerEscaneo(filename);

        return ResponseEntity.ok(escaneo);
    }

    @PostMapping("escanear")
    public ResponseEntity<Escaneo> updatedFile(@RequestParam("file") MultipartFile file) {
        ResponseEntity<Escaneo> responseEntity = null;

        String nombre = null;

        try {
            nombre = storageService.guardar(file);

            Escaneo escaneo = storageService.leerEscaneo(nombre);

            responseEntity = ResponseEntity.ok(escaneo);
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
