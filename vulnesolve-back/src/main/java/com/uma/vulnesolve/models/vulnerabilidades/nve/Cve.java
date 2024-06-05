package com.uma.vulnesolve.models.vulnerabilidades.nve;

import java.util.Date;
import java.util.List;

public class Cve {

    private String id;
    private List<Description> descriptions;
    private Date published;
    private Metrics metrics;

    public Cve() {
    }

    public Cve(String id, List<Description> descriptions, Date published, Metrics metrics) {
        this.id = id;
        this.descriptions = descriptions;
        this.published = published;
        this.metrics = metrics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<Description> getDescriptions() {
        return descriptions;
    }

    public void setDescriptions(List<Description> descriptions) {
        this.descriptions = descriptions;
    }

    public Date getPublished() {
        return published;
    }

    public void setPublished(Date published) {
        this.published = published;
    }

    public Metrics getMetrics() {
        return metrics;
    }

    public void setMetrics(Metrics metrics) {
        this.metrics = metrics;
    }

}
