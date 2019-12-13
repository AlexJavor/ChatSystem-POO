/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import java.net.*;
import java.io.*;
import java.util.*;

/**
 *
 * @author salinasg
 */
public class User {
    // Each User has a pseudonym that can be changed at any time
    private String pseudonym;
    
    // Every User has its own IP address 
    private InetAddress ipAddress;
    
    private NetworkInterface netInterface;
    
    public User(String pseudo, String ipAddr, Receiver rcv, Sender snd){
        this.pseudonym = pseudo;
        this.netInterface = new NetworkInterface(rcv, snd);
        // TODO
        try{
            this.ipAddress = InetAddress.getByName(ipAddr);
        } catch (UnknownHostException e) {
            System.out.println("ERROR : This host doesn't exist");
            System.exit(1);
        }
    }
    
    public String getPseudonym(){ return this.pseudonym; }
    
    public void ChangePseudonym(String newPseudo){
        this.pseudonym = newPseudo;
    }
    
    public void SendMessage(){
        this.netInterface.SendMessage();
    }
    
    public InetAddress getIPAddress() { return this.ipAddress; }
}
