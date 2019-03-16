import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class IToken {
    protected String name;
    protected String regEx;

    public boolean matchCheck(String input) {
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(input);
        if (matcher.find())
            if (input.length() == matcher.group().length())
                return true;
        return false;
    }


    public IToken(String name, String regEx) {
        this.name = name;
        this.regEx = regEx;
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


}
