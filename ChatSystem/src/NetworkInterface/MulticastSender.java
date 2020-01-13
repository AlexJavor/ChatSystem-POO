/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;

import static MainChat.ChatSystem.myUser;
import MainChat.User;
import java.net.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author javornik
 */
public class MulticastSender {
   
    private DatagramSocket socket;
    private byte[] buf;
    private InetAddress group;
    private int port;
    private String multicastMsg;
    
    
    public MulticastSender(String groupIP, int port){
        try{
            this.group = InetAddress.getByName(groupIP);
        } catch (UnknownHostException e){
            System.out.println("ERROR : look at multicastsender");
            System.exit(1);
        }
        this.port = port;
        // Defalut message is hello world
        CurrentActiveStatus();
    }
    
    public final void CurrentActiveStatus(){
        this.multicastMsg = "Status ACTIVE. Pseudonym-" + myUser.getPseudonym() + "-IP address-" + myUser.getIPAddress() + "-MAC Address-" + myUser.getMACAddress();
    }
    
    public final void Disconnecting(){
        this.multicastMsg = "Status INACTIVE.";
    }
 
    public void Send(String status){
        switch(status){
            case "online":
                CurrentActiveStatus();
                break;
            case "offline":
                Disconnecting();
                break;                  
        }        
        System.setProperty("java.net.preferIPv4Stack", "true");
        try{
            MulticastSocket socket = new MulticastSocket();
            byte[] buf = this.multicastMsg.getBytes();

            DatagramPacket packet = new DatagramPacket(buf, buf.length, this.group, this.port);
            socket.send(packet);
            socket.close();
        } catch(IOException e){
            System.out.println("ERROR : look at multicastsender - " + e);
            System.exit(1);
        }
    }
}