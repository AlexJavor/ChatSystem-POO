/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import HistoryLogs.DateLog;
import static HistoryLogs.JSONReader.read;
import HistoryLogs.MessageLog;
import static MainChat.ChatSystem.myUser;
import static MainChat.ChatSystem.netInterface;
import MainChat.User;
import NetworkInterface.ActiveUsers;
import NetworkInterface.NetInterface;
import NetworkInterface.Sender;
import java.awt.event.ActionListener;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import static javax.swing.JFileChooser.FILES_ONLY;
/**
 *
 * @author alexjavor
 */
public class Controller {
    // Methods declaration and definition
    
    public static boolean repeatedPseudo = false;
    public static boolean firstConnection = true;
    private String localPseudonym;
    private boolean changePseudonym;
    
    // Constructor
    public Controller(){
        this.changePseudonym = false;
    }
    
    // Getters
    public void setChangePseudonym(boolean changePseudonym) { this.changePseudonym = changePseudonym; }
    
    // Methods
    public void confirmPseudonym(PseudonymGUI pseudonymGUI, ChatGUI chatGUI, java.awt.event.ActionEvent evt, javax.swing.JTextField jTextField1, javax.swing.JLabel jLabelMyPsudonym){
        
        repeatedPseudo = false;
        
        // Get pseudonym from text field
        this.localPseudonym = jTextField1.getText();
        // Check if its a correct pseudo:
        if(this.localPseudonym.matches("^[a-zA-Z0-9]+$") && this.localPseudonym.length() > 0){
            
            myUser.setPseudonym(this.localPseudonym);
            
            // If it is not the first connection there should be already a neetwork interface
            System.out.println("FirstConnection = " + firstConnection);
            if(firstConnection){
                netInterface = new NetInterface(chatGUI);
                netInterface.sendMulticastMessage("Status:CONNECTED");
                firstConnection = false;
            } else {
                if(changePseudonym){
                    netInterface.sendMulticastMessage("Status:NEW_PSEUDONYM");
                } else {
                    netInterface.sendMulticastMessage("Status:CONNECTED");
                }
            }
            
            // Waiting to receive all conections (it can change)
            try{ Thread.sleep(400); } catch (InterruptedException ex) {}
            
            System.out.println("repeatedPseudo: " + repeatedPseudo);
            if(repeatedPseudo){
                JOptionPane.showMessageDialog(null, "Pseudonym already in use, please choose another one");
                jTextField1.setText("");
                myUser.setPseudonym(null);
            } else {
                // Start chatGUI
                jLabelMyPsudonym.setText(this.localPseudonym);
                pseudonymGUI.setVisible(false);
                chatGUI.setVisible(true);                     
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Your pseudonym must contain only alphanumeric characters and not be empty");
            jTextField1.setText("");
        }
    }
    
    public void selectActiveUserGUI(ChatGUI chatGUI) {
        // Check if something is selected
        if(chatGUI.getListActiveUsers().isSelectionEmpty()){
             // Clearing text area
            chatGUI.getTextAreaHistory().setText("");    
        } else {
            // Setting current sender
            String selectedPseudo = chatGUI.getListActiveUsers().getSelectedValue();
            User selectedUser = netInterface.getActiveUsers().getUserFromPseudo(selectedPseudo); 
            Sender snd = netInterface.getActiveUsers().getSenderFromIP(selectedUser.getIPAddress());
            chatGUI.setCurrentSenderGUI(snd);
            
            // Load history file if it exists else setText("")
            loadHistoryFile(chatGUI, selectedUser);

            // Delete notification
            String notificationMessage = "New message(s) from " + selectedPseudo;
            if(chatGUI.getListModelOtherMessages().contains(notificationMessage)){
                int index = chatGUI.getListOtherMessages().getNextMatch(notificationMessage, 0, javax.swing.text.Position.Bias.Forward);
                chatGUI.getListModelOtherMessages().remove(index);
            }
        }
    }
    
    public void sendTextMessageChatGUI(ChatGUI chatGUI) {
        if(chatGUI.getListActiveUsers().isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "Please select an active user");
        } else {
            String message = chatGUI.getTextFieldSend().getText();
            // Getting current date
            DateLog dateMsg = DateLog.getCurrentDate();
            // Sending message
            netInterface.sendMessageToUser(chatGUI.getCurrentSenderGUI(), "t", message, dateMsg);   
            chatGUI.getTextFieldSend().setText("");
            chatGUI.getTextAreaHistory().append(myUser.getPseudonym() + "(me): " + message + "\n" + dateMsg + "\n\n");
            chatGUI.getTextAreaHistory().setCaretPosition(chatGUI.getTextAreaHistory().getDocument().getLength());
        }
    }
    
