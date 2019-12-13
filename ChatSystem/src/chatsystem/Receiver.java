/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.ByteBuffer;
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
    
    /**
    * On lance un thread : ce thread doit lire un seul message puis 
    * on le ferme. 
    */
    public void run(){
        
        try{
            ServerSocket servSocket = new ServerSocket(this.port);
            while(true){
                Socket socket = servSocket.accept();

                InputStream inStream = socket.getInputStream();
                byte[] msgType = new byte[2];
                inStream.read(msgType);
                char messageType = ByteBuffer.wrap(msgType).asCharBuffer().get();
                
                //System.out.println("MessageType: " + messageType);
                
                if(messageType == 't'){
                    byte[] sizeByteText = new byte[4];
                    inStream.read(sizeByteText);
                    int sizeText = ByteBuffer.wrap(sizeByteText).asIntBuffer().get();
                    byte[] byteText = new byte[sizeText];
                    inStream.read(byteText);
                    String stringText = new String(byteText);
                    System.out.println("Receiving text message: " + stringText + " of length: " + stringText.length());
                } else if (messageType == 'i') {
                    System.out.println("Receiving image message");
                } else if (messageType == 'f') {
                    System.out.println("Receiving file message");
                } else if (messageType == 'b') {
                    System.out.println("Receiving begin message");
                } else if (messageType == 'e') {
                    System.out.println("Receiving end message");
                } else {
                    System.out.println("Error unrecognised Type");
                }

                socket.close();
            }
            
        } catch (IOException e){
            System.out.println("ERROR : look at receiver - " + e);
            System.exit(1);
        }
        
        /***** UDP Version *****
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
            System.out.println("ERROR : look at receiver - " + e);
            System.exit(1);
        }
        **************************/
    }
    
}
