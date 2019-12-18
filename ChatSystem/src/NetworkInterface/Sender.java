/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;

import Messages.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.*;
/**
 *
 * @author salinasg
 */
public class Sender{
    private InetAddress host;
    private int port;
    private String pseudonym;
    private InetAddress userIP;
    
    public Sender(String host, int port, String pseudo){
        try{
            this.host = InetAddress.getByName(host);
        } catch (UnknownHostException e){
            System.out.println("ERROR : look at sender");
            System.exit(1);
        }
        this.port = port;
        this.pseudonym = pseudo;
    }
    
    public void start(){
        try{
            while(true){
                BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
                String msgType = bufferRead.readLine();
                
                try (Socket socket = new Socket(this.host, this.port)) {
                    OutputStream outStream = socket.getOutputStream();
                    
                    byte[] byteMsg;
                    switch (msgType){
                        case "t" :
                            bufferRead = new BufferedReader(new InputStreamReader(System.in));
                            String message = bufferRead.readLine();
                            
                            TextMessage txtMsg = new TextMessage(this.pseudonym, message);
                            byteMsg = txtMsg.getBytesMessage();
                            break;
                        case "i":
                            ImageMessage imMsg = new ImageMessage(this.pseudonym, "/home/salinasg/Bureau/ImageSend/img2.jpg");
                            
                            byteMsg = imMsg.getBytesMessage();
                            break;
                        default :
                            byteMsg = new byte[1];
                    }
                    // Send message type
                    /*byte[] msgType = ByteBuffer.allocate(2).putChar('t').array();
                    outStream.write(msgType);
                    outStream.flush();
                    
                    byte[] byteText = text.getBytes();
                    byte[] sizeText = ByteBuffer.allocate(4).putInt(text.length()).array();
                    outStream.write(sizeText);
                    outStream.write(byteText);
                    outStream.flush();*/
                    
                    //TextMessage textMsg = new TextMessage(this.pseudonym, text);
                    
                    outStream.write(byteMsg);
                    outStream.flush();
                    socket.close();
                } catch (IOException e) {
                    System.out.println("ERROR : look at sender - " + e);
                    System.exit(1);
                }
            }
            
        } catch(Exception e){
            System.out.println("ERROR : look at sender - " + e);
            System.exit(1);
        }
        
        
        /****** UDP version ******
        try{
            System.out.println("Client started!");
            DatagramSocket dgramClient = new DatagramSocket();
            
            String message = "Bonjour Server!";
            DatagramPacket outPacket = new DatagramPacket(message.getBytes(), message.length(), this.host, this.port);
            dgramClient.send(outPacket);
            System.out.println("Message sent!");
            
            dgramClient.close();
            
        } catch(IOException e){
            System.out.println("ERROR : look at sender");
            System.exit(1);
        }
        * **************************/
        
        
    }
}
