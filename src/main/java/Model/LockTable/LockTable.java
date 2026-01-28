package Model.LockTable;
import Model.Exception.ADT.FullCollection;
import Model.Exception.MyException;
import Model.SymTable.MyDictionary;
import Model.SymTable.MyIDictionary;
import Model.Value.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class LockTable implements ILockTable<Integer,Integer> {
    private HashMap<Integer,Integer> lockTable;
    private Integer freeLocation;

    public LockTable() {
        this.lockTable=new HashMap<>();
        this.freeLocation=0;
    }

    public synchronized Integer getFreeLocation(){
        this.freeLocation++;
        return this.freeLocation;
    }

    @Override
    public synchronized Map<Integer, Integer> getContent() {
        return this.lockTable;
    }

    public synchronized String toString(){
        String s="";
        for(Integer key : this.getKeys()){
            s+=key+"->"+this.lookup(key)+"\n";
        }
        return s;
    }

    @Override
    public synchronized void add(Integer key, Integer value) throws MyException {
        if(lockTable.containsKey(key))
            throw new MyException("Lock table already contains key");
        lockTable.put(key,value);
    }

    @Override
    public synchronized void update(Integer key, Integer value) throws MyException {
        if (lockTable.containsKey(key)) {
            this.lockTable.replace(key,value);
        }
        else throw new MyException("Lock table doesn't contain this key");
    }
    @Override
    public synchronized Integer remove(Integer key) {
        return this.lockTable.remove(key);
    }

    @Override
    public synchronized boolean isDefined(Integer id) {
        return this.lockTable.containsKey(id);
    }

    @Override
    public synchronized ILockTable<Integer, Integer> deepCopy() {
        ILockTable<Integer,Integer> newDict=new LockTable();
        for(Map.Entry<Integer,Integer> entry : this.lockTable.entrySet()){
            try {
                newDict.add(entry.getKey(), entry.getValue());
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        }
        return newDict;
    }

    @Override
    public synchronized Integer lookup(Integer key) {
        return this.lockTable.get(key);
    }

    @Override
    public synchronized Set<Integer> getKeys() {
        return this.lockTable.keySet();
    }

}
