package Synatx_Analysis;

public class Compound_Stmt extends Node{
    /*{ local_decls stmt_list }*/
    private Token opendCurly;
    private Node local_decls;
    private Node stmt_list;
    private Token closedCurly;

    public Compound_Stmt(Token opendCurly, Node local_decls, Node stmt_list, Token closedCurly) {
        this.opendCurly = opendCurly;
        this.local_decls = local_decls;
        this.stmt_list = stmt_list;
        this.closedCurly = closedCurly;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Compound_Stmt--------------");
        printToken(opendCurly,"{");
        local_decls.printNode();
        stmt_list.printNode();
        printToken(closedCurly, "}");
        System.out.println("--------------Compound_Stmt-End-----------");

    }
}
