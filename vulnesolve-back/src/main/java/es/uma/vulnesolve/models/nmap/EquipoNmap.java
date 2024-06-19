package es.uma.vulnesolve.models.nmap;

import java.util.ArrayList;
import java.util.List;

public class EquipoNmap {
    private int id;
    private String ip;
    private String mac;
    private int x;
    private int y;
    private int tipo;
    private List<PuertoNmap> puertoNmaps;

    public EquipoNmap() {
        this.id = 0;
        this.ip = "";
        this.mac = "";
        this.x = 0;
        this.y = 0;
        this.tipo = 0;
        this.puertoNmaps = new ArrayList<>();
    }

    public EquipoNmap(int id, String ip, String mac, int x, int y, int tipo, List<PuertoNmap> puertoNmaps) {
        this.id = id;
        this.ip = ip;
        this.mac = mac;
        this.x = x;
        this.y = y;
        this.puertoNmaps = puertoNmaps;
        this.tipo = tipo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getTipo() {
        return tipo;
    }

    public void setTipo(int tipo) {
        this.tipo = tipo;
    }

    public List<PuertoNmap> getPuertos() {
        return puertoNmaps;
    }

    public void setPuertos(List<PuertoNmap> puertoNmaps) {
        this.puertoNmaps = puertoNmaps;
    }

    @Override
    public String toString() {
        return
        "Equipo: {" +
            "id: '" + id + "', "+
            "ip: '" + ip + "', "+
            "mac: '" + mac + "', "+
            "x: '" + x + "', "+
            "y: '" + y + "', "+
            "tipo: '" + tipo + "', "+
            "puertos: " + puertoNmaps +
        '}';
    }
}
