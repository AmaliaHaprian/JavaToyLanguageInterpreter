package Model.Type;

import Model.Value.Value;

public interface Type {
    boolean equals(Object o);
    String toString();
    Value defaultValue();
    Type deepCopy();
}
