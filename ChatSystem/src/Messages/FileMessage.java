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
import java.nio.file.Files;


/**
 *
 * @author salinasg
 */
public class FileMessage extends Message {
    
    public FileMessage(String pseudo, String path) throws Exception {
        super(pseudo);
        this.msgType = 'f';
        
        try{
            File file = new File(path);
            this.payloadSize = (int)(file.length());
            this.payload = Files.readAllBytes(file.toPath());
        } catch (IOException e) {
            System.out.println("ERROR : look at ImageMessage - " + e);
            throw(e);
        }
        
    }
}
