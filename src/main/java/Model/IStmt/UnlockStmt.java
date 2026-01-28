package Model.IStmt;

import Model.Exception.MyException;
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

public class UnlockStmt implements IStmt {
    private String var;
    private static final Lock lock=new ReentrantLock();
    public UnlockStmt(String var){
        this.var=var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> tbl= state.getSymtbl();
        ILockTable<Integer,Integer> lockTable = state.getLockTable();
        if(tbl.isDefined(var) && tbl.lookup(var).getType() instanceof IntType){
            IntValue location=(IntValue)tbl.lookup(var);
            Integer foundIndex=location.getVal();

            if(lockTable.isDefined(foundIndex)){
                Integer value=lockTable.lookup(foundIndex);
                if(value==state.getPersonalId()){
                    lockTable.update(foundIndex, -1);
                    System.out.println("Released lock "+ var);
                }
                lock.unlock();
                return null;
            }
            else throw new MyException("Index in Lock Table is Not Defined");
        }
        else throw new MyException("Variable "+var+" is not defined");
    }

    @Override
    public IStmt deepCopy() {
        return new UnlockStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        if(typeEnv.lookup(var) instanceof IntType){
            return typeEnv;
        }
        else throw new MyException("Variable "+var+" is not an integer");

    }

    @Override
    public String toString() {
        return "unlock("+var+")";
    }
}
