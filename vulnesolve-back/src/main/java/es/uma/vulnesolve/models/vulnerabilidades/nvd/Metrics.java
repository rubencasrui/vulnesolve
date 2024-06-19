package es.uma.vulnesolve.models.vulnerabilidades.nvd;

import java.util.List;

public class Metrics {

    private List<CvssMetricV20> cvssMetricV2;
    private List<CvssMetricV3X> cvssMetricV30;
    private List<CvssMetricV3X> cvssMetricV31;

    public Metrics() {
    }

    public List<CvssMetricV20> getCvssMetricV2() {
        return cvssMetricV2;
    }

    public void setCvssMetricV2(List<CvssMetricV20> cvssMetricV2) {
        this.cvssMetricV2 = cvssMetricV2;
    }

    public List<CvssMetricV3X> getCvssMetricV30() {
        return cvssMetricV30;
    }

    public void setCvssMetricV30(List<CvssMetricV3X> cvssMetricV30) {
        this.cvssMetricV30 = cvssMetricV30;
    }

    public List<CvssMetricV3X> getCvssMetricV31() {
        return cvssMetricV31;
    }

    public void setCvssMetricV31(List<CvssMetricV3X> cvssMetricV31) {
        this.cvssMetricV31 = cvssMetricV31;
    }

}
