package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;

public class NopStmt implements IStmt {
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack=state.getStk();
        //stack.pop();
        state.setStk(stack);
        return null;
    }
    public IStmt deepCopy(){
        return new NopStmt();
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        return typeEnv;
    }
}
