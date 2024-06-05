package com.uma.vulnesolve.models.vulnerabilidades.nve;

import java.util.List;

public class JsonNve {

    private int totalResults;
    private List<Vulnerabilities> vulnerabilities;

    public JsonNve() {
    }

    public JsonNve(int totalResults, List<Vulnerabilities> vulnerabilities) {
        this.totalResults = totalResults;
        this.vulnerabilities = vulnerabilities;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public List<Vulnerabilities> getVulnerabilities() {
        return vulnerabilities;
    }

    public void setVulnerabilities(List<Vulnerabilities> vulnerabilities) {
        this.vulnerabilities = vulnerabilities;
    }

}
