package Repository;

import Model.Exception.MyException;
import Model.PrgState.PrgState;

import java.util.List;

public interface IRepository {
   // PrgState getCrtPrg();
    void setCrtPrg(int crtPrg);
    void logPrgStateExec(PrgState state) throws MyException;
    List<PrgState> getPrgStates();
    void setPrgStates(List<PrgState> prgStates);
}
