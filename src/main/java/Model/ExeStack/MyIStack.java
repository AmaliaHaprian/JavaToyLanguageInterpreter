package Model.ExeStack;

import Model.Exception.MyException;

import java.util.List;

public interface MyIStack<T> {
    T pop() throws MyException;
    void push(T value) throws MyException;
    boolean isEmpty();
    String toString();
    int size();
    T peek() throws MyException;
    String print();
    List<T> stackToList();
    List<T> reverse();
}
