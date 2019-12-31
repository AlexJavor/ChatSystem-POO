/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainChat;

import NetworkInterface.*;
import Messages.*;
/**
 *
 * @author javornik
 */
public class ChatSystem {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        User usr = new User("Aleks", "127.0.0.1");
        
        Receiver rcv = new Receiver("Receiver", 2077);
        Sender snd = new Sender("127.0.0.1", 2078, usr.getPseudonym());
        MulticastReceiver multiRcv = new MulticastReceiver("225.0.0.3", 1138, usr);
        MulticastSender multiSnd = new MulticastSender("225.0.0.3", 1138, usr);
        
        NetInterface netInterface = new NetInterface(rcv, snd, multiRcv, multiSnd);
        
        netInterface.BroadcastMessage();
        netInterface.SendMessage();
    }
}
