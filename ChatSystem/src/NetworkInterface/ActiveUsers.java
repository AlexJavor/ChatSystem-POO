/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;

import GUI.ChatGUI;
import MainChat.User;
import java.net.InetAddress;
import java.util.*;

/**
 *
 * @author javornik
 */
public class ActiveUsers {
    
    // Attributes
    private ArrayList<User> userList;
    private ArrayList<Sender> senderList;
    private ChatGUI chatGUI;
    private int unicastPort;
    
    // Constructor
    public ActiveUsers(ChatGUI chatGUI, int unicastPort){
        this.userList = new ArrayList<>();
        this.senderList = new ArrayList<>();
        this.chatGUI = chatGUI;
        this.unicastPort = unicastPort;
    }
    
    // *********************************** Getters ************************************** //
    public ArrayList<User> getActiveUsers(){ return this.userList; }
    public ArrayList<Sender> getActiveSenders(){ return this.senderList; }
    
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
    
    public void updateActiveUser(User usr){
        // Remove previous entry
        this.userList.remove(usr);
        // Check where is the user to update and change its pseudo
        if (!this.containsSamePseudonym(usr.getPseudonym())){
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
            this.userList.add(usr);
            this.chatGUI.getListModelActiveUsers().setElementAt(usr.getPseudonym(),index);
        }
    }
    
    public void removeActiveUser(User usr){
        // Remove active user
        this.userList.remove(usr);
        // Remove corresponding sender
        Sender snd = getSenderFromIP(usr.getIPAddress());
        this.senderList.remove(snd);
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
        // Delete user from GUI list
        this.chatGUI.getListModelActiveUsers().remove(index);
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
