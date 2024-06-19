package es.uma.vulnesolve.models.nmap;

import java.util.ArrayList;
import java.util.List;

public class EscaneoNmap {

    private List<EquipoNmap> equipoNmaps;
    private List<UnionNmap> uniones;

    public EscaneoNmap() {
        this.equipoNmaps = new ArrayList<>();
        this.uniones = new ArrayList<>();
    }

    public EscaneoNmap(List<EquipoNmap> equipoNmaps, List<UnionNmap> uniones) {
        this.equipoNmaps = equipoNmaps;
        this.uniones = uniones;
    }

    public List<EquipoNmap> getEquipos() {
        return equipoNmaps;
    }

    public void setEquipos(List<EquipoNmap> equipoNmaps) {
        this.equipoNmaps = equipoNmaps;
    }

    public List<UnionNmap> getUniones() {
        return uniones;
    }

    public void setUniones(List<UnionNmap> uniones) {
        this.uniones = uniones;
    }

    @Override
    public String toString() {
        return
        "Escaneo: {" +
            "equipos: " + equipoNmaps + ", " +
            "uniones: " + uniones +
        '}';
    }
}
