package Synatx_Analysis;

public class ExprT implements Node {
    private String name;
    private Token token1;

    public ExprT(String name, Token token1) {
        this.name = name;
        this.token1 = token1;
    }

    @Override
    public void printNode() {
        System.out.println("----------------"+ name +"---------------");
        printToken(token1,"");
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
