package com.uma.vulnesolve.models;

public class Puerto {

    private int numero;
    private String estado;
    private String nombre;
    private String descripcion;

    public Puerto() {
        this.numero = 0;
        this.estado = "";
        this.nombre = "";
        this.descripcion = "";
    }

    public Puerto(int numero, String estado, String nombre, String descripcion) {
        this.numero = numero;
        this.estado = estado;
        this.nombre = nombre;
        this.descripcion = descripcion;
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

    @Override
    public String toString() {
        return
        "Puerto: {" +
            "numero: '" + numero + "', "+
            "estado: '" + estado + "', "+
            "nombre: '" + nombre + "'," +
            "descripcion: '" + descripcion + "'"+
        '}';
    }
}
