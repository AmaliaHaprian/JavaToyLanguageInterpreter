package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;

public class CompStmt implements IStmt {
    IStmt first;
    IStmt snd;

    @Override
    public String toString(){
        return "("+first.toString()+","+snd.toString()+")";
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        //stk.pop();
        stk.push(snd);
        stk.push(first);
        state.setStk(stk);
        return null;
    }
    public  CompStmt(IStmt first, IStmt snd){
        this.first=first;
        this.snd=snd;
    }
    public IStmt deepCopy(){
        return new CompStmt(first.deepCopy(),snd.deepCopy());
    }
    public IStmt getFirst(){
        return first;
    }
    public IStmt getSnd(){
        return snd;
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        MyIDictionary<String, Type> typEnv1=first.typecheck(typeEnv);
        MyIDictionary<String, Type> typEnv2=snd.typecheck(typEnv1);
        return typEnv2;
    }
}
