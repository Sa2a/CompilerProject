package Synatx_Analysis;

public class Return_Stmt_D extends Node{
    // ; | expr00 ;
    private Node expr00;
    private Token semicolon;

    public Return_Stmt_D(Node expr00, Token semicolon) {
        this.expr00 = expr00;
        this.semicolon = semicolon;
    }

    public Return_Stmt_D(Token semicolon) {
        this.semicolon = semicolon;
        this.expr00 = null;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Return_Stmt_D----------------");
        if (expr00 != null)
            expr00.printNode();
        printToken(semicolon, ";");
        System.out.println("--------------Return_Stmt_D-End-----------");
    }
}
