package Synatx_Analysis;

public class ExprN extends Node{
    private String name;
    private Node node;

    public ExprN(String name,Node node) {
        this.name = name;
        this.node = node;
    }

    @Override
    public void printNode() {

        try{
            System.out.println("----------------"+ name +"---------------");
            node.printNode();
            System.out.println("----------------"+ name +"-End-----------");
        }catch (Exception e)
        {
            System.out.println("Syntax Error");
            System.exit(0);
        }
    }
}
