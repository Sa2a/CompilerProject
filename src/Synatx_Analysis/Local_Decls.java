package Synatx_Analysis;

public class Local_Decls extends Node {
    //local_decl local_decls | ε
    private Node local_decl;
    private Node local_decls;

    public Local_Decls(Node local_decl, Node local_decls) {
        this.local_decl = local_decl;
        this.local_decls = local_decls;
    }

    @Override
    public void printNode() {

        System.out.println("--------------Local_Decls--------------");
        local_decl.printNode();
        local_decls.printNode();
        System.out.println("--------------Local_Decls_END--------------");

    }
}
