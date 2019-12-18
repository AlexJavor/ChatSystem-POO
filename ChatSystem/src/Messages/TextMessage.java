/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.*;

/**
 *
 * @author salinasg
 */
public class TextMessage extends Message {
    
    public TextMessage(String pseudo, String payload){
        super(pseudo);
        this.payload = payload.getBytes();
        this.payloadSize = payload.length();
        this.msgType = 't';
    }
    
}
