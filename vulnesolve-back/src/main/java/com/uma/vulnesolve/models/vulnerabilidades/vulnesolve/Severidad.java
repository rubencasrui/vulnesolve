package com.uma.vulnesolve.models.vulnerabilidades.vulnesolve;

public class Severidad {
    private String fuente;
    private String version;
    private double valor;
    private String Severity;

    public Severidad() {
        this.version = "";
        this.valor = 0;
        this.Severity = "";
    }

    public Severidad(String fuente, String version, double valor, String Severity) {
        this.fuente = fuente;
        this.version = version;
        this.valor = valor;
        this.Severity = Severity;
    }

    public String getFuente() {
        return fuente;
    }

    public void setFuente(String fuente) {
        this.fuente = fuente;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getSeverity() {
        return Severity;
    }

    public void setSeverity(String Severity) {
        this.Severity = Severity;
    }

}
