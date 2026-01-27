package Model.Exp;

import Model.Exception.MyException;
import Model.Heap.IHeap;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class ValueExp implements Exp {
    private Value e;

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp) throws MyException {
        return e;
    }
    public String  getType() {
        if  (e instanceof IntValue)
        {
            Type t = ((IntValue)e).getType();
            return t.toString();
        }
        else if (e instanceof BoolValue)
        {
            Type t = ((BoolValue)e).getType();
            return t.toString();
        }
        return new String("");
    }
    public ValueExp(Value e) {
        this.e = e;
    }
    @Override
    public String toString() {
        return e.toString();
    }
    public Exp deepCopy(){
        return new ValueExp(e.deepCopy());
    }
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return e.getType();
    }
}
