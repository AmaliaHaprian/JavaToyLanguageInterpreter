package Model.BarrierTable;

import Model.Exception.ADT.FullCollection;
import Model.Exception.MyException;
import Pair.Pair;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

public class BarrierTable implements IBarrierTable<Integer, Pair<Integer, Vector<Integer>>> {
    private HashMap<Integer,Pair<Integer, Vector<Integer>>> barrierTable;
    private Integer freeLocation;

    public BarrierTable() {
        this.barrierTable = new HashMap<>();
        this.freeLocation = 0;
    }
    @Override
    public synchronized Integer getFreeLocation() {
        this.freeLocation++;
        return this.freeLocation;
    }

    @Override
    public synchronized void add(Integer key, Pair<Integer, Vector<Integer>> value) throws MyException {
        this.barrierTable.put(key,value);
    }

    @Override
    public synchronized Pair<Integer, Vector<Integer>> remove(Integer key) throws MyException {
        return null;
    }

    @Override
    public synchronized boolean isDefined(Integer id) {
        return this.barrierTable.containsKey(id);
    }

    @Override
    public synchronized void update(Integer key, Pair<Integer, Vector<Integer>> value) {
        this.barrierTable.replace(key,value);
    }

    @Override
    public synchronized Pair<Integer, Vector<Integer>> lookup(Integer key) {
        return this.barrierTable.get(key);
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public synchronized Set<Integer> getKeys() {
        return this.barrierTable.keySet();
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, Vector<Integer>>> getContent() {
        return this.barrierTable;
    }

    @Override
    public synchronized IBarrierTable<Integer, Pair<Integer, Vector<Integer>>> deepCopy() {
        IBarrierTable<Integer,Pair<Integer, Vector<Integer>>> newDict=new BarrierTable();
        for(Map.Entry<Integer,Pair<Integer, Vector<Integer>>> entry : this.barrierTable.entrySet()){
            try {
                newDict.add(entry.getKey(), entry.getValue());
            } catch (FullCollection e) {
                throw new RuntimeException(e);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        }
        return newDict;
    }

    @Override
    public synchronized String toString(){
        String s="";
        for(Integer key : this.getKeys()){
            s+=key+"->"+this.lookup(key).toString()+"\n";
        }
        return s;
    }
}
