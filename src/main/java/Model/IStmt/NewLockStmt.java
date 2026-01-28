package Model.IStmt;

import Model.Exception.MyException;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.LockTable.ILockTable;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLockStmt implements IStmt {
    private String var;
    private static final Lock lock=new ReentrantLock();

    public NewLockStmt(String var){
        this.var = var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> tbl= state.getSymtbl();
        ILockTable<Integer,Integer> lockTable = state.getLockTable();
        Integer location=lockTable.getFreeLocation();
        lockTable.add(location,-1);
        if (tbl.isDefined(var) && tbl.lookup(var).getType() instanceof IntType){
            tbl.update(var,new IntValue(location));
            lock.unlock();
            return null;
        }
        else throw new MyException("Variable "+var+" is not defined");
    }

    @Override
    public IStmt deepCopy() {
        return new NewLockStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var) instanceof IntType){
            return typeEnv;
        }
        else throw new MyException("Variable "+var+" is not an integer");
    }

    @Override
    public String toString(){
        return "newLock("+var+")";
    }
}
