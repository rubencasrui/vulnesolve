package com.uma.vulnesolve.models.escaneo;

import java.util.ArrayList;
import java.util.List;

public class Escaneo {

    private List<Equipo> equipos;
    private List<Union> uniones;

    public Escaneo() {
        this.equipos = new ArrayList<>();
        this.uniones = new ArrayList<>();
    }

    public Escaneo(List<Equipo> equipos, List<Union> uniones) {
        this.equipos = equipos;
        this.uniones = uniones;
    }

    public List<Equipo> getEquipos() {
        return equipos;
    }

    public void setEquipos(List<Equipo> equipos) {
        this.equipos = equipos;
    }

    public List<Union> getUniones() {
        return uniones;
    }

    public void setUniones(List<Union> uniones) {
        this.uniones = uniones;
    }

    @Override
    public String toString() {
        return
        "Escaneo: {" +
            "equipos: " + equipos + ", " +
            "uniones: " + uniones +
        '}';
    }
}
