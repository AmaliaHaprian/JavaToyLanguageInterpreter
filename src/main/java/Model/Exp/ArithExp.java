package Model.Exp;

import Model.Exception.Expression.DivisionByZeroException;
import Model.Exception.Expression.TypeException;
import Model.Exception.MyException;
import Model.Heap.IHeap;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.Value;

public class ArithExp implements Exp {
    private Exp e1;
    private Exp e2;
    char op;

    public ArithExp(Exp e1, Exp e2, char op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp) throws MyException {
        Value v1,v2;
        v1=e1.eval(tbl, hp);
        if(v1.getType().equals(new IntType())){
            v2=e2.eval(tbl,hp );
            if(v2.getType().equals(new IntType())){
                IntValue i1=(IntValue) v1;
                IntValue i2=(IntValue)v2;
                int n1,n2;
                n1=i1.getVal();
                n2=i2.getVal();
                if(op=='+') return new IntValue(n1+n2);
                if(op=='-') return new IntValue(n1-n2);
                if(op=='*') return new IntValue(n1*n2);
                if(op=='\\')
                    if(n2==0) throw new DivisionByZeroException("Division by zero");
                    else return new IntValue(n1/n2);
            }
            else throw new TypeException("Second operand is not an integer");
        }
        else throw new TypeException("First operand is not an integer");
        return new IntValue(op);
    }
    @Override
    public String toString() {
        return e1.toString() + op + e2.toString();
    }
    public Exp deepCopy(){
        return new ArithExp(e1.deepCopy(), e2.deepCopy(), op);
    }
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1,typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if(typ1.equals(new IntType())) {
            if(typ2.equals(new IntType())) {
                return new IntType();
            }
            else throw new TypeException("Second operand is not an integer");
        }
        else throw new TypeException("First operand is not an integer");
    }
}
