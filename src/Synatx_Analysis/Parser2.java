package Synatx_Analysis;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Parser2 {
    private Queue<Token> tokenQueue;
    private Node root;

    public Parser2() {
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
        root =  this.decl_list();
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
            Token token2 = tokenQueue.peek();
            if(token2!=null && (next.getName().equals("<LEFT_ROUND_B>"))){
                return new ExprTNT("fun_decl", token1, params(), token2); //compound_stmt
            }
        }
        return null;
    }

    private Node type_spec() {
        Token temp = tokenQueue.peek();
        if(temp == null){
            System.out.println("Syntax error no more tokens");
            return null;
        }
        else if(temp.getName().equals("<VOID>") || temp.getName().equals("<BOOL>") || temp.getName().equals("<INT>") || temp.getName().equals("<FLOAT>"))
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

    //not implement
    private Node stmt(){
        return null;
    }
}

