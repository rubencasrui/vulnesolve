package com.uma.vulnesolve.models.vulnerabilidades.nve;

public class CvssMetricV20 {
    String source;
    CvssDataV20 cvssData;
    String baseSeverity;

    public CvssMetricV20() {
    }

    public CvssMetricV20(String source, CvssDataV20 cvssData, String baseSeverity) {
        this.source = source;
        this.cvssData = cvssData;
        this.baseSeverity = baseSeverity;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public CvssDataV20 getCvssData() {
        return cvssData;
    }

    public void setCvssData(CvssDataV20 cvssData) {
        this.cvssData = cvssData;
    }

    public String getBaseSeverity() {
        return baseSeverity;
    }

    public void setBaseSeverity(String baseSeverity) {
        this.baseSeverity = baseSeverity;
    }
}
