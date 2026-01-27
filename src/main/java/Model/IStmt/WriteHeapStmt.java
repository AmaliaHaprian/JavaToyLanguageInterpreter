package Model.IStmt;

import Model.Exception.Expression.TypeException;
import Model.Exception.Expression.UndefinedException;
import Model.Exception.MyException;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.IntType;
import Model.Type.RefType;
import Model.Type.StringType;
import Model.Type.Type;
import Model.Value.RefValue;
import Model.Value.Value;

public class WriteHeapStmt implements IStmt {
    private String var_name;
    private Exp exp;
    public WriteHeapStmt(String var_name, Exp exp) {
        this.var_name = var_name;
        this.exp = exp;
    }
    public String toString()
    {
        return "wH("+var_name+","+exp.toString()+")";
    }
    public IStmt deepCopy() {
        return new WriteHeapStmt(var_name, this.exp.deepCopy());
    }
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl=state.getSymtbl();
        IHeap<Integer, Value> heap=state.getHeap();
        if(tbl.isDefined(this.var_name)){
            Value val=tbl.lookup(this.var_name);
            if(val instanceof RefValue){
                RefValue ref=(RefValue)val;
                int addr=ref.getAddr();
                if (heap.isDefined(addr)) {
                    Value val2=heap.lookup(addr);
                    Value finalVal=exp.eval(tbl,heap);
                    if(finalVal.getType().equals(val2.getType())){
                        heap.update(addr, finalVal);
                    }
                    else throw new TypeException("Values don't have the same type");
                }
                else throw new UndefinedException(addr+" is not an address in the heap");
            }
            else throw new TypeException("Variable "+this.var_name+" is not a RefValue");
        }
        else throw new UndefinedException(var_name+" is not defined");
        return null;
    }
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException{
        Type typevar=typeEnv.lookup(this.var_name);
        Type typexp= exp.typecheck(typeEnv);
        if(typevar.equals(new RefType(typexp))){
            return typeEnv;
        }
        else throw new TypeException("the wH argument is not a RefType");
    }
}
