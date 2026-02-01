package Model.ExeStack;

import Model.Exception.ADT.EmptyCollection;
import Model.Exception.ADT.FullCollection;
import Model.Exception.MyException;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws EmptyCollection;
    void push(T value) throws FullCollection;
    boolean isEmpty();
    String toString();
    int size();
    T peek() throws EmptyCollection;
    List<T> stackToList();
    List<T> reverse();
    MyIStack<T> deepCopy();
    String printInOrder();
}
