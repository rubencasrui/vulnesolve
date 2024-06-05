package com.uma.vulnesolve.models.vulnerabilidades.vulnesolve;

import java.util.LinkedList;
import java.util.List;

public class JsonVulneSolve {

    private String nombre;
    private double indiceVulneSolve;
    private String severidadVulneSolve;
    private int resultados;
    private List<Vulnerabilidad> vulnerabilidades;

    public JsonVulneSolve() {
        nombre = "";
        resultados = 0;
        vulnerabilidades = new LinkedList<>();
        indiceVulneSolve = 0;
        severidadVulneSolve = "Baja";
    }

    public JsonVulneSolve(String nombre, int resultados, List<Vulnerabilidad> vulnerabilidades, double indiceVulneSolve, String severidadVulneSolve) {
        this.nombre = nombre;
        this.resultados = resultados;
        this.vulnerabilidades = vulnerabilidades;
        this.indiceVulneSolve = indiceVulneSolve;
        this.severidadVulneSolve = severidadVulneSolve;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getResultados() {
        return resultados;
    }

    public void setResultados(int resultados) {
        this.resultados = resultados;
    }

    public List<Vulnerabilidad> getVulnerabilidades() {
        return vulnerabilidades;
    }

    public void setVulnerabilidades(List<Vulnerabilidad> vulnerabilidades) {
        this.vulnerabilidades = vulnerabilidades;
    }

    public double getIndiceVulneSolve() {
        return indiceVulneSolve;
    }

    public void setIndiceVulneSolve(double indiceVulneSolve) {
        this.indiceVulneSolve = indiceVulneSolve;
    }

    public String getSeveridadVulneSolve() {
        return severidadVulneSolve;
    }

    public void setSeveridadVulneSolve(String severidadVulneSolve) {
        this.severidadVulneSolve = severidadVulneSolve;
    }

}
