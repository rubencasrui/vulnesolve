package com.uma.vulnesolve.models;

import java.util.ArrayList;
import java.util.List;

public class Equipo {
    private int id;
    private String ip;
    private String mac;
    private int x;
    private int y;
    private int tipo;
    private List<Puerto> puertos;

    public Equipo() {
        this.id = 0;
        this.ip = "";
        this.mac = "";
        this.x = 0;
        this.y = 0;
        this.tipo = 0;
        this.puertos = new ArrayList<>();
    }

    public Equipo(int id, String ip, String mac, int x, int y, int tipo, List<Puerto> puertos) {
        this.id = id;
        this.ip = ip;
        this.mac = mac;
        this.x = x;
        this.y = y;
        this.puertos = puertos;
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

    public List<Puerto> getPuertos() {
        return puertos;
    }

    public void setPuertos(List<Puerto> puertos) {
        this.puertos = puertos;
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
            "puertos: " + puertos +
        '}';
    }
}
