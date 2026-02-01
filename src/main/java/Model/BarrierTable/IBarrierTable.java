package Model.BarrierTable;

import Model.Exception.MyException;

import java.util.Map;
import java.util.Set;

public interface IBarrierTable<K,T> {
    Integer getFreeLocation();
    void add(K key, T value) throws MyException;
    T remove(K key) throws MyException;
    boolean isDefined(K id);
    void update(K key, T value);
    T lookup(K key);
    int size();
    Set<K> getKeys();
    String toString();
    Map<K,T> getContent();
    IBarrierTable<K,T> deepCopy();
}
