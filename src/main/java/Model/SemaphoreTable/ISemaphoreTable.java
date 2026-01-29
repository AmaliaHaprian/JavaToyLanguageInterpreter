package Model.SemaphoreTable;

import Model.Exception.ADT.EmptyCollection;
import Model.Exception.ADT.FullCollection;
import Model.SymTable.MyIDictionary;

import java.util.Map;
import java.util.Set;

public interface ISemaphoreTable<K,T> {
    String toString();
    void add(K key, T value) throws FullCollection;
    T remove(K key) throws EmptyCollection;
    boolean isDefined(K id);
    void update(K key, T value);
    T lookup(K key);
    Set<K> getKeys();
    Map<K,T> getContent();
    ISemaphoreTable<K,T> deepCopy();
    Integer getFreeLocation();
}
