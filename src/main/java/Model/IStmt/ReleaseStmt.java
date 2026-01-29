package Model.IStmt;

import Model.Exception.MyException;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SemaphoreTable.ISemaphoreTable;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Pair.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReleaseStmt implements  IStmt {
    private String var;
    private static final Lock lock=new ReentrantLock();

    public ReleaseStmt(String var) {
        this.var = var;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        ISemaphoreTable<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable= state.getSemaphoreTable();

        if(!(tbl.isDefined(var) && tbl.lookup(var).getType() instanceof IntType))
            throw new MyException("Variable "+var+" is not an integer");

        Value val=tbl.lookup(var);
        if (! (val.getType() instanceof IntType))
            throw new MyException("Variable "+var+" is not an integer");
        IntValue location=(IntValue)val;
        int foundIndex=location.getVal();

        if(!(semaphoreTable.isDefined(foundIndex)))
            throw new MyException("Variable "+foundIndex+" is not a key");
        Pair<Integer, ArrayList<Integer>> pair=semaphoreTable.lookup(foundIndex);
        ArrayList<Integer> vector=pair.getSecond();
        if(vector.contains(state.getPersonalId())){
            int pos=vector.indexOf(state.getPersonalId());
            vector.remove(pos);
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReleaseStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typVar=typeEnv.lookup(var);
        if (typVar instanceof IntType) return typeEnv;
        else throw new MyException("Variable "+var+" is not an integer");
    }

    @Override
    public String toString() {
        return "release("+var+")";
    }
}
