package Synatx_Analysis;

public class Var_Decl_Extend extends Node{
    private Token token1;
    private Token token2;

    public Var_Decl_Extend() {
        this.token1 = null;
        this.token2 = null;
    }


    public Var_Decl_Extend(Token token1, Token token2) {
        this.token1 = token1;
        this.token2 = token2;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Var Decl Extend--------");
        if(token1==null && token2==null)
            System.out.println("Empty");
        else{
            printToken(token1,"[");
            printToken(token2,"]");
        }
        System.out.println("--------------Var Decl Extend End----");
    }
}
