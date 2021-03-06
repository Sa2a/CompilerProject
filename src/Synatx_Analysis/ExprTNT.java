package Synatx_Analysis;

public class ExprTNT extends Node {
    private String name;
    private Token token1;
    private Node expr;
    private Token token2;

    public ExprTNT(String name,Token token1, Node expr, Token token2) {
        this.name = name;
        this.token1 = token1;
        this.expr = expr;
        this.token2 = token2;
    }

    @Override
    public void printNode() {
        try{
            System.out.println("----------------"+ name +"---------------");
            printToken(token1,"");
            expr.printNode();
            printToken(token2,"");
            System.out.println("----------------"+ name +"-End-----------");
        }catch (Exception e)
        {
            System.out.println("Syntax Error");
            System.exit(0);
        }

    }

}
