package Model.IStmt;

import Model.Exception.Expression.TypeException;
import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
import Model.Exp.UnaryExp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;

public class RepeatUntilStmt implements IStmt {
    IStmt stmt;
    Exp exp;
    public RepeatUntilStmt(IStmt stmt, Exp exp) {
        this.stmt = stmt;
        this.exp = exp;
    }

    public IStmt deepCopy() {
        return new RepeatUntilStmt(this.stmt.deepCopy(), this.exp.deepCopy());
    }
    public String toString(){
        return "repeat "+stmt.toString() + "until " + exp.toString();
    }
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<StringValue, BufferedReader> fileTable=state.getFileTable();
        MyIDictionary<String, Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();

        Value val=exp.eval(tbl,heap);
        if (val instanceof BoolValue) {
            stk.push(new WhileStmt(new UnaryExp(exp,"!"), stmt));
            stk.push(stmt);
        }
        else throw new TypeException("Not a boolean");
        return null;
    }

    public MyIDictionary<String, Type> typecheck (MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        if(typexp.equals(new BoolType())){
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new TypeException("The condition of REPEAT...UNTIL does not have type bool");
    }
}
