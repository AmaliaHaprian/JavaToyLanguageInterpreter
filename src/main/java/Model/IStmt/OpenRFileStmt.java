package Model.IStmt;

import Model.Exception.Expression.TypeException;
import Model.Exception.MyException;
import Model.Exception.Statement.StatementException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;


public class OpenRFileStmt implements IStmt{
    private Exp exp;
    public OpenRFileStmt(Exp exp){
        this.exp = exp;
    }

    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack=state.getStk();
        MyIDictionary<String, Value> tbl= state.getSymtbl();
        MyIDictionary<StringValue, BufferedReader> fileTable=state.getFileTable();
        IHeap<Integer,Value> heap=state.getHeap();
        Value val=this.exp.eval(tbl, heap );
        if (val.getType().equals(new StringType())){
            if (fileTable.isDefined((StringValue)val)){
                throw new StatementException("File already exists");
            }
            else{
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(val.toString()));
                    fileTable.add((StringValue) val, reader);
                } catch (IOException e) {
                    throw new MyException(e.getMessage());
                }
            }
        }
        else throw new TypeException("operand value is not string");
    return null;
    }
    @Override
    public String toString(){
        return "openRFile("+this.exp.toString()+')';
    }
    public IStmt deepCopy() {
        return new OpenRFileStmt(exp.deepCopy());
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typ= exp.typecheck(typeEnv);
        if(typ instanceof StringType){
            return typeEnv;
        }
        else  throw new MyException("OpenRFile expression not a string");
    }
}
