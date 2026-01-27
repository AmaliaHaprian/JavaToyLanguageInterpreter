package Model.Value;

import Model.Type.BoolType;
import Model.Type.Type;

public class BoolValue implements Value {
    boolean val;

    public BoolValue(boolean val) {
        this.val = val;
    }
    public BoolValue() {
        this.val = false;
    }

    public boolean getVal(){
        return val;
    }

    @Override
    public Type getType(){
        return new BoolType();
    }
    @Override
    public String toString(){
        return Boolean.toString(val);
    }
    public Value deepCopy() {
        return new BoolValue(val);
    }
}
