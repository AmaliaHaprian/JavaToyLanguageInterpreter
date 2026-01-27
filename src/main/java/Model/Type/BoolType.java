package Model.Type;

import Model.Value.BoolValue;
import Model.Value.Value;

public class BoolType implements Type{
    public boolean equals(Object another){
        if(another instanceof BoolType){
            return true;
        }
        else{
            return false;
        }
    }
    @Override
    public String toString(){
        return "boolean";
    }
    @Override
    public Value defaultValue() {
        return new BoolValue();
    }
    public Type deepCopy() {
        return new BoolType();
    }
}
