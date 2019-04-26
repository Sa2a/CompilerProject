package Synatx_Analysis;

public class ExprTN implements Node{
    private String name;
    private Token token;
    private Node expr;

    public ExprTN(String name, Token token, Node expr) {
        this.name = name;
        this.token = token;
        this.expr = expr;
    }

    @Override
    public void printNode() {
        try{
            System.out.println("----------------"+ name +"---------------");
            printToken(token,"");
            expr.printNode();
            System.out.println("----------------"+ name +"-End-----------");
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
