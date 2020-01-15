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
     */
    
    public static User myUser = null;
    public static NetInterface netInterface = null;
    
    public static void main(String[] args) {
        try{
            // *** Create current user with the obtained Pseudonym, IP Address and MAC Address *** //
            // myPseudonym null when starting
            String myPseudonym = null;
            InetAddress myIpAddr = getMyLocalIPAddress();
            //InetAddress myIpAddr = InetAddress.getByName("192.168.1.1"); // Tested locally
            String myMacAddr = getMyMacAddress(myIpAddr);
            
            System.out.println("System IP Address  : " + (myIpAddr.getHostAddress()).trim());
            System.out.println("System MAC Address : " + myMacAddr);
            
            // Create MY User
            myUser = new User(myPseudonym, myIpAddr, myMacAddr);
            
            // Start GUI
            Controller controller = new Controller();
            PseudonymGUI UI = new PseudonymGUI(controller);
            UI.setVisible(true);
            
            
            
        } catch (SocketException e) {}
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
            
        } catch (Exception e) {e.printStackTrace();}
        return sb.toString();
    }
}
