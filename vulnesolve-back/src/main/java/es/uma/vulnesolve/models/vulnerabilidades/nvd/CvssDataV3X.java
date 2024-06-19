package es.uma.vulnesolve.models.vulnerabilidades.nvd;

public class CvssDataV3X {

    private double baseScore;
    private String baseSeverity;

    public CvssDataV3X() {
    }

    public CvssDataV3X(double baseScore, String baseSeverity) {
        this.baseScore = baseScore;
        this.baseSeverity = baseSeverity;
    }

    public double getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(double baseScore) {
        this.baseScore = baseScore;
    }

    public String getBaseSeverity() {
        return baseSeverity;
    }

    public void setBaseSeverity(String baseSeverity) {
        this.baseSeverity = baseSeverity;
    }

}
