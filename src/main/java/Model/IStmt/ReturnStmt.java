package Model.IStmt;

import Model.Exception.MyException;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;

public class ReturnStmt implements IStmt {
    public ReturnStmt() {

    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        state.getSymTblStack().pop();
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ReturnStmt();
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "return";
    }
}
