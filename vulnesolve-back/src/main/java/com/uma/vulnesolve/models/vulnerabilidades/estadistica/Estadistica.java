package com.uma.vulnesolve.models.vulnerabilidades.estadistica;

public class Estadistica {
    private String indice;
    private int cantidad;

    public Estadistica(String indice, int cantidad) {
        this.indice = indice;
        this.cantidad = cantidad;
    }

    public String getIndice() {
        return indice;
    }

    public void setIndice(String indice) {
        this.indice = indice;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
}
