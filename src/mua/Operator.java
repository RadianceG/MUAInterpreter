package mua;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Operator {
    public static String reg = "^-?[0-9]+(.[0-9]+)?$";

    public static Value make(Value key, Value v, Namespace namespace) {
        if (key.getType() == 2) {
            namespace.inMap(key.getValue(), v);
            return v;
        } else {
            System.out.println("wrong type name");
            return null;
        }
    }

    public static Value print(Value key, Namespace namespace) {
        System.out.println(key.getValue());
        return key;
    }

    public static Value thing(Value key, Namespace namespace) {
        return namespace.getValue(key.getValue());
    }

    public static Value add(Value para1, Value para2, Namespace namespace) {
        if (para1.getValue().matches(reg) && para2.getValue().matches(reg)) {
            return new Value(1, String.valueOf(CalculateOp.PLUS.eval(para1, para2)));
        } else {
            System.out.println("wrong type");
            return null;
        }
    }

    public static Value sub(Value para1, Value para2, Namespace namespace) {
        if (para1.getValue().matches(reg) && para2.getValue().matches(reg)) {
            return new Value(1, String.valueOf(CalculateOp.MINUS.eval(para1, para2)));
        } else {
            System.out.println("wrong type");
            return null;
        }
    }

    public static Value isEmpty(Value para1, Namespace namespace) {
        if (para1.getValue() == "") {
            return new Value(3, "true");
        } else {
            return new Value(3, "false");
        }
    }

    public static Value isNumber(Value para1, Namespace namespace) {
        String reg = "^-?[0-9]+(.[0-9]+)?$";
        if (para1.getValue().matches(reg)) {
            return new Value(3, "true");
        } else {
            return new Value(3, "false");
        }
    }

    public static Value isWord(Value para1, Namespace namespace) {
        if (para1.getType() == 2) {
            return new Value(3, "true");
        } else {
            return new Value(3, "false");
        }
    }

    public static Value isBool(Value para1, Namespace namespace) {
        if (para1.getType() == 3) {
            return new Value(3, "true");
        } else {
            return new Value(3, "false");
        }
    }

    public static Value isList(Value para1, Namespace namespace) {
        if (para1.getType() == 4) {
            return new Value(3, "true");
        } else {
            return new Value(3, "false");
        }
    }

    public static Value mul(Value para1, Value para2, Namespace namespace) {
        if (para1.getValue().matches(reg) && para2.getValue().matches(reg)) {
            return new Value(1, String.valueOf(CalculateOp.TIMES.eval(para1, para2)));
        } else {
            System.out.println("wrong type");
            return null;
        }
    }

    public static Value div(Value para1, Value para2, Namespace namespace) {
        if (para1.getValue().matches(reg) && para2.getValue().matches(reg)) {
            return new Value(1, String.valueOf(CalculateOp.DIVIDE.eval(para1, para2)));
        } else {
            System.out.println("wrong type");
            return null;
        }
    }

    public static Value mod(Value para1, Value para2, Namespace namespace) {
        if (para1.getValue().matches(reg) && para2.getValue().matches(reg)) {
            return new Value(1, String.valueOf(CalculateOp.MOD.eval(para1, para2)));
        } else {
            System.out.println("wrong type");
            return null;
        }
    }

    public static String getRestList(String list, Scanner scanner) {
        if (charCounter(list, '[') == charCounter(list, ']')) {
            if (list.split(" |\\[|\\]").length == 0) {
                return "";
            }
            return list.substring(1, list.length() - 1);
        }
        list += " " + scanner.next();
        while (charCounter(list, '[') != charCounter(list, ']')) {
            list = list + " " + scanner.next();
        }
        if (list.split(" |\\[|\\]").length == 0) {
            return "";
        }
        return list.substring(1, list.length() - 1);
    }

    public static String solveExpress(String list, Scanner scanner, Namespace namespace) {
        String expression = readExpress(list, scanner, namespace);
        Pattern p = Pattern.compile("[:][a-zA-Z]+");
        Matcher m = p.matcher(expression);
        double temp = 0;
        while (m.find()) {
            temp = Double.parseDouble(namespace.getValue(m.group().substring(1)).getValue());
            expression = expression.replace(m.group(), String.valueOf((int) temp));
        }
        if (Character.isDigit(expression.toCharArray()[0])) {
            expression = expression.replaceAll(" ", "");
            return String.valueOf(Expression.getExpress(expression));
        } else {
            Interpreter listInterpreter = new Interpreter();
            Scanner listScan = new Scanner(expression);
            Value returnValue = null;
            while (listScan.hasNext()) {
                returnValue = listInterpreter.nextCMD(listScan, namespace);
            }
            return returnValue.getValue();
        }

    }

    public static String readExpress(String list, Scanner scanner, Namespace namespace) {
        if (charCounter(list, '(') == charCounter(list, ')')) {
            if (list.split(" |\\(|\\)").length == 0) {
                return "";
            }
            return list.substring(1, list.length() - 1);
        }
        list += " " + scanner.next();
        while (charCounter(list, '(') != charCounter(list, ')')) {
            list = list + " " + scanner.next();
        }
        if (list.split(" |\\(|\\)").length == 0) {
            return "";
        }
        return list.substring(1, list.length() - 1);
    }

    public static int charCounter(String list, char ch) {
        int count = 0;
        for (int i = 0; i < list.length(); i++) {
            if (list.charAt(i) == ch) {
                count++;
            }
        }
        return count;
    }

    public static Value run(Value value, Namespace namespace) {
        if (value.getValue() == "") {
            return value;
        }
        Interpreter listInterpreter = new Interpreter();
        Scanner listScan = new Scanner(value.getValue());
        Value returnValue = null;
        while (listScan.hasNext()) {
            returnValue = listInterpreter.nextCMD(listScan, namespace);
        }
        return returnValue;
    }

    public static Value erase(Value value, Namespace namespace) {
        return namespace.removeValue(value.getValue());
    }

    public static Value isname(Value value, Namespace namespace) {
        return new Value(3, String.valueOf(namespace.isName(value.getValue())));
    }

    public static Value eq(Value v1, Value v2) {
        if (v1.getType() == 1 && v2.getType() == 1) {
            return new Value(3, String.valueOf((int) (Double.parseDouble(v1.getValue())) == (int) (Double.parseDouble(v2.getValue()))));
        } else {
            return new Value(3, String.valueOf((v1.getValue().compareTo(v2.getValue()) == 0) ? true : false));
        }
    }

    public static Value gt(Value v1, Value v2) {
        if (v1.getType() == 1 && v2.getType() == 1) {
            return new Value(3, String.valueOf(Double.valueOf(v1.getValue()) > Double.valueOf(v2.getValue())));
        } else {
            return new Value(3, String.valueOf((v1.getValue().compareTo(v2.getValue()) > 0) ? true : false));
        }
    }

    public static Value export(Value v1, Namespace namespace) {
        Main.global.inMap(v1.getValue(), namespace.getValue(v1.getValue()));
        return v1;
    }


    public static Value lt(Value v1, Value v2) {
        if (v1.getType() == 1 && v2.getType() == 1) {
            return new Value(3, String.valueOf(Double.valueOf(v1.getValue()) < Double.valueOf(v2.getValue())));
        } else {
            return new Value(3, String.valueOf((v1.getValue().compareTo(v2.getValue()) < 0) ? true : false));
        }
    }

    public static Value and(Value v1, Value v2) {
        return new Value(3, String.valueOf(Boolean.valueOf(v1.getValue()) && Boolean.valueOf(v2.getValue())));
    }

    public static Value or(Value v1, Value v2) {
        return new Value(3, String.valueOf(Boolean.valueOf(v1.getValue()) || Boolean.valueOf(v2.getValue())));
    }

    public static Value not(Value v1) {
        return new Value(3, String.valueOf(!Boolean.valueOf(v1.getValue())));
    }

    public static Value iif(Value v1, Value v2, Value v3, Namespace namespace) {
        if (Boolean.valueOf(v1.getValue())) {
            return run(v2, namespace);
        } else {
            return run(v3, namespace);
        }
    }

    public static Value return1(Value v1, Namespace namespace) {
        namespace.inMap("-returnVALUE", v1);
        return v1;
    }

    public Value function(Scanner scanner, Namespace nameSpace, String command) {
        Value realFunction = null;
        Namespace localNamespace = new Namespace();
        ArrayList<Value> valueArrayList = new ArrayList<Value>();
        localNamespace.getMap().putAll(nameSpace.getMap());
        String paraList = "";
        String funList = "";
        Value func = new Value(4, "");
        if (nameSpace.getValue(command) != null) {
            realFunction = nameSpace.getValue(command);
        } else {
            realFunction = Main.global.getValue(command);
        }
        String realFunctionClean = realFunction.getValue().trim();
        char[] realFunctionSTR = realFunctionClean.toCharArray();

        for (int i = 0; i < realFunction.getValue().length(); i++) {
            if (realFunctionSTR[i] == ']') {
                paraList = realFunction.getValue().trim().substring(1, i);
                break;
            }
        }
        for (int i = 1; i < realFunction.getValue().length(); i++) {
            if (realFunctionSTR[i] == '[') {
                funList = realFunction.getValue().trim().substring(i + 1, realFunction.getValue().trim().length() - 1);
                func = new Value(4, funList);
                break;
            }
        }
        String[] paras = paraList.split(" ");
        if (!paraList.equals("")) {
            for (int i = 0; i < paras.length; i++) {
                Interpreter localInterpreter = new Interpreter();
                Value value = localInterpreter.nextCMD(scanner, localNamespace);
                valueArrayList.add(value);
            }
            for (int i = 0; i < paras.length; i++) {
                localNamespace.inMap(paras[i], valueArrayList.get(i));
            }
        }

        Value returnValue = Operator.run(func, localNamespace);
        if (localNamespace.getValue("-returnVALUE") != null) {
            return localNamespace.getValue("-returnVALUE");
        } else {
            return returnValue;
        }

    }

}
