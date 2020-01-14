/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;

import MainChat.User;
import java.net.InetAddress;
import java.util.*;

/**
 *
 * @author javornik
 */
public class ActiveUsers {
    
    private ArrayList<User> userList;
    
    public ActiveUsers(){
        this.userList = new ArrayList<>();
    }
    
    public ArrayList<User> getActiveUsers(){ return this.userList; }
    
    // Adds active user if its pseudonym is not already on the list: It ignores the new one.
    public void addActiveUser(User usr){
        if (!this.containsSamePseudonym(usr.getPseudonym())){
            this.userList.add(usr);
        }
    }
    
    public void removeActiveUser(User usr){ 
        this.userList.remove(usr); 
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
