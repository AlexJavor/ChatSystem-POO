/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainChat;
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
    private final String macAddress;
    
    public User(String pseudo, InetAddress ipAddr, String macAddr){
        this.pseudonym = pseudo;
        this.ipAddress = ipAddr;
        this.macAddress = macAddr;
    }
    
    public String getPseudonym(){ return this.pseudonym; }
    public InetAddress getIPAddress() { return this.ipAddress; }
    public String getMACAddress(){ return this.macAddress; }
    
    public void ChangePseudonym(String newPseudo){
        this.pseudonym = newPseudo;
    }
    
    public String toString(){
        return "Pseudonym : " + this.pseudonym + " - IP Address : " + this.ipAddress + " - MAC Address : " + this.macAddress;
    }
}
