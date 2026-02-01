package Model.SemaphoreTable;

import Model.Exception.ADT.FullCollection;
import Model.Exception.MyException;
import Pair.Pair;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class SemaphoreTable implements ISemaphoreTable<Integer, Pair<Integer, ArrayList<Integer>>> {
    private ConcurrentHashMap<Integer, Pair<Integer, ArrayList<Integer>>> semaphoreTable;
    private Integer freeLocation;

    public SemaphoreTable() {
        this.semaphoreTable = new ConcurrentHashMap<>();
        this.freeLocation = 0;
    }

    public synchronized Integer getFreeLocation(){
        this.freeLocation++;
        return this.freeLocation; }

    @Override
    public synchronized void add(Integer key, Pair<Integer, ArrayList<Integer>> value) throws MyException {
        this.semaphoreTable.put(key,value);
    }

    @Override
    public synchronized Pair<Integer, ArrayList<Integer>> remove(Integer key) throws MyException {
        return null;
    }

    @Override
    public synchronized boolean isDefined(Integer id) {
        return semaphoreTable.containsKey(id);
    }

    @Override
    public synchronized Pair<Integer, ArrayList<Integer>> lookup(Integer key) {
        return semaphoreTable.get(key);
    }

    @Override
    public synchronized Set<Integer> getKeys() {
        return semaphoreTable.keySet();
    }

    @Override
    public synchronized Map<Integer, Pair<Integer, ArrayList<Integer>>> getContent() {
        return this.semaphoreTable;
    }

    @Override
    public synchronized ISemaphoreTable<Integer, Pair<Integer, ArrayList<Integer>>> deepCopy() {
        ISemaphoreTable<Integer, Pair<Integer, ArrayList<Integer>>> newTbl=new SemaphoreTable();
        for(Map.Entry<Integer, Pair<Integer, ArrayList<Integer>>> entry : this.semaphoreTable.entrySet()){
            try {
                newTbl.add(entry.getKey(), entry.getValue());
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        }
        return newTbl;
    }

    @Override
    public synchronized void update(Integer key, Pair<Integer, ArrayList<Integer>> value) {
        this.semaphoreTable.replace(key, value);
    }
    @Override
    public synchronized String toString() {
        String s="";
        for(Map.Entry<Integer, Pair<Integer, ArrayList<Integer>>> entry : this.semaphoreTable.entrySet()){
            s+=entry.getKey()+":";
            s+=entry.getValue().getFirst()+",";
            s+=entry.getValue().getSecond();
        }
        return s;
    }
}
