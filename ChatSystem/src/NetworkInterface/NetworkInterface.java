/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;
import chatsystem.*;
import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author salinasg
 */
public class NetInterface{
    private Receiver server;
    private Sender client;
    private Thread threadReceiver;

    
    public NetInterface(Receiver serv, Sender cli){
        this.client = cli;
        this.server = serv;
        this.threadReceiver = new Thread(this.server);
        this.threadReceiver.start();
    }
    /*
    public void NewSender(User activeUser){
        
    }*/
    
    public void SendMessage() {
        this.client.start();
    }
    
}
