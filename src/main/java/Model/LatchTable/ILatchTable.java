package Model.LatchTable;

import Model.Exception.ADT.EmptyCollection;
import Model.Exception.ADT.FullCollection;

import java.util.Map;
import java.util.Set;

public interface ILatchTable<K,T> {
    void add(K key, T value) throws FullCollection;
    T remove(K key) throws EmptyCollection;
    boolean isDefined(K id);
    void update(K key, T value);
    T lookup(K key);
    int size();
    Set<K> getKeys();
    String toString();
    Map<K,T> getContent();
    ILatchTable<K,T> deepCopy();
    Integer getLocation();
}
