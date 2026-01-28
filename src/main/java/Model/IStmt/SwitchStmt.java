package Model.IStmt;

import Model.Exception.MyException;
import Model.Exp.Exp;
import Model.Exp.LogicExp;
import Model.Exp.RelationalExp;
import Model.PrgState.PrgState;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;

public class SwitchStmt implements IStmt {
    private Exp exp, exp1, exp2;
    private IStmt stmt1, stmt2, stmt3;

    public SwitchStmt(Exp exp, Exp exp1, IStmt stmt1, Exp exp2, IStmt stmt2, IStmt stmt3){
        this.exp = exp;
        this.exp1 = exp1;
        this.stmt1 = stmt1;
        this.exp2 = exp2;
        this.stmt2 = stmt2;
        this.stmt3 = stmt3;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {

        IStmt ifStmt = new IfStmt(new RelationalExp(exp, exp1, "=="),
                stmt1, new IfStmt(new RelationalExp(exp, exp2, "=="), stmt2, stmt3));
        state.getStk().push(ifStmt);
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new SwitchStmt(exp.deepCopy(), exp1.deepCopy(), stmt1.deepCopy(), exp2.deepCopy(), stmt2.deepCopy(), stmt3.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        Type typexp=exp.typecheck(typeEnv);
        Type typexp1=exp1.typecheck(typeEnv);
        Type typexp2=exp2.typecheck(typeEnv);
        if(!(typexp.equals(typexp1)&&typexp.equals(typexp2)))
            throw new MyException("Expressions in the switch stmt don't have the same type");
        stmt1.typecheck(typeEnv.deepCopy());
        stmt2.typecheck(typeEnv.deepCopy());
        stmt3.typecheck(typeEnv.deepCopy());

        return typeEnv;
    }

    @Override
    public String toString(){
        return "(switch ("+exp.toString()+")\n\t"+"(case ("+exp1.toString()+"):"+stmt1.toString()
                +"\n\t"+"(case ("+exp2.toString()+"):"+stmt2.toString()
                +"\n\t"+"(default:"+stmt3.toString()+")";
    }
}
