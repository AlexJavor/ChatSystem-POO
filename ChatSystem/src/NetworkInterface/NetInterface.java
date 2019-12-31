/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;
import MainChat.*;
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
    private MulticastReceiver multiRcv;
    private MulticastSender multiSnd;
    private Thread threadReceiver;
    private Thread threadMulticastReceiver;

    
    public NetInterface(Receiver serv, Sender cli, MulticastReceiver multiRcv ,MulticastSender multiSnd){
        this.client = cli;
        this.server = serv;
        this.multiRcv = multiRcv;
        this.multiSnd = multiSnd;
        this.threadReceiver = new Thread(this.server);
        this.threadReceiver.start();
        this.threadMulticastReceiver = new Thread(this.multiRcv);
        this.threadMulticastReceiver.start();
    }
    /*
    public void NewSender(User activeUser){
        
    }*/
    public void BroadcastMessage(){
        this.multiSnd.multicast();
    }
    
    public void SendMessage() {
        this.client.start();
    }
    
}
