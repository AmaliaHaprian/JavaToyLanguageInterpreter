package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.LatchTable.ILatchTable;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NewLatchStmt implements IStmt {
    private String var;
    private Exp exp;
    private static final Lock lock = new ReentrantLock();

    public NewLatchStmt(String var, Exp exp) {
        this.var = var;
        this.exp = exp;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        MyIStack<IStmt> stk=state.getStk();
        ILatchTable<Integer,Integer> latchTable=state.getLatchTable();
        Value val=exp.eval(tbl,heap);
        if(!(val.getType() instanceof IntType))
            throw new MyException("Exp is not an integer");
        Integer num1=((IntValue)val).getVal();
        Integer location= latchTable.getLocation();
        latchTable.add(location,num1);

        if(!(tbl.isDefined(var) && tbl.lookup(var).getType() instanceof IntType ))
            throw new MyException("Variable "+var+" is not an integer");
        tbl.update(var, new IntValue(location));
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new  NewLatchStmt(var,exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typVar=typeEnv.lookup(var);
        if(!(typVar instanceof IntType))
            throw new MyException("Variable "+var+" is not an integer");
        Type typExp=exp.typecheck(typeEnv);
        if(typExp instanceof IntType)
            return typeEnv;
        else
            throw new MyException("Type of "+exp+" is not an integer");
    }

    @Override
    public String toString(){
        return "newLatch("+this.var+","+this.exp+")";
    }
}
