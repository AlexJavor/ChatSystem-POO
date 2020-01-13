/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Messages;

/**
 *
 * @author javornik
 */
public class StatusMessage extends Message {
        public StatusMessage(String pseudo, String payload){
            super(pseudo);
            this.payload = payload.getBytes();
            this.payloadSize = payload.length();
            this.msgType = 's';
        }
}
