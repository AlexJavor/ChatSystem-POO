package HistoryLogs;

import java.io.*;

import org.json.simple.*;
/**
 * JSONGenerator : Class used to generate an empty JSON file
 Used when a new session is stablished with someone you've never talked before
 */
public class JSONGenerator {

    private static final String UNCHECKED = "unchecked";

    /**
     * Create a new empty JSON file with only {"LogMessage":[]} in it
     * 
     * @param filePath The path to the JSON file
     * @param fileName The name of the JSON file
     */
    @SuppressWarnings(UNCHECKED)
    public static void generate(String filePath, String fileName){
        
        try {
            if (new File(filePath + fileName).exists()){
                System.out.println("File already exists !!\n");
            } else {
                JSONObject jsonLogMessage = new JSONObject();
                JSONArray jsonMsgArray = new JSONArray();

                jsonLogMessage.put("LogMessage", jsonMsgArray);

                FileWriter fWriter = new FileWriter(filePath + fileName);
                fWriter.write(jsonLogMessage.toJSONString());
                fWriter.flush();
                fWriter.close();

                System.out.println("JSON file generated succesfully !!");
            }
        } catch (IOException e) {
        }

    }
}