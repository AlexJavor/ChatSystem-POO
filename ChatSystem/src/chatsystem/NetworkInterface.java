/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import java.net.*;
import java.io.*;
import java.util.*;
/**
 *
 * @author salinasg
 */
public class NetworkInterface{
    private Receiver server;
    private Sender client;
    private Thread threadReceiver;

    
    public NetworkInterface(Receiver serv, Sender cli){
        this.server = serv;
        this.client = cli;
        this.threadReceiver = new Thread(this.server);
        this.threadReceiver.start();
    }
    
    public void SendMessage(){
        this.client.start();
    }
    
}
