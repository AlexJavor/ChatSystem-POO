/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;

import static MainChat.ChatSystem.myUser;
import MainChat.User;
import HistoryLogs.*;
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
    private final ActiveUsers activeUserList;
    
    public Sender(InetAddress host, int port, ActiveUsers au){
        this.host = host;
        this.port = port;
        this.activeUserList = au;
    }
    
    // Getters
    public InetAddress getSenderIPAddress() { return this.host; }
    
    // Methods
    public void send(String msgType, String inMessage, DateLog dateMsg){
        try{
            //BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
            //String msgType = bufferRead.readLine();

            try (Socket socket = new Socket(this.host, this.port)) {
                OutputStream outStream = socket.getOutputStream();

                // Some information for the HistoryLogs
                String typeMsg = null;
                String contentMsg = null;

                byte[] byteMsg;
                
                switch (msgType){
                    case "t" :
                        //bufferRead = new BufferedReader(new InputStreamReader(System.in));
                        //String message = bufferRead.readLine();

                        TextMessage txtMsg = new TextMessage(myUser.getPseudonym(), inMessage);
                        byteMsg = txtMsg.getBytesMessage();

                        typeMsg = "t";
                        contentMsg = inMessage;
                        break;
                    case "i":
                        String imagePath = "/home/salinasg/Bureau/ImageSend/img2.jpg";
                        ImageMessage imMsg = new ImageMessage(myUser.getPseudonym(), imagePath);
                        byteMsg = imMsg.getBytesMessage();

                        typeMsg = "i";
                        contentMsg = imagePath;
                        break;
                    default :
                        byteMsg = new byte[1];
                }

                // **** Getting some information to write the message in the HistoryLog associated to this conversation **** //
                if (!(typeMsg == null && contentMsg == null)){
                    User senderMsg = myUser;
                    User receiverMsg = this.activeUserList.getUserFromIP(this.host);

                    // Creating a new JSON file in the case this communication is new
                    // Current directory : /home/salinasg/Bureau/ChatSystem-POO/ChatSystem
                    String fileName = "Chat_" + receiverMsg.getMACAddress().replace(":", "-") + ".json";
                    JSONGenerator.generate("./JSONFiles/", fileName);
                    MessageLog message = new MessageLog(typeMsg, senderMsg, receiverMsg, contentMsg, dateMsg);
                    JSONWriter.write("./JSONFiles/" + fileName, message);
                }


                outStream.write(byteMsg);
                outStream.flush();
                socket.close();
            } catch (IOException e) {
                System.out.println("ERROR : look at sender - " + e);
                System.exit(1);
            }
          
        } catch(Exception e){
            System.out.println("ERROR : look at sender - " + e);
            System.exit(1);
        }
    }
}