    public void sendImageMessageChatGUI(ChatGUI chatGUI) {
        if(chatGUI.getListActiveUsers().isSelectionEmpty()){
            JOptionPane.showMessageDialog(null, "Please select an active user");
        } else {
            FileExplorerGUI fileExplorerGUI = new FileExplorerGUI(this);
            JFileChooser chooser = fileExplorerGUI.getFileChooser();
            //chooser.setCurrentDirectory(new java.io.File("."));
            chooser.setDialogTitle("Select Image (.jpg)");
            chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
            chooser.setAcceptAllFileFilterUsed(false);

            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                File imagePath = chooser.getSelectedFile();
                System.out.println("getSelectedFile() : " + chooser.getSelectedFile());
                // Getting current date
                DateLog dateMsg = DateLog.getCurrentDate();
                // Sending message
                netInterface.sendMessageToUser(chatGUI.getCurrentSenderGUI(), "i", imagePath.getAbsolutePath(), dateMsg);   
                chatGUI.getTextAreaHistory().append(myUser.getPseudonym() + "(me): << Image: " + imagePath.getName() + " sended >> \n" + dateMsg + "\n\n");
                chatGUI.getTextAreaHistory().setCaretPosition(chatGUI.getTextAreaHistory().getDocument().getLength());
            } else {
                System.out.println("No Selection ");
            }
        }
    }
    
    public void receiverManagerGUI(ChatGUI chatGUI, String stringPseudo, String stringText, DateLog dateMsg, char messageType){
        User user = netInterface.getActiveUsers().getUserFromPseudo(stringPseudo);
        String selectedActiveUser = chatGUI.getListActiveUsers().getSelectedValue();
        if(selectedActiveUser == null ? user.getPseudonym() == null : selectedActiveUser.equals(user.getPseudonym())) {
            switch (messageType) {
                case 't':
                    String fullMessage = user.getPseudonym() + ": " + stringText + "\n" + dateMsg + "\n\n";
                    chatGUI.getTextAreaHistory().append(fullMessage);
                    break;
                case 'i':
                    String imagePath = user.getPseudonym() + ": " + stringText + " \n" + dateMsg + "\n\n";
                    chatGUI.getTextAreaHistory().append(imagePath);
                    break;
                default:
                    JOptionPane.showMessageDialog(null, "Unknown received type");
                    break;
            }
            // Set the scrollbar down when a message is received
            chatGUI.getTextAreaHistory().setCaretPosition(chatGUI.getTextAreaHistory().getDocument().getLength());
        } else {
            String notificationMessage = "New message(s) from " + user.getPseudonym();
            if(!chatGUI.getListModelOtherMessages().contains(notificationMessage)){
                chatGUI.getListModelOtherMessages().addElement(notificationMessage);
            }
        }
    }
    
    private void loadHistoryFile(ChatGUI chatGUI, User selectedUser){
        String jsonChatFile = "../../JSONFiles/Chat_" + selectedUser.getMACAddress().replaceAll(":", "-") + ".json";
        if(new File(jsonChatFile).isFile()){
            ArrayList<MessageLog> MessageLogList = read(jsonChatFile);
            String fullMessageLog = "";
            String currentMessageLog;
            String currentSenderPseudo;
            MessageLog iterMessageLog;
            Iterator<MessageLog> iter = MessageLogList.iterator();
            while (iter.hasNext()){
                iterMessageLog = iter.next();
                // Check who send it: if the sender has the your MAC address its you
                if(iterMessageLog.getSender().getMACAddress().equals(myUser.getMACAddress())){
                    currentSenderPseudo = myUser.getPseudonym() + "(me)";
                } else {
                    currentSenderPseudo = selectedUser.getPseudonym();
                }
                currentMessageLog = currentSenderPseudo + ": " + iterMessageLog.getContent() + "\n" + iterMessageLog.getDate() + "\n\n";
                fullMessageLog += currentMessageLog;
            }
            chatGUI.getTextAreaHistory().setText(fullMessageLog);
        } else  {
            chatGUI.getTextAreaHistory().setText("");     
        }
    }
}


