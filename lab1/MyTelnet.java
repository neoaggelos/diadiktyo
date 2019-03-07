
// MyTelnet.java

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.io.*;

public class MyTelnet extends JFrame {
    private JTextArea msgBody, rcvText;
    private JTextField toUrl, toPort;
    private JButton con, send, receive;
    private PrintWriter out;
    private BufferedReader in;

    public MyTelnet() {
        super("MyTelnet Screen");
        Box bHor = Box.createHorizontalBox();
        Box bVerIn = Box.createVerticalBox();
        Box bVer = Box.createVerticalBox();
        toUrl = new JTextField("localhost", 5);
        bHor.add(toUrl);
        toPort = new JTextField("8189", 5);
        bHor.add(toPort);
        con = new JButton("CONNECT");
        con.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                connectToServer(toUrl.getText(), Integer.parseInt(toPort.getText()));
            }
        });
        bVerIn.add(con);
        send = new JButton("SEND");
        send.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try {
                    sendData(msgBody.getText());
                } catch (IOException exception) {
                }
            }
        });
        bVerIn.add(send);
        receive = new JButton("SEE RESPONSE");
        receive.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                try { // 'hangs' waiting for data reception
                    String recvData = in.readLine();
                    rcvText.setText(recvData);
                    // String recvData;
                    // %%
                    // while ((recvData = in.readLine()) != null) //%%
                    // {rcvText.append(recvData + "\r\n");}
                    // %%
                    // one more %% line below %%
                } catch (IOException exception) {
                }
            }
        });
        bVerIn.add(receive);
        bHor.add(bVerIn);
        bVer.add(bHor);
        msgBody = new JTextArea("Body", 10, 15);
        bVer.add(new JScrollPane(msgBody));
        rcvText = new JTextArea(10, 15);
        rcvText.setEditable(false);
        bVer.add(new JScrollPane(rcvText));
        Container c = getContentPane();
        c.add(bVer);
        setSize(425, 200);
        setVisible(true);
    }

    private void connectToServer(String host, int port) {
        try {
            Socket outgoing = new Socket(host, port);
            // &&
            // && Remove last line and insert next 2, for 10sec connection timeout
            // Socket outgoing = new Socket();
            // &&
            // outgoing.connect(new InetSocketAddress(host, port), 10000);//&&
            // outgoing.setSoTimeout(4000);
            // %% 4sec timeout for data arrivals
            out = new PrintWriter(outgoing.getOutputStream());
            in = new BufferedReader(new InputStreamReader(outgoing.getInputStream()));
            String connResp = in.readLine();
            // 'hangs' waiting for connection confirm
            rcvText.setText(connResp);
        } catch (IOException exception) {
        }
    }

    private void sendData(String textToSend) throws IOException {
        out.print(textToSend);
        out.print("\r\n"); // CR+LF to complete line
        out.flush();
        System.out.println("Sent: " + textToSend);
    }

    public static void main(String args[]) {
        MyTelnet app = new MyTelnet();
        app.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }
}
