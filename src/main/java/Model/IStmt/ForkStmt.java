package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.ExeStack.MyStack;
import Model.Heap.IHeap;
import Model.Out.MyIList;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

import static java.lang.IO.println;

public class ForkStmt implements IStmt {
    private IStmt stmt;
    public String toString(){
        return "fork("+stmt+")";
    }
    public ForkStmt(IStmt stmt){
        this.stmt = stmt;
    }
    public IStmt deepCopy(){
        return new ForkStmt(stmt.deepCopy());
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> newStack=new MyStack<>();
        //newStack.push(stmt);
        MyIDictionary<String,Value> newSymTbl= state.getSymtbl().deepCopy();
        IHeap<Integer, Value> newHeap=state.getHeap();
        MyIDictionary<StringValue, BufferedReader> newFileTable=state.getFileTable();
        MyIList<Value> newOut=state.getOut();
        PrgState newState=new PrgState(newStack,newSymTbl,newOut,stmt,newFileTable,newHeap, state.getLockTable() );
        return newState;
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }
}
