/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chatsystem;

import java.awt.image.BufferedImage;
import java.net.*;
import java.io.*;
import java.util.*;
import java.nio.*;
import javax.imageio.ImageIO;
/**
 *
 * @author salinasg
 */
public class ImageMessage extends Message {
    
    public ImageMessage(String pseudo, String path){
        super(pseudo);
        try{
            BufferedImage image = ImageIO.read(new File(path));
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            ImageIO.write(image, "jpg", byteArrayOutputStream);
        } catch (Exception e){
            System.out.println("ERROR : look at ImageMessage - " + e);
            System.exit(1);
        }
    }
    
}
