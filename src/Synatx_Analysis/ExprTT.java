package Synatx_Analysis;

public class ExprTT implements Node {
    private String name;
    private Token token1;
    private Token token2;

    public ExprTT(String name, Token token1, Token token2) {
        this.name = name;
        this.token1 = token1;
        this.token2 = token2;
    }

    @Override
    public void printNode() {
        System.out.println("----------------"+ name +"---------------");
        printToken(token1,"");
        printToken(token2,"");
        System.out.println("----------------"+ name +"-End--------------");
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
