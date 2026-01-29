package Model.IStmt;

import Model.Exception.MyException;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;

public class SleepStmt implements IStmt {
    private Integer number;

    public SleepStmt(Integer number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!(number==0))
            state.getStk().push(new SleepStmt(number-1));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new  SleepStmt(number);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString() {
        return "sleep("+number+")";
    }
}
