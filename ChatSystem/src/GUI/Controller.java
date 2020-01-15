/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static MainChat.ChatSystem.myUser;
import static MainChat.ChatSystem.netInterface;
import MainChat.User;
import NetworkInterface.ActiveUsers;
import NetworkInterface.NetInterface;
import NetworkInterface.Sender;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.JOptionPane;

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
                jLabelMyPsudonym.setText(this.localPseudonym);
                pseudonymGUI.setVisible(false);
                chatGUI.setVisible(true);                     
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Your pseudonym must contain only alphanumeric characters and not be empty");
            jTextField1.setText("");
        }
    }
    
    public void updateGUIActiveUserList(ActiveUsers activeUsers, ChatGUI chatGUI){
        //First we remove all elements:
        chatGUI.getListModel().removeAllElements();
        
        //Second we add all pseudos from the active user list to the GUI jList
        ArrayList<User> activeUsersList = activeUsers.getActiveUsers();
        User usr;
        Iterator<User> iter = activeUsersList.iterator();
        while(iter.hasNext()){
            usr = iter.next();
            // Add Pseudonym's user to GUI list
            chatGUI.getListModel().addElement(usr.getPseudonym());
        }
    }
}


