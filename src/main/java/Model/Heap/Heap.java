package Model.Heap;

import Model.Exception.ADT.FullCollection;
import Model.SymTable.MyDictionary;
import Model.SymTable.MyIDictionary;
import Model.Value.Value;

public class Heap<Integer,Value> extends MyDictionary<Integer,Value> implements IHeap<Integer,Value> {
    static int lastKey=0;
    public String toString(){
        String s="";
        for(Integer key : this.getKeys()){
            s+=key+"->"+this.lookup(key).toString()+"\n";
        }
        return s;
    }
    public int getNextKey(){
        lastKey++;
        return lastKey;
    }
}
