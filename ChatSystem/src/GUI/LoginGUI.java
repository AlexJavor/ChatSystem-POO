/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static MainChat.ChatSystem.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author alexjavor
 */
public class LoginGUI extends JFrame implements ActionListener {
    
    private JButton confirmButton1;
    private JLabel welcomeLabel1;
    private JLabel requestLabel1;
    private JTextField textfield;
    private String myPseudonym;
    
    public LoginGUI() {
        super("Chat System v0.2");
        setSize(600,200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        //Set GUI in the center
        setLocationRelativeTo(null);
        
        InitializeLoginGUI();
    }
    // Getters
    public String getPseudonymFromInput() { return this.myPseudonym; }
    
    /**
     * InitializeGUI
     */
    private void InitializeLoginGUI(){
        
        // PANEL config
        JPanel panel = new JPanel();
        welcomeLabel1 = new JLabel("Welcome to the Ultimate JAVA Chat System");
        requestLabel1 = new JLabel("Please, enter the Pseudonym you would like to use: ");
        textfield = new JTextField(20);
        confirmButton1 = new JButton("Send");
        
        confirmButton1.addActionListener(this);
        textfield.addActionListener(this);
        
        panel.add(welcomeLabel1);
        panel.add(requestLabel1);
        panel.add(textfield);
        panel.add(confirmButton1); 
        
        add(panel);
        
    }
    
    @Override
    public void actionPerformed(ActionEvent event){
        //If you click the send button or tap "enter" you will perform the action 
        if(event.getSource() == confirmButton1 || event.getSource() == textfield){
            this.myPseudonym = textfield.getText();
            try {
                // Waiting to receive all conections (it can change)
                Thread.sleep(400);
            } catch (InterruptedException ex) {}
            if(repeatedPseudo){
                JOptionPane.showMessageDialog(null, "Pseudonym already in use, please choose another one:");
                textfield.setText("");
                this.myPseudonym = null;
            } else {
                new ChatGUI(this.myPseudonym).setVisible(true);
                setVisible(false); 
            }
        }
    }
    
}
