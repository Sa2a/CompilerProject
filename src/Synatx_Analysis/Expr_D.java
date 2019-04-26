package Synatx_Analysis;

public class Expr_D implements Node{
    private String name;
    private Token token;
    private Node expr;
    private Node exprD;

    public Expr_D(String name,Token token, Node expr, Node exprD) {
        this.name = name;
        this.token = token;
        this.expr = expr;
        this.exprD = exprD;
    }

    @Override
    public void printNode() {
        try{
            System.out.println("----------------"+ name +"---------------");
            printToken(token,"Symbol");
            expr.printNode();
            exprD.printNode();
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
