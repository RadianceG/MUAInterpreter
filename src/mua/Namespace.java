package mua;

import java.util.HashMap;

public class Namespace implements Cloneable {
    private HashMap<String, Value> namespace;

    public Namespace() {
        namespace = new HashMap<String, Value>();
    }

    public Namespace(Namespace outnamespace) {
        this.namespace.putAll(outnamespace.getMap());
    }

    public void inMap(String key, Value v) {
        namespace.put(key, v);
    }

    public HashMap getMap() {
        return namespace;
    }

    public void setMap(Namespace outnamespace) {
        this.namespace.putAll(outnamespace.getMap());
    }


    public Value outMap(String key) {
        Value retValue = namespace.get(key);
        namespace.remove(key);
        return retValue;
    }

    public Value getValue(String key) {
        if (namespace.get(key) != null)
            return namespace.get(key);
        else return null;
    }

    public boolean isName(String key) {
        return namespace.containsKey(key);
    }

    public Value removeValue(String key) {
        if (namespace.get(key) != null) {
            Value temp = namespace.get(key);
            namespace.remove(key);
            return temp;
        } else {
            return null;
        }
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Namespace clonenamespace = (Namespace) super.clone();
        clonenamespace.setMap(this);
        return clonenamespace;
    }
}
