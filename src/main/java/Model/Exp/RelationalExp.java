package Model.Exp;

import Model.Exception.Expression.TypeException;
import Model.Exception.MyException;
import Model.Heap.IHeap;
import Model.SymTable.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.Value;

public class RelationalExp implements Exp{
    private Exp exp1;
    private Exp exp2;
    private String op;
    public RelationalExp(Exp exp1, Exp exp2, String op) {
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.op = op;
    }
    @Override
    public Value eval(MyIDictionary<String, Value> tbl, IHeap<Integer, Value> hp) throws MyException {
        Value v1,v2;
        v1=exp1.eval(tbl,hp);
        if(v1.getType().equals(new IntType())){
            v2=exp2.eval(tbl, hp);
            if(v2.getType().equals(new IntType())){
                IntValue i1=(IntValue)v1;
                IntValue i2=(IntValue)v2;
                int n1,n2;
                n1=i1.getVal();
                n2=i2.getVal();
                if(op.equals("<")) return new BoolValue(n1-n2<0);
                else if(op.equals("<=")) return new BoolValue(n1-n2<=0);
                else if(op.equals("==")) return new BoolValue(n1-n2==0);
                else if(op.equals("!=")) return new BoolValue(n1-n2!=0);
                else if(op.equals(">")) return new BoolValue(n1-n2>0);
                else if(op.equals(">=")) return new BoolValue(n1-n2>=0);
                else throw new MyException("Invalid operation");
            }
            else throw new TypeException("Second operand is not an int");
        }
        else throw new TypeException("First operand is not an int");
    }
    @Override
    public String toString() {
        return "(" + exp1.toString() + " " + op + " " + exp2.toString() + ")";
    }
    public Exp deepCopy() {
        return new RelationalExp(exp1.deepCopy(), exp2.deepCopy(), op);
    }
    public Type typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typ1,typ2;
        typ1=exp1.typecheck(typeEnv);
        typ2=exp2.typecheck(typeEnv);
        if(typ1.equals(new IntType())) {
            if(typ2.equals(new IntType())) {
                return new BoolType();
            }
            else throw new TypeException("Second operand is not an integer");
        }
        else throw new TypeException("First operand is not an integer");
    }
}
