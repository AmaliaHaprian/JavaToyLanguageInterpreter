package Repository;
import Model.Exception.MyException;
import Model.IStmt.*;
import Model.PrgState.PrgState;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class Repository implements IRepository {
    private List<PrgState> prgStates;
    int crtPrg;
    String logFilePath;

    public Repository(){
        this.prgStates=new ArrayList<>(5);
        this.crtPrg=0;
        //this.addPrograms();
    }

    public Repository(PrgState prg, String logFilePath){
        this.prgStates=new ArrayList<>(5);
        this.prgStates.add(prg);
        this.crtPrg=0;
        this.logFilePath=logFilePath;
    }

    //@Override
    //public PrgState getCrtPrg() {
    //    return prgStates.get(crtPrg);
    //}
    @Override
    public void setCrtPrg(int crtPrg){
        this.crtPrg=crtPrg;
    }
    public void add(PrgState state){
        prgStates.add(state);
    }

    public void logPrgStateExec(PrgState state) throws MyException {

        PrintWriter logFile;
        try {
            logFile = new PrintWriter(new BufferedWriter(new FileWriter(this.logFilePath, true)));
        } catch (IOException e) {
            throw new MyException(e.getMessage());
        }
        //PrgState prg=this.prgStates.get(this.crtPrg);
        logFile.println(state.toString());
        logFile.close();
    }
    public List<PrgState> getPrgStates() {
        return prgStates;
    }
    public void setPrgStates(List<PrgState> prgStates) {
        this.prgStates=prgStates;
    }
    public PrgState getPrgById(int prgId){
        for(PrgState prgState:prgStates){
            if (prgState.getPersonalId()==prgId){
                return prgState;
            }
        }
        return null;
    }
}
