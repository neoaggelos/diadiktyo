/* Vehicles/Vehicles.java */

package Vehicles;

import java.util.HashMap;

public class Vehicles {
    private HashMap<String, VehicleBean> mVehicles;

    public Vehicles() {
        mVehicles = new HashMap<>();

        insert(new VehicleBean(
                "Yaris", "Toyota", "2005",
                new VehicleMotorBean("1000", "8", "120")
        ));
    }

    public void insert(VehicleBean bean) {
        /* insert a new vehicle bean */
        if (bean == null) {
            throw new IllegalArgumentException("Required parameter missing");
        }
        mVehicles.put(bean.getModel(), bean);
    }

    public void delete(String model) {
        /* remove a vehicle bean, if it exists */
        if (mVehicles.keySet().contains(bean.getModel())) {
            mVehicles.remove(bean.getModel());
        }
    }

    public HashMap<String, VehicleBean> vehicles() {
        return mVehicles;
    }

    public VehicleBean getVehicle(String model) {
        if (model == null) {
            throw new IllegalArgumentException("Required parameter missing");
        }

        return mVehicles.get(model);
    }
}