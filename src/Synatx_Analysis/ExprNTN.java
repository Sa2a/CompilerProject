package Synatx_Analysis;

public class ExprNTN implements Node {
    private String name;
    private Node expr;
    private Token token;
    private Node exprD;

    public ExprNTN(String name, Node expr,Token token, Node exprD) {
        this.name = name;
        this.expr = expr;
        this.token = token;
        this.exprD = exprD;
    }

    @Override
    public void printNode() {
        try{
            System.out.println("----------------"+ name +"---------------");
            expr.printNode();
            printToken(token,"");
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
