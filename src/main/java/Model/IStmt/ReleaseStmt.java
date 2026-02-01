package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SemaphoreTable.ISemaphoreTable;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;
import Tuple.Tuple;

public class ReleaseStmt implements IStmt{
    private String var;

    public ReleaseStmt(String var){
        this.var=var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        MyIStack<IStmt> stk=state.getStk();
        ISemaphoreTable<Integer, Tuple> semTbl=state.getSemaphoreTable();

        if(!(tbl.isDefined(var) && tbl.lookup(var).getType() instanceof IntType))
            throw new MyException("Variable is not an integer");
        Integer foundIndex=((IntValue)tbl.lookup(var)).getVal();
        if(!(semTbl.isDefined(foundIndex)))
            throw new MyException("Variable doesn't correspond to an index");
        Tuple t=semTbl.lookup(foundIndex);
        if(t.getSecond().contains(state.getPersonalId())){
            t.getSecond().remove((Integer) state.getPersonalId());
        }
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReleaseStmt(var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typVal=typeEnv.lookup(var);
        if(!(typVal instanceof IntType))
            throw new MyException("Var is not an integer");
        return typeEnv;
    }

    @Override
    public String toString(){
        return "release("+var+")";
    }
}
