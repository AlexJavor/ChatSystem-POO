/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;
import MainChat.*;
import static MainChat.ChatSystem.myUser;
/**
 *
 * @author salinasg
 */
public class NetInterface{
    
    private int unicastPort;
    private String multicastAddr;
    private int multicastPort;
    
    private ActiveUsers activeUserList;
    private Receiver rcv;
    private MulticastReceiver multiRcv;
    private MulticastSender multiSnd;
    private Thread threadReceiver;
    private Thread threadMulticastReceiver;

    
    public NetInterface(User usr, int unicastPort, String multicastAddr, int multicastPort){
        
        this.unicastPort = unicastPort;
        this.multicastAddr = multicastAddr;
        this.multicastPort = multicastPort;
        
        this.activeUserList = new ActiveUsers();
        this.rcv = new Receiver(this.unicastPort, activeUserList);
        this.multiSnd = new MulticastSender(this.multicastAddr, this.multicastPort);
        this.multiRcv = new MulticastReceiver(this.multicastAddr, this.multicastPort, activeUserList, this.unicastPort);
        startReceiver();
        startMulticastReceiver();
    }
    
    // Getters
    public ActiveUsers getActiveUsers() { return this.activeUserList; }
    
    // Methods
    private void startReceiver(){
        this.threadReceiver = new Thread(this.rcv);
        this.threadReceiver.setName("Receiver");
        this.threadReceiver.start();
    }
    
    private void startMulticastReceiver(){
        this.threadMulticastReceiver = new Thread(this.multiRcv);
        this.threadReceiver.setName("MulticastReceiver");
        this.threadMulticastReceiver.start();
    }
    
    public void sendMulticastMessage(String status){
        this.multiSnd.Send(status);
    }
    
    public void sendMessageToUser(Sender snd) {
        snd.send();
    }
}
