package Model.Value;

import Model.Type.IntType;
import Model.Type.Type;

public class IntValue implements Value {
    int val;

    public IntValue(int val){
        this.val = val;
    }
    public IntValue(){
        this.val = 0;
    }
    public int getVal(){
        return val;
    }
    @Override
    public Type getType(){
        return new IntType();
    }
    @Override
    public String toString(){
        return String.valueOf(val);
    }
    public Value deepCopy() {
        return new IntValue(val);
    }
}
