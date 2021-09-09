import com.google.gson.Gson;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Base64;

public class Main {
    public static void main(String[] args) throws IOException {
        /**
         * first we need a token that is created and added as Authentication for the Skribble API
         */
        Token t = new Token();
        StringBuilder token = t.createToken();

        /**
         * We need the document path to encode it to base 64 and add it as content in the API request
         * you need to specify your path running the jar file, or you replace the args[0] with it and run it again
         * example path = "C:\\User\\Desktop\Hello.pdf"
         */
        String encodedPDFContent = encodePDFToBase64(args[0]);

        /**
         * create the document with all its necessary attributes to make a correct JSON request body
         * you can change the position as you like, also the account name can be changed, but you need a specific Seal form Skribble to
         * ais_demo_seal just illustrates that it worked with a demo seal
         */
        Document document = new Document(encodedPDFContent,"ais_demo_seal", new VisualSignature(new Position(20,40,100,100,"0")));

        /**
         * convert the Document into a JSON file, so we can do a Post request
         */
        Gson gson = new Gson();
        String documentAsJSON = gson.toJson(document,Document.class);

        /**
         * pass the JSON file and the token to the SealDocument Object to seal it finally
         */
        SealDocument sealDocument = new SealDocument();
        String documentID = sealDocument.sealDocument(token,documentAsJSON);
        System.out.println(documentID);

    }

    /**
     * this code enables us to convert our PDF into a base64 content
     */
    public static String encodePDFToBase64(String documentPath) throws IOException {
        File file = new File(documentPath);
        byte[] bytes = Files.readAllBytes(file.toPath());
        return Base64.getEncoder().encodeToString(bytes);
    }
}
