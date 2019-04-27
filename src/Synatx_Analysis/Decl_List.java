package Synatx_Analysis;

public class Decl_List extends Node{
    private Node decl;
    private Decl_List_D declListD;

    public Decl_List(Var_Decl decl, Decl_List_D declListD) {
        this.decl = decl;
        this.declListD = declListD;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Decl List--------------");
        decl.printNode();
        if(declListD!= null)
            declListD.printNode();
        System.out.println("--------------Decl list End----------");
    }
}
