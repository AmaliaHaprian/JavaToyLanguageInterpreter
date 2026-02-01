package Model.IStmt;

import Model.Exception.Expression.TypeException;
import Model.Exception.MyException;
import Model.Exp.Exp;
import Model.Exp.RelationalExp;
import Model.Exp.VarExp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;

public class ForStmt implements IStmt {
    private String var;
    private Exp exp1,exp2,exp3;
    private IStmt stmt;

    public ForStmt(String var, Exp exp1,Exp exp2,Exp exp3, IStmt stmt) {
        this.var = var;
        this.exp1 = exp1;
        this.exp2 = exp2;
        this.exp3 = exp3;
        this.stmt = stmt;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
//        MyIDictionary<String, Value> tbl=state.getSymtbl();
//        IHeap<Integer,Value> heap=state.getHeap();
//        if(!(tbl.isDefined(var) ))
//            throw new MyException("Variable "+var+" is not defined");
        IStmt newStmt=new CompStmt(new AssignStmt(var, exp1),
                new WhileStmt(new RelationalExp(new VarExp(var), exp2, "<"), new CompStmt(stmt, new AssignStmt("v", exp3))));
        state.getStk().push(newStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ForStmt(var, exp1.deepCopy(), exp2.deepCopy(), exp3.deepCopy(), stmt.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typVar=typeEnv.lookup(var);
        Type typExp1=exp1.typecheck(typeEnv);
        Type typExp2=exp2.typecheck(typeEnv);
        Type typExp3=exp3.typecheck(typeEnv);
        if(typVar == null)
            throw new MyException("variable "+var+" not defined");
        if (!(typVar.equals(typExp1) && typVar.equals(typExp2) &&  typVar.equals(typExp3)))
            throw new TypeException("Expressions in FOR don't have the same type");
        stmt.typecheck(typeEnv.deepCopy());
        return typeEnv;
    }

    @Override
    public String toString() {
        return "for(" + var + "=" + exp1.toString() + "; " + var + "<" + exp2 + "; " + var + "=" + exp3.toString() + ") " + stmt.toString();

    }
}
