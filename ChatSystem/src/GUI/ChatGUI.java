/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static MainChat.ChatSystem.myUser;
import static MainChat.ChatSystem.netInterface;
import MainChat.User;
import NetworkInterface.Sender;
import java.awt.event.WindowAdapter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.DefaultListModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.text.DefaultCaret;

/**
 *
 * @author alexjavor
 */
public class ChatGUI extends javax.swing.JFrame {
    
    private Controller controller;
    private FileExplorerGUI fileExplorerGUI;
    private DefaultListModel listModelActiveUsers;
    private DefaultListModel listModelOtherMessages;
    private Sender currentSender;
    /**
     * Creates new form ChatGUI
     * @param myPseudo
     */
    
    public ChatGUI(Controller controller){
        super("Chat System v1.0");
        initComponents();
        
        this.controller = controller;
        
        this.listModelActiveUsers = new DefaultListModel();
        this.listModelOtherMessages = new DefaultListModel();
        jListActiveUsers.setModel(listModelActiveUsers);
        jListOtherMessages.setModel(listModelOtherMessages);
                
        // Set frame to the center
        setLocationRelativeTo(null);
        
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                netInterface.sendMulticastMessage("Status:DISCONNECTED");
                System.out.println("Program Closed.");
            }
        });
    }
    
    // Getters
    public Controller getController() { return this.controller; }
    
    public javax.swing.JLabel getMyPseudoGUI() { return this.myPsudonym; }
    public DefaultListModel getListModelActiveUsers() { return this.listModelActiveUsers; }
    public DefaultListModel getListModelOtherMessages() { return this.listModelOtherMessages; }
    public Sender getCurrentSenderGUI() { return this.currentSender; }
    
    public javax.swing.JTextArea getTextAreaHistory() { return this.jTextAreaHistory; }
    public javax.swing.JTextField getTextFieldSend() { return this.jTextFieldSend; }
    public javax.swing.JList<String> getListActiveUsers() { return this.jListActiveUsers; }
    public javax.swing.JList<String>  getListOtherMessages() { return this.jListOtherMessages; }
    
    // Setters
    public void setCurrentSenderGUI(Sender snd) { this.currentSender = snd;}
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        myPsudonym = new javax.swing.JLabel();
        jTextFieldSend = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButtonSend = new javax.swing.JButton();
        jButtonChangePseudo = new javax.swing.JButton();
        jButtonSendImage = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jListActiveUsers = new javax.swing.JList<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaHistory = new javax.swing.JTextArea();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        jListOtherMessages = new javax.swing.JList<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Welcome");

        myPsudonym.setText("<<Pseudonym>>");

        jTextFieldSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldSendActionPerformed(evt);
            }
        });

        jLabel2.setText("Choose Active User:");

        jButtonSend.setText("Send");
        jButtonSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendActionPerformed(evt);
            }
        });

        jButtonChangePseudo.setText("Change");
        jButtonChangePseudo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonChangePseudoActionPerformed(evt);
            }
        });

        jButtonSendImage.setText("Send Image");
        jButtonSendImage.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonSendImageActionPerformed(evt);
            }
        });

        jListActiveUsers.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                jList1ActiveUsersListSelectionListener(evt);
            }
        });
        jScrollPane3.setViewportView(jListActiveUsers);

        jTextAreaHistory.setEditable(false);
        jTextAreaHistory.setColumns(20);
        jTextAreaHistory.setLineWrap(true);
        jTextAreaHistory.setRows(5);
        jTextAreaHistory.setWrapStyleWord(true);
        jTextAreaHistory.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        jScrollPane1.setViewportView(jTextAreaHistory);

        jLabel3.setText("Messages from other users:");

        jScrollPane4.setViewportView(jListOtherMessages);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(55, 55, 55)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jTextFieldSend)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jButtonSend, javax.swing.GroupLayout.PREFERRED_SIZE, 78, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jButtonSendImage)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(myPsudonym)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButtonChangePseudo)))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(myPsudonym)
                    .addComponent(jButtonChangePseudo))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 328, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldSend, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButtonSend))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButtonSendImage))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(45, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldSendActionPerformed
        this.controller.sendTextMessageChatGUI(this);
    }//GEN-LAST:event_jTextFieldSendActionPerformed

    private void jButtonSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendActionPerformed
        this.controller.sendTextMessageChatGUI(this);
    }//GEN-LAST:event_jButtonSendActionPerformed

    private void jButtonSendImageActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonSendImageActionPerformed
        this.controller.sendImageMessageChatGUI(this);
    }//GEN-LAST:event_jButtonSendImageActionPerformed

    private void jButtonChangePseudoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonChangePseudoActionPerformed
        this.controller.setChangePseudonym(true);
        new PseudonymGUI(this.controller, this).setVisible(true);
        this.setVisible(false); 
    }//GEN-LAST:event_jButtonChangePseudoActionPerformed
    private void jList1ActiveUsersListSelectionListener(javax.swing.event.ListSelectionEvent evt) {
        if (!evt.getValueIsAdjusting()) {
            this.controller.selectActiveUserGUI(this);
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButtonChangePseudo;
    private javax.swing.JButton jButtonSend;
    private javax.swing.JButton jButtonSendImage;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JList<String> jListActiveUsers;
    private javax.swing.JList<String> jListOtherMessages;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTextArea jTextAreaHistory;
    private javax.swing.JTextField jTextFieldSend;
    private javax.swing.JLabel myPsudonym;
    // End of variables declaration//GEN-END:variables
}
