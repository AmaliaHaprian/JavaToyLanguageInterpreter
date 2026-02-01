package Model.IStmt;

import Model.Exception.MyException;
import Model.Exp.ValueExp;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;
import Model.Value.IntValue;

public class WaitStmt implements IStmt {
    private Integer number;

    public WaitStmt(Integer number) {
        this.number = number;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        if(!(number==0))
            state.getStk().push(new CompStmt(new PrintStmt(new ValueExp(new IntValue(number))), new WaitStmt(number-1)));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new WaitStmt(number);
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
