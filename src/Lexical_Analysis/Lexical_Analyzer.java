package Lexical_Analysis;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Lexical_Analyzer {

    private final String ID = "\\b[a-zA-Z_]\\w*\\b";
    private final String INTEGRAL_LITERAL = "\\b\\d+\\b";
    private final String FLOAT_LITERAL = "\\b\\d+\\.\\d+\\b";
    private final String STRING_LITERAL = "\".*\"";
    private final String CHAR_LITERAL = "\'.?\'";
    private final String MULTI_COMMENT = "/\\*([^*]|\\*+[^/])*\\*+/";
    private final String SINGLE_COMMENT = "//.*$";
    private final String SPECIAL_SYMBOLS = ">=|<=|!=|[=<>&\\|]{2}|[\\p{Punct}&&[^\"\\$':\\?@_`]]";
    //"[=<>&\\|]{2}
    private GroupToken[] groupToken;
    // keywords and identifiers

    private String regEx;

    public Lexical_Analyzer() {
        groupToken = new GroupToken[4];
        groupToken[0] = new GroupToken("identifier", ID);
        // constants
        groupToken[1] = new GroupToken("constant",  FLOAT_LITERAL+ '|' + INTEGRAL_LITERAL);
        // strings
        groupToken[2] = new GroupToken("strings", STRING_LITERAL + '|' + CHAR_LITERAL + '|' + MULTI_COMMENT + '|' + SINGLE_COMMENT);
        // specialSymbols & Operators
        groupToken[3] = new GroupToken("specialSymbol", SPECIAL_SYMBOLS);

        regEx = "\\s|" + groupToken[0].getRegEx() + '|' + groupToken[1].getRegEx() + '|' + groupToken[2].getRegEx() + '|' + groupToken[3].getRegEx();

    }


    public void tokenizing(String file) {
        Pattern pattern = Pattern.compile(regEx, Pattern.MULTILINE);
        String contents = null;
        try {
            contents = new String(Files.readAllBytes(Paths.get(file))).replaceAll("\\r\\n", "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matcher matcher = pattern.matcher(contents);
        String output = "";

        int start, prevEnd = 0;
        while (matcher.find()) {
            String matched = matcher.group();
            start = matcher.start();
            if (start != prevEnd) {
                output +=("error no token = " + contents.substring(prevEnd, start)+'\n');
                break;
            }
            for (GroupToken groupToken1 : groupToken) {
                if (groupToken1.matchCheck(matched)) {
                    Token t = groupToken1.findToken(matched);
                    output += ('<' + t.getName() + ">: " + matched) + "\n";
                    break;
                }
            }
            prevEnd = matcher.end();
        }

        try {
            System.out.println(output);
            String path = "src\\output.txt";
            Files.write(Paths.get(path), output.getBytes(), StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
