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
public class Receiver implements Runnable{
    private String threadName;
    private int port;
    
    public Receiver(String name, int port){
        this.threadName = name;
        this.port = port;
    }
    
    /*
    * On lance un thread : ce thread doit lire un seul message puis 
    * on le ferme. 
    */
    public void run(){
        try{
            System.out.println("Server started!");
            DatagramSocket dgramSocket = new DatagramSocket(this.port);
            
            byte[] buffer = new byte[256];
            DatagramPacket inPacket = new DatagramPacket(buffer, buffer.length);
            while(true){
                dgramSocket.receive(inPacket);

                InetAddress clientAddress = inPacket.getAddress();
                int clientPort = inPacket.getPort();

                String message = new String(inPacket.getData(), 0, inPacket.getLength());
                System.out.println("Message received from : " + clientAddress + " at port nb : " + clientPort);
                System.out.println("Message says : " + message);
            }
            
        } catch (IOException e){
            System.out.println("ERROR : look at receiver ur fukd");
            System.exit(1);
        }
        
        
    }
    
}
