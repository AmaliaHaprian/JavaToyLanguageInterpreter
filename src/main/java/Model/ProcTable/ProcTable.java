package Model.ProcTable;

import Model.IStmt.IStmt;
import Model.SymTable.MyDictionary;
import Pair.Pair;

import java.util.HashMap;
import java.util.List;

public class ProcTable extends MyDictionary<String, Pair<List<String>, IStmt>> implements IProcTable {
    public String toString(){
        String s="";
        for(String key : this.getKeys()){
            s+=key+"->"+this.lookup(key).toString()+"\n";
        }
        return s;
    }
}
