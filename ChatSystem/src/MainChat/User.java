/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainChat;
import NetworkInterface.*;
import Messages.*;
import java.net.*;
import java.io.*;
import java.util.*;


/**
 *
 * @author salinasg
 */
public class User {
    // Each User has a pseudonym that can be changed at any time and Every User has its own IP address 
    private String pseudonym;
    private InetAddress ipAddress;
    private NetInterface netInterface;
    
    public User(String pseudo, String ipAddr){
        this.pseudonym = pseudo;
        try{
            this.ipAddress = InetAddress.getByName(ipAddr);
        } catch (UnknownHostException e) {
            System.out.println("ERROR : This host doesn't exist");
            System.exit(1);
        }
    }
    
    public String getPseudonym(){ return this.pseudonym; }
    public InetAddress getIPAddress() { return this.ipAddress; }
    
    public void ChangePseudonym(String newPseudo){
        this.pseudonym = newPseudo;
    }
}
