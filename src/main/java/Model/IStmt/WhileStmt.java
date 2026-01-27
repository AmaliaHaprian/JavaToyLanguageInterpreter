package Model.IStmt;

import Model.Exception.Expression.TypeException;
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

public class WhileStmt implements  IStmt {
    private Exp exp;
    private IStmt stmt;

    public String toString() {
        return "while("+exp.toString()+")"+stmt.toString();
    }
    public WhileStmt(Exp exp,  IStmt stmt) {
        this.exp = exp;
        this.stmt = stmt;
    }
    public IStmt deepCopy() {
        return new WhileStmt(exp.deepCopy(), stmt.deepCopy());
    }
    public PrgState execute(PrgState state) throws MyException{
        MyIDictionary<String,Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        MyIStack<IStmt> stk=state.getStk();
        Value val=exp.eval(tbl,heap);
        if (val instanceof BoolValue){
            BoolValue bv=(BoolValue)val;
            if (bv.getVal()==false){

            }
            else{
                stk.push(this);
                stk.push(stmt);
            }
        }
        else throw new TypeException("Not a boolean");
        return null;
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typexp=exp.typecheck(typeEnv);
        if(typexp.equals(new BoolType())){
            stmt.typecheck(typeEnv.deepCopy());
            return typeEnv;
        }
        else throw new TypeException("The condition of WHILE has not the type bool");
    }
}
