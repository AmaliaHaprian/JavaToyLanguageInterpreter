package com.example.mapgui;

import Controller.Controller;
import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.ExeStack.MyStack;
import Model.Exp.*;
import Model.Heap.Heap;
import Model.Heap.IHeap;
import Model.IStmt.*;
import Model.LockTable.ILockTable;
import Model.LockTable.LockTable;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class StartingWindowController implements Initializable {
    @FXML
    private ListView<IStmt> programsListView;
    @FXML
    private Button viewButton;

    @FXML
    private void viewProgram(ActionEvent event) {
        IStmt selectedStmt = programsListView.getSelectionModel().getSelectedItem();
        if (selectedStmt==null) {
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Please select a statement");
            alert.showAndWait();
            return;
        }
        int id=programsListView.getSelectionModel().getSelectedIndex();
        try{
            try{
            MyIDictionary<String,Type> typeEnv=new MyDictionary<>();
            selectedStmt.typecheck(typeEnv);}
            catch(Exception e){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText(e.getMessage());
                alert.showAndWait();
                return;
            }

            MyIStack<IStmt> stk=new MyStack<IStmt>();
            MyIDictionary<String, Value> symtbl=new MyDictionary<String, Value>();
            MyIList<Value> out=new MyList<Value>();
            MyIDictionary<StringValue, BufferedReader> fileTbl=new MyDictionary<StringValue, BufferedReader>();
            IHeap<Integer,Value> heap=new Heap<Integer,Value>();
            ILockTable<Integer,Integer> lockTable=new LockTable();
            PrgState prg= null;
            try {
                prg=new PrgState(stk, symtbl, out,selectedStmt,fileTbl,heap,lockTable);
            } catch (MyException e) {
                System.out.println(e.getMessage());
            }
            IRepository repo=new Repository(prg, "log"+(id+1)+".txt");
            Controller ctr=new Controller(repo);


            FXMLLoader loader=new FXMLLoader(getClass().getResource("view-window.fxml"));
            Parent root=loader.load();
            ViewProgramController viewController=loader.getController();
            viewController.setController(ctr);

            System.out.println("Created controller");
            Stage stage=new Stage();
            stage.setTitle("View Program Window");
            stage.setScene(new Scene(root));
            stage.show();
        }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @FXML
    public List<IStmt> getProgramsList() {
        List<IStmt> programs = new ArrayList<IStmt>();

        IStmt ex1=new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(2))), new PrintStmt(new VarExp("v"))));

        ArithExp arithExpr1=new ArithExp(new ValueExp(new IntValue(3)), new ValueExp(new IntValue(5)), '*');
        ArithExp arithExpr2=new ArithExp(new ValueExp(new IntValue(2)), arithExpr1, '+');
        AssignStmt assignStmt1=new AssignStmt("a", arithExpr2);
        AssignStmt assignStmt2=new AssignStmt("b", new ArithExp(new VarExp("a"), new ValueExp(new IntValue(1)),'+'));
        IStmt ex2=new CompStmt(new VarDeclStmt("a", new IntType()),
                new CompStmt(new VarDeclStmt("b", new IntType()),
                        new CompStmt(assignStmt1, new CompStmt(assignStmt2, new PrintStmt(new VarExp("b"))))));

        VarDeclStmt varDeclStmt1=new VarDeclStmt("a", new BoolType());
        VarDeclStmt varDeclStmt2=new VarDeclStmt("v", new IntType());
        AssignStmt assignStmt3=new AssignStmt("a", new ValueExp(new BoolValue(true)));
        IfStmt ifStmt1=new IfStmt(new VarExp("a"), new AssignStmt("v", new ValueExp(new IntValue(2))), new AssignStmt("v", new ValueExp(new IntValue(3))));
        CompStmt compStmt1=new CompStmt(ifStmt1, new PrintStmt(new VarExp("v")));
        CompStmt compStmt2=new CompStmt(assignStmt3, compStmt1);
        CompStmt compStmt3=new CompStmt(varDeclStmt2, compStmt2);
        IStmt ex3=new CompStmt(varDeclStmt1, compStmt3);


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

        IStmt ex9=new CompStmt(new VarDeclStmt("v", new IntType()),
                new CompStmt(new AssignStmt("v", new ValueExp(new IntValue(4))),
                        new CompStmt(
                                new WhileStmt(new RelationalExp(new VarExp("v"), new ValueExp(new IntValue(0)), ">"),
                                        new CompStmt(new PrintStmt(new VarExp("v")), new AssignStmt("v", new ArithExp(new VarExp("v"), new ValueExp(new IntValue(1)), '-')))),
                                new PrintStmt(new VarExp("v"))
                        )));

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

        IStmt ex11=new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("x",  new IntType()),
                                new CompStmt(new VarDeclStmt("q", new IntType()),
                                        new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(20))),
                                                new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))),
                                                        new CompStmt(new NewLockStmt("x"),
                                                                new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(new CompStmt(new LockStmt("x"),
                                                                        new CompStmt(new WriteHeapStmt("v1", new ArithExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)), '-')), new UnlockStmt("x")))),
                                                                        new CompStmt(new LockStmt("x"), new CompStmt(new WriteHeapStmt("v1", new ArithExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(10)), '*')),new UnlockStmt("x"))))
                                                                        ),new CompStmt(new NewLockStmt("q"),
                                                                                        new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(new CompStmt(new LockStmt("q"), new CompStmt( new WriteHeapStmt("v2", new ArithExp(new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(5)),'+')), new UnlockStmt("q")))),
                                                                                                                new CompStmt(new LockStmt("q"), new CompStmt(new WriteHeapStmt("v2", new ArithExp(new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(10)), '*')), new UnlockStmt("q"))))),
                                                                                                new CompStmt(new NopStmt(), new CompStmt(new NopStmt(), new CompStmt(new NopStmt(),new CompStmt(new NopStmt(),
                                                                                                        new CompStmt(new LockStmt("x"), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                                new CompStmt(new UnlockStmt("x"),
                                                                                                                        new CompStmt(new LockStmt("q"), new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v2"))), new UnlockStmt("q"))))))))))))))))))));
        IStmt ex12=new CompStmt(new VarDeclStmt("v1", new RefType(new IntType())),
                new CompStmt(new VarDeclStmt("v2", new RefType(new IntType())),
                        new CompStmt(new VarDeclStmt("x", new IntType()),
                                new CompStmt(new NewStmt("v1", new ValueExp(new IntValue(20))),
                                        new CompStmt(new NewStmt("v2", new ValueExp(new IntValue(30))),
                                                new CompStmt(new NewLockStmt("x"),
                                new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(new CompStmt(new LockStmt("x"),
                                                                                                new CompStmt(new WriteHeapStmt("v1", new ArithExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)), '-')),
                                                                                                        new UnlockStmt("x")))),
                                                                    new CompStmt(new LockStmt("x"),
                                                                            new CompStmt(new WriteHeapStmt("v1", new ArithExp(new ReadHeapExp(new VarExp("v1")), new ValueExp(new IntValue(1)), '+')),
                                                                                    new UnlockStmt("x"))))),
                                        new CompStmt(new ForkStmt(new CompStmt(new ForkStmt(new WriteHeapStmt("v2", new ArithExp(new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(1)), '+'))),
                                                                            new CompStmt(new WriteHeapStmt("v2", new ArithExp(new ReadHeapExp(new VarExp("v2")), new ValueExp(new IntValue(1)), '+')),
                                                                                        new UnlockStmt("x")))),
                                                new CompStmt(new NopStmt(),
                                                        new CompStmt(new NopStmt(),
                                                                new CompStmt(new NopStmt(),
                                                                        new CompStmt(new NopStmt(),
                                                                                new CompStmt(new NopStmt(),
                                                                                        new CompStmt(new NopStmt(),
                                                                                                new CompStmt(new NopStmt(),
                                                                                                        new CompStmt(new NopStmt(),
                                                                                                                new CompStmt(new NopStmt(),
                                                                                                                        new CompStmt(new PrintStmt(new ReadHeapExp(new VarExp("v1"))),
                                                                                                                                new PrintStmt(new ReadHeapExp(new VarExp("v2")))))))))))))))))))));
        programs.add(ex1);
        programs.add(ex2);
        programs.add(ex3);
        programs.add(ex4);
        programs.add(ex5);
        programs.add(ex6);
        programs.add(ex7);
        programs.add(ex8);
        programs.add(ex9);
        programs.add(ex10);
        programs.add(ex11);
        programs.add(ex12);

        return programs;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        programsListView.getItems().addAll(this.getProgramsList());
        programsListView.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }
}
