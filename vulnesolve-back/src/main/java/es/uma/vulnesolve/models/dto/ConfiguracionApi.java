package es.uma.vulnesolve.models.dto;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;


@Entity
public class ConfiguracionApi {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 16, unique = true)
    private String nombre;
    private int cantidadResultados;
    private int modoBusqueda;
    private boolean soloCriticos;
    private int incrementoIndice;

    public ConfiguracionApi() {

    }

    public ConfiguracionApi(Long id, String nombre, int cantidadResultados, int modoBusqueda, boolean soloCriticos, int incrementoIndice) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadResultados = cantidadResultados;
        this.modoBusqueda = modoBusqueda;
        this.soloCriticos = soloCriticos;
        this.incrementoIndice = incrementoIndice;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getCantidadResultados() {
        return cantidadResultados;
    }

    public void setCantidadResultados(int cantidadResultados) {
        this.cantidadResultados = cantidadResultados;
    }

    public int getModoBusqueda() {
        return modoBusqueda;
    }

    public void setModoBusqueda(int modoBusqueda) {
        this.modoBusqueda = modoBusqueda;
    }

    public boolean isSoloCriticos() {
        return soloCriticos;
    }

    public void setSoloCriticos(boolean soloCriticos) {
        this.soloCriticos = soloCriticos;
    }

    public int getIncrementoIndice() {
        return incrementoIndice;
    }

    public void setIncrementoIndice(int incrementoIndice) {
        this.incrementoIndice = incrementoIndice;
    }

}
