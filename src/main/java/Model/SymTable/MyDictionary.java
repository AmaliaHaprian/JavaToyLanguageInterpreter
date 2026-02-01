package Model.SymTable;

import Model.Exception.ADT.EmptyCollection;
import Model.Exception.ADT.FullCollection;
import Model.Exception.MyException;
import Model.Value.Value;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class MyDictionary<K,T> implements MyIDictionary<K,T> {
    private Map<K,T> dictionary;
    int size;
    int capacity;

    public MyDictionary() {
        this.dictionary = new ConcurrentHashMap<>(5);
        this.capacity = 50;
        this.size = 0;
    }
    public void add(K key, T value) throws MyException {
        if(size==capacity){
            throw new MyException("Full dictionary. Cannot perform add");
        }
        this.dictionary.put(key, value);
        size++;
    }
    public T remove(K key) throws MyException {
        if(size==0){
            throw new MyException("Empty dictionary. Cannot perform remove");
        }
        size--;
        return this.dictionary.remove(key);
    }
    public boolean isDefined(K id) {
        return this.dictionary.containsKey(id);
    }
    public void update(K key, T value) {
        this.dictionary.replace(key, value);
    }
    public T lookup(K key) {
        return this.dictionary.get(key);
    }
    public int size(){
        return this.dictionary.size();
    }
    public Set<K> getKeys(){
        return this.dictionary.keySet();
    }
    public String toString(){
        String s="";
        for(K key : this.getKeys()){
            s+=key+"->"+this.lookup(key).toString()+"\n";
        }
        return s;
    }
    public Map<K,T> getContent(){
        return this.dictionary;
    }
    public void setContent(Map<K,T> content){
        this.dictionary = content;
    }
    public MyIDictionary<K,T> deepCopy(){
        MyIDictionary<K,T> newDict=new MyDictionary<>();
        for(Map.Entry<K,T> entry : this.dictionary.entrySet()){
            try {
                if (entry.getValue() instanceof Value)
                    newDict.add(entry.getKey(), (T)((Value) entry.getValue()).deepCopy());
                else
                    newDict.add(entry.getKey(), entry.getValue());
            } catch (FullCollection e) {
                throw new RuntimeException(e);
            } catch (MyException e) {
                throw new RuntimeException(e);
            }
        }
        return newDict;
    }
}
