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
abstract public class Message {
    
    private String pseudonym;
    protected char msgType;
    protected byte[] payload;
    protected int payloadSize;
    
    public Message(String pseudo){
        this.pseudonym = pseudo;
    }
    
    public String getPseudonym(){ return this.pseudonym; }
    private byte[] getBytesMsgType(){ return ByteBuffer.allocate(2).putChar(this.msgType).array(); }
    private byte[] getBytesPayloadSize(){ return ByteBuffer.allocate(4).putInt(this.payloadSize).array(); }
    private byte[] getBytesPseudonymSize(){ return ByteBuffer.allocate(4).putInt(this.getPseudonym().length()).array(); }
    private byte[] getBytesPseudonym(){ return this.pseudonym.getBytes(); }
    
    /**
     * 
     * @return Returns the message with the message Type, the Pseudonym of the sender, and the payload
     * as a byte array
     */
    public byte[] getBytesMessage(){
        // Creation of all the byte arrays used to create the full message
        byte[] MsgTypeBytes = getBytesMsgType();
        byte[] msgPseudoSize = getBytesPseudonymSize();
        byte[] msgPseudo = getBytesPseudonym();
        byte[] msgPayloadSize = getBytesPayloadSize();
        byte[] msgPayload = this.payload;
        
        // Calculate the position of the field where the information should be written
        int pseudoSizeStart = MsgTypeBytes.length;
        int pseudoStart = pseudoSizeStart + msgPseudoSize.length;
        int payloadSizeStart = pseudoStart + msgPseudo.length;
        int payloadStart = payloadSizeStart + msgPayloadSize.length;
        
        // Creation of the message in a byte buffer (by joining all the info)
        byte[] fullMsg = new byte[payloadStart + msgPayload.length];
        System.arraycopy(MsgTypeBytes,   0, fullMsg, 0,                MsgTypeBytes.length  );
        System.arraycopy(msgPseudoSize,  0, fullMsg, pseudoSizeStart,  msgPseudoSize.length );
        System.arraycopy(msgPseudo,      0, fullMsg, pseudoStart,      msgPseudo.length     );
        System.arraycopy(msgPayloadSize, 0, fullMsg, payloadSizeStart, msgPayloadSize.length);
        System.arraycopy(msgPayload,     0, fullMsg, payloadStart,     msgPayload.length    );
        
        return fullMsg;
    }
}
