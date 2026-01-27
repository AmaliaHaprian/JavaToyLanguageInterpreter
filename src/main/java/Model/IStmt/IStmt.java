package Model.IStmt;

import Model.Exception.MyException;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;

import java.io.IOException;

public interface IStmt {
    PrgState execute(PrgState state) throws MyException;
    @Override
    String toString();
    IStmt deepCopy();
    MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException;
}
