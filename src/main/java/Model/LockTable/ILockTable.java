package Model.LockTable;

import Model.Exception.ADT.FullCollection;
import Model.Exception.MyException;
import Model.SymTable.MyIDictionary;

import java.util.Map;
import java.util.Set;

public interface ILockTable<K, V> {
    String toString();
    void add(K key, V value) throws MyException;
    void update(K key, V value) throws MyException;
    V remove(K key);
    boolean isDefined(K id);
    ILockTable<K, V> deepCopy();
    V lookup(K key);
    Set<K> getKeys();
    Integer getFreeLocation();
    Map<K,V> getContent();
}
