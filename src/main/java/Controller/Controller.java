package Controller;

import Model.Exception.MyException;
import Model.ExeStack.MyIStack;
import Model.IStmt.IStmt;
import Model.PrgState.PrgState;
import Model.Value.RefValue;
import Model.Value.Value;
import Repository.IRepository;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class Controller {
    private IRepository repo;
    private ExecutorService executor;

    public Controller(IRepository repo) {
        this.repo =  repo;
    }
    public void oneStep() throws MyException{
        executor= Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgStates());

        for (PrgState prg : prgList) {
            prg.getHeap().setContent(safeGarbageCollector(
                    getAddFromSymTable(prg.getSymtbl().getContent().values()), getAddFromHeap(prg.getHeap().getContent().values()),
                    prg.getHeap().getContent()));
            }
        oneStepForAllPrg(prgList);
        //prgList = removeCompletedPrg(prgList);

        executor.shutdownNow();
        repo.setPrgStates(prgList);
    }

//    public void allStep() throws MyException {
//        PrgState prg=repo.getCrtPrg();
//        repo.logPrgStateExec();
//        while(!prg.getStk().isEmpty()){
//            this.oneStep(prg);
//            //this.display();
//            repo.logPrgStateExec();
////            prg.getHeap().setContent(unsafeGarbageCollector(
////                    getAddFromSymTable(prg.getSymtbl().getContent().values()),
////                    prg.getHeap().getContent()));
//            prg.getHeap().setContent(safeGarbageCollector(
//                    getAddFromSymTable(prg.getSymtbl().getContent().values()), getAddFromHeap(prg.getHeap().getContent().values()),
//                    prg.getHeap().getContent()));
//            repo.logPrgStateExec();
//        }
//    }

//    public void display() throws MyException{
//        System.out.println(repo.getCrtPrg().toString());
//    }
    public void setCrtPrg(int crtPrg){
        repo.setCrtPrg(crtPrg);
    }

    Map<Integer, Value> unsafeGarbageCollector(List<Integer> symTableAddr, Map<Integer, Value> heap){
        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }
    List<Integer> getAddFromSymTable(Collection<Value> symTableValues){
        return symTableValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1=(RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    List<Integer> getAddFromHeap(Collection<Value> HeapValues){
        return HeapValues.stream()
                .filter(v->v instanceof RefValue)
                .map(v->{RefValue v1=(RefValue)v; return v1.getAddr();})
                .collect(Collectors.toList());
    }

    Map<Integer, Value> safeGarbageCollector(List<Integer> symTableAddr,List<Integer> heapAddr, Map<Integer, Value> heap){
        heapAddr.forEach(v->{if (!symTableAddr.contains(v)) symTableAddr.add(v);});

        return heap.entrySet().stream()
                .filter(e->symTableAddr.contains(e.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public List<PrgState> removeCompletedPrg(List<PrgState> inPrgList){
        return inPrgList.stream()
                .filter(p->p.isNotCompleted())
                .collect(Collectors.toList());
    }

    public void oneStepForAllPrg(List<PrgState> prgList) {
        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        List<Callable<PrgState>> callList = prgList.stream()
                .map((PrgState p) -> (Callable<PrgState>) (() -> {
                    return p.oneStep();
                }))
                .collect(Collectors.toList());

        List<PrgState> newPrgList = null;
        try {
            newPrgList = executor.invokeAll(callList).stream()
                    .map(future -> {
                        try {
                            return future.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .filter(p -> p != null)
                    .collect(Collectors.toList());
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        prgList.addAll(newPrgList);

        prgList.forEach(prg -> {
            try {
                repo.logPrgStateExec(prg);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        });

        repo.setPrgStates(prgList);
    }

    public void allStep() throws MyException {
        executor= Executors.newFixedThreadPool(2);
        List<PrgState> prgList = removeCompletedPrg(repo.getPrgStates());
        while(prgList.size()>0) {
            for (PrgState prg : prgList) {
                prg.getHeap().setContent(safeGarbageCollector(
                        getAddFromSymTable(prg.getSymtbl().getContent().values()), getAddFromHeap(prg.getHeap().getContent().values()),
                        prg.getHeap().getContent()));
            }
            oneStepForAllPrg(prgList);
            prgList = removeCompletedPrg(prgList);
        }
        executor.shutdownNow();
        repo.setPrgStates(prgList);
    }
    public List<PrgState> getPrgStates() {
        return repo.getPrgStates();
    }
    public PrgState getPrgById(int prgId){
        for(PrgState prgState:repo.getPrgStates()){
            if (prgState.getPersonalId()==prgId){
                return prgState;
            }
        }
        return null;
    }

    public void setPrgStates(List<PrgState> prgStates) {
        repo.setPrgStates(prgStates);
    }
}
