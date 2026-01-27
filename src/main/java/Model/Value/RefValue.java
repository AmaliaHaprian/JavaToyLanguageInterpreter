package Model.Value;

import Model.Type.RefType;
import Model.Type.Type;

public class RefValue implements Value {
    private int address;
    private Type locationType;

    public RefValue(int address, Type locationType) {
        this.address = address;
        this.locationType = locationType;
    }
    public Type getLocationType() {
        return locationType;
    }
    public Type getType(){
        return new RefType(locationType);
    }
    public int getAddr(){
        return address;
    }
    public String toString(){
        return address + ":" + locationType.toString();
    }
    public  Value deepCopy(){
        return new RefValue(address, locationType.deepCopy());
    }
}
