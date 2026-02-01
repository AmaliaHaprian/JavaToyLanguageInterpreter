package Model.ExeStack;

import Model.Exception.ADT.EmptyCollection;
import Model.Exception.ADT.FullCollection;
import Model.IStmt.CompStmt;
import Model.IStmt.IStmt;

import java.util.*;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;
    int size;
    int capacity;

    public MyStack() {
        stack=new Stack<>();
        size=0;
        capacity=10;
    }
    public T pop() throws EmptyCollection {
        if(size==0){
            throw new EmptyCollection("Empty stack. Cannot perform pop");
        }
        T elem=stack.pop();
        size--;
        return elem;
    }
    public void push(T value) throws FullCollection {
        if(size==capacity){
            throw new FullCollection("Full stack. Cannot perform push");
        }
        stack.push(value);
        size++;
    }
    public T peek() throws EmptyCollection {
        if(size==0) throw new EmptyCollection("Empty stack. Cannot perform peek");
        return stack.peek();
    }
    public boolean isEmpty(){
        return size==0;
    }
    public int size(){
        return size;
    }
    public String toString(){
        String s="";
        List<T> reversed=new ArrayList<T>(stack);
        Collections.reverse(reversed);
        for(T elem:reversed){
            s+= elem.toString()+"\n";
        }
        return s;
    }

    public void InOrderList(T elem, List<T> result){
        if(elem==null) return;
        if (elem instanceof CompStmt comp){
            InOrderList( (T)comp.getFirst(), result);
            InOrderList((T)comp.getSnd(), result);
        }
        else result.add(elem);
    }

    public List<T> stackToList(){
        List<T> list=new ArrayList<>();
        List<T> reversed=new ArrayList<T>(stack);
        Collections.reverse(reversed);
        for(T elem:reversed){
            InOrderList(elem,list);
        }
        return list;
    }

    public String printInOrder(){
        String s="";
        List<T> list=stackToList();
        if(list.isEmpty()) return "";
        for(T elem:list){
            s+=elem.toString()+"\n";
        }
        return s;
    }

    public List<T> reverse(){
        List<T> list=new ArrayList<>();
        for(T elem:stack){
            list.add(elem);
        }
        Collections.reverse(list);
        return list;
    }
}
