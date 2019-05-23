package Vehicles;

public class VehicleMotorBean {
    private String Cc, Cylinders, Mps;

    public VehicleMotorBean() {}
    public VehicleMotorBean(String cc, String cylinders, String mps) {
        Cc = cc; Cylinders = cylinders; Mps = mps;
    }

    public void setCc(String cc) {Cc = cc;}
    public void setCylinders(String cylinders) {Cylinders = cylinders;}
    public void setMps(String mps) {Mps = mps;}

    public String getCc() {return Cc;}
    public String getMps() {return Mps;}
    public String getCylinders() {return Cylinders;}

    @Override
    public String toString() {
        /* Motor(1000,8,120) */
        return "Motor(" + Cc + "," + Cylinders + "," + Mps + ")";
    }

}
