package Synatx_Analysis;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {
    private Queue<Token> tokenQueue;
    private Node root;
    private String []binaryOperators = new String[]{"<OR>","<AND>","<EQUAL>","<NOT_EQUAL>","<LESS_EQ>","<LESSTHAN>","<GREAT_EQ>","<GREATERTHAN>","<PLUS>","<MINUS>","<ASTERICK>","<DIVIDE>","<MOD>"};
    private String []unaryOperators = new String[]{"<MINUS>","<PLUS>","<NOT>"};


    public Parser() {
        tokenQueue = new LinkedList<>();
    }

    private void readTokens(String file) {
        String line = null;
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(new File(file)));
            String[] token;
            while ((line = bufferedReader.readLine()) != null) {
                token = line.split(":");
                tokenQueue.add(new Token(token[0], token[1]));
            }
            bufferedReader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parse(String file){
        readTokens(file);
        root =  new ExprN("Program",this.decl_list());
        root.printNode();
    }

    private Node decl_list(){
        Node decl = decl();
        return new ExprNN("decl_list",decl, decl_list_d());
    }

    private Node decl_list_d(){
        Node decl = decl();
        if(decl == null){
            return new Empty();
        }
        return new ExprNN("decl_list_d", decl, decl_list_d());
    }

    private Node decl(){
        Node type_spec = type_spec();
        if(type_spec != null)
        {
            Token token = tokenQueue.peek();
            if(token != null && token.getName().equals("<ID>"))
            {
                token = tokenQueue.poll();
                return new ExprNTN("decl", type_spec, token, decl_d());
            }
        }
        return null;
    }

    private Node decl_d(){
        Token next = tokenQueue.peek();
        if(next != null && (next.getName().equals("<RIGHT_SQUARE_B>"))){
            Token token1 = tokenQueue.poll();
            Token token2 = tokenQueue.peek();
            if(token2!=null && token2.getName().equals("<LEFT_SQUARE_B>")){
                Token token3 = tokenQueue.peek();
                if(token3!=null && token3.getName().equals("<SEMICOLON>")){
                    return new ExprTTT("var_decl", token1, token2, token3);
                }
            }
        }
        else if(next != null && (next.getName().equals("<SEMICOLON>"))){
            Token token = tokenQueue.poll();
            return new ExprT("var_decl", token);
        }
        else if(next != null && (next.getName().equals("<RIGHT_ROUND_B>"))){
            Token token1 = tokenQueue.poll();
            Node params = params();
            next = tokenQueue.peek();
            if(next!=null && (next.getName().equals("<LEFT_ROUND_B>"))){
                Token token2 = tokenQueue.poll();
                return new ExprTNTN("fun_decl", token1, params, token2, compound_stmt());
            }
        }
        return null;
    }

    private Node type_spec() {
        Token temp = tokenQueue.peek();
        if(temp!= null && (temp.getName().equals("<VOID>") || temp.getName().equals("<BOOL>") || temp.getName().equals("<INT>") || temp.getName().equals("<FLOAT>")))
            return new ExprT("type_spec", tokenQueue.poll());
        else
            return null;
    }

    private Node params(){
        Token token = tokenQueue.peek();
        if(token!=null && token.getName().equals("<VOID>")){
            return new ExprT("VOID", tokenQueue.poll());
        }
        return new ExprN("params_list", params_list());
    }

    private Node params_list(){
        Node param = param();
        if(param != null){
            return new ExprNN("params_list", param, params_list_d());
        }
        return null;
    }

    private Node params_list_d(){
        Token token = tokenQueue.peek();
        if(token != null && token.getName().equals("<COMMA>")){
            return new ExprTNN("params_list_d", token, param(), params_list_d());
        }
        return new Empty();
    }

    private Node param(){
        Node type_spec = type_spec();
        if (type_spec != null){
            Token token = tokenQueue.peek();
            if(token != null && token.getName().equals("<ID>")){
                token = tokenQueue.poll();
                return new ExprNTN("param", type_spec, token , param_d());
            }
        }
        return null;
    }

    private Node param_d(){
        Token token = tokenQueue.peek();
        if(token != null && token.getName().equals("<RIGHT_SQUARE_B>")){
            token = tokenQueue.poll();
            Token token1 = tokenQueue.peek();
            if(token1 != null && token1.getName().equals("<LEFT_SQUARE_B>")){
                return new ExprTT("param_d", token, token1);
            }
        }
        return new Empty();
    }

    private Node stmt_list(){
        return new ExprN("stmt_list", stmt_list_d());
    }

    private Node stmt_list_d(){
        Node stmt = stmt();
        if(stmt != null){
            return new ExprNN("stmt_list_d", stmt, stmt_list_d());
        }
        return new Empty();
    }

    private Node stmt(){
        Token token = tokenQueue.peek();
        if(token != null && token.getName().equals("<RETURN>")){
            return new ExprN("stmt", return_stmt());
        }
        else if(token != null && token.getName().equals("<WHILE>")){
            return new ExprN("stmt", while_stmt());
        }
        else if(token != null && token.getName().equals("<IF>")){
            return new ExprN("stmt", if_stmt());
        }
        else if(token != null && token.getName().equals("<RIGHT_CURLY_B>")){
            return new ExprN("stmt", compound_stmt());
        }
        else if(token != null && (token.getName().equals("<SEMICOLON>")
                || token.getName().equals("<ID>")
                || token.getName().equals("<NOT>")
                || token.getName().equals("<MINUS>")
                || token.getName().equals("<PLUS>")
                || token.getName().equals("<RIGHT_ROUND_B>")
                || token.getName().equals("<TRUE>")
                || token.getName().equals("<FALSE>")
                || token.getName().equals("<INTEGRAL_LITERAL>")
                || token.getName().equals("<FLOAT_LITERAL>")
        )){
            return new ExprN("stmt", expr_stmt());
        }
        return null;

    }

    private Node expr_stmt() {
        Token next = tokenQueue.peek();
        // ; ! - + ( IDENT, BOOL_LIT, INT_LIT, FLOAT_LIT ,NEW
        if (next != null) {
            if (next.getName().equals("<SEMICOLON>"))
                return new ExprT("Expr_Stmt",tokenQueue.poll());

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

                Node expr = binaryExpr(0);
                next = tokenQueue.peek();
                if (next.getName().equals("<SEMICOLON>"))
                    return new NodeNT( "Expr_Stmt", expr, tokenQueue.poll());

            }
        }
        return null;
    }

    private Node while_stmt() {
        Token next = tokenQueue.peek();

        //WHILE ( expr00 ) stmt
        if (next != null && next.getName().equals("<WHILE>")) {
            Token wileToken = tokenQueue.poll();
            next = tokenQueue.peek();

            if (next != null && next.getName().equals("<RIGHT_ROUND_B>")) {
                Token openBrace = tokenQueue.poll();
                Node expr = binaryExpr(0);
                next = tokenQueue.peek();
                if (next != null && next.getName().equals("<LEFT_ROUND_B>")) {
                    Token closeBrace = tokenQueue.poll();
                    Node stmt = stmt();
                    return new While_Stmt(wileToken, openBrace,expr, closeBrace,stmt);
                }
            }
        }
        return null;
    }

    private Node compound_stmt() {
        /*{ local_decls stmt_list }*/
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<RIGHT_CURLY_B>")) {
            Token openCurly = tokenQueue.poll();

            Node local_decls = local_decls();
            Node stmt_list = stmt_list();

            next = tokenQueue.peek();
            if (next != null && next.getName().equals("<LEFT_CURLY_B>")) {
                Token closeCurly = tokenQueue.poll();
                return new Compound_Stmt(openCurly, local_decls, stmt_list, closeCurly);
            }
        }
        return null;
    }

    private Node local_decls() {
        Token next = tokenQueue.peek();
        //local_decl local_decls | ε

        //type_spec → VOID | BOOL | INT | FLOAT
        if (next != null && (next.getName().equals("<VOID>") || next.getName().equals("<BOOL>") ||
                next.getName().equals("<INT>") || next.getName().equals("<FLOAT>"))) {
            Node local_decl = local_decl();
            Node local_decls = local_decls();
            return new Local_Decls(local_decl, local_decls);
        }
        return new Empty();
    }

    private Node local_decl() {
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

    private Node local_decl_d() {
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

    private Node return_stmt() {
        //RETURN return_stmt'
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<RETURN>")) {
            Token returnToken = tokenQueue.poll();
            return new Return_Stmt(returnToken, return_stmt_d());
        }
        return null;
    }

    private Node return_stmt_d() {
        // ; | expr00 ;
        Token next = tokenQueue.peek();
        if (next != null) {
            if (next.getName().equals("<SEMICOLON>")) {
                return new Return_Stmt_D(tokenQueue.poll());
            }
            else
            {
                Node expr = binaryExpr(0);
                next = tokenQueue.peek();
                if (next.getName().equals("<SEMICOLON>")) {
                    return new Return_Stmt_D(expr,tokenQueue.poll());
                }
            }
        }
        return null;
    }

    private Node if_stmt() {
        //if_stmt   → IF ( expr ) stmt else_stmt
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<IF>")) {
            Token ifToken = tokenQueue.poll();
            next = tokenQueue.peek();
            if (next != null && next.getName().equals("<RIGHT_ROUND_B>")) {
                Token openBrace = tokenQueue.poll();
                Node expr = binaryExpr(0);
                next = tokenQueue.peek();
                if (next != null && next.getName().equals("<LEFT_ROUND_B>")) {
                    Token closeBrace = tokenQueue.poll();
                    Node stmt = stmt();
                    return new If_Stmt(ifToken,openBrace,expr,closeBrace,stmt, else_stmt());
                }
            }
        }
        return null;
    }

    private Node else_stmt() {
        //else_stmt -> ELSE stmt| ε
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<ELSE>")) {
            Token elseToken = tokenQueue.poll();
            return new Else_Stmt(elseToken, stmt());
        }
        return new Empty();
    }

    private Node binaryExpr(int operator) {
        Node expr = operator == 12? unaryExpr(0): binaryExpr(operator+1);
        Node exprD = binaryExpr_D(operator);
        return new ExprNN(binaryOperators[operator] +" Binary Expr", expr, exprD);
    }

    private Node binaryExpr_D(int operator) {
        Token token = null;
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals(binaryOperators[operator])) {
            token = tokenQueue.poll();
            Node expr = operator == 12? unaryExpr(0): binaryExpr(operator+1);
            Node exprD = binaryExpr_D(operator);
            return new Expr_D(binaryOperators[operator] + " Binary Expr'",token, expr, exprD);
        } else {
            return new Empty();
        }
    }

    private Node unaryExpr(int operator) {
        Token token = null;
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals(unaryOperators[operator])) {
            token = tokenQueue.poll();
            Node expr = unaryExpr(operator);
            return new ExprTN(unaryOperators[operator] + "Unary Expr",token, expr);
        }
        Node expr = operator == 2? expr16() : unaryExpr(operator+1);
        return new ExprN(unaryOperators[operator] + "Unary Expr",expr);
    }

    private Node expr16() {
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<RIGHT_ROUND_B>")) {
            Token token1 = tokenQueue.poll();
            Token token2 = null;
            Node expr = binaryExpr(0);
            next = tokenQueue.peek();
            if (next != null && next.getName().equals("<LEFT_ROUND_B>"))
                token2 = tokenQueue.poll();
            return new ExprTNT("( Expr )",token1, expr, token2);
        } else if (next != null && next.getName().equals("<ID>")) {
            Token token = tokenQueue.poll();
            Node beta = beta0();
            return new ExprTN("ID beta0",token,beta);
        } else if (next != null && (next.getName().equals("<TRUE>") || next.getName().equals("<FALSE>") || next.getName().equals("<FLOAT_LITERAL>") || next.getName().equals("<INTEGRAL_LITERAL>"))) {
            Token token = tokenQueue.poll();
            return new ExprT(token.getName(),token);
        }else if(next.getName().equals("<NEW>")) {
            Token token1 =tokenQueue.poll();
            Token token2 = null;
            Token token3 = null;
            Node type_spec = type_spec();
            next = tokenQueue.peek();
            if (next != null && next.getName().equals("<RIGHT_SQUARE_B>")){
                token2 = tokenQueue.poll();
                Node expr = binaryExpr(0);
                next = tokenQueue.peek();
                if (next != null && next.getName().equals("<LEFT_SQUARE_B>")){
                    token3 = tokenQueue.poll();
                    return new ExprTNTNT("New type_spec[ Expr ]",token1,type_spec,token2, expr,token3);
                }
            }
        }
        return null;
    }

    private Node beta0() {
        Token next = tokenQueue.peek();
        if(next != null && next.getName().equals("<ASSIGN_OPERATOR>"))
        {
            Token token = tokenQueue.poll();
            Node expr = binaryExpr(0);
            return new ExprTN("= Expr",token,expr);
        }
        else if (next != null && next.getName().equals("<RIGHT_SQUARE_B>")){
            Token token1 = tokenQueue.poll();
            Token token2 = null;
            Node expr = binaryExpr(0);
            next = tokenQueue.peek();
            if (next != null && next.getName().equals("<LEFT_SQUARE_B>")){
                token2 = tokenQueue.poll();
            }
            Node beta1 = beta1();
            return new ExprTNTN("Expr[ Expr ] beta1",token1,expr,token2,beta1);
        }
        else if(next != null && next.getName().equals("<RIGHT_ROUND_B>")){
            Token token1 = tokenQueue.poll();
            Token token2 = null;
            Node args = args();
            next = tokenQueue.peek();
            if (next != null && next.getName().equals("<LEFT_ROUND_B>")){
                token2 = tokenQueue.poll();
                return new ExprTNT("Expr ( args )",token1,args,token2);
            }
        }
        else if(next != null && next.getName().equals("<DOT>")){
            Token token1 = tokenQueue.poll();
            Token token2 = null;
            next = tokenQueue.peek();
            if (next != null && next.getName().equals("<SIZE>")){
                token2 = tokenQueue.poll();
                return new ExprTT("Expr9",token1,token2);
            }
        }
        return new Empty();
    }

    private Node beta1() {
        Token token = null;
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<ASSIGN_OPERATOR>")) {
            token = tokenQueue.poll();
            Node expr = binaryExpr(0);
            return new ExprTN("beta1",token, expr);
        }
        return new Empty();
    }

    private Node args() {
        String list[] = {"<ID>","<RIGHT_SQUARE_B>","<NEW>","<TRUE>","<FALSE>","<FLOAT_LITERAL>","<INTEGRAL_LITERAL>"};
        Token next = tokenQueue.peek();
        for(int i=0;i<list.length;i++){
            if(next!= null && next.getName().equals(list[i]))
            {
                return new ExprN("Args",args_list());
            }
        }
        return new Empty();
    }

    private Node args_list() {
        Node expr = binaryExpr(0);
        Node exprD = args_list_D();
        return new ExprNN("Args_List", expr,exprD);
    }

    private Node args_list_D() {
        Token token = null;
        Token next = tokenQueue.peek();
        if (next != null && next.getName().equals("<COMMA>")) {
            token = tokenQueue.poll();
            Node expr = binaryExpr(0);
            Node exprD = args_list_D();
            return new Expr_D("Args_List'",token, expr, exprD);
        } else {
            return new Empty();
        }
    }


}
