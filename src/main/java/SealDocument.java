import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class SealDocument {


    public String sealDocument(StringBuilder token, String documentAsJSON) throws IOException {

        /**
         * we need a URL where we want to send our POST request, so we can seal our document
         */
        URL url = new URL("https://api.scribital.com/v1/seal");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        /**
         * we need to set the Authorization and this is the Token we created at the beginning
         */
        connection.setRequestProperty("Authorization","Bearer "+ token.toString());
        connection.setRequestProperty("Content-Type","application/json");

        /**
         * to seal the document we use a POST request
         */
        connection.setRequestMethod("POST");
        connection.setDoOutput(true);

        /**
         * now the connection is made, so we can send our JSON request body with its content
         */
        try(OutputStream os = connection.getOutputStream()){
            byte[] input = documentAsJSON.getBytes(StandardCharsets.UTF_8);
            os.write(input,0, input.length);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String responseData = "";
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
                responseData += responseLine;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }
}
