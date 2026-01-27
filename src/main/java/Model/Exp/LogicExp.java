package Model.Exp;

import Model.Exception.Expression.TypeException;
import Model.Exception.MyException;
import Model.Heap.IHeap;
import Model.SymTable.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class LogicExp implements Exp{
    private Exp e1;
    private Exp e2;
    int op;

    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp) throws MyException {
        Value v1,v2;
        v1=e1.eval(tbl,hp );
        if(v1.getType().equals(new BoolValue()))
        {   v2=e2.eval(tbl, hp);
            if(v2.getType().equals(new BoolValue())){
                BoolValue i1=(BoolValue)v1;
                BoolValue i2=(BoolValue)v2;
                boolean b1=i1.getVal();
                boolean b2=i2.getVal();
                if(op==1) return new BoolValue(b1&& b2);
                else if(op==2) return new BoolValue(b1||b2);
            }
            else throw new TypeException("Second operand is not a boolean");
        }
        else throw new TypeException("First operand is not a boolean");
        return new BoolValue();
    }
    public LogicExp(Exp e1, Exp e2, int op) {
        this.e1 = e1;
        this.e2 = e2;
        this.op = op;
    }
    @Override
    public String toString() {
        String operator="";
        if(op==1) operator= "&&";
        if(op==2) operator= "||";
        return e1.toString() + " " + op+ " " + e2.toString();
    }
    public Exp deepCopy(){
        return new LogicExp(e1.deepCopy(), e2.deepCopy(), op);
    }
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1,typ2;
        typ1=e1.typecheck(typeEnv);
        typ2=e2.typecheck(typeEnv);
        if(typ1.equals(new BoolType())) {
            if(typ2.equals(new BoolType())) {
                return new BoolType();
            }
            else throw new TypeException("Second operand is not a boolean");
        }
        else throw new TypeException("First operand is not a boolean");
    }
}
