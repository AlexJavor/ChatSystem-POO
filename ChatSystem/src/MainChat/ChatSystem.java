/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MainChat;

import NetworkInterface.*;
import GUI.*;
import java.net.*;
import java.util.Enumeration;
/**
 *
 * @author javornik
 */
public class ChatSystem {

    /**
     * @param args the command line arguments
     */ 
    public static boolean repeatedPseudo = true;
    public static int attempt = 0;

    
    public static void main(String[] args) {
        try{
            // Get IP and MAC Addresses
            //InetAddress myIpAddr = getLocalAddress();
            InetAddress myIpAddr = InetAddress.getByName("192.168.1.1"); // Tested locally
            String myMacAddr = getMacAddress(myIpAddr);
            System.out.println("System IP Address : " + (myIpAddr.getHostAddress()).trim());
            
            // Start GUI
            LoginGUI UI = new LoginGUI();
            UI.setVisible(true);
            
            User usr;
            ActiveUsers activeUserList;
            Receiver rcv;
            Sender snd;
            MulticastReceiver multiRcv;
            MulticastSender multiSnd;
            NetInterface netInterface = null;
            
            while(repeatedPseudo){
                // We set it by default to false. If it is true it will be changed before finishing the loop
                repeatedPseudo = false;
                // Counting attempts
                attempt++;
                System.out.println("\nChecking pseudonym - attmept number " + attempt);
                
                //Get pseudonym from GUI
                String myPseudonym = UI.getPseudonymFromInput();
               
                while(myPseudonym == null){
                    myPseudonym = UI.getPseudonymFromInput();
                    Thread.sleep(1);
                }
                
                System.out.println("New pseudo: " + myPseudonym);
                        
                // Create current user with the obtained Pseudonym, IP Address and MAC Address
                usr = new User(myPseudonym, myIpAddr, myMacAddr);

                activeUserList = new ActiveUsers();
                
                rcv = new Receiver(2077, activeUserList, usr.getPseudonym());
                multiRcv = new MulticastReceiver("225.192.0.1", 1138, usr, activeUserList);
                snd = new Sender("192.168.1.2", 2077, usr.getPseudonym());
                multiSnd = new MulticastSender("225.192.0.1", 1138, usr);

                netInterface = new NetInterface(rcv, snd, multiRcv, multiSnd);  
                netInterface.BroadcastMessage();
                
                
                // Waiting to receive all conections (it can change)
                try { Thread.sleep(400);} 
                catch (InterruptedException ex) {}
                
                System.out.println("repeatedPseudo : " + repeatedPseudo);
                
                // Waiting to clear GUI message "Pseudo already in use"
                while(myPseudonym != null && repeatedPseudo){
                    myPseudonym = UI.getPseudonymFromInput();
                    //System.out.println("RepeatedPseudo : " + repeatedPseudo + " | Pseudonym : " + myPseudonym);
                    Thread.sleep(1);
                }
                
                System.out.println("End while");
                //netInterface.SendMessage();
            }
            
            
        } catch (InterruptedException | UnknownHostException e) {}


    }
    
    public static InetAddress getLocalAddress() throws SocketException {
        Enumeration<NetworkInterface> ifaces = NetworkInterface.getNetworkInterfaces();
        while( ifaces.hasMoreElements() )
        {
            NetworkInterface iface = ifaces.nextElement();
            Enumeration<InetAddress> addresses = iface.getInetAddresses();

            while( addresses.hasMoreElements() )
            {
              InetAddress addr = addresses.nextElement();
              if( addr instanceof Inet4Address && !addr.isLoopbackAddress() )
              {
                return addr;
              }
            }
        }

        return null;
    }
   
    
    private static String getMacAddress(InetAddress myIpAddr){
        StringBuilder sb = new StringBuilder();
        try{
            NetworkInterface network = NetworkInterface.getByInetAddress(myIpAddr);

            byte[] mac = network.getHardwareAddress();

            for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));		
            }
            
        } catch (Exception e) {e.printStackTrace();}
        return sb.toString();
    }

    private static void getThreadReceiver() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
