package Model.Type;

import Model.Value.StringValue;
import Model.Value.Value;

public class StringType implements Type {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof StringType) {
            return true;
        }
        else {
            return false;
        }
    }
    @Override
    public String toString() {
        return "string";
    }
    @Override
    public Value defaultValue() {
        return new StringValue();
    }
    public Type deepCopy() {
        return new StringType();
    }
}
