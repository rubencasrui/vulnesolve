package com.uma.vulnesolve.models.vulnerabilidades.nve;

public class Vulnerabilities {
    Cve cve;

    public Vulnerabilities() {
    }

    public Vulnerabilities(Cve cve) {
        this.cve = cve;
    }

    public Cve getCve() {
        return cve;
    }

    public void setCve(Cve cve) {
        this.cve = cve;
    }

}
