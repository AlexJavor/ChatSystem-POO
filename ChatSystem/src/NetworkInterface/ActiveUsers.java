/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;

import GUI.ChatGUI;
import static MainChat.ChatSystem.myUser;
import MainChat.User;
import java.net.InetAddress;
import java.util.*;
import javax.swing.ListModel;

/**
 *
 * @author javornik
 */
public class ActiveUsers {
    
    // Attributes
    private ArrayList<User> userList;
    private ArrayList<Sender> senderList;
    private ArrayList<User> userGloablList;
    private ChatGUI chatGUI;
    private int unicastPort;
    
    // Constructor
    public ActiveUsers(ChatGUI chatGUI, int unicastPort){
        this.userList = new ArrayList<>();
        this.senderList = new ArrayList<>();
        this.userGloablList = new ArrayList<>();
        this.chatGUI = chatGUI;
        this.unicastPort = unicastPort;
    }
    
    // *********************************** Getters ************************************** //
    public ArrayList<User> getActiveUsers(){ return this.userList; }
    public ArrayList<Sender> getActiveSenders(){ return this.senderList; }
    public ArrayList<User> getGlobalActiveUsers() { return this.userGloablList; }
    
    public Sender getSenderFromIP(InetAddress ipAddr) {
        Sender snd;
        Iterator<Sender> iter = this.senderList.iterator();
        snd = iter.next();
        while(iter.hasNext()){
            if(snd.getSenderIPAddress().equals(ipAddr)){
                break;
            }
            snd = iter.next();
        }
        return snd;
    }
    
    public User getUserFromPseudo(String pseudo){
        Iterator<User> iter = this.userList.iterator();
        User usr_it;
        User usr_return = null;
        while (iter.hasNext() && (usr_return == null)){
            usr_it = iter.next();
            if (usr_it.getPseudonym().equals(pseudo)){
                usr_return = usr_it;
            }
        }
        return usr_return;
    }
    
    public User getUserFromIP(InetAddress ip){
        Iterator<User> iter = this.userList.iterator();
        User usr_it;
        User usr_return = null;
        while (iter.hasNext() && (usr_return == null)){
            usr_it = iter.next();
            if (usr_it.getIPAddress().equals(ip)){
                usr_return = usr_it;
            }
        }
        return usr_return;
    }
    
    // Use after checking if the user is in the list
    public User getActiveUserFromMAC(String macAddress){
        User usr;
        Iterator<User> iter = this.userList.iterator();
        usr = iter.next();
        while(iter.hasNext()){
            if(usr.getMACAddress().equals(macAddress)){
                break;
            }
            usr = iter.next();
        }
        return usr;
    }
    
    // ******************************** Methods ********************************************* //
    // Adds active user if its pseudonym is not already on the list: It ignores the new one.
    public void addActiveUser(User usr){
        if (!this.containsSamePseudonym(usr.getPseudonym())){
            // Adding user to list
            this.userList.add(usr);
            // Adding sender to list
            Sender snd = new Sender(usr.getIPAddress(), this.unicastPort, this);
            this.senderList.add(snd);
            // Print the new user in the chatGUI
            this.chatGUI.getListModelActiveUsers().addElement(usr.getPseudonym());
        }
    }
    
    public void updateActiveUser(User usr, String newPseudo){
        // Remove previous entry
        //this.userList.remove(getUserFromPseudo());
        System.out.println("old pseudo:" + usr.getPseudonym() + "new pseudo: " + newPseudo);
        // Check where is the user to update and change its pseudo
        if (this.containsSamePseudonym(usr.getPseudonym())){
            // Take user index
            User iterUser;
            int index = 0;
            Iterator<User> iter = this.userList.iterator();
            while(iter.hasNext()){
                iterUser = iter.next();
                if(iterUser.equals(usr)){
                    break;
                }
                index++;
            }
            System.out.println("index GUI:" + index);
            //this.userList.add(newUsr);
            this.chatGUI.getListModelActiveUsers().setElementAt(newPseudo,index);
            
            // Change other messages received pseudo
            String messagesWithNewPseudo = "New message(s) from " + newPseudo;
            String messagesWithOldPseudo = "New message(s) from " + usr.getPseudonym();
            ListModel model = this.chatGUI.getListModelOtherMessages();
            for(int i=0; i < model.getSize(); i++) {
                String str = (String) model.getElementAt(i);
                if(str.equals(messagesWithOldPseudo)){
                    this.chatGUI.getListModelOtherMessages().setElementAt(messagesWithNewPseudo, i);
                }
            }
        }
    }
    
    public void removeActiveUser(User usr){
        System.out.println("removing active user");
        // Remove active user
        this.userList.remove(usr);
        // Remove corresponding sender
        Sender snd = getSenderFromIP(usr.getIPAddress());
        this.senderList.remove(snd);
        // Take user index        this.chatGUI.getListModelActiveUsers().remove(index);
        User iterUser;
        int index = 0;
        Iterator<User> iter = this.userList.iterator();
        while(iter.hasNext()){
            iterUser = iter.next();
            if(iterUser.equals(usr)){
                break;
            }
            index++;
        }
        // Delete user from GUI list
        System.out.println("index GUI:" + index);
        
        // GUI remove correct user
        ListModel model = this.chatGUI.getListModelActiveUsers();
        for(int i=0; i < model.getSize(); i++) {
            String str = (String) model.getElementAt(i);
            if(str.equals(usr.getPseudonym())){
                this.chatGUI.getListModelActiveUsers().remove(i);
            }
        }
    }
    
    public boolean containsSamePseudonym(String pseudo){
        boolean exists = false;
        User usr;
        Iterator<User> iter = this.userList.iterator();
        while(iter.hasNext()){
            usr = iter.next();
            if(usr.getPseudonym().equals(pseudo)){
                exists = true;
            }
        }
        return exists;
    }
    
        
    public boolean containsUser(String macAddress){
        boolean exists = false;
        User usr;
        Iterator<User> iter = this.userList.iterator();
        while(iter.hasNext()){
            usr = iter.next();
            if(usr.getMACAddress().equals(macAddress)){
                exists = true;
            }
        }
        return exists;
    }
    
    public boolean newPseudoIsValid(String newPseudo) {
        return (!newPseudo.equals(myUser.getPseudonym()) && !this.containsSamePseudonym(newPseudo));
    }
   
    
    @Override
    public String toString() {
        String str = "Active Users: \n";
        User usr;
        Iterator<User> iter = this.userList.iterator();
        while(iter.hasNext()){
            usr = iter.next();
            str += usr + "\n"; 
        }
        return str;
    }
}
