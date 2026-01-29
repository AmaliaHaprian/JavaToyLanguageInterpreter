package Model.IStmt;

import Model.Exception.MyException;
import Model.Exp.Exp;
import Model.Exp.ValueExp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SemaphoreTable.ISemaphoreTable;
import Model.SemaphoreTable.SemaphoreTable;
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

public class CreateSemaphoreStmt implements IStmt{
    private String var;
    private Exp exp;
    private static final Lock lock=new ReentrantLock();

    public CreateSemaphoreStmt(String var, Exp exp){
        this.var=var;
        this.exp=exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        lock.lock();
        MyIDictionary<String, Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        ISemaphoreTable<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable= state.getSemaphoreTable();
        Value val= exp.eval(tbl,heap);
        if(!(val.getType() instanceof IntType)){
            throw new MyException("Value of exp is not an integer");
        }
        IntValue intValue=(IntValue)val;
        Integer location=semaphoreTable.getFreeLocation();
        semaphoreTable.add(location, new Pair<>(intValue.getVal(), new ArrayList<Integer>()));

        if(!(tbl.isDefined(var) && tbl.lookup(var).getType() instanceof IntType))
            throw new MyException("Variable "+var+" is not an integer");

        tbl.update(var, new IntValue(location));
        lock.unlock();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CreateSemaphoreStmt(var, exp.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typVar=typeEnv.lookup(var);
        Type typExp=exp.typecheck(typeEnv);
        if (typVar instanceof IntType) {
            if(typExp instanceof IntType)
                return typeEnv;
            else throw new MyException("Type of exp is not an integer");
        }
        else throw new MyException("Variable "+var+" is not an integer");
    }

    @Override
    public String toString(){
        return "createSemaphore("+var+","+exp.toString()+")";
    }
}
