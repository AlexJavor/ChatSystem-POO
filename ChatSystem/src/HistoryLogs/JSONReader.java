package HistoryLogs;

/**
 * Reader : Class created to read a JSON File
 */
import java.io.*;
import java.util.*;

import org.json.simple.*;
import org.json.simple.parser.*;

import MainChat.User;

public class JSONReader {

    private static final String UNCHECKED = "unchecked";

    /**
     * Reads the JSON file and returns an ArrayList with MessageLogs (the ones it found on the file)
     * 
     * @param filePath The path to the JSON file
     * @return An ArrayList with all the messages in the history
     */
    @SuppressWarnings(UNCHECKED)
    public static ArrayList<MessageLog> read(String filePath) {
        
        ArrayList<MessageLog> logMessages = new ArrayList<>();

        JSONParser jsonParser = new JSONParser();
        try (FileReader fReader = new FileReader(filePath)) {
            Object obj = jsonParser.parse(fReader);

            JSONObject jsonObj = (JSONObject) obj;
            JSONArray msgArray = (JSONArray) jsonObj.get("LogMessage");

            Iterator<JSONObject> itMessages = msgArray.iterator();

            while (itMessages.hasNext()){
                MessageLog msg = parseMsg( (JSONObject) ((itMessages.next()).get("Message")) );
                logMessages.add(msg);
            }

            fReader.close();

        } catch (Exception e) {
        }
        
        return logMessages;
    }

    private static MessageLog parseMsg(JSONObject msg){
        MessageLog msge;

        String type = (String) msg.get("Type");
        String cont = (String) msg.get("Content");
        User sndr = parsePrsn( (JSONObject) msg.get("Sender") );
        User rcvr = parsePrsn( (JSONObject) msg.get("Receiver") );
        DateLog date = parseDate( (JSONObject) msg.get("Date") );

        msge = new MessageLog(type, sndr, rcvr, cont, date);
        return msge;
    }

    private static User parsePrsn(JSONObject prsn){
        String macAddr = (String) prsn.get("MACAddress");
        String pseudo = (String) prsn.get("Pseudonym");
        String ipAddr = (String) prsn.get("IPAddress");

        User p = new User(macAddr, ipAddr, pseudo);
        return p;
    }
    
    private static DateLog parseDate(JSONObject date){
        String dt = (String) date.get("Day");
        String tm = (String) date.get("Time");

        DateLog d = new DateLog(dt, tm);
        return d;
    }
}