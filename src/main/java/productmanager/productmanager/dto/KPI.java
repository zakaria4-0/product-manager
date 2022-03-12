package productmanager.productmanager.dto;

public class KPI {
    private float efficiency;
    private float PPM;

    public KPI(float efficiency, float PPM) {
        this.efficiency = efficiency;
        this.PPM = PPM;
    }

    public KPI() {
    }

    public float getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(float efficiency) {
        this.efficiency = efficiency;
    }

    public float getPPM() {
        return PPM;
    }

    public void setPPM(float PPM) {
        this.PPM = PPM;
    }
}
