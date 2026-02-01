package Model.SemaphoreTable;

import Model.Exception.ADT.FullCollection;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SemaphoreTable<K,T> implements ISemaphoreTable<K,T>{
    private HashMap<K,T> dictionary;
    private Integer location;
    public SemaphoreTable(){
        dictionary = new HashMap<>();
        location=0;
    }

    @Override
    public synchronized void add(K key, T value) throws FullCollection {
        this.dictionary.put(key,value);
    }

    @Override
    public synchronized boolean isDefined(K id) {
        return this.dictionary.containsKey(id);
    }

    @Override
    public synchronized void update(K key, T value) {
        this.dictionary.replace(key,value);
    }

    @Override
    public synchronized T lookup(K key) {
        return this.dictionary.get(key);
    }

    @Override
    public synchronized Set<K> getKeys() {
        return this.dictionary.keySet();
    }

    @Override
    public synchronized Map<K, T> getContent() {
        return this.dictionary;
    }

    @Override
    public synchronized ISemaphoreTable<K, T> deepCopy() {
        ISemaphoreTable<K,T> copy = new SemaphoreTable<>();
        for(Map.Entry<K,T> entry : this.dictionary.entrySet()){
            try {
                copy.add(entry.getKey(), entry.getValue());
            } catch (FullCollection e) {
                throw new RuntimeException(e);
            }
        }
        return copy;
    }

    @Override
    public String toString() {
        String s = "";
        for(Map.Entry<K,T> entry : this.dictionary.entrySet()){
            s+=entry.getKey()+":"+entry.getValue()+"\n";
        }
        return s;
    }

    @Override
    public synchronized Integer getLocation(){
        location++;
        return location;
    }
}
