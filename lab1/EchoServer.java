/* EchoServer.java */

import java.io.*;
import java.net.*;

/**
 * Simple server that listens to port 8189; it echoes back all client input.
 * TEST this with commandline: 'telnet localhost 8189'
 */
public class EchoServer {
    public static void main(String[] args) {
        try { // establish server socket
            ServerSocket s = new ServerSocket(8189);
            // wait for client connection – as soon as this comes,
            Socket incoming = s.accept(); // socket obj. incoming is constructed
            BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            PrintWriter out = new PrintWriter(incoming.getOutputStream(), true/* autoFlush */);
            out.println("Hello! Enter BYE to exit.");
            // echo client input
            boolean done = false;
            while (!done) {
                String line = in.readLine(); // wait here until a line arrives !
                if (line == null)
                    done = true;
                else {
                    out.println("Echo: " + line);
                    if (line.trim().equals("BYE"))
                        done = true;
                }
            }
            incoming.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}