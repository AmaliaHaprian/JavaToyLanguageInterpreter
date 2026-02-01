package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SemaphoreTable.ISemaphoreTable;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.Type;
import Model.Exp.Exp;
import Model.Value.IntValue;
import Model.Value.Value;
import Tuple.Tuple;

import java.util.ArrayList;

public class NewSemaphoreStmt implements IStmt{
    private String var;
    private Exp exp1,exp2;

    public NewSemaphoreStmt(String var, Exp exp1, Exp exp2){
        this.var=var;
        this.exp1=exp1;
        this.exp2=exp2;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String,Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        MyIStack<IStmt> stk=state.getStk();
        ISemaphoreTable<Integer, Tuple> semTbl=state.getSemaphoreTable();
        Value val1=exp1.eval(tbl,heap);
        Value val2=exp2.eval(tbl,heap);
        if(!(val1.getType() instanceof IntType && val2.getType() instanceof IntType))
            throw new MyException("Type mismatch between expressions");
        Integer num1=((IntValue)val1).getVal();
        Integer num2=((IntValue)val2).getVal();
        Integer location=semTbl.getLocation();
        semTbl.add(location, new Tuple(num1, new ArrayList<Integer>(), num2));

        if(!(tbl.isDefined(var) && tbl.lookup(var).getType() instanceof IntType))
            throw new MyException("Variable is not an integer");
        tbl.update(var, new IntValue(location));

        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new NewSemaphoreStmt(var,exp1.deepCopy(),exp2.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typVar= typeEnv.lookup(var);
        if(!(typVar instanceof IntType))
            throw new MyException("Variable is not an integer");
        Type typExp1=exp1.typecheck(typeEnv);
        Type typExp2=exp2.typecheck(typeEnv);
        if(!(typExp1 instanceof IntType && typExp2 instanceof IntType))
            throw new MyException("Type mismatch between expressions");
        return typeEnv;
    }

    @Override
    public String toString() {
        return "newSemaphore("+var+","+exp1+","+exp2+")";
    }
}
