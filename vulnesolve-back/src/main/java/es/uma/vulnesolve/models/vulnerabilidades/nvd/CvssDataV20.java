package es.uma.vulnesolve.models.vulnerabilidades.nvd;

public class CvssDataV20 {

    private double baseScore;

    public CvssDataV20() {
    }

    public CvssDataV20(double baseScore) {
        this.baseScore = baseScore;
    }

    public double getBaseScore() {
        return baseScore;
    }

    public void setBaseScore(double baseScore) {
        this.baseScore = baseScore;
    }

}
