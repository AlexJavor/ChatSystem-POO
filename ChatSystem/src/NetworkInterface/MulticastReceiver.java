/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;

import MainChat.User;
import Messages.TextMessage;
import java.io.*;
import java.net.*;

/**
 *
 * @author javornik
 */
public class MulticastReceiver implements Runnable {
    
    private InetAddress group;
    private int port;
    private User myUser;
    private String newUserPseudonym;
    private InetAddress newUserIPAddress;
    
    public MulticastReceiver(String groupIP, int port, User usr){
        try{
            this.group = InetAddress.getByName(groupIP);
        } catch (UnknownHostException e){
            System.out.println("ERROR : look at multicastreceiver");
            System.exit(1);
        }
        this.port = port;
        this.myUser = usr;
    }
    
    public void run() {
        System.setProperty("java.net.preferIPv4Stack", "true");
        try {
            MulticastSocket socket = new MulticastSocket(this.port);
            socket.joinGroup(this.group);

            while(true) {
                // Receive IP and pseudonym of a new user
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                System.out.println(received);

                // Extract Pseudonym and IP address from message "received"
                String[] newUserInfo = received.split(":");
                this.newUserPseudonym = newUserInfo[1];
                try{
                    this.newUserIPAddress = InetAddress.getByName(newUserInfo[3].split("/")[1]);
                } catch (UnknownHostException e){
                    System.out.println("ERROR : newUserIPAddress incorrect format");
                    System.exit(1);
                }

                //Send your own IP and Pseudonym to the new user                
                try (Socket tcpSocket = new Socket(this.newUserIPAddress, 2078)) {
                    OutputStream outStream = tcpSocket.getOutputStream();
                    
                    byte[] byteMsg;
                    TextMessage txtMsg = new TextMessage(this.myUser.getPseudonym(), "IP Address:" + this.myUser.getIPAddress());
                    byteMsg = txtMsg.getBytesMessage();
                    
                    outStream.write(byteMsg);
                    outStream.flush();
                    tcpSocket.close();
                } catch (IOException e) {
                    System.out.println("ERROR : look at multicastsender - " + e);
                    System.exit(1);
                }
                
                // Keep thread open until you close the connection
                if(received == "endConection") {
                    break;
                }
            }
            socket.leaveGroup(this.group);
            socket.close();
        } catch(IOException e){
            System.out.println("ERROR : look at multireceviver");
            System.exit(1);
        }
    }
}
