package Model.Value;

import Model.Type.StringType;
import Model.Type.Type;

public class StringValue implements Value {
    private String val;
    public StringValue(String value) {
        this.val = value;
    }
    public StringValue() {
        this.val = "";
    }
    public String getVal(){
        return this.val;
    }
    @Override
    public Type getType(){
        return new StringType();
    }
    public String toString(){
        return this.val;
    }
    public Value deepCopy() {
        return new StringValue(this.val);
    }
}
