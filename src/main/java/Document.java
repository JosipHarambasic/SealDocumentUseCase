public class Document {

    String content;
    String account_name;
    VisualSignature visual_signature;

    public Document(String content,String account_name, VisualSignature visual_signature){
        this.content = content;
        this.account_name = account_name;
        this.visual_signature = visual_signature;
    }
}
