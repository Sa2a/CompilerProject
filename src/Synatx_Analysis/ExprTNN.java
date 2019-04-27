package Synatx_Analysis;

public class ExprTNN implements Node {
    private String name;
    private Token token;
    private Node expr1;
    private Node expr2;

    public ExprTNN(String name, Token token, Node expr1, Node expr2) {
        this.name = name;
        this.token = token;
        this.expr1 = expr1;
        this.expr2 = expr2;
    }

    @Override
    public void printNode() {
        try{
            System.out.println("----------------"+ name +"---------------");
            printToken(token,"");
            expr1.printNode();
            if(expr2 == null)
                System.out.println("Empty");
            else
                expr2.printNode();
            System.out.println("--------------"+ name +"-End-------------");
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
