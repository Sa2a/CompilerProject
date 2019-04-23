package Synatx_Analysis;

public class Program implements Node{
    private Decl_List decl_list;

    public Program(Decl_List decl_list) {
        this.decl_list = decl_list;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Program----------------");
        decl_list.printNode();
        System.out.println("--------------Program End------------");
    }
}
