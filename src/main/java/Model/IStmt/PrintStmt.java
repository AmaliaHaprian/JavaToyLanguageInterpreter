package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.Out.MyIList;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public class PrintStmt implements IStmt {
    private Exp exp;

    @Override
    public String toString() {
        return "print("+exp.toString()+")";
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack=state.getStk();
        MyIDictionary<String, Value> symTable=state.getSymtbl();
        MyIList<Value> out=state.getOut();
        IHeap<Integer,Value> hp = state.getHeap();
        //stack.pop();
        state.setStk(stack);
        state.setSymtbl(symTable);

        Value result=exp.eval(state.getSymtbl(),hp );
        out.add(result);
        state.setOut(out);
        return null;
    }
    public PrintStmt(Exp exp) {
        this.exp=exp;
    }
    public IStmt deepCopy() {
        return new PrintStmt(exp.deepCopy());
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        exp.typecheck(typeEnv);
        return typeEnv;
    }
}
