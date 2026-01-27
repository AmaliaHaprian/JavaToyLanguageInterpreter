package Model.Out;

import java.util.ArrayList;
import java.util.Vector;

public interface MyIList<T> {
    void add(T elem);
    T remove(int index);
    int size();
    Vector<T> getList();
    String toString();
}
