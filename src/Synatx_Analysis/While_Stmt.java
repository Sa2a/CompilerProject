package Synatx_Analysis;


public class While_Stmt extends Node{
    //WHILE ( expr00 ) stmt
    private Token whiletoken;
    private Token openBrace;
    private Node expr00;
    private Token closeBrace;
    private Node stmt;

    public While_Stmt(Token whiletoken, Token openBrace, Node expr00, Token closeBrace, Node stmt) {
        this.whiletoken = whiletoken;
        this.openBrace = openBrace;
        this.expr00 = expr00;
        this.closeBrace = closeBrace;
        this.stmt = stmt;
    }

    @Override
    void printNode() {
        System.out.println("--------------While_Stmt--------------");
        printToken(whiletoken,"while");
        printToken(openBrace,"(");
        expr00.printNode();
        printToken(closeBrace,")");
        stmt.printNode();
        System.out.println("--------------While_Stmt-End-----------");

    }



}
