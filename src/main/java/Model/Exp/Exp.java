package Model.Exp;

import Model.Exception.MyException;
import Model.Heap.IHeap;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public interface Exp {
    Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer,Value> hp) throws MyException;
    @Override
    String toString();
    Exp deepCopy();
    Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
