package es.uma.vulnesolve.models.vulnerabilidades.nvd;

public class CvssMetricV3X {

    private String source;
    private CvssDataV3X cvssData;

    public CvssMetricV3X() {
    }

    public CvssMetricV3X(String source, CvssDataV3X cvssData) {
        this.source = source;
        this.cvssData = cvssData;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public CvssDataV3X getCvssData() {
        return cvssData;
    }

    public void setCvssData(CvssDataV3X cvssData) {
        this.cvssData = cvssData;
    }

}
