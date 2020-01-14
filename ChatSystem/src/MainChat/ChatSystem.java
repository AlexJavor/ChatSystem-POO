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
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author javornik
 */
public class ChatSystem {

    /**
     * @param args the command line arguments
     */
    private static final int unicastPort = 2077;
    private static final int multicastPort = 1138;
    private static final String multicastAddr = "225.192.0.1";
    
    public static boolean repeatedPseudo = true;
    public static User myUser = null;
    
    public static void main(String[] args) {
        try{
            // *** Create current user with the obtained Pseudonym, IP Address and MAC Address *** //
            // myPseudonym null when starting
            String myPseudonym = null;
            InetAddress myIpAddr = getMyLocalIPAddress();
            //InetAddress myIpAddr = InetAddress.getByName("192.168.1.1"); // Tested locally
            String myMacAddr = getMyMacAddress(myIpAddr);
          
            myUser = new User(myPseudonym, myIpAddr, myMacAddr);
            
            System.out.println("System IP Address  : " + (myIpAddr.getHostAddress()).trim());
            System.out.println("System MAC Address : " + myMacAddr);
            
            String path = System.getProperty("user.dir");        
            System.out.println("Working Directory = " + path);
            
            // Start GUI
            LoginGUI UI = new LoginGUI();
            UI.setVisible(true);
            
            // Define netInterface
            NetInterface netInterface = null;
            
            int attemptNumber = 0;
            
            while(repeatedPseudo){
                // We set it by default to false. If it is true it will be changed before finishing the loop
                repeatedPseudo = false;
                // Counting attempts
                attemptNumber++;
                System.out.println("\nChecking pseudonym - attmept number " + attemptNumber);
                
                //Get pseudonym from GUI
                myPseudonym = UI.getPseudonymFromInput();
                // Wait until a pseudo is introduced
                while(myPseudonym == null){
                    myPseudonym = UI.getPseudonymFromInput();
                    Thread.sleep(1);
                }
                System.out.println("New pseudo: " + myPseudonym);
                        

               // The first attempt you create everything, after that we just change the pseudonym
                if(attemptNumber == 1){
                    // Create network interface
                    netInterface = new NetInterface(myUser, unicastPort, multicastAddr, multicastPort);  
                }
                myUser.setPseudonym(myPseudonym);
                
                // Send my user's Pseudonym, IP Address and MAC Address to all active users
                netInterface.sendMulticastMessage("online");
                
                
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
                
                System.out.println("Starting conversation");
                Sender snd = new Sender(InetAddress.getByName("10.1.5.153"), 2077, netInterface.getActiveUsers());
                netInterface.sendMessageToUser(snd);
            }
        } catch (InterruptedException | SocketException e) {} catch (UnknownHostException ex) {
            Logger.getLogger(ChatSystem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static InetAddress getMyLocalIPAddress() throws SocketException {
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
   
    private static String getMyMacAddress(InetAddress myIpAddr){
        StringBuilder sb = new StringBuilder();
        try{
            NetworkInterface network = NetworkInterface.getByInetAddress(myIpAddr);

            byte[] mac = network.getHardwareAddress();

            for (int i = 0; i < mac.length; i++) {
                    sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? ":" : ""));		
            }
            
        } catch (SocketException e) {}
        return sb.toString();
    }
}
