/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import NetworkInterface.*;
import Messages.*;
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
        Sender snd = new Sender("localhost", 2078, "Anthony");
        User usr = new User("Anthony", "localhost", rcv, snd);
        usr.SendMessage();
    }
    
}
