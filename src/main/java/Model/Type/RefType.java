package Model.Type;

import Model.Value.RefValue;
import Model.Value.Value;

public class RefType implements Type{
    private Type inner;
    public RefType(Type inner){
        this.inner = inner;
    }
    public RefType(){

    }
    public Type getInner(){
        return inner;
    }
    public boolean equals(Object o){
        if(o instanceof RefType){
            return inner.equals(((RefType) o).getInner());
        }
        else
            return false;
    }
    public String toString(){
        return "Ref("+inner.toString()+")";
    }
    public Value defaultValue() {
        return new RefValue(0,inner);
    }
    public Type deepCopy(){
        return new RefType(inner.deepCopy());
    }
}
