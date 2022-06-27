package productmanager.productmanager.dto;

public class KPI {
    private int efficiency;
    private int PPM;

    public KPI(int efficiency, int PPM) {
        this.efficiency = efficiency;
        this.PPM = PPM;
    }

    public KPI() {
    }

    public int getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(int efficiency) {
        this.efficiency = efficiency;
    }

    public int getPPM() {
        return PPM;
    }

    public void setPPM(int PPM) {
        this.PPM = PPM;
    }
}
