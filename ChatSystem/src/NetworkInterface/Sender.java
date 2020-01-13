/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;

import static MainChat.ChatSystem.myUser;
import MainChat.User;
import Messages.*;
import java.net.*;
import java.io.*;
/**
 *
 * @author salinasg
 */
public class Sender{
    private InetAddress host;
    private int port;
    
    public Sender(InetAddress host, int port){
        this.host = host;
        this.port = port;
    }
    
    public void send(){
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
                            
                            TextMessage txtMsg = new TextMessage(myUser.getPseudonym(), message);
                            byteMsg = txtMsg.getBytesMessage();
                            break;
                        case "i":
                            ImageMessage imMsg = new ImageMessage(myUser.getPseudonym(), "/home/salinasg/Bureau/ImageSend/img2.jpg");
                            byteMsg = imMsg.getBytesMessage();
                            break;
                        default :
                            byteMsg = new byte[1];
                    }
                    
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
    }
}
