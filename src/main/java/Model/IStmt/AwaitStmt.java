package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Heap.IHeap;
import Model.LatchTable.ILatchTable;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class AwaitStmt implements IStmt {
    private String var;

    public AwaitStmt(String var) {
        this.var = var;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        MyIStack<IStmt> stk=state.getStk();
        ILatchTable<Integer,Integer> latchTable=state.getLatchTable();

        if(tbl.isDefined(var) && tbl.lookup(var) instanceof IntType){
            IntValue val=(IntValue) tbl.lookup(var);
            Integer foundIndex=val.getVal();
            if(!(latchTable.isDefined(foundIndex)))
                throw new MyException("Variable not mapped to an actual index in latch table");
            Integer value=latchTable.lookup(foundIndex);
            if(value!=0)
                stk.push(this);
        }
        else throw new MyException("Variable "+var+" is not defined");
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new AwaitStmt(this.var);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typVar=typeEnv.lookup(var);
        if(!(typVar instanceof IntType))
            throw new MyException("Variable "+var+" is not an integer");
        return typeEnv;
    }

    @Override
    public String toString(){
        return "await("+this.var+")";
    }
}
