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
public class Sender{
    private InetAddress host;
    private int port;
    
    
    public Sender(String host, int port){
        try{
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException e){
            System.out.println("ERROR : look at sender ur fukd");
            System.exit(1);
        }
        this.port = port;
    }
    
    public void start(){
        try{
            System.out.println("Client started!");
            DatagramSocket dgramClient = new DatagramSocket();
            
            String message = "Bonjour Server!";
            DatagramPacket outPacket = new DatagramPacket(message.getBytes(), message.length(), this.host, this.port);
            dgramClient.send(outPacket);
            System.out.println("Message sent!");
            
            dgramClient.close();
            
        } catch(IOException e){
            System.out.println("ERROR : look at sender ur fukd");
            System.exit(1);
        }
        
        
    }
}
