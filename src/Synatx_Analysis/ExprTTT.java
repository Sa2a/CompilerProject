package Synatx_Analysis;

public class ExprTTT implements Node {
    private String name;
    private Token token1;
    private Token token2;
    private Token token3;

    public ExprTTT(String name, Token token1, Token token2, Token token3) {
        this.name = name;
        this.token1 = token1;
        this.token2 = token2;
        this.token3 = token3;
    }

    @Override
    public void printNode() {
        try{
            System.out.println("----------------"+ name +"---------------");
            printToken(token1,"");
            printToken(token2,"");
            printToken(token3,"");
            System.out.println("--------------"+ name +"-End-------------");
        }catch (Exception e)
        {
            System.out.println("Syntax Error");
            System.exit(0);
        }
    }

    private void printToken(Token token,String expectedValue) {
        if(token == null) {
            System.out.println("Syntax Error Expected "+expectedValue);
            System.exit(0);
        }
        else {
            System.out.println(token.getName());
        }
    }
}
