package Model.SemaphoreTable;

import Model.Exception.ADT.FullCollection;
import Model.SymTable.MyIDictionary;

import java.util.Map;
import java.util.Set;

public interface ISemaphoreTable<K,T> {
    void add(K key, T value) throws FullCollection;
    boolean isDefined(K id);
    void update(K key, T value);
    T lookup(K key);
    Set<K> getKeys();
    String toString();
    Map<K,T> getContent();
    ISemaphoreTable<K,T> deepCopy();
    Integer getLocation();
}
