package Model.Exp;

import Model.Exception.Expression.TypeException;
import Model.Exception.MyException;
import Model.Heap.IHeap;
import Model.IStmt.IStmt;
import Model.SymTable.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class UnaryExp implements Exp {
    private Exp exp;
    private String  op;
    public UnaryExp(Exp exp, String op) {
        this.exp = exp;
        this.op = op;
    }

    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp) throws MyException{
        Value val=exp.eval(tbl,hp);
        if(val.getType().equals(new BoolType())){
            if (op.equals("!")){
                BoolValue boolVal=(BoolValue)val;
                boolean b= boolVal.getVal();
                return new BoolValue(!b);
            }
            else throw new MyException("Undefined operator");
        }
        else throw new TypeException("Operand is not a boolean");
    }

    public Exp deepCopy(){
        return new UnaryExp(exp.deepCopy(), op);
    }
    public String toString(){
        return op+exp.toString();
    }
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typ=exp.typecheck(typeEnv);
        if(typ.equals(new BoolType())){
            return typ;
        }
        else throw new TypeException("Operand is not a boolean");
    }
}
