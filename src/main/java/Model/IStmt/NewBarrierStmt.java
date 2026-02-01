package Model.IStmt;

import Model.BarrierTable.IBarrierTable;
import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
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

public class NewBarrierStmt implements IStmt {
    private String var;
    private Exp exp;
    private static final Lock lock=new ReentrantLock();

    public NewBarrierStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String,Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        MyIStack<IStmt> stk=state.getStk();
        IBarrierTable<Integer, Pair<Integer, Vector<Integer>>> barrierTable=state.getBarrierTable();

        Value val=exp.eval(tbl,heap);
        Integer num=((IntValue)val).getVal();
        Integer location=barrierTable.getFreeLocation();
        barrierTable.add(location,new Pair<>(num,new Vector<Integer>()));

        if(tbl.isDefined(var))
            tbl.update(var,new IntValue(location));
        else
            tbl.add(var,new IntValue(location));
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewBarrierStmt(var, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typVar=typeEnv.lookup(var);
        if(!(typVar instanceof IntType))
            throw new MyException("Type variable "+var+" is not an integer");

        Type typExp= exp.typecheck(typeEnv);
        if(!(typExp instanceof IntType))
            throw new MyException("Type variable of expression is not an integer");
        return typeEnv;
    }

    @Override
    public String toString(){
        return "newBarrier( "+var+","+exp.toString()+")";
    }
}
