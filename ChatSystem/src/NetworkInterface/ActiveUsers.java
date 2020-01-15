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
    private ChatGUI chatGUI;
    // Constructor
    public ActiveUsers(ChatGUI chatGUI){
        this.userList = new ArrayList<>();
        this.chatGUI = chatGUI;
    }
    
    // Getters
    public ArrayList<User> getActiveUsers(){ return this.userList; }
    
    // Methods
    // Adds active user if its pseudonym is not already on the list: It ignores the new one.
    public void addActiveUser(User usr){
        if (!this.containsSamePseudonym(usr.getPseudonym())){
            this.userList.add(usr);
            this.chatGUI.getListModel().addElement(usr.getPseudonym());
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
            this.chatGUI.getListModel().setElementAt(usr.getPseudonym(),index);
        }
    }
    
    public void removeActiveUser(User usr){ 
        this.userList.remove(usr);
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
        this.chatGUI.getListModel().remove(index);
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
