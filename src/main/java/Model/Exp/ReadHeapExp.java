package Model.Exp;

import Model.Exception.Expression.TypeException;
import Model.Exception.Expression.UndefinedException;
import Model.Exception.MyException;
import Model.Heap.IHeap;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class ReadHeapExp implements Exp {
    private Exp exp;
    public ReadHeapExp(Exp exp) {
        this.exp = exp;
    }
    public Exp deepCopy(){
        return new ReadHeapExp(exp.deepCopy());
    }
    public String toString(){
        return "rH("+exp.toString()+")";
    }
    public Value eval(MyIDictionary<String,Value> symTbl, IHeap<Integer, Value> hp) throws MyException {
        Value val=exp.eval(symTbl,hp );
        if (val instanceof RefValue){
            RefValue rval=(RefValue)val;
            int addr=rval.getAddr();
            if (hp.isDefined(addr)){
                return hp.lookup(addr);
            }
            else throw new UndefinedException("Key"+addr+" is not defined");

        }
        else throw new TypeException("Expression did not evaluate to a RefValue");
    }
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ=exp.typecheck(typeEnv);
        if (typ instanceof RefType){
            RefType reft=(RefType)typ;
            return reft.getInner();
        }
        else throw new TypeException("The rH argument is not a RefType");
    }
}
