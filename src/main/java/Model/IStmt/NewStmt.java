package Model.IStmt;

import Model.Exception.MyException;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.RefType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class NewStmt implements IStmt {
    private String var_name;
    private Exp exp;
    public NewStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl= state.getSymtbl();
        IHeap<Integer,Value> heap = state.getHeap();
        if(tbl.isDefined(var_name) && tbl.lookup(var_name).getType() instanceof RefType) {
            Value val=exp.eval(tbl, heap);
            RefValue valTbl=(RefValue) tbl.lookup(var_name);
            if(val.getType().equals(valTbl.getLocationType())) {
                int addr=heap.getNextKey();
                heap.add(addr,val);
                tbl.update(var_name, new RefValue(addr, valTbl.getLocationType()));
            }
            else throw new MyException("Types not equal");
        }
        else throw new MyException("Variable " + var_name + " not found");
        return null;
    }
    public String toString(){
        return "new("+var_name+","+exp.toString()+")";
    }
    public IStmt deepCopy(){
        return new NewStmt(var_name, exp.deepCopy());
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typevar=typeEnv.lookup(var_name);
        Type typexp= exp.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp)))
            return typeEnv;
        else throw new MyException("NEW stmt: right hand side and left hand side have different types");
    }
}
