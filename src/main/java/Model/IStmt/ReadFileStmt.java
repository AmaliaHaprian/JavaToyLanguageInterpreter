package Model.IStmt;

import Model.Exception.Expression.TypeException;
import Model.Exception.File.FileNotFound;
import Model.Exception.MyException;
import Model.Exception.Statement.StatementException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

import java.io.BufferedReader;
import java.io.IOException;

public class ReadFileStmt implements IStmt{
    private Exp exp;
    private String var_name;
    public ReadFileStmt(Exp exp, String name){
        this.exp = exp;
        this.var_name = name;
    }
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stk = state.getStk();
        MyIDictionary<String, Value> tbl=state.getSymtbl();
        MyIDictionary<StringValue, BufferedReader> fileTable = state.getFileTable();
        IHeap<Integer,Value> hp = state.getHeap();
        if (tbl.isDefined(this.var_name) && (tbl.lookup(this.var_name).getType() instanceof IntType)){
            Value val=this.exp.eval(tbl, hp);
            if (val.getType().equals(new StringType())){
                BufferedReader reader = fileTable.lookup((StringValue) val);
                if (reader == null) throw new FileNotFound("File " + val + " not found");
                else {
                    String line = null;
                    try {
                        line = reader.readLine();
                    } catch (IOException e) {
                        throw new MyException(e.getMessage());
                    }
                    int n;
                    if (line == null) n = 0;
                    else {
                        n = Integer.parseInt(line);
                    }
                    Value v = new IntValue(n);
                    tbl.update(this.var_name, v);
                }
            }
            else throw new TypeException("Operand not a string");
        }
        else throw new StatementException("Variable "+this.var_name+" not defined");
        return null;
    }
    @Override
    public String toString() {
        return "Read file :"+this.exp.toString();
    }
    public IStmt deepCopy() {
        return new ReadFileStmt(exp.deepCopy(), var_name);
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typevar=typeEnv.lookup(this.var_name);
        if (typevar instanceof IntType){
            Type typ= exp.typecheck(typeEnv);
            if(typ instanceof StringType){
                return typeEnv;
            }
            else  throw new MyException("ReadFile requires a string as the file name");
        }
        else throw new MyException("ReadFile requires an int as the variable");
    }
}
