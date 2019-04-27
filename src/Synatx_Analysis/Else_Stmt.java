package Synatx_Analysis;


public class Else_Stmt extends Node {
    //else_stmt -> ELSE stmt| ε
    private Token elseToken;
    private Node stmt;

    public Else_Stmt(Token elseToken, Node stmt) {
        this.elseToken = elseToken;
        this.stmt = stmt;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Else_Stmt--------------");
        printToken(elseToken, "else");
        stmt.printNode();
        System.out.println("--------------Else_Stmt-End-----------");

    }

}

