package Synatx_Analysis;

public class Local_Decl_D extends Node{
    // ; | [ ] ;
    private Token semicolon;
    private Token openSquare;
    private Token closeSquare;

    public Local_Decl_D(Token semicolon, Token openSquare, Token closeSquare) {
        this.semicolon = semicolon;
        this.openSquare = openSquare;
        this.closeSquare = closeSquare;
    }

    public Local_Decl_D(Token semicolon) {
        this.semicolon = semicolon;
        this.openSquare = null;
        this.closeSquare = null;
    }

    @Override
    public void printNode() {
        System.out.println("--------------Local_Decl_D----------------");
        if (openSquare !=null){
            printToken(openSquare,"[");
            printToken(closeSquare,"]");
        }
        printToken(semicolon,";");
        System.out.println("--------------Local_Decl_D-End-----------");
    }
}
