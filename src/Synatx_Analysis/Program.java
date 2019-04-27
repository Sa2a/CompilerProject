package Synatx_Analysis;

public class Program extends Node{
    private Node decl_list;

    public Program(Node decl_list) {
        this.decl_list = decl_list;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Program----------------");
        decl_list.printNode();
        System.out.println("--------------Program End------------");
    }
}
