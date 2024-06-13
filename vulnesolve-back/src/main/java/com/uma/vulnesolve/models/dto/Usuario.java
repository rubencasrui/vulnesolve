package com.uma.vulnesolve.models.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 16, unique = true)
    private String usuario;
    @Column(length = 64)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String clave;
    private boolean esAdmin;

    public Usuario() {

    }

    public Usuario(Long id, String usuario, String clave, boolean esAdmin) {
        this.id = id;
        this.usuario = usuario;
        this.clave = clave;
        this.esAdmin = esAdmin;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String pass) {
        this.clave = pass;
    }

    public boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

}
