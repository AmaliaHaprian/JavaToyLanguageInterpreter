package Model.IStmt;

import Model.Exception.Expression.TypeException;
import Model.Exception.File.FileNotFound;
import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class CloseRFileStmt implements IStmt{
    private Exp exp;
    public CloseRFileStmt(Exp exp){
        this.exp=exp;
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk=state.getStk();
        MyIDictionary<StringValue, BufferedReader> fileTable=state.getFileTable();
        MyIDictionary<String, Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        Value val=this.exp.eval(tbl, heap);
        if(val.getType().equals(new StringType())){

            BufferedReader reader=fileTable.lookup((StringValue) val);
            if(reader==null) throw new FileNotFound("File "+val.toString()+" not found");
            else {
                try {
                    reader.close();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                fileTable.remove((StringValue) val);
            }
        }
        else throw new TypeException("Operand not a string");
    return null;
    }
    @Override
    public String toString(){
        return "CloseRFileStmt("+this.exp.toString()+")";
    }
    public IStmt deepCopy() {
        return new CloseRFileStmt(exp.deepCopy());
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typ= exp.typecheck(typeEnv);
        if(typ instanceof StringType){
            return typeEnv;
        }
        else throw new MyException("CloseRFile expression not a string");
    }
}
