package es.uma.vulnesolve.models.vulnerabilidades.nvd;

import java.util.ArrayList;
import java.util.List;

public class JsonNvd {

    private int totalResults;
    private List<Vulnerabilities> vulnerabilities;

    public JsonNvd() {
        totalResults = 0;
        vulnerabilities = new ArrayList<>();
    }

    public JsonNvd(int totalResults, List<Vulnerabilities> vulnerabilities) {
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
