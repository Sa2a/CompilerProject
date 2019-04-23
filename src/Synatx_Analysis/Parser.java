package Synatx_Analysis;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

public class Parser {
    private Queue<Token> tokenQueue;
    private Program root;

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
        root =  this.program();
        root.printNode();
    }


    private Program program()
    {
        return new Program(decl_list());
    }


    private Decl_List decl_list() {
        Var_Decl decl = var_decl();
        Decl_List_D declListD= decl_list_d();
        return new Decl_List(decl,declListD);
    }


    private Decl_List_D decl_list_d() {
        Token temp = tokenQueue.peek();
        if(temp == null)
            return null;
        else if(temp.getName().equals("<VOID>") || temp.getName().equals("<BOOL>") || temp.getName().equals("<INT>") ||temp.getName().equals("<FLOAT>"))
        {
            Var_Decl decl = var_decl();
            Decl_List_D declListD= decl_list_d();
            return new Decl_List_D(decl,declListD);
        }//fun_decl
        else
            return new Decl_List_D();

    }


    private Var_Decl var_decl() {
        Token id=null,semicolon=null;

        Type_Spec type_spec = type_spec();

        Token temp = tokenQueue.peek();

        if(temp != null && temp.getName().equals("<ID>")) {
            id = tokenQueue.poll();
        }
        else{
            System.out.println("Syntax error no identifier");
        }

        Var_Decl_Extend extend = var_decl_extend();
        temp = tokenQueue.peek();
        if(temp != null && temp.getName().equals("<SEMICOLON>"))
        {
            semicolon = tokenQueue.poll();
        }
        else
        {
            System.out.println("Syntax error no Semeicolon");
        }
        return new Var_Decl(type_spec,id,extend,semicolon);
    }

    private Var_Decl_Extend var_decl_extend() {
        Token temp = tokenQueue.peek();
        Token token1=null,token2=null;

        if(temp != null && temp.getName().equals("<RIGHT_SQUARE_B>"))
        {
            token1 = tokenQueue.poll();
            temp = tokenQueue.peek();

            if(temp != null && temp.getName().equals("<LEFT_SQUARE_B>"))
                token2 = tokenQueue.poll();
        }
        return new Var_Decl_Extend(token1,token2);
    }

    private Type_Spec type_spec() {
        Token temp = tokenQueue.peek();
        if(temp == null){
            System.out.println("Syntax error no more tokens");
            return null;
        }
        else if(temp.getName().equals("<VOID>") || temp.getName().equals("<BOOL>") || temp.getName().equals("<INT>") || temp.getName().equals("<FLOAT>"))
             return new Type_Spec(tokenQueue.poll());
        else
            return null;
    }
}
