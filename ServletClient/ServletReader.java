/**
 * ServletReader
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ServletReader {

    public static void main(String[] args) {

        try (FileReader reader = new FileReader("./HTML-File");
             BufferedReader br = new BufferedReader(reader)) {

            // read line by line
            String line = "";
            while (!line.equals("<!--")){
                if ((line = br.readLine()) != null) {
                    continue;
                }
                else {
                    throw new IOException();
                }
            }
            while ((line = br.readLine()) != null && !line.equals("-->")) {
                String[] cutted_line = line.split("-");
                System.out.println("Pseudonym: " + cutted_line[0] + " | IP Address: " + cutted_line[1] + " | MAC Address: " + cutted_line[2]);
            }

        } catch (IOException e) {
            System.err.format("IOException: %s%n", e);
        }
    }
}