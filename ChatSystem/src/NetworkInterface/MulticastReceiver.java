/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;

import static MainChat.ChatSystem.myUser;
import MainChat.User;
import Messages.*;
import java.io.*;
import java.net.*;

/**
 *
 * @author javornik
 */
public class MulticastReceiver implements Runnable {
    
    private InetAddress group;
    private int multicastPort;
    private int unicastPort;
    private User newUser;
    private ActiveUsers activeUserList;
    private boolean newUserINActiveUserList;
    private String newUserStatus;
    private String newUserPseudonym;
    private InetAddress newUserIPAddress;
    private String newUserMACAdress;
    
    public MulticastReceiver(String groupIP, int multicastPort, ActiveUsers activeUserList, int unicastPort){
        try{
            this.group = InetAddress.getByName(groupIP);
        } catch (UnknownHostException e){
            System.out.println("ERROR : look at multicastreceiver " + e);
            System.exit(1);
        }
        this.multicastPort = multicastPort;
        this.unicastPort = unicastPort;
        this.activeUserList = activeUserList;
    }
        
    // Methods
    public void replyMyUser(InetAddress newUserIP){
        // Send your own IP and Pseudonym to the new user
        try (Socket tcpSocket = new Socket(newUserIP, 2077)) {
            OutputStream outStream = tcpSocket.getOutputStream();

            byte[] byteMsg;
            StatusMessage txtMsg = new StatusMessage(myUser.getPseudonym(), myUser.getIPAddress() + "-" + myUser.getMACAddress());
            byteMsg = txtMsg.getBytesMessage();
            outStream.write(byteMsg);

            outStream.flush();
            tcpSocket.close();
            System.out.println("Sending my info to new user");
        } catch (IOException e) {
            System.out.println("ERROR : look at Sender replyMyUser - " + e);
            System.exit(1);
        }
    }

    public void run() {
        System.setProperty("java.net.preferIPv4Stack", "true");
        try {
            MulticastSocket socket = new MulticastSocket(this.multicastPort);
            socket.joinGroup(this.group);

            while(true) {
                // Receive IP and pseudonym of a new user
                byte[] buf = new byte[256];
                DatagramPacket packet = new DatagramPacket(buf, buf.length);
                socket.receive(packet);
                String received = new String(packet.getData(), 0, packet.getLength());
                //System.out.println(received);
                
                // Extract Status, Pseudonym, IP address and MAC address
                String[] newUserInfo = received.split("-"); 
                this.newUserStatus = newUserInfo[0];
                this.newUserPseudonym = newUserInfo[2];
                try{
                    this.newUserIPAddress = InetAddress.getByName(newUserInfo[4].split("/")[1]);
                } catch (UnknownHostException e){
                    System.out.println("ERROR : newUserIPAddress incorrect format");
                    System.exit(1);
                }
                this.newUserMACAdress = newUserInfo[6];
               
                //System.out.println("New user addr: " + this.newUserIPAddress);
                //System.out.println("My user addr : " + this.myUser.getIPAddress());
                
                // Add new user to active user list and Send your own IP and Pseudonym to the new user
                // Check if it is an autoreceive message
                if (!this.newUserIPAddress.toString().equals(myUser.getIPAddress().toString())) {
                    // Check if the new user has a different pseudo as you
                    if(!this.newUserPseudonym.equals(myUser.getPseudonym())){
                        // Check if new user has same MAC address as someone of the ActiveUsers list + verify status
                        this.newUserINActiveUserList = this.activeUserList.containsUser(this.newUserMACAdress);
                        
                        if(!this.newUserINActiveUserList && this.newUserStatus.equals("Status:CONNECTED")){
                            // Adding new user to active user list if its pseudo is not already in it
                            this.newUser = new User(this.newUserPseudonym, this.newUserIPAddress, this.newUserMACAdress);
                            this.activeUserList.addActiveUser(this.newUser);
                        } else if (this.newUserINActiveUserList && this.newUserStatus.equals("Status:NEW_PSEUDONYM")) {
                            // Changing new user's pseudonym name
                            this.newUser = this.activeUserList.getActiveUserFromMAC(this.newUserMACAdress);
                            this.newUser.setPseudonym(this.newUserPseudonym);
                            this.activeUserList.addActiveUser(this.newUser);
                        } else if (this.newUserINActiveUserList && this.newUserStatus.equals("Status:DISCONNECTED")) {
                            this.newUser = this.activeUserList.getActiveUserFromMAC(this.newUserMACAdress);
                            this.activeUserList.removeActiveUser(this.newUser);
                        } else {
                            System.out.println("Status unrecognised / User error? - check MulitcastReceiver");
                        }
                    } else {
                        System.out.println("Omiting new user with the same pseudonym as me");
                    }
                    System.out.println(activeUserList.toString());
                    replyMyUser(this.newUserIPAddress);
                } else {
                    System.out.println("Autoreceived multicast packet");
                }
                
                // Keep thread open until you close the connection
                if(this.newUserStatus.equals("Status:DISCONNECTED")) {
                    break;
                }
            }
            socket.leaveGroup(this.group);
            socket.close();
        } catch(IOException e){
            System.out.println("ERROR : look at multicastreceviver - " + e);
            System.exit(1);
        }
    }
}
