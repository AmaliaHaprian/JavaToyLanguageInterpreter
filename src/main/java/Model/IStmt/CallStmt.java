package Model.IStmt;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.Exp.Exp;
import Model.Heap.IHeap;
import Model.PrgState.PrgState;
import Model.ProcTable.IProcTable;
import Model.SymTable.MyDictionary;
import Model.SymTable.MyIDictionary;
import Model.Type.Type;
import Model.Value.Value;
import Pair.Pair;

import java.util.ArrayList;
import java.util.List;

public class CallStmt implements IStmt {
    private String fname;
    private List<Exp> expList;

    public CallStmt(String fname, List<Exp> expList){
        this.fname=fname;
        this.expList=expList;
    }
    @Override
    public PrgState execute(PrgState state) throws MyException {
        MyIDictionary<String, Value> tbl=state.getSymtbl();
        IHeap<Integer,Value> heap=state.getHeap();
        MyIStack<IStmt> stk=state.getStk();
        IProcTable procTable=state.getProcTable();
        MyIStack<MyIDictionary<String, Value>> symTblStack=state.getSymTblStack();
        if(!(procTable.isDefined(fname)))
            throw new MyException("The function "+fname+" is not defined");
        Pair<List<String>, IStmt> pair=procTable.lookup(fname);
        List<String> formalParams=pair.getFirst();

        MyIDictionary<String,Value> tbl2=new MyDictionary<>();
        for(int i=0;i<expList.size();i++){
            Value val= expList.get(i).eval(tbl,heap);
            tbl2.add(formalParams.get(i),val);
        }
        symTblStack.push(tbl2);
        stk.push(new ReturnStmt());
        stk.push(pair.getSecond());

        return null;
    }

    @Override
    public IStmt deepCopy() {
        List<Exp> newExpList=new ArrayList<>();
        for(Exp exp:expList){
            newExpList.add(exp.deepCopy());
        }
        return new CallStmt(fname,newExpList);
    }

    @Override
    public MyIDictionary<String, Type> typecheck(MyIDictionary<String, Type> typeEnv) throws MyException {
        return typeEnv;
    }

    public String toString(){
        return "call "+fname+"("+expList+")";
    }
}
