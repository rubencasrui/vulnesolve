package com.uma.vulnesolve.services;

import java.io.IOException;
import java.nio.file.Path;
import java.util.stream.Stream;

import com.uma.vulnesolve.models.Escaneo;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {

    void iniciar() throws IOException;

    String guardar(MultipartFile file);


    Stream<Path> cargarTodos();

    Path cargar(String filename);

    Resource cargarComoRecurso(String filename);

    Escaneo leerEscaneo(String filename);

    void borrar(String filename);

}
