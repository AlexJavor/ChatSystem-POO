import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Java8HttpClient {

    private static HttpURLConnection con;

    public static void main(final String[] args) throws IOException {

        String url = "http://192.168.1.3:8080/Salinas-Javornik-Servlets/myservlet";

        String postResult = POSTRequest("Esteban", "d8:cb:8a:7e:88:db", "connected", url);

        GETRequest(url);

        System.out.println(postResult);
    }

    private static void GETRequest(String url) {

        try {

            URL myurl = new URL(url);
            con = (HttpURLConnection) myurl.openConnection();

            con.setRequestMethod("GET");

            StringBuilder content;

            try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

                String line;
                content = new StringBuilder();

                while ((line = in.readLine()) != null) {

                    content.append(line);
                    content.append(System.lineSeparator());
                }
            }

            WriteHTMLinFile(content.toString());

            System.out.println(content.toString().trim());

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

            con.disconnect();
        }
    }

    private static String POSTRequest(String name, String mac, String status, String urlString) {

        Map<Object, Object> data = new HashMap<>();
        data.put("pseudo", name);
        data.put("macAdr", mac);
        data.put("status", status);

        String urlParameters = buildFormDataFromMap(data);

        HttpURLConnection connection = null;

        try {
            // Create connection
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            connection.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
            connection.setRequestProperty("Content-Language", "en-US");

            connection.setUseCaches(false);
            connection.setDoOutput(true);

            // Send request
            DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
            wr.writeBytes(urlParameters);
            wr.close();

            // Get Response
            InputStream is = connection.getInputStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            StringBuilder response = new StringBuilder(); // or StringBuffer if Java version 5+
            String line;
            while ((line = rd.readLine()) != null) {
                response.append(line);
                response.append('\r');
            }
            rd.close();
            return response.toString();
        } catch ( Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }

    private static String buildFormDataFromMap(final Map<Object, Object> data) {
        final StringBuilder builder = new StringBuilder();
        for (final Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            try {
                builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8.toString()));
                builder.append("=");
                builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8.toString()));
            } catch (final UnsupportedEncodingException e) {
            }
        }
        return builder.toString();
    }

    private static void WriteHTMLinFile(final String content) {
        try {
            final BufferedWriter writer = new BufferedWriter(new FileWriter("./HTML-File"));
            writer.write(content);
            writer.close();
        } catch (final IOException e) {
        }
    }
}