import java.io.*;
import java.util.ArrayList;

public class GroupToken extends IToken{
    private ArrayList<Token> tokens;


    public void addToken(Token t) {
        this.tokens.add(t);
    }

    public Token findToken(String token) {
        for (Token t : tokens) {
            if (t.matchCheck(token))
                return t;
        }
        return null;
    }

    private void readTokensFile(String fileName) {
        String line = null;
        fileName = "src\\tokens\\"+fileName+".txt";
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(fileName)));
            String[] token;
            while ((line = bufferedReader.readLine()) != null) {
                token = line.split(" ");
                addToken(new Token(token[0], token[1]));
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public GroupToken(String name, String regEx) {
        super(name,regEx);
        this.tokens = new ArrayList<>();
        readTokensFile(name);
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }


}
