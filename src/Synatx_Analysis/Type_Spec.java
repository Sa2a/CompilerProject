package Synatx_Analysis;

public class Type_Spec extends Node {

    private Token token;

    public Type_Spec(Token token) {
        this.token = token;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Type Spec----------------");
        System.out.println(token.getName());
        System.out.println("--------------Type Spec End------------");
    }
}
