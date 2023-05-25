import java.util.List;

public class Document {
    private int documentID;
    private List<String> tokens;

    public Document(int documentID, List<String> tokens) {
        this.documentID = documentID;
        this.tokens = tokens;
    }

    public Document(int documentID) {
        this.documentID = documentID;
    }

    public int getDocumentID() {
        return documentID;
    }

    public void setDocumentID(int documentID) {
        this.documentID = documentID;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public void setTokens(List<String> tokens) {
        this.tokens = tokens;
    }

}
