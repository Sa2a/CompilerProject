package Synatx_Analysis;

public class ExprTNTNT implements Node {
    private String name;
    private Token token1;
    private Node node1;
    private Token token2;
    private Node node2;
    private Token token3;

    public ExprTNTNT(String name, Token token1, Node node1, Token token2, Node node2, Token token3) {
        this.name = name;
        this.token1 = token1;
        this.node1 = node1;
        this.token2 = token2;
        this.node2 = node2;
        this.token3 = token3;
    }

    @Override
    public void printNode() {
        try{
            System.out.println("----------------"+ name +"---------------");
            printToken(token1,"");
            node1.printNode();
            printToken(token2,"");
            node2.printNode();
            printToken(token3,"");
            System.out.println("----------------"+ name +"-End----------");
        }catch (Exception e)
        {
            System.out.println("Syntax Error");
            System.exit(0);
        }


    }


    private void printToken(Token token,String expectedValue) {
        if(token == null) {
            System.out.println("Syntax Error Expected "+expectedValue);
            System.exit(0);
        }
        else {
            System.out.println(token.getName());
        }
    }
}
