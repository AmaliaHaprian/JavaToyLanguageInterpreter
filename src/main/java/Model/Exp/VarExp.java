package Model.Exp;

import Model.Exception.Expression.UndefinedException;
import Model.Exception.MyException;
import Model.Heap.IHeap;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public class VarExp implements Exp{
    private String id;
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp) throws MyException {
        Value val=tbl.lookup(id);
        if(val==null){
            throw new UndefinedException("Variable "+id+" is not defined");
        }
        return val;
    }
    public VarExp(String id) {
        this.id = id;
    }
    @Override
    public String toString() {
        return id;
    }
    public Exp deepCopy() {
        return new VarExp(id);
    }
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv.lookup(id);
    }
}
