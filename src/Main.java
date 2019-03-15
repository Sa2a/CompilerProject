import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {
        Token ID = new Token("ID", "[a-zA-Z_]\\w*");
        Token INTEGRAL_LITERAL = new Token("INTEGRAL_LITERAL", "[-+]?\\d+");
        Token FLOAT_LITERAL = new Token("FLOAT_LITERAL", "[-+]?[0-9]*\\.?[0-9]+");
        Token STRING_LITERAL = new Token("STRING_LITERAL", "\".*\"");
        Token CHAR_LITERAL = new Token("CHAR_LITERAL", "\'.?\'");
        Token MULTI_COMMENT = new Token("MULTI_COMMENT", "/\\*.*\\*/");
        Token SINGLE_COMMENT = new Token("SINGLE_COMMENT", "//.*\n");

        GroupToken identifier = new GroupToken("identifier", ID.getRegEx());
        identifier.readTokensFile("E:\\1_College\\y4_T2\\Compilers\\project\\src\\tokens\\identifier.txt");

        GroupToken constant = new GroupToken("constant", INTEGRAL_LITERAL.getRegEx() + '|' + FLOAT_LITERAL.getRegEx());
        constant.addToken(INTEGRAL_LITERAL);
        constant.addToken(FLOAT_LITERAL);

        GroupToken strings = new GroupToken("strings", STRING_LITERAL.getRegEx() + '|' + CHAR_LITERAL.getRegEx() + '|' +
                MULTI_COMMENT.getRegEx() + '|' + SINGLE_COMMENT.getRegEx());
        strings.readTokensFile("E:\\1_College\\y4_T2\\Compilers\\project\\src\\tokens\\strings.txt");

        GroupToken specialSymbol = new GroupToken("specialSymbol", "=>|=<|==|!=|&&|\\|\\||>>|<<|\\p{Punct}");
        specialSymbol.readTokensFile("E:\\1_College\\y4_T2\\Compilers\\project\\src\\tokens\\specialSymbol.txt");

        String regEx = identifier.getRegEx() + '|' + constant.getRegEx() + '|' + strings.getRegEx()+'|'+specialSymbol.getRegEx();
        Pattern pattern = Pattern.compile(regEx,Pattern.MULTILINE);
        String contents = null;
        try {
            contents = new String(Files.readAllBytes(Paths.get("E:\\1_College\\y4_T2\\Compilers\\project\\src\\input.txt")));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matcher matcher = pattern.matcher(contents);
        String output = "";
        while (matcher.find()) {
            String mached = matcher.group();
            if(identifier.matchCheck(mached))
            {
                Token t = identifier.findToken(mached);
                output +=('<'+t.getName()+">: "+mached)+"\n";
            }
            else if(constant.matchCheck(mached))
            {
                Token t = constant.findToken(mached);
                output +=('<'+t.getName()+">: "+mached)+"\n";
            }
            else if(strings.matchCheck(mached))
            {
                Token t = strings.findToken(mached);
                output +=('<'+t.getName()+">: "+mached)+"\n";
            }
            else if(specialSymbol.matchCheck(mached)) {
                Token t = specialSymbol.findToken(mached);
                output += ('<' + t.getName() + ">: " + mached) + "\n";
            }
        }

        try {
            System.out.println(output);
            String path = "E:\\1_College\\y4_T2\\Compilers\\project\\src\\output.txt";
            Files.write( Paths.get(path), output.getBytes(), StandardOpenOption.CREATE);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}