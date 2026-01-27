package Interpreter;

import Controller.Controller;
import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.ExeStack.MyStack;
import Model.Exp.*;
import Model.Heap.Heap;
import Model.Heap.IHeap;
import Model.IStmt.*;
import Model.Out.MyIList;
import Model.Out.MyList;
import Model.PrgState.PrgState;
import Model.SymTable.MyDictionary;
import Model.SymTable.MyIDictionary;
import Model.Type.*;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;
import Repository.IRepository;
import Repository.Repository;
import View.ExitCommand;
import View.RunExample;
import View.TextMenu;

import java.io.BufferedReader;

//  ------ A2 ----
// Started 16 oct 25 - 5 hours
//         18 oct 25 - 3 --90%done
//         20 oct 25 - 2
//         25 oct 25 - 1
//  ------- A3 ----
//         30 oct 25 - 3'30
//         31 oct 25 -  '30
//  ------- A4 + A5 ----
//         10 nov 25 -  '30
//         13 nov 25 -  '30
//         17 nov 25 - 2
//         18 nov 25 - 3'30
//         19 nov 25 - 1
//         24 nov 25 -  '30
//  ------- A6 ----
//         2  dec 25 - 1'20

public class Interpreter {
    public static void main(String[] args) {
        MyIStack<IStmt> stk1=new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl1=new MyDictionary<String, Value>();
        MyIList<Value> out1=new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> fileTbl=new MyDictionary<StringValue, BufferedReader>();
        IHeap<Integer,Value> heap1=new Heap<Integer,Value>();
        IStmt ex1=new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));
        //stk1.push(ex1);
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<>();
            ex1.typecheck(typeEnv);
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return;
        }
        PrgState prg1= null;
        try {
            prg1 = new PrgState(stk1, symtbl1, out1,ex1, fileTbl,heap1);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        IRepository repo1=new Repository(prg1, "log1.txt");
        Controller ctr1=new Controller(repo1);

        MyIStack<IStmt> stk2=new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl2=new MyDictionary<String, Value>();
        MyIList<Value> out2=new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> fileTbl2=new MyDictionary<StringValue, BufferedReader>();
        IHeap<Integer,Value> heap2=new Heap<Integer,Value>();
        ArithExp arithExpr1=new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), '*');
        ArithExp arithExpr2=new ArithExp(new ValueExp(new IntValue(2)), arithExpr1, '+');
        AssignStmt assignStmt1=new AssignStmt("a", arithExpr2);
        AssignStmt assignStmt2=new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)),'+'));
        IStmt ex2=new CompStmt(new VarDeclStmt("a", new IntType()),
            new CompStmt(new VarDeclStmt("b", new IntType()),
            new CompStmt(assignStmt1, new CompStmt(assignStmt2, new PrintStmt(new VarExp("b"))))));
        //stk2.push(ex2);
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<>();
            ex2.typecheck(typeEnv);
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return;
        }
        PrgState prg2= null;
        try {
            prg2 = new PrgState(stk2, symtbl2, out2,ex2,fileTbl2,heap2);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        IRepository repo2=new Repository(prg2, "log2.txt");
        Controller ctr2=new Controller(repo2);

        MyIStack<IStmt> stk3=new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl3=new MyDictionary<String, Value>();
        MyIList<Value> out3=new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> fileTbl3=new MyDictionary<StringValue, BufferedReader>();
        IHeap<Integer,Value> heap3=new Heap<Integer,Value>();
        VarDeclStmt varDeclStmt1=new VarDeclStmt("a", new BoolType());
        VarDeclStmt varDeclStmt2=new VarDeclStmt("v", new IntType());
        AssignStmt assignStmt3=new AssignStmt("a", new ValueExp(new BoolValue(true)));
        IfStmt ifStmt1=new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3))));
        CompStmt compStmt1=new CompStmt(ifStmt1, new PrintStmt(new VarExp("v")));
        CompStmt compStmt2=new CompStmt(assignStmt3, compStmt1);
        CompStmt compStmt3=new CompStmt(varDeclStmt2, compStmt2);
        IStmt ex3=new CompStmt(varDeclStmt1, compStmt3);
        //stk3.push(ex3);
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<>();
            ex3.typecheck(typeEnv);
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return;
        }
        PrgState prg3= null;
        try {
            prg3=new PrgState(stk3, symtbl3, out3,ex3,fileTbl3,heap3);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        IRepository repo3=new Repository(prg3, "log3.txt");
        Controller ctr3=new Controller(repo3);

        MyIStack<IStmt> stk4=new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl4=new MyDictionary<String, Value>();
        MyIList<Value> out4=new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> fileTbl4=new MyDictionary<StringValue, BufferedReader>();
        IHeap<Integer,Value> heap4=new Heap<Integer,Value>();

        VarDeclStmt varDeclStmt3=new VarDeclStmt("varf", new StringType());
        AssignStmt assignStmt4=new AssignStmt("varf", new ValueExp(new StringValue("test.in")));
        OpenRFileStmt openRFileStmt1=new OpenRFileStmt(new VarExp("varf"));
        VarDeclStmt varDeclStmt4=new VarDeclStmt("varc", new IntType());
        ReadFileStmt readFileStmt1=new ReadFileStmt(new VarExp("varf"), "varc");
        PrintStmt printStmt1=new PrintStmt(new VarExp("varc"));
        ReadFileStmt readFileStmt2=new ReadFileStmt(new VarExp("varf"), "varc");
        PrintStmt printStmt2=new PrintStmt(new VarExp("varc"));
        CloseRFileStmt closeRFileStmt1=new CloseRFileStmt(new VarExp("varf"));

        IStmt ex4=new CompStmt(varDeclStmt3,
                new CompStmt(assignStmt4,
                        new CompStmt(openRFileStmt1,
                                new CompStmt(varDeclStmt4,
                                        new CompStmt(readFileStmt1,
                                                new CompStmt(printStmt1,
                                                        new CompStmt(readFileStmt2,
                                                                new CompStmt(printStmt2,closeRFileStmt1))))))));
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<>();
            ex4.typecheck(typeEnv);
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return;
        }
        PrgState prg4= null;
        try {
            prg4=new PrgState(stk4, symtbl4, out4,ex4,fileTbl4,heap4);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        IRepository repo4=new Repository(prg4, "log4.txt");
        Controller ctr4=new Controller(repo4);

        MyIStack<IStmt> stk5=new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl5=new MyDictionary<String, Value>();
        MyIList<Value> out5=new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> fileTbl5=new MyDictionary<StringValue, BufferedReader>();
        IHeap<Integer,Value> heap5=new Heap<Integer,Value>();

        VarDeclStmt varDeclStmt5=new VarDeclStmt("v", new RefType(new IntType()));
        NewStmt newStmt1=new NewStmt("v", new ValueExp(new IntValue(20)));
        VarDeclStmt varDeclStmt6=new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        NewStmt newStmt2=new NewStmt("a", new VarExp("v"));
        IStmt ex5=new CompStmt(varDeclStmt5,
                new CompStmt(newStmt1,
                        new CompStmt(varDeclStmt6,
                                new CompStmt(newStmt2,
                                new CompStmt(new PrintStmt(new VarExp("v")),
                                        new PrintStmt(new VarExp("a")))))));
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<>();
            ex5.typecheck(typeEnv);
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return;
        }
        PrgState prg5 = null;
        try {
            prg5 = new PrgState(stk5, symtbl5, out5, ex5, fileTbl5, heap5);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        IRepository repo5 = new Repository(prg5, "log5.txt");
        Controller ctr5 = new Controller(repo5);

        //read heap
        MyIStack<IStmt> stk6=new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl6=new MyDictionary<String, Value>();
        MyIList<Value> out6=new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> fileTbl6=new MyDictionary<StringValue, BufferedReader>();
        IHeap<Integer,Value> heap6=new Heap<Integer,Value>();

        VarDeclStmt varDeclStmt8=new VarDeclStmt("v", new RefType(new IntType()));
        NewStmt newStmt3=new NewStmt("v", new ValueExp(new IntValue(20)));
        VarDeclStmt varDeclStmt7=new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        NewStmt newStmt4=new NewStmt("a", new VarExp("v"));
        ReadHeapExp rH2=new ReadHeapExp(new VarExp("v"));
        ReadHeapExp rH3=new ReadHeapExp(new VarExp("a"));
        IStmt ex6=new CompStmt(varDeclStmt8,
                new CompStmt(newStmt3,
                        new CompStmt(varDeclStmt7,
                               new CompStmt(newStmt4,
                                       new CompStmt(new PrintStmt(rH2),
                                               new PrintStmt(new ArithExp(new ReadHeapExp(rH3), new ValueExp(new IntValue(5)), '+')))))));
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<>();
            ex6.typecheck(typeEnv);
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return;
        }
        PrgState prg6= null;
        try {
            prg6=new PrgState(stk6, symtbl6, out6,ex6,fileTbl6,heap6);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        IRepository repo6=new Repository(prg6, "log6.txt");
        Controller ctr6=new Controller(repo6);

        //write heap
        MyIStack<IStmt> stk7=new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl7=new MyDictionary<String, Value>();
        MyIList<Value> out7=new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> fileTbl7=new MyDictionary<StringValue, BufferedReader>();
        IHeap<Integer,Value> heap7=new Heap<Integer,Value>();

        VarDeclStmt varDeclStmt11=new VarDeclStmt("v", new RefType(new IntType()));
        NewStmt newStmt5=new NewStmt("v", new ValueExp(new IntValue(20)));
        ReadHeapExp rH4=new ReadHeapExp(new VarExp("v"));
        WriteHeapStmt wH2=new WriteHeapStmt("v", new ValueExp(new IntValue(30)));
        ReadHeapExp rH5=new ReadHeapExp(new VarExp("v"));
        IStmt ex7=new CompStmt(varDeclStmt11,
                new CompStmt(newStmt5,
                        new CompStmt(new PrintStmt(rH4),
                                new CompStmt(wH2,
                                        new PrintStmt(new ArithExp(rH5, new ValueExp(new IntValue(5)),'+'))))));
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<>();
            ex7.typecheck(typeEnv);
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return;
        }
        PrgState prg7= null;
        try {
            prg7=new PrgState(stk7, symtbl7, out7,ex7,fileTbl7,heap7);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        IRepository repo7=new Repository(prg7, "log7.txt");
        Controller ctr7=new Controller(repo7);

        //garbage collector
        MyIStack<IStmt> stk8=new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl8=new MyDictionary<String, Value>();
        MyIList<Value> out8=new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> fileTbl8=new MyDictionary<StringValue, BufferedReader>();
        IHeap<Integer,Value> heap8=new Heap<Integer,Value>();

        VarDeclStmt varDeclStmt9=new VarDeclStmt("v", new RefType(new IntType()));
        VarDeclStmt varDeclStmt10=new VarDeclStmt("a", new RefType(new RefType(new IntType())));
        NewStmt newStmt6=new NewStmt("v", new ValueExp(new IntValue(20)));
        NewStmt newStmt7=new NewStmt("a", new VarExp("v"));
        NewStmt newStmt8=new NewStmt("v", new ValueExp(new IntValue(30)));
        IStmt ex8=new CompStmt(varDeclStmt9,
                        new CompStmt(newStmt6,
                                new CompStmt(varDeclStmt10,
                                        new CompStmt(newStmt7,
                                                new CompStmt(newStmt8,
                                                        new PrintStmt(new ReadHeapExp(new ReadHeapExp(new VarExp("a")))))))));
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<>();
            ex8.typecheck(typeEnv);
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return;
        }
        PrgState prg8= null;
        try {
            prg8=new PrgState(stk8, symtbl8, out8,ex8,fileTbl8,heap8);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        IRepository repo8=new Repository(prg8, "log8.txt");
        Controller ctr8=new Controller(repo8);

        //while
        MyIStack<IStmt> stk9=new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl9=new MyDictionary<String, Value>();
        MyIList<Value> out9=new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> fileTbl9=new MyDictionary<StringValue, BufferedReader>();
        IHeap<Integer,Value> heap9=new Heap<Integer,Value>();

        IStmt ex9=new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                        new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-')))),
                        new PrintStmt(new VarExp("v"))
                        )));

        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<>();
            ex9.typecheck(typeEnv);
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return;
        }
        PrgState prg9= null;
        try {
            prg9=new PrgState(stk9, symtbl9, out9,ex9,fileTbl9,heap9);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        IRepository repo9=new Repository(prg9, "log9.txt");
        Controller ctr9=new Controller(repo9);

        //fork
        MyIStack<IStmt> stk10=new MyStack<IStmt>();
        MyIDictionary<String, Value> symtbl10=new MyDictionary<String, Value>();
        MyIList<Value> out10=new MyList<Value>();
        MyIDictionary<StringValue, BufferedReader> fileTbl10=new MyDictionary<StringValue, BufferedReader>();
        IHeap<Integer,Value> heap10=new Heap<Integer,Value>();

        IStmt ex10=new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new VarDeclStmt("a", new RefType(new IntType())),
                        new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(10))),
                                new CompStmt(new NewStmt("a", new ValueExp(new IntValue(22))),
                                        new CompStmt(new ForkStmt(new WriteHeapStmt("a", new ValueExp(new IntValue(30)))),
                                                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(32))),
                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("a"))),
                                                                        new CompStmt(new PrintStmt(new VarExp("v")),
                                                                                new PrintStmt(new ReadHeapExp(new VarExp("a"))))))))))));
