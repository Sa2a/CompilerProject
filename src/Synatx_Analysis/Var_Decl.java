package Synatx_Analysis;

public class Var_Decl extends Node{
    private Type_Spec type_spec;
    private Token ident;
    private Var_Decl_Extend extend;
    private Token semicolon;

    public Var_Decl(Type_Spec type_spec, Token ident, Var_Decl_Extend extend, Token semicolon) {
        this.type_spec = type_spec;
        this.ident = ident;
        this.extend = extend;
        this.semicolon = semicolon;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Var Decl----------------");
        type_spec.printNode();
        printToken(ident,"identifier");
        extend.printNode();
        printToken(semicolon,";");
        System.out.println("--------------Var Decl-End-----------");
    }

}
