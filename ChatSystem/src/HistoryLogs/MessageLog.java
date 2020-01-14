package HistoryLogs;

import MainChat.User;

/**
 * MessageLog : Class created to store all the info of a message
 */
public class MessageLog {

    private String msgType;
    
    private User sender;

    private User receiver;

    private String content;

    // Should be changed from date to calendar
    private DateLog date;

    /**
     * Constructor
     * @param type The type of the message (image, text, file)
     * @param snd The person who sent the message
     * @param rcv The person who received the message
     * @param content The content of the message (a string for a text message, a path for an image or a file)
     * @param date The date at which the message was sent (it is a DateLog - watch its documentation)
     */
    public MessageLog(String type, User snd, User rcv, String content, DateLog date){
        this.msgType = type;
        this.sender = snd;
        this.receiver = rcv;
        this.content = content;
        this.date = date;
    }

    /* Getters */
    /**
     * Get the type of the message
     * @return The type of the message as a character (t, i, f)
     */
    public String getType(){ return this.msgType; }

    /**
     * Get the sender of the message
     * @return The sender of the message
     */
    public User getSender(){ return this.sender; }

    /**
     * Get the receiver of the message
     * @return The receiver of the message
     */
    public User getReceiver(){ return this.receiver; }

    /**
     * Get the content of the message
     * @return The content of the message (a text if it is a text message, a path if it is a file or an image)
     */
    public String getContent(){ return this.content; }

    /**
     * Get the date of the message
     * @return The date at which the message was sent
     */
    public DateLog getDate(){ return this.date; }

    /**
     * Changes a MessageLog into a printable string
     * @return A String containing all the information of the MessageLog
     */
    @Override
    public String toString() {
        User sender = this.sender;
        User receiver = this.receiver;
        String tp = this.msgType;
        String type;
        switch (tp) {
            case "t":
                type = "text";
                break;
            case "i":
                type = "image";
                break;
            case "f":
                type = "file";
                break;
            default:
                type = "error";
                break;
        }
        String content = this.content;
        DateLog date = this.date;

        String senderInfo = sender.getPseudonym() + " sent a message from IP @ : " + sender.getIPAddress() + " and MAC @ : " + sender.getMACAddress() + " to";
        String receiverInfo = receiver.getPseudonym() + " at IP @ : " + receiver.getIPAddress() + " and MAC @ : " + receiver.getMACAddress() + ".";
        String messageInfo = "The message is a " + type + " message. It was sent on " + date + ". Its content is : " + content + " ";

        return senderInfo + "\n" + receiverInfo + "\n" + messageInfo;
    }
}