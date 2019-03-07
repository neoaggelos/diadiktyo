import java.io.*;
import java.net.*;
import java.util.*;

public class GETwithURL {
    public static void main(String[] args) {
        try {
            String urlString = args[0];
            URL url = new URL(urlString);

            URLConnection cnx = url.openConnection();
            cnx.connect();

            for(var header : cnx.getHeaderFields().entrySet()) {
                System.out.println("The Key " + header.getKey() + " has value " + header.getValue());
            }

            System.out.println("--- Response details");
            System.out.println("Content type: " + cnx.getContentType());
            System.out.println("Content length: " + cnx.getContentLength());
            System.out.println("Content encoding: " + cnx.getContentEncoding());
            System.out.println("Date: " + cnx.getDate());
            System.out.println("Expiration: " + cnx.getExpiration());
            System.out.println("Last Modified: " + cnx.getLastModified());
            System.out.println("------------------------");
            System.out.println("");
            System.out.println("--- Response");

            BufferedReader bin = new BufferedReader(new InputStreamReader(cnx.getInputStream()));

            int i = 0; String line;
            while ((line = bin.readLine()) != null && (i++ < 10)) {
                System.out.println(line);
            }
            if (line != null)
                System.out.println("...");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}