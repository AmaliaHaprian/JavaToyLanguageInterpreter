package Model.Out;

import Model.Value.Value;

import java.util.ArrayList;
import java.util.Vector;

public class MyList<T> implements MyIList<T> {
    private Vector<T> list;
    int size;

    public MyList(){
        this.size = 0;
        this.list = new Vector<>();
    }
    public void add(T t){
        this.list.add(t);
        this.size++;
    }
    public T remove(int index){
        this.size--;
        return this.list.remove(index);
    }
    public int size(){
        return this.size;
    }
    public Vector<T> getList(){
        return this.list;
    }
    public String toString(){
        String s="";
        for(T v: this.getList()){
            s+=v.toString()+"\n";
        }
        return s;
    }
}
