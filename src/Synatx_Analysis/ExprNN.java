package Synatx_Analysis;

public class ExprNN extends Node{
    private String name;
    private Node expr;
    private Node exprD;

    public ExprNN(String name, Node expr, Node exprD) {
        this.name = name;
        this.expr = expr;
        this.exprD = exprD;
    }

    @Override
    public void printNode() {
        try{
            System.out.println("----------------"+ name +"---------------");
            expr.printNode();
            if(exprD == null)
                System.out.println("Empty");
            else
                exprD.printNode();
            System.out.println("--------------"+ name +"-End-------------");
        }catch (Exception e)
        {
            System.out.println("Syntax Error");
            System.exit(0);
        }
    }
}
