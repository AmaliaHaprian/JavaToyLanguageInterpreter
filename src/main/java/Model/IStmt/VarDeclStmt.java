package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.BoolType;
import Model.Type.IntType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.BoolValue;
import Model.Value.IntValue;
import Model.Value.StringValue;
import Model.Value.Value;

public class VarDeclStmt implements IStmt {
    String name;
    Type typ;

    public VarDeclStmt(String name, Type typ) {
        this.name=name;
        this.typ=typ;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIStack<IStmt> stack=state.getStk();
        //stack.pop();
        state.setStk(stack);
        MyIDictionary<String, Value> symTable=state.getSymtbl();
        if (symTable.isDefined(name)) {
            throw new MyException("variable is already declared");
        }
        else{
            //if(typ instanceof IntType){
            //    Value val=new IntValue();
            //    symTable.add(name,val);
            // }
            //else if (typ instanceof BoolType){
            //    Value val=new BoolValue();
            //    symTable.add(name,val);
            //}
            //else if (typ instanceof StringType){
            //    Value val=new StringValue();
            //    symTable.add(name,val);
            //}
            symTable.add(name, typ.defaultValue());
            state.setSymtbl(symTable);
        }
        return null;
    }
    @Override
    public String toString() {
        return typ.toString()+" "+name;
    }
    public IStmt deepCopy() {
        return new VarDeclStmt(new String(name), typ.deepCopy());
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        typeEnv.add(name, typ);
        return typeEnv;
    }
}
