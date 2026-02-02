package Model.LatchTable;

import Model.Exception.MyException;

import java.util.Map;
import java.util.Set;

public interface ILatchTable<K,T> {
    void add(K key, T value) throws MyException;
    T remove(K key) throws MyException;
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