//        IStmt ex8=new ForkStmt(new PrintStmt(new ValueExp(new IntValue(20))));
        try{
            MyIDictionary<String, Type> typeEnv=new MyDictionary<>();
            ex10.typecheck(typeEnv);
        }
        catch(MyException e){
            System.out.println(e.getMessage());
            return;
        }
        PrgState prg10= null;
        try {
            prg10=new PrgState(stk10, symtbl10, out10,ex10,fileTbl10,heap10);
        } catch (MyException e) {
            System.out.println(e.getMessage());
        }
        IRepository repo10=new Repository(prg10, "log10.txt");
        Controller ctr10=new Controller(repo10);


        TextMenu menu=new TextMenu();
        menu.addCommand(new ExitCommand("0", "exit"));
        menu.addCommand(new RunExample("1", ex1.toString(), ctr1));
        menu.addCommand(new RunExample("2", ex2.toString(), ctr2));
        menu.addCommand(new RunExample("3", ex3.toString(), ctr3));
        menu.addCommand(new RunExample("4", ex4.toString(), ctr4));
        menu.addCommand(new RunExample("5", ex5.toString(), ctr5));
        menu.addCommand(new RunExample("6", ex6.toString(), ctr6));
        menu.addCommand(new RunExample("7", ex7.toString(), ctr7));
        menu.addCommand(new RunExample("8", ex8.toString(), ctr8));
        menu.addCommand(new RunExample("9", ex9.toString(), ctr9));
        menu.addCommand(new RunExample("10", ex10.toString(), ctr10));
        menu.show();
    }
}
