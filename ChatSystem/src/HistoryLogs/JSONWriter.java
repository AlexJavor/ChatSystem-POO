package HistoryLogs;

import java.io.*;

import org.json.simple.*;
import org.json.simple.parser.*;
/**
 * Writer : Class used to write in a JSON File already initialized
 * The minimum content of the file is { "LogMessage" : [] }
 */
public class JSONWriter {

    private static final String UNCHECKED = "unchecked";

    /**
     * Reads the JSON file and returns an ArrayList with MessageLogs (the ones it found on the file)
     * 
     * @param filePath The path to the JSON file
     * @param msg The message to be written in the JSON file
     */
    @SuppressWarnings(UNCHECKED)
    public static void write(String filePath, MessageLog msg){
        
        JSONParser jsonParser = new JSONParser();
        
        try (FileReader fReader = new FileReader(filePath)) {
            Object obj = jsonParser.parse(fReader);
            fReader.close();

            JSONObject jsonLogMessage = (JSONObject) obj;
            JSONArray msgArray = (JSONArray) jsonLogMessage.get("LogMessage");

            JSONObject jsonSender = new JSONObject();
            jsonSender.put("MACAddress", msg.getSender().getMACAddress());
            jsonSender.put("IPAddress", msg.getSender().getIPAddress().toString().replace("/", ""));
            jsonSender.put("Pseudonym", msg.getSender().getPseudonym());

            JSONObject jsonReceiver = new JSONObject();
            jsonReceiver.put("MACAddress", msg.getReceiver().getMACAddress());
            jsonReceiver.put("IPAddress", msg.getReceiver().getIPAddress().toString().replace("/", ""));
            jsonReceiver.put("Pseudonym", msg.getReceiver().getPseudonym());

            JSONObject jsonDate = new JSONObject();
            String date = msg.getDate().getYear() + "-" + msg.getDate().getMonth() + "-" + msg.getDate().getDay();
            String time = msg.getDate().getHour() + ":" + msg.getDate().getMinute();
            jsonDate.put("Day", date);
            jsonDate.put("Time", time);

            JSONObject jsonMessageContent = new JSONObject();
            jsonMessageContent.put("Type", msg.getType());
            jsonMessageContent.put("Sender", jsonSender);
            jsonMessageContent.put("Receiver", jsonReceiver);
            jsonMessageContent.put("Content", msg.getContent());
            jsonMessageContent.put("Date", jsonDate);

            JSONObject jsonMessage = new JSONObject();
            jsonMessage.put("Message", jsonMessageContent);

            msgArray.add(jsonMessage);
            jsonLogMessage.put("LogMessage", msgArray);

            FileWriter fWriter = new FileWriter(filePath);
            fWriter.write(toReadableJSONString(jsonLogMessage));
            fWriter.flush();
            fWriter.close();

            System.out.println("Message added succesfully !\n");
        } catch (Exception e) {
            System.out.println("Error when adding message to JSON file !\n");
            e.printStackTrace();
        }
    }
    
    /**
     * Transforms a LogMessage JSON Object into a readable string
     * @param jsonLog Object to be converted into string 
     * @return A readable string representing a JSON Object
     */
    private static String toReadableJSONString (JSONObject jsonLog){
        String nonReadable = jsonLog.toJSONString();

        String nr1 = nonReadable.replace("{\"LogMessage\":[", "{\"LogMessage\":\n\t[");
        String nr2 = nr1.replace("{\"Message\":", "\n\t\t{\"Message\":");
        String nr3 = nr2.replace("{\"Sender\"", "\n\t\t\t{\"Sender\"");
        String nr4 = nr3.replace("\"Type", "\n\t\t\t\"Type");
        String nr5 = nr4.replace("\"Content", "\n\t\t\t\"Content");
        String nr6 = nr5.replace("\"Receiver", "\n\t\t\t\"Receiver");
        String nr7 = nr6.replace("\"Date", "\n\t\t\t\"Date");
        String nr8 = nr7.replace("{\"MAC", "\n\t\t\t\t{\"MAC");
        String nr9 = nr8.replace("\"IP", "\n\t\t\t\t\"IP");
        String nr10 = nr9.replace("\"Pseu", "\n\t\t\t\t\"Pseu");
        String nr11 = nr10.replace("{\"Time", "\n\t\t\t\t{\"Time");
        String nr12 = nr11.replace("\"Day", "\n\t\t\t\t\"Day");
        String nr13 = nr12.replace("}}}]}", "}\n\t\t\t}\n\t\t}\n\t]\n}");
        String readable = nr13.replace("}}},","}\n\t\t\t}\n\t\t},");

        return readable;
    }
}