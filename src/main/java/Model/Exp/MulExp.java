package Model.Exp;

import Model.Exception.MyException;
import Model.Heap.IHeap;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class MulExp implements Exp{
    private Exp exp1,exp2;

    public MulExp(Exp exp1,Exp exp2){
        this.exp1=exp1;
        this.exp2=exp2;
    }

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp) throws MyException {
        Value v1 = exp1.eval(tbl, hp);
        Value v2 = exp2.eval(tbl, hp);
        if(v1.getType() instanceof IntType && v2.getType() instanceof IntType){
            Integer i1=((IntValue) v1).getVal();
            Integer i2=((IntValue) v2).getVal();
            Integer result=i1*i2-(i1+i2);
            return new IntValue(result);
        }
        throw new MyException("Expressions do not evaluate to integers");
    }

    @Override
    public Exp deepCopy() {
        return new MulExp(exp1.deepCopy(), exp2.deepCopy());
    }

    @Override
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type t1=exp1.typecheck(typeEnv);
        Type t2=exp2.typecheck(typeEnv);
        if(t1 instanceof IntType && t2 instanceof IntType){
            return new IntType();
        }
        throw new MyException("Type Error");
    }

    @Override
    public String toString(){
        return "MUL( "+exp1.toString()+","+exp2.toString()+" )";
    }
}
