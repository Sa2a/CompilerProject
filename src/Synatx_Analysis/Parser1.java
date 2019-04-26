package Synatx_Analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Parser1 {
    private Queue<Token> tokenQueue;
    private Node root;
    private String []binaryOperators = new String[]{"<OR>","<AND>","<EQUAL>","<NOT_EQUAL>","<LESS_EQ>","<LESSTHAN>","<GREAT_EQ>","<GREATERTHAN>","<PLUS>","<MINUS>","<ASTERICK>","<DIVIDE>","<MOD>"};
    private String []unaryOperators = new String[]{"<MINUS>","<PLUS>","<NOT>"};


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

    public Parser1() {
        tokenQueue = new LinkedList<>();
    }

    public void parse(String file) {
        readTokens(file);
        root = binaryExpr(0);
        root.printNode();
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
            Node expr = operator == 2? unaryExpr(operator):unaryExpr(operator+1);
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

    private Type_Spec type_spec() {
        Token temp = tokenQueue.peek();
        if(temp == null){
            System.out.println("Syntax error no more tokens");
            return null;
        }
        else if(temp.getName().equals("<VOID>") || temp.getName().equals("<BOOL>") || temp.getName().equals("<INT>") || temp.getName().equals("<FLOAT>"))
            return new Type_Spec(tokenQueue.poll());
        return null;
    }
}