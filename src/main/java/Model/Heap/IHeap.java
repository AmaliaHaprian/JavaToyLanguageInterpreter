package Model.Heap;

import Model.Exception.ADT.FullCollection;
import Model.SymTable.MyIDictionary;

public interface IHeap<K,V> extends MyIDictionary<K,V> {
    String toString();
    int getNextKey();
}
