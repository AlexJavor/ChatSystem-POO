/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

/**
 *
 * @author salinasg
 */
public class ChatSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Receiver rcv = new Receiver("Receiver", 2077);
        Sender snd = new Sender("10.1.5.17", 2077);
        User usr = new User("Anthony", "10.1.5.139", rcv, snd);
        usr.SendMessage();
        
    }
    
}
