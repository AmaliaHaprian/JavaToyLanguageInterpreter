package com.example.mapgui;

import Controller.Controller;
import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Heap.IHeap;
import Model.IStmt.IStmt;
import Model.Out.MyIList;
import Model.PrgState.PrgState;
import Model.ProcTable.IProcTable;
import Model.SymTable.MyIDictionary;
import Model.Value.StringValue;
import Model.Value.Value;
import Pair.Pair;
import javafx.beans.property.ReadOnlyIntegerWrapper;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ViewProgramController{
    private Controller controller;

    @FXML
    private TextField prgStatesNumber;

    @FXML
    private TableView<Pair<Integer, Value>> heapTable;

    @FXML
    private ListView<Value> outList;

    @FXML
    private ListView<StringValue> fileTable;

    @FXML
    private ListView<Integer> prgStatesList;

    @FXML
    private TableView<Pair<String, Value>> symTable;

    @FXML
    private ListView<IStmt> exeStack;

    @FXML
    private Button runOneStepButton;

    @FXML
    private TableView<Pair<String, Pair<List<String>, IStmt>>> procTable;

    public void setController(Controller controller) {
        this.controller=controller;
        this.init();
    }

    //symTable
    @FXML
    private TableColumn<Pair<String,Value>, String> variableNameColumn;
    @FXML
    private TableColumn<Pair<String,Value>, String> valueColumnSymTbl;

    //heapTable
    @FXML
    private TableColumn<Pair<Integer,Value>, Integer> addressColumn;
    @FXML
    private TableColumn<Pair<Integer,Value>, String> valueColumnHeapTbl;

    //procTable
    @FXML
    private TableColumn<Pair<String,Pair<List<String>, IStmt>>, String> procNameColumn;
    @FXML
    private TableColumn<Pair<String,Pair<List<String>, IStmt>>, List<String>> paramColumn;
    @FXML
    private TableColumn<Pair<String,Pair<List<String>, IStmt>>, String> bodyColumn;

    @FXML
    private void initialize(){
        this.prgStatesList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        variableNameColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getFirst()));
        valueColumnSymTbl.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getSecond().toString()));

        addressColumn.setCellValueFactory(cellData ->
                new ReadOnlyIntegerWrapper(cellData.getValue().getFirst()).asObject());
        valueColumnHeapTbl.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getSecond().toString()));

        procNameColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getFirst()));
        paramColumn.setCellValueFactory(cellData ->
                new ReadOnlyObjectWrapper<>(cellData.getValue().getSecond().getFirst()));
        bodyColumn.setCellValueFactory(cellData ->
                new ReadOnlyStringWrapper(cellData.getValue().getSecond().getSecond().toString()));
    }
    public void init() {
        List<PrgState> prgStates=controller.getPrgStates();
        this.prgStatesNumber.setText(String.valueOf(prgStates.size()));
        this.populatePrgStatesList();
        this.populateHeapTable();
        this.populateOutList();
        this.populateFileTable();
        this.populateProcTable();
    }

    public PrgState getSelectedPrgState(){
        Integer crtId=prgStatesList.getSelectionModel().getSelectedItem();
        if (crtId==null) return null;
        PrgState selectedPrgState= controller.getPrgById(crtId);
        return selectedPrgState;
    }

    public void populatePrgStatesList(){
        this.prgStatesList.getItems().clear();
        for(PrgState prgState:controller.getPrgStates()){
            this.prgStatesList.getItems().add(prgState.getPersonalId());
        }
    }
    public void populateHeapTable(){
        //PrgState selectedPrgState=getSelectedPrgState();
        this.heapTable.getItems().clear();
        PrgState crtPrg=controller.getPrgStates().getFirst();
        IHeap<Integer,Value> heap=crtPrg.getHeap();
        for(Map.Entry<Integer,Value> entry: heap.getContent().entrySet()){
            this.heapTable.getItems().add(new Pair<>(entry.getKey(), entry.getValue()));
        }
    }
    public void populateProcTable(){
        this.procTable.getItems().clear();
        PrgState crtPrg=controller.getPrgStates().getFirst();
        IProcTable procTbl=crtPrg.getProcTable();
        for(Map.Entry<String,Pair<List<String>, IStmt>> entry: procTbl.getContent().entrySet()){
            this.procTable.getItems().add(new Pair<String, Pair<List<String>, IStmt>>(entry.getKey(), entry.getValue()));
        }
    }
    public void populateOutList() {
        //PrgState selectedPrgState = getSelectedPrgState();
        this.outList.getItems().clear();
        PrgState crtPrg=controller.getPrgStates().getFirst();
        MyIList<Value> out = crtPrg.getOut();
        for (Value v : out.getList()) {
            this.outList.getItems().add(v);
        }
    }
    public void populateFileTable(){
        //PrgState selectedPrgState=getSelectedPrgState();
        this.fileTable.getItems().clear();
        PrgState crtPrg=controller.getPrgStates().getFirst();
        MyIDictionary<StringValue, BufferedReader> fileTbl=crtPrg.getFileTable();
        Set<StringValue> fileNames=fileTbl.getKeys();
        for(StringValue fileName:fileNames){
            this.fileTable.getItems().add(fileName);
        }
    }

    public void populateSymTable(){
        this.symTable.getItems().clear();
        PrgState selectedPrgState=getSelectedPrgState();
        if(selectedPrgState==null) return;
        MyIDictionary<String, Value> symtbl=selectedPrgState.getSymtbl();
        for(Map.Entry<String,Value> entry: symtbl.getContent().entrySet()){
            this.symTable.getItems().add(new Pair<>(entry.getKey(), entry.getValue()));
        }
    }

    public void populateExeStack(){
        this.exeStack.getItems().clear();
        PrgState selectedPrgState=getSelectedPrgState();
        if(selectedPrgState==null) return;
        MyIStack<IStmt> stk=selectedPrgState.getStk();
        for (IStmt istmt: stk.stackToList()){
            this.exeStack.getItems().add(istmt);
        }
    }

    @FXML
    public void prgSelectedHandler(){
        this.populateSymTable();
        this.populateExeStack();
        this.populatePrgStatesList();
    }

    @FXML
    public void runOneStepHandler() {
        List<PrgState> prgStates=controller.getPrgStates();
        try{


                this.controller.oneStep();
                populateHeapTable();
                populateOutList();
                populateFileTable();
                populateProcTable();
                this.symTable.getItems().clear();
                this.exeStack.getItems().clear();
                prgStates = controller.removeCompletedPrg(controller.getPrgStates());
                controller.setPrgStates(prgStates);
                this.prgStatesNumber.setText(String.valueOf(prgStates.size()));
                populatePrgStatesList();


            if (prgStates.isEmpty()){
                this.runOneStepButton.setDisable(true);
                populatePrgStatesList();
            }
        }
        catch (MyException e){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(e.getMessage());
            alert.showAndWait();
        }
    }
}