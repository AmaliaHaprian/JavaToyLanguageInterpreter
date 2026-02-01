package Model.IStmt;

import Model.Exception.MyException;
import Model.PrgState.PrgState;
import Model.ProcTable.IProcTable;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;
import Pair.Pair;

import java.util.List;

public class ProcDeclStmt implements IStmt{
    private String name;
    private List<String> formalParams;
    private IStmt body;

    public ProcDeclStmt(String name, List<String> formalParams, IStmt body){
        this.name = name;
        this.formalParams = formalParams;
        this.body = body;
    }

    @Override
    public PrgState execute(PrgState state) throws MyException {
        IProcTable procTable=state.getProcTable();
        procTable.add(this.name,new Pair<>(this.formalParams,this.body));
        return null;
    }

    @Override
    public IStmt deepCopy() {
        return new ProcDeclStmt(name,formalParams,body.deepCopy());
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    @Override
    public String toString(){
        return "procedure "+this.name+"("+this.formalParams+") "+this.body.toString();
    }
}
