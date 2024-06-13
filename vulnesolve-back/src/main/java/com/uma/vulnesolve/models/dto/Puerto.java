package com.uma.vulnesolve.models.dto;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Puerto {

    @Id
    private Integer numero;

    @Column(length = 16, nullable = false)
    private String servicio;

    @Column(length = 1024)
    private String descripcion;

    public Puerto() {

    }

    public Puerto(int numero, String servicio, String descripcion) {
        this.numero = numero;
        this.servicio = servicio;
        this.descripcion = descripcion;
    }

    public int getNumero() {
        return numero;
    }

    public void setNumero(int numero) {
        this.numero = numero;
    }

    public String getServicio() {
        return servicio;
    }

    public void setServicio(String servicio) {
        this.servicio = servicio;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

}
