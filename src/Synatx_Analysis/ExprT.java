package Synatx_Analysis;

public class ExprT extends Node {
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

}
