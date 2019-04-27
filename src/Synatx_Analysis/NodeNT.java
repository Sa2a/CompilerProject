package Synatx_Analysis;

public class NodeNT extends Node{
    private String name;
    private Node expr;
    private Token token;


    public NodeNT(String name, Node expr, Token token) {
        this.name = name;
        this.token = token;
        this.expr = expr;
    }

    @Override
    public void printNode() {
        try{
            System.out.println("----------------"+ name +"---------------");
            expr.printNode();
            printToken(token,"");
            System.out.println("----------------"+ name +"-End-----------");
        }catch (Exception e)
        {
            System.out.println("Syntax Error");
            System.exit(0);
        }

    }

}
