package mua;

import java.util.Scanner;
class LuoErrorCode {

    private String value;
    private String desc;

    public LuoErrorCode(String value, String desc) {
        this.setValue(value);
        this.setDesc(desc);
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    @Override
    public String toString() {
        return "[" + this.value + "]" + this.desc;
    }
}
class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public BusinessException(Object Obj) {
        super(Obj.toString());
    }

}
public class Main {

    public static void main(String args[]) {
        Object user = null;
        LuoErrorCode luotry = new LuoErrorCode("LUO1", "what");
        if(user == null){
            throw new BusinessException(luotry);
        }
    }
}