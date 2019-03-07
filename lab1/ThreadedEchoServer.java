import java.io.*;
import java.net.*;

/**
 * Multithreaded server that listens to 8189; it echoes back input from each
 * client. TEST this with commandline: 'telnet localhost 8189' from several
 * parallel command windows.
 */
public class ThreadedEchoServer
// main class listening and building new threads and sockets
{
    public static void main(String[] args) {
        try {
            int i = 1;
            ServerSocket s = new ServerSocket(8189);
            for (;;) {
                Socket incoming = s.accept();
                System.out.println("Spawning " + i);
                Thread t = new ThreadedEchoHandler(incoming, i);
                t.start();
                i++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

/** This class handles the client input for one server socket connection. */
class ThreadedEchoHandler extends Thread { // Constructs a handler: i the incoming socket, c the counter for the
                                           // handlers (used in prompt)
    public ThreadedEchoHandler(Socket i, int c) {
        incoming = i;
        counter = c;
    }

    public void run() {
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(incoming.getInputStream()));
            PrintWriter out = new PrintWriter(incoming.getOutputStream(), true /* autoFlush */);
            out.println("Hello! Enter BYE to exit.");
            boolean done = false;
            while (!done) {
                String str = in.readLine();
                if (str == null)
                    done = true;
                else {
                    out.println("Echo (" + counter + "): " + str);
                    if (str.trim().equals("BYE"))
                        done = true;
                }
            }
            incoming.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Socket incoming;
    private int counter;
}