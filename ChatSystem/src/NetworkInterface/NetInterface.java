/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;
import GUI.ChatGUI;
/**
 *
 * @author salinasg
 */
public class NetInterface{
    
    private ActiveUsers activeUserList;
    private Receiver rcv;
    private MulticastReceiver multiRcv;
    private MulticastSender multiSnd;
    private Thread threadReceiver;
    private Thread threadMulticastReceiver;

    private final int unicastPort = 2077;
    private final int multicastPort = 1138;
    private final String multicastAddr = "225.192.0.1";
    
    public NetInterface(ChatGUI chatGUI){
        
        this.activeUserList = new ActiveUsers(chatGUI);
        this.rcv = new Receiver(this.unicastPort, activeUserList);
        this.multiSnd = new MulticastSender(this.multicastAddr, this.multicastPort);
        this.multiRcv = new MulticastReceiver(this.multicastAddr, this.multicastPort, activeUserList, this.unicastPort);
        startReceiver();
        startMulticastReceiver();
    }
    
    // Getters
    public ActiveUsers getActiveUsers() { return this.activeUserList; }
    public MulticastSender getMulticastSender() { return this.multiSnd; }
    
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
