package Synatx_Analysis;


public class If_Stmt extends Node {
    //if_stmt   → IF ( expr ) stmt else_stmt
    private Token ifToken;
    private Token openBrace;
    private Node expr00;
    private Token closeBrace;
    private Node stmt;
    private Node else_stmt;

    public If_Stmt(Token ifToken, Token openBrace, Node expr00, Token closeBrace, Node stmt, Node else_stmt) {
        this.ifToken = ifToken;
        this.openBrace = openBrace;
        this.expr00 = expr00;
        this.closeBrace = closeBrace;
        this.stmt = stmt;
        this.else_stmt = else_stmt;
    }

    @Override
    public void printNode() {
        System.out.println("--------------If_Stmt--------------");
        printToken(ifToken, "if");
        printToken(openBrace, "(");
        expr00.printNode();
        printToken(closeBrace, ")");
        stmt.printNode();
        else_stmt.printNode();
        System.out.println("--------------If_Stmt-End-----------");

    }

}

