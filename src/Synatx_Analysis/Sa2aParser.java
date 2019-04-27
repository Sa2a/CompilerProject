package Synatx_Analysis;

import javax.swing.plaf.TabbedPaneUI;
import java.util.Queue;

public class Sa2aParser {

    Queue<Token> tokenQueue;


    public Node expr_stmt() {
        Token next = tokenQueue.peek();
        // ; ! - + ( IDENT, BOOL_LIT, INT_LIT, FLOAT_LIT ,NEW
        if (next != null) {
            if (next.getName().equals("<SEMICOLON>"))
                return new Expr_Stmt(tokenQueue.poll());

            if (next.getName().equals("<NOT>") ||
                    next.getName().equals("<MINUS>") ||
                    next.getName().equals("<PLUS>") ||
                    next.getName().equals("<RIGHT_ROUND_B>") ||
                    next.getName().equals("<ID>") ||
                    next.getName().equals("<TRUE>") ||
                    next.getName().equals("<FALSE>") ||
                    next.getName().equals("<FLOAT_LITERAL>") ||
                    next.getName().equals("<INTEGRAL_LITERAL>") ||
                    next.getName().equals("<NEW>")) {

                /* expr00 parser*/
                next = tokenQueue.peek();
                if (next.getName().equals("<SEMICOLON>"))
                    return new Expr_Stmt( /* expr00 parser*/, tokenQueue.poll());

            }
        }
        return null;
    }


    public Node while_stmt() {
        Token next = tokenQueue.peek();

        //WHILE ( expr00 ) stmt
        if (next != null && next.getName().equals("<WHILE>")) {
            Token wileToken = tokenQueue.poll();
            next = tokenQueue.peek();

            if (next != null && next.getName().equals("<RIGHT_ROUND_B>")) {
                Token openBrace = tokenQueue.poll();

                /* expr00 parser*/

                next = tokenQueue.peek();
                if (next != null && next.getName().equals("<LEFT_ROUND_B>")) {
                    Token closeBrace = tokenQueue.poll();

                    /* stmt parser*/

                    return new While_Stmt(wileToken, openBrace,/* expr00 parser*/, closeBrace,/* stmt parser*/);
                }
            }
        }
        return null;
    }

    public Node compound_stmt() {
        /*{ local_decls stmt_list }*/
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<RIGHT_CURLY_B>")) {
            Token openCurly = tokenQueue.poll();

            Node local_decls = local_decls();
            /*stmt_list parser*/

            next = tokenQueue.peek();
            if (next != null && next.getName().equals("<LEFT_CURLY_B>")) {
                Token closeCurly = tokenQueue.poll();
                return new Compound_Stmt(openCurly, local_decls, /*stmt_list parser*/, closeCurly);
            }
        }
        return null;
    }

    public Node local_decls() {
        Token next = tokenQueue.peek();
        //local_decl local_decls | ε

        //type_spec → VOID | BOOL | INT | FLOAT
        if (next != null && (next.getName().equals("<VOID>") || next.getName().equals("<BOOL>") ||
                next.getName().equals("<INT>") || next.getName().equals("<FLOAT>"))) {
            Node local_decl = local_decl();
            Node local_decls = local_decls();
            return new Local_Decls(local_decl, local_decls());
        }
        return new Empty();
    }

    public Node local_decl() {
        //type_spec IDENT local_decl'
        Node type_spec = type_spec();
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<ID>")) {
            Token IDENT = tokenQueue.poll();
            /*local_decl_d parser */
            return new Local_Decl(type_spec, IDENT, local_decl_d());
        }


        return null;
    }

    public Node local_decl_d() {
        Token next = tokenQueue.peek();
        //  ; | [ ] ;
        if (next != null) {
            if (next.getName().equals("<SEMICOLON>")) {
                return new Local_Decl_D(tokenQueue.poll());
            }
            /*
            LEFT_SQUARE_B \]
            RIGHT_SQUARE_B \[
             */
            else if (next.getName().equals("<RIGHT_SQUARE_B>")) {
                Token openSquare = tokenQueue.poll();
                next = tokenQueue.peek();
                if (next != null && next.getName().equals("<LEFT_SQUARE_B>")) {
                    Token closeSquare = tokenQueue.poll();
                    next = tokenQueue.peek();
                    if (next != null && next.getName().equals("<SEMICOLON>")) {
                        Token semicolon = tokenQueue.poll();
                        return new Local_Decl_D(openSquare, closeSquare, semicolon);
                    }
                }
            }
        }
        return null;
    }
    public Node return_stmt() {
        //RETURN return_stmt'
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<RETURN>")) {
            Token returnToken = tokenQueue.poll();
            return new Return_Stmt(returnToken, return_stmt_d());
        }
        return null;
    }

    public Node return_stmt_d() {
        // ; | expr00 ;
        Token next = tokenQueue.peek();
        if (next != null) {
            if (next.getName().equals("<SEMICOLON>")) {
                return new Return_Stmt_D(tokenQueue.poll());
            }
            else
            {
                /* expr00 parser*/

                next = tokenQueue.peek();
                if (next.getName().equals("<SEMICOLON>")) {
                    return new Return_Stmt_D(/* expr00 parser*/,tokenQueue.poll());
                }
            }
        }
        return null;
    }
    public Node if_stmt() {
        //if_stmt   → IF ( expr ) stmt else_stmt
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<IF>")) {
            Token ifToken = tokenQueue.poll();
            next = tokenQueue.peek();
            if (next != null && next.getName().equals("<RIGHT_ROUND_B>")) {
                Token openBrace = tokenQueue.poll();
                /* expr00 parser*/
                next = tokenQueue.peek();
                if (next != null && next.getName().equals("<LEFT_ROUND_B>")) {
                    Token closeBrace = tokenQueue.poll();
                    return new If_Stmt(ifToken,openBrace,/* expr00 parser*/,closeBrace, else_stmt());
                }
            }
        }
        return null;
    }

    public Node else_stmt() {
        //else_stmt -> ELSE stmt| ε
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<ELSE>")) {
            Token elseToken = tokenQueue.poll();
            return new Else_Stmt(elseToken, /* stmt parser*/);
        }
        return new Empty();
    }
}