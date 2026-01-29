package Model.LatchTable;

import Model.Exception.ADT.EmptyCollection;
import Model.Exception.ADT.FullCollection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LatchTable implements ILatchTable<Integer,Integer> {
    private HashMap<Integer,Integer> map;
    private Integer freeLocation;

    public LatchTable(){
        this.map = new HashMap<>();
        this.freeLocation = 0;
    }

    @Override
    public synchronized void add(Integer key, Integer value) throws FullCollection {
        this.map.put(key,value);
    }

    @Override
    public synchronized Integer remove(Integer key) throws EmptyCollection {
        return 0;
    }

    @Override
    public synchronized boolean isDefined(Integer id) {
        return this.map.containsKey(id);
    }

    @Override
    public synchronized void update(Integer key, Integer value) {
        this.map.replace(key,value);
    }

    @Override
    public synchronized Integer lookup(Integer key) {
        return this.map.get(key);
    }

    @Override
    public synchronized int size() {
        return 0;
    }

    @Override
    public synchronized Set<Integer> getKeys() {
        return this.map.keySet();
    }

    @Override
    public synchronized Map<Integer, Integer> getContent() {
        return this.map;
    }

    @Override
    public synchronized ILatchTable<Integer, Integer> deepCopy() {
        ILatchTable<Integer,Integer> newDict=new LatchTable();
        for(Map.Entry<Integer,Integer> entry : this.map.entrySet()){
            try {
                newDict.add(entry.getKey(), entry.getValue());
            } catch (FullCollection e) {
                throw new RuntimeException(e);
            }
        }
        return newDict;
    }

    @Override
    public Integer getLocation() {
        this.freeLocation++;
        return this.freeLocation;
    }

    public synchronized String toString(){
        String s="";
        for(Integer key : this.getKeys()){
            s+=key+"->"+this.lookup(key).toString()+"\n";
        }
        return s;
    }
}
