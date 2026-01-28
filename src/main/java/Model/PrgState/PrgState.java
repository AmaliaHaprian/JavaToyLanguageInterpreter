package Model.PrgState;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Heap.IHeap;
import Model.IStmt.IStmt;
import Model.LockTable.ILockTable;
import Model.Out.MyIList;
import Model.SymTable.MyIDictionary;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class PrgState {
    static int nextId;

    private int id;
    private MyIStack<IStmt> exeStack;
    private MyIDictionary<String, Value> symTable;
    private MyIList<Value> out;
    IStmt originalProgram;
    private MyIDictionary<StringValue, BufferedReader> fileTable;
    private IHeap<Integer,Value> heap;
    private ILockTable<Integer,Integer> lockTable;

    public PrgState(MyIStack<IStmt> stk, MyIDictionary<String, Value> symtbl, MyIList<Value> ot, IStmt prg, MyIDictionary<StringValue, BufferedReader> fileTable, IHeap<Integer,Value> heap, ILockTable<Integer, Integer> lockTable) throws MyException {
        this.exeStack = stk;
        this.symTable = symtbl;
        this.out = ot;
        this.originalProgram = deepCopy(prg);
        this.fileTable = fileTable;
        this.heap = heap;
        this.lockTable = lockTable;
        this.id=getId();
        stk.push(prg);
    }

    public int getPersonalId(){ return id; }
    public MyIStack<IStmt> getStk(){
        return this.exeStack;
    }
    public MyIDictionary<String, Value> getSymtbl(){
        return this.symTable;
    }
    public MyIList<Value> getOut(){
        return this.out;
    }
    public IStmt getOriginalProgram(){
        return this.originalProgram;
    }
    public MyIDictionary<StringValue, BufferedReader> getFileTable(){
        return this.fileTable;
    }
    public IHeap<Integer,Value> getHeap(){ return this.heap; }
    public ILockTable<Integer, Integer> getLockTable(){ return this.lockTable; }
    public void setStk(MyIStack<IStmt> stk){
        this.exeStack = stk;
    }
    public void setSymtbl(MyIDictionary<String, Value> symtbl){
        this.symTable = symtbl;
    }
    public void setOut(MyIList<Value> out){
        this.out = out;
    }

    IStmt deepCopy(IStmt prg){
        return prg.deepCopy();
    }
    @Override
    public String toString() {
        String s="";
        s+="------- Program #"+id+" -------\n";
        MyIStack<IStmt> stk=this.getStk();
        s+="\t    ExeStack:\n";
       // s+=stk.toString();
        s+=stk.print();

        MyIDictionary<String, Value> symtbl=this.getSymtbl();
        s+="\n\t    Symtbl:\n";
        s+=symtbl.toString();

        MyIList<Value> out=this.getOut();
        s+="\t    Out:\n";
        s+=out.toString();

        MyIDictionary<StringValue, BufferedReader> fileTable=this.getFileTable();
        s+="\t    File Table:\n";
        s+=fileTable.toString();

        IHeap<Integer,Value> heap=this.getHeap();
        s+="\t    Heap:\n";
        s+=heap.toString();

        ILockTable<Integer, Integer> lockTable=this.getLockTable();
        s+="\t    Lock Table:\n";
        s+=lockTable.toString();
    return s;
    }

    public Boolean isNotCompleted(){
        return !this.exeStack.isEmpty();
    }
    public PrgState oneStep() throws MyException{
        if(this.exeStack.isEmpty()) throw new MyException("prgstate stack is empty");
        IStmt crtStmt=exeStack.pop();
        return crtStmt.execute(this);
    }

    static synchronized int getId(){
        nextId++;
        return nextId;
    }
}
