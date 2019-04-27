package Synatx_Analysis;


public class Expr_Stmt extends Node {
    private Node expr00;
    private Token semicolon;

    public Expr_Stmt(Node expr, Token semicolon) {
        this.expr00 = expr;
        this.semicolon = semicolon;
    }

    public Expr_Stmt(Token semicolon) {
        this.semicolon = semicolon;
        expr00 = null;
    }

    @Override
    public void printNode() {
        System.out.println("--------------sxpr_stmt--------------");

        if (expr00 != null)
            expr00.printNode();
        printToken(semicolon, ";");
        System.out.println("--------------sxpr_stmt-End-----------");

    }

}

