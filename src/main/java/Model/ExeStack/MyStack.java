package Model.ExeStack;

import Model.Exception.ADT.EmptyCollection;
import Model.Exception.ADT.FullCollection;
import Model.Exception.MyException;
import Model.IStmt.CompStmt;
import Model.IStmt.IStmt;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class MyStack<T> implements MyIStack<T> {
    Stack<T> stack;
    int size;
    int capacity;

    public MyStack() {
        stack=new Stack<>();
        size=0;
        capacity=10;
    }
    public T pop() throws MyException {
        if(size==0){
            throw new MyException("Empty stack. Cannot perform pop");
        }
        T elem=stack.pop();
        size--;
        return elem;
    }
    public void push(T value) throws MyException {
        if(size==capacity){
            throw new MyException("Full stack. Cannot perform push");
        }
        stack.push(value);
        size++;
    }
    public T peek() throws MyException {
        if(size==0) throw new MyException("Empty stack. Cannot perform peek");
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

    public String print(){
        String s="";
        List<T> reversed=new ArrayList<T>(stack);
        Collections.reverse(reversed);
        for(T elem:reversed){
            s+= InOrder((IStmt) elem);
        }
        return s.stripTrailing();
    }
    public String InOrder(IStmt stmt){
        if(stmt==null) return "";
        if (stmt instanceof CompStmt){
            String left= InOrder(((CompStmt) stmt).getFirst());
            String right= InOrder(((CompStmt) stmt).getSnd());
            return left+"\n"+right+"\n";
        }
        else return stmt.toString()+"\n";
    }

    public void InOrderList(IStmt stmt, List<IStmt> result){
        if(stmt==null) return;
        if (stmt instanceof CompStmt){
            InOrderList(((CompStmt) stmt).getFirst(), result);
            InOrderList(((CompStmt) stmt).getSnd(), result);
        }
        else result.add(stmt);
    }
    public List<T> stackToList(){
        List<T> list=new ArrayList<>();
        List<T> reversed=new ArrayList<T>(stack);
        Collections.reverse(reversed);
        for(T elem:reversed){
            InOrderList((IStmt) elem, (List<IStmt>) list);
        }
        return list;
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
