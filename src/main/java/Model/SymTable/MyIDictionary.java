package Model.SymTable;

import Model.Exception.ADT.EmptyCollection;
import Model.Exception.ADT.FullCollection;

import java.util.Map;
import java.util.Set;

public interface MyIDictionary<K,T> {
    void add(K key, T value) throws FullCollection;
    T remove(K key) throws EmptyCollection;
    boolean isDefined(K id);
    void update(K key, T value);
    T lookup(K key);
    int size();
    Set<K> getKeys();
    String toString();
    void setContent(Map<K,T> map);
    Map<K,T> getContent();
    MyIDictionary<K,T> deepCopy();
}
