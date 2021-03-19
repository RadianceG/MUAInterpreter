package mua;

public class Value {
    private int type;//1 number 2 string 3 boolean 4 list
    private String value;

    public Value(int type1, String value1) {
        type = type1;
        value = value1;
    }

    public Value(Value v) {
        type = v.type;
        value = v.value;
    }

    public void setType(int type1) {
        type = type1;
    }

    public int getType() {
        return type;
    }

    public void setValue(String value1) {
        value = value1;
    }

    public String getValue() {
        return value;
    }

    public String toString() {
        return value;
    }
}
