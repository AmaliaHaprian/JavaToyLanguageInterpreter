package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.Value;

public class CondAssignStmt implements IStmt {
    private String var;
    private Exp exp1,exp2,exp3;
    public CondAssignStmt(String var,Exp exp1,Exp exp2,Exp exp3) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String,Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        MyIStack<IStmt> stk=state.getStk();
        IStmt newStmt=new IfStmt(exp1, new AssignStmt(var,exp2), new AssignStmt(var,exp3));
        stk.push(newStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new CondAssignStmt(var,exp1.deepCopy(),exp2.deepCopy(),exp3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typExp1=exp1.typecheck(typeEnv);
        if(!(typExp1 instanceof BoolType))
            throw new MyException("Conditional statement doesn't have type bool");
        Type typVar=typeEnv.lookup(var);
        Type typExp2=exp2.typecheck(typeEnv);
        Type typExp3=exp3.typecheck(typeEnv);
        if(!(typVar.equals(typExp2) && typVar.equals(typExp3)))
            throw new MyException("Conditional assignment doesn't match");
        return typeEnv;
    }

    @Override
    public String toString(){
        return var + "=("+exp1.toString()+") ? "+exp2.toString()+" : "+exp3.toString();
    }
}
