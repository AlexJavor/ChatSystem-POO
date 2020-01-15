/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;
import MainChat.*;
import HistoryLogs.*;
import static MainChat.ChatSystem.myUser;
import static GUI.Controller.repeatedPseudo;
import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.ByteBuffer;
import java.awt.image.BufferedImage;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.imageio.ImageIO;

/**
 *
 * @author salinasg
 */
public class Receiver implements Runnable{
    private final int port;
    private final ActiveUsers activeUserList;
    
    public Receiver(int port, ActiveUsers activeUserList){
        this.port = port;
        this.activeUserList = activeUserList;
    }
    
    /**
    * On lance un thread : ce thread doit lire un seul message puis 
    * on le ferme. 
    */
    @Override
    public void run(){
        try{
            ServerSocket servSocket = new ServerSocket(this.port);
            while(true){
                Socket socket = servSocket.accept();
                InputStream inStream = socket.getInputStream();

                // **** Receiving Type Message **** //
                byte[] msgType = new byte[2];
                inStream.read(msgType);
                char messageType = ByteBuffer.wrap(msgType).asCharBuffer().get();
                //System.out.println("MessageType: " + messageType);

                // **** Receiving Pseudonym Size **** //
                byte[] sizeBytePseudo = new byte[4];
                inStream.read(sizeBytePseudo);
                int sizePseudo = ByteBuffer.wrap(sizeBytePseudo).asIntBuffer().get();
                //System.out.println("Pseudonym Size: " + sizePseudo);

                // **** Receiving Pseudonym **** //
                byte[] bytePseudo = new byte[sizePseudo];
                inStream.read(bytePseudo);
                String stringPseudo = new String(bytePseudo);
                //System.out.println("Pseudonym: " + stringPseudo);
                
                String typeMsg = null;
                String contentMsg = null;                

                // **** Receiving Payload Size **** //
                byte[] sizeBytePayload = new byte[4];
                inStream.read(sizeBytePayload);
                int sizePayload = ByteBuffer.wrap(sizeBytePayload).asIntBuffer().get();
                //System.out.println("Text Payload Size: " + sizePayload);

                // **** Receiving Payload (Text Message) **** //
                byte[] bytePayload = new byte[sizePayload];

                int totalRead = 0;
                int currentRead = 0;
                while (totalRead < sizePayload && (currentRead = inStream.read(bytePayload, totalRead, sizePayload-totalRead)) > 0) {
                    totalRead += currentRead;
                }

                switch (messageType) {
                    case 't':
                        String stringText = new String(bytePayload);
                        //System.out.println("Text Payload: " + stringText);
                        // **** Print Constructed Text Message **** //
                        System.out.println(stringPseudo + ": " + stringText);
                        
                        typeMsg = "t";
                        contentMsg = stringText;
                        
                        break;
                    case 'i':
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
                        Date date = new Date();
                        BufferedImage image = ImageIO.read(new ByteArrayInputStream(bytePayload));
                        String path = "/home/javornik/Bureau/Photo-" + stringPseudo + "-" + dateFormat.format(date) + ".jpg";
                        ImageIO.write(image, "jpg", new File(path));
                        
                        typeMsg = "i";
                        contentMsg = path;
                        
                        //System.out.println("Receiving image message ");
                        break;
                    case 'f':
                        path = "/home/javornik/Bureau";
                        
                        typeMsg = "f";
                        contentMsg = path;
                        
                        System.out.println("Receiving file message");
                        break;
                    case 's':
                        //System.out.println("Receiving status message");
                        String stringStatus = new String(bytePayload);
                        //System.out.println(stringPseudo + ":" + stringStatus);
                        String[] newUserInfo = stringStatus.split("-");
                        // Adding new user to active user list if its pseudo is not the same. If it is change notify to the main thread.
                        if(stringPseudo.equals(myUser.getPseudonym())){
                            repeatedPseudo = true;
                            //System.out.println("Pseudos equals ! OUT!");
                        } else {
                            this.activeUserList.addActiveUser(new User(stringPseudo, socket.getInetAddress(), newUserInfo[1]));
                        }
                        System.out.println(activeUserList.toString());
                        break;
                    case 'e':
                        System.out.println("Receiving end message");
                        break;
                    default:
                        System.out.println("Error unrecognised Type");
                        break;
                }
                if (!(typeMsg == null && contentMsg == null)){
                    // **** Getting some information to write the message in the HistoryLog associated to this conversation **** //
                    User senderMsg = this.activeUserList.getUserFromPseudo(stringPseudo);
                    User receiverMsg = myUser;
                    DateLog dateMsg = DateLog.getCurrentDate();
                    
                    // Creating a new JSON file in the case this communication is new
                    // Current directory : /home/salinasg/Bureau/ChatSystem-POO/ChatSystem                    
                    String fileName = "Chat_" + senderMsg.getMACAddress().replace(":", "-") + ".json";
                    JSONGenerator.generate("./JSONFiles/", fileName);
                    MessageLog message = new MessageLog(typeMsg, senderMsg, receiverMsg, contentMsg, dateMsg);
                    JSONWriter.write("./JSONFiles/" + fileName, message);
                }
                
                socket.close();
            }
            //servSocket.close();       
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
