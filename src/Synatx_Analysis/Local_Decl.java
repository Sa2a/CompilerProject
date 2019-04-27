package Synatx_Analysis;

public class Local_Decl extends Node{
    //type_spec IDENT local_decl'
    private Node type_spec;
    private Token identifier;
    private Node local_decl_d;

    public Local_Decl(Node type_spec, Token identifier, Node local_decl_d) {
        this.type_spec = type_spec;
        this.identifier = identifier;
        this.local_decl_d = local_decl_d;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Local_Decl----------------");
        type_spec.printNode();
        printToken(identifier,"identifier");
        local_decl_d.printNode();
        System.out.println("--------------Local_Decl-End-----------");
    }
}
