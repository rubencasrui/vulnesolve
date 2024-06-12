package com.uma.vulnesolve.models.dto;

import jakarta.persistence.*;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 16, unique = true)
    private String usuario;
    @Column(length = 64)
    private String pass;
    private boolean esAdmin;

    public Usuario() {

    }

    public Usuario(Long id, String usuario, String pass, boolean esAdmin) {
        this.id = id;
        this.usuario = usuario;
        this.pass = pass;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public boolean getEsAdmin() {
        return esAdmin;
    }

    public void setEsAdmin(boolean esAdmin) {
        this.esAdmin = esAdmin;
    }

}
