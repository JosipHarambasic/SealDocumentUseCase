import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class Token {
    StringBuilder Token;

    /**
     * we create a token, this is necessary to continue and use the API calls
     */
    public StringBuilder createToken() throws IOException {
        /**
         * we need the url where we want to make an API call
         */
        URL url = new URL("https://api.scribital.com/v1/access/login");
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        /**
         * set the required type, in this case it's a POST request
         */
        connection.setRequestMethod("POST");

        /**
         * set the type of content, here we use a JSON type
         */
        connection.setRequestProperty("Content-Type", "application/json; utf-8");
        connection.setDoOutput(true);

        /**
         * set the Timeout, will disconnect if the connection did not work, avoid infinite waiting
         */
        connection.setConnectTimeout(6000);
        connection.setReadTimeout(6000);

        /**
         * create the request body
         * here we need only the username and the api-key to make a POST request and to receive a valid token for the Skribble API
         */
        String jsonInputString = "{\"username\": \"api_demo_skribble_d901_0\", \"api-key\":\"118d6d49-1415-4f8e-bd16-2a0ef03beaf9\"}";
        try(OutputStream os = connection.getOutputStream()){
            byte[] input = jsonInputString.getBytes(StandardCharsets.UTF_8);
            os.write(input,0, input.length);
        }

        /**
         * read the response from the Skriblle API which is a token in this case
         */
        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
                Token = response;
            }
        }
        return Token;
    }
}
