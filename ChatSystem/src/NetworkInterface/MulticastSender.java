/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package NetworkInterface;

import java.net.*;
import java.io.IOException;
import java.net.DatagramPacket;

import java.net.MulticastSocket;

/**
 *
 * @author javornik
 */
public class MulticastSender {
   
    private DatagramSocket socket;
    private InetAddress group;
    private byte[] buf;
 
    public void multicast(
      String multicastMessage) throws IOException {
        socket = new DatagramSocket();
        group = InetAddress.getByName("230.0.0.0");
        buf = multicastMessage.getBytes();
 
        DatagramPacket packet 
          = new DatagramPacket(buf, buf.length, group, 4446);
        socket.send(packet);
        socket.close();
    }
}
