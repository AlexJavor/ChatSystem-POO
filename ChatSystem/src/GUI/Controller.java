/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static MainChat.ChatSystem.myUser;
import static MainChat.ChatSystem.netInterface;
import NetworkInterface.NetInterface;
import NetworkInterface.Sender;
import java.net.InetAddress;
import java.net.UnknownHostException;
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
    public void confirmPseudonym(PseudonymGUI pseudonymGUI, java.awt.event.ActionEvent evt, javax.swing.JTextField jTextField1){
        
        repeatedPseudo = false;
        
        // Get pseudonym from text field
        this.localPseudonym = jTextField1.getText();
        //ChatSystem.ChangePseudonymMain(this.localPseudonym);
        // Check if its a correct pseudo:
        if(this.localPseudonym.matches("^[a-zA-Z0-9]+$") && this.localPseudonym.length() > 0){
            
            myUser.setPseudonym(this.localPseudonym);
            
            // If it is not the first connection there should be already a neetwork interface
            System.out.println("FirstConnection = " + firstConnection);
            if(firstConnection){
                netInterface = new NetInterface();
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
                new ChatGUI(this).setVisible(true);
                pseudonymGUI.setVisible(false);          
            }
            
        } else {
            JOptionPane.showMessageDialog(null, "Your pseudonym must contain only alphanumeric characters and not be empty");
            jTextField1.setText("");
        }
    }
}


