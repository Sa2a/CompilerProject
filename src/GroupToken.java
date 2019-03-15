import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GroupToken {
    private String name;
    private String regEx;
    private ArrayList<Token> tokens;


    public void addToken(Token t) {
        this.tokens.add(t);
    }

    public GroupToken(String name, String regEx, ArrayList<Token> tokens) {
        this.name = name;
        this.regEx = regEx;
        this.tokens = tokens;
    }

    public GroupToken(String name, String regEx) {
        this.name = name;
        this.regEx = regEx;
        this.tokens = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegEx() {
        return regEx;
    }

    public void setRegEx(String regEx) {
        this.regEx = regEx;
    }

    public ArrayList<Token> getTokens() {
        return tokens;
    }

    public void setTokens(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public boolean matchCheck(String input) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find())
            if (input.length() == matcher.group().length())
                return true;
        return false;
    }

    public Token findToken(String token) {
        for (Token t : tokens) {
            if (t.matchCheck(token))
                return t;
        }
        return null;
    }

    public void readTokensFile(String fileName) {
        String line = null;
        try {
            FileReader fileReader = new FileReader(new File(fileName));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String[] token;
            while ((line = bufferedReader.readLine()) != null) {
                token = line.split(" ");
                addToken(new Token(token[0], token[1]));
            }
            bufferedReader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
