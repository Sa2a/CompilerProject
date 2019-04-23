package Synatx_Analysis;

public class Decl_List_D implements Node{

    private Node decl;
    private Decl_List_D declListD;

    public Decl_List_D() {
        this.decl = null;
        this.declListD = null;
    }
    public Decl_List_D(Var_Decl decl, Decl_List_D declListD) {
        this.decl = decl;
        this.declListD = declListD;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Decl_List_D------------");
        decl.printNode();
        if(declListD==null)
            System.out.println("Empty");
        else
            declListD.printNode();
        System.out.println("--------------Decl_List_D End--------");

    }
}
