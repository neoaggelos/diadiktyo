/* Client.java */

package Vehicles;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Vector;

import java.util.Scanner;

import org.apache.soap.Constants;
import org.apache.soap.SOAPException;
import org.apache.soap.encoding.SOAPMappingRegistry;
import org.apache.soap.encoding.soapenc.BeanSerializer;
import org.apache.soap.rpc.Call;
import org.apache.soap.rpc.Parameter;
import org.apache.soap.rpc.Response;
import org.apache.soap.util.xml.QName;

public class Client {

    public static void addVehicle(URL url, Call call) throws SOAPException {
        call.setMethodName("insert");

        Scanner input = new Scanner(System.in);
        System.out.println("Enter a new vehicle!");
        System.out.print("Model: ");
        String model = input.nextLine();
        System.out.print("Manufacturer: ");
        String mnf = input.nextLine();
        System.out.print("Year: ");
        String year = input.nextLine();
        System.out.print("Cc: ");
        String cc = input.nextLine();
        System.out.print("Cylinders: ");
        String cylinders = input.nextLine();
        System.out.print("Mps: ");
        String mps = input.nextLine();

        VehicleBean bean = new VehicleBean(
                model, mnf, year,
                new VehicleMotorBean(cc, cylinders, mps)
        );

        Vector<Parameter> params = new Vector<>();
        params.addElement(new Parameter("vehicleObj", VehicleBean.class, bean, null));
        call.setParams(params);

        Response res = call.invoke(url, "");


        if (!res.generatedFault()) {
            System.out.println("Success!");
        } else {
            System.out.println("Error: " + res.getFault().getFaultString());
        }
    }

    public static void deleteVehicle(URL url, Call call) throws SOAPException {
        call.setMethodName("delete");

        Scanner input = new Scanner(System.in);
        System.out.println("Choose vehicle to delete!");
        System.out.print("Model: ");
        String model = input.nextLine();

        Vector<Parameter> params = new Vector<>();
        params.addElement(new Parameter("model", String.class, model, null));
        call.setParams(params);

        Response res = call.invoke(url, "");

        if (!res.generatedFault()) {
            System.out.println("Success!");
        } else {
            System.out.println("Error: " + res.getFault().getFaultString());
        }
    }

    public static void getVehicle(URL url, Call call) throws SOAPException {
        call.setMethodName("getVehicle");

        Scanner.input = new Scanner(System.in);
        System.out.println("Choose vehicle to retrieve!");
        System.out.print("Model: ");

        Vector<Parameter> params = new Vector<>();
        params.addElement(new Parameter("model", String.class, model, null));
        call.setParams(params);

        Response res = call.invoke(url, "");
        if (res.generatedFault()) {
            System.out.println("Error: " + res.getFault().getFaultString());
        } else {
            System.out.println("Success!");

            VehicleBean bean = (VehicleBean) res.getReturnValue().getValue();
            System.out.println(bean.toString());
        }
    }

    public static void listVehicles(URL url, Call call) throws SOAPException {
        call.setMethodName("vehicles");
        call.setParams(null);

        Response res = call.invoke(url, "");
        if (res.generatedFault()) {
            System.out.println("Error: " + res.getFault().getFaultString());
        } else {
            System.out.println("Success!");

            var map = (HashMap<String, VehicleBean>)res.getReturnValue().getValue();

            for (var entry : map.entrySet()) {
                System.out.println("" + entry.getKey() + "-> " + entry.getValue().toString());
            }
        }
    }

    public static void usage() {
        System.out.println("Usage: java Client [insert/delete/list/get]");
        System.exit(-1);
    }

    public static void main(String[] args) {
        try {
            URL url = new URL("http://localhost:8080/SOAPService/");
            if (args.length != 2) {
                usage();
            }

            Call call = new Call();
            SOAPMappingRegistry reg = new SOAPMappingRegistry();
            BeanSerializer serializer = new BeanSerializer();
            reg.mapTypes(
                    Constants.NS_URI_SOAP_ENC,
                    new QName("urn:VehicleBean_xmlns", "vehicleObj"),
                    VehicleBean.class,
                    serializer, serializer
            );
            reg.mapTypes(
                    Constants.NS_URI_SOAP_ENC,
                    new QName("urn:VehicleMotorBean_xmlns", "vehicleMotorObj"),
                    VehicleMotorBean.class,
                    serializer, serializer
            );
            call.setSOAPMappingRegistry(reg);
            call.setEncodingStyleURI(Constants.NS_URI_SOAP_ENC);
            call.setTargetObjectURI("urn:VehicleCatalog");

            String action = args[1];
            if (action.equals("insert")) {
                addVehicle(url, call);
            } else if (action.equals("delete")) {
                deleteVehicle(url, call);
            } else if (action.equals("list")) {
                listVehicles(url, call);
            } else if (action.equals("get")) {
                getVehicle(url, call);
            }

        } catch (MalformedURLException | SOAPException e) {
            e.printStackTrace();
        }
    }
}