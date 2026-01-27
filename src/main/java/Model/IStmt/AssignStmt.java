package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public class AssignStmt implements IStmt {
    private String id;
    private Exp exp;

    @Override
    public String toString(){
        return id+"="+exp.toString();
    }
    public AssignStmt(String id, Exp exp){
        this.id = id;
        this.exp = exp;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack = state.getStk();
        MyIDictionary<String, Value> symTbl=state.getSymtbl();
        IHeap<Integer,Value> hp = state.getHeap();
        if(symTbl.isDefined(id)){
            Value val=exp.eval(symTbl,hp );
            Type typId=(symTbl.lookup(id)).getType();
            if(val.getType().equals(typId))
                symTbl.update(id,val);
            else throw new MyException("declared type of variable "+id+" and type of assigned expression do not match");
        }
        else throw new MyException("the used variable "+id+" was not declared before");
       // stack.pop();
        state.setStk(stack);
        state.setSymtbl(symTbl);
        return null;
    }
    public IStmt deepCopy(){
        return new AssignStmt(new String(id), exp.deepCopy());
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typevar=typeEnv.lookup(id);
        Type typexp=exp.typecheck(typeEnv);
        if (typevar.equals(typexp))
            return typeEnv;
        else throw new MyException("Assignment: right hand side and left hand side have different types");
    }
}
