import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    public static void main(String[] args) {

        final String ID = "[a-zA-Z_]\\w*";
        final String INTEGRAL_LITERAL = "\\d+";
        final String FLOAT_LITERAL = "[0-9]*\\.?[0-9]+";
        final String STRING_LITERAL = "\".*\"";
        final String CHAR_LITERAL = "\'.?\'";
        final String MULTI_COMMENT = "/\\*.*\\*/";
        final String SINGLE_COMMENT = "//.*\n";
        final String SPECIAL_SYMBOLS = "=>|=<|==|!=|&&|\\|\\||>>|<<|\\p{Punct}";


        GroupToken groupToken[] =  new GroupToken[4];
        // keywords and identifiers
        groupToken[0] = new GroupToken("identifier", ID);
        // constants
        groupToken[1] = new GroupToken("constant", INTEGRAL_LITERAL + '|' + FLOAT_LITERAL);
        // strings
        groupToken[2] = new GroupToken("strings", STRING_LITERAL + '|' + CHAR_LITERAL + '|' + MULTI_COMMENT + '|' + SINGLE_COMMENT);
        // specialSymbols & Operators
        groupToken[3] = new GroupToken("specialSymbol", SPECIAL_SYMBOLS);

        String regEx = groupToken[0].getRegEx() + '|' + groupToken[1].getRegEx() + '|' + groupToken[2].getRegEx()+'|'+groupToken[3].getRegEx();
        Pattern pattern = Pattern.compile(regEx,Pattern.MULTILINE);
        String contents = null;
        try {
            contents = new String(Files.readAllBytes(Paths.get("src\\input.txt"))).replaceAll("\\r\\n", "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
        Matcher matcher = pattern.matcher(contents);
        String output = "";
        while (matcher.find()) {
            String mached = matcher.group();
            for(int i=0;i<groupToken.length;i++)
            {
                if(groupToken[i].matchCheck(mached))
                {
                    Token t = groupToken[i].findToken(mached);
                    output +=('<'+t.getName()+">: "+mached)+"\n";
                    break;
                }
            }
        }

        try {
            System.out.println(output);
            String path = "src\\output.txt";
            Files.write( Paths.get(path), output.getBytes(), StandardOpenOption.CREATE);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}