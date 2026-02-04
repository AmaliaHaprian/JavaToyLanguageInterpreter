package Model.IStmt;

import Model.BarrierTable.IBarrierTable;
import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Pair.Pair;

import java.util.Vector;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AwaitStmt implements IStmt {
    private String var;
    private static final Lock lock=new ReentrantLock();

    public AwaitStmt(String var) {
        this.var=var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        MyIStack<IStmt> stk=state.getStk();
        IBarrierTable<Integer, Pair<Integer, Vector<Integer>>> barrierTable=state.getBarrierTable();

        if(!(tbl.isDefined(var) && tbl.lookup(var).getType() instanceof IntType))
        {
            lock.unlock();
            throw new MyException("Variable "+var+" is not defined or is not a int");
        }

        Integer foundIndex=((IntValue)tbl.lookup(var)).getVal();
        if(!(barrierTable.isDefined(foundIndex)))
        {
            lock.unlock();
            throw new MyException("Variable "+var+" does not point to an index");
        }
        Pair<Integer, Vector<Integer>> pair=barrierTable.lookup(foundIndex);
        Integer len=pair.getSecond().size();
        if(pair.getFirst()>len){
            if(pair.getSecond().contains(state.getPersonalId()))
                state.getStk().push(this);
            else {
                pair.getSecond().add(state.getPersonalId());
                state.getStk().push(this);
            }
        }
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typVar=typeEnv.lookup(var);
        if(typVar==null)
            throw new MyException("Variable "+var+" is not defined");
        if(!(typVar instanceof IntType))
            throw new MyException("Type variable "+var+" is not an integer");
        return typeEnv;
    }

    @Override
    public String toString(){
        return "await("+ var+")";
    }
}
