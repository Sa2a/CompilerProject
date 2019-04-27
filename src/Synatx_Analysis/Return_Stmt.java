package Synatx_Analysis;

public class Return_Stmt extends Node{
    //RETURN return_stmt'
    private Token returnToken;
    private Node return_stmt_d;

    public Return_Stmt(Token returnToken, Node return_stmt_d) {
        this.returnToken = returnToken;
        this.return_stmt_d = return_stmt_d;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Return_Stmt----------------");
        printToken(returnToken, "return");
        return_stmt_d.printNode();
        System.out.println("--------------Return_Stmt-End-----------");
    }
}
