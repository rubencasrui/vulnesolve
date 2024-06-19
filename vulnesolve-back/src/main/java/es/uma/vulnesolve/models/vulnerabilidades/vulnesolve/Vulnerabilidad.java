package es.uma.vulnesolve.models.vulnerabilidades.vulnesolve;

import java.util.ArrayList;
import java.util.List;

public class Vulnerabilidad {
    private String id;
    private String descripcion;
    private List<Severidad> severidades;

    public Vulnerabilidad() {
        this.id = "";
        this.descripcion = "";
        this.severidades = new ArrayList<>();
    }

    public Vulnerabilidad(String id, String descripcion, List<Severidad> severidades) {
        this.id = id;
        this.descripcion = descripcion;
        this.severidades = severidades;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public List<Severidad> getSeveridades() {
        return severidades;
    }

    public void setSeveridades(List<Severidad> severidades) {
        this.severidades = severidades;
    }

}
