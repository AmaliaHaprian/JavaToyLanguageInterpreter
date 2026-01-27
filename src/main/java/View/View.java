package View;

import Controller.Controller;
import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.ExeStack.MyStack;
import Model.Exp.ArithExp;
import Model.Exp.ValueExp;
import Model.Exp.VarExp;
import Model.IStmt.*;
import Model.Out.MyIList;
import Model.Out.MyList;
import Model.PrgState.PrgState;
import Model.SymTable.MyDictionary;
import Model.SymTable.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.Scanner;

public class View {
    private Controller controller;
    public View(Controller controller) {
        this.controller=controller;
    }
    public void menu(){
        System.out.println("\t\tMENU");
        System.out.println("1. Input a program");
        System.out.println("2. Complete execution");
    }

    public void run(){
        Scanner sc=new Scanner(System.in);
        while(true){
            this.menu();
            System.out.print(">>");
            int option=sc.nextInt();
            if(option==1){
                System.out.println("Choose a program to run");
                System.out.println("1. int v;\n v=2;\n Print(v);");
                System.out.println("2. int a;\n int b;\n a=2+3*5;\n b=a+1;\n Print(b);");
                System.out.println("3. bool a;\n int v;\n a=true;\n If a Then v=2 Else v=3\n Print(v);");
                System.out.println("4. int a;\n int b;\n b=0;\n a=a/b;");
                System.out.println("5. int a;\n int a;");
                System.out.print(">>");
                int prg=sc.nextInt();
                controller.setCrtPrg(prg-1);
            }
            else if(option==2){
                try{
                controller.allStep();}
                catch(MyException e)
                    {
                    System.out.println(e.getMessage());
                    }
            }
        }
    }
}
