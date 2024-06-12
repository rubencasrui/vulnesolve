package com.uma.vulnesolve.models.nmap;

import com.uma.vulnesolve.models.vulnerabilidades.vulnesolve.JsonVulneSolve;

public class PuertoNmap {

    private int numero;
    private String estado;
    private String nombre;
    private String descripcion;
    private JsonVulneSolve vulnerabilidades;

    public PuertoNmap() {
        this.numero = 0;
        this.estado = "";
        this.nombre = "";
        this.descripcion = "";
        this.vulnerabilidades = null;
    }

    public PuertoNmap(int numero, String estado, String nombre, String descripcion, JsonVulneSolve vulnerabilidades) {
        this.numero = numero;
        this.estado = estado;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.vulnerabilidades = vulnerabilidades;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public JsonVulneSolve getVulnerabilidades() {
        return vulnerabilidades;
    }

    public void setVulnerabilidades(JsonVulneSolve vulnerabilidades) {
        this.vulnerabilidades = vulnerabilidades;
    }

}
