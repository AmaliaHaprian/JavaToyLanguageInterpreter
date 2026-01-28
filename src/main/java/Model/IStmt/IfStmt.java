package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.Value;

public class IfStmt implements IStmt {
    Exp exp;
    IStmt thenS;
    IStmt elseS;
    public IfStmt(Exp e, IStmt t, IStmt el){
        this.exp = e;
        this.thenS = t;
        this.elseS = el;
    }
    @Override
    public String toString() {
        return "IF ("+exp.toString()+") THEN ("+thenS.toString()+") ELSE ("+ elseS.toString()+")";
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack=state.getStk();
        MyIDictionary<String, Value> symTable=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        Value cond=exp.eval(state.getSymtbl(),heap );
        Type typ=cond.getType();
        if (!(typ instanceof BoolType)){
            throw new MyException("conditional expression is not a boolean");
        }
        else{
            BoolValue v=(BoolValue) cond;
            if( v.getVal())
                stack.push(thenS);
            else
                stack.push(elseS);
        }
        state.setStk(stack);
        return null;
    }
    public IStmt deepCopy(){
        return new IfStmt(exp.deepCopy(), thenS.deepCopy(), elseS.deepCopy());
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typexp= exp.typecheck(typeEnv);
        if(typexp.equals(new BoolType())){
            thenS.typecheck(typeEnv.deepCopy());
            elseS.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new MyException("The condition of IF has not the type bool");
    }
}
