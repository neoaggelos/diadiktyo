/* Vehicles/VehicleBean.java */

package Vehicles;

public class VehicleBean {
    VehicleMotorBean Motor;
    String Model;
    String Manufacturer;
    String Year;

    public String getManufacturer() {return Manufacturer;}
    public String getModel() {return Model;}
    public VehicleMotorBean getMotor() {return Motor;}
    public String getYear() {return Year;}

    public void setMotor(VehicleMotorBean motor) {Motor = motor;}
    public void setModel(String model) {Model = model;}
    public void setManufacturer(String mnf) {Manufacturer = mnf;}
    public void setYear(String year) {Year = year;}

    public VehicleBean() {}
    public VehicleBean(String mod, String mnf, String year, VehicleMotorBean mot) {
        Model = mod; Manufacturer = mnf; Year = year; Motor = mot;
    }

    @Override
    public String toString() {
        /* Yaris/Toyota/2005: Motor(...) */
        return "" + Model + "/" + Manufacturer + "/" + Year + ": " + Motor.toString();
    }
}
