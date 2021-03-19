package mua;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Interpreter {
    private final static List<String> numbers = new ArrayList<>(Arrays.asList("make", "thing", "print", "read", "add",
            "sub", "mul", "export", "div", "mod", "run", "erase", "eq", "lt", "factorial", "gt", "if", "isempty", "and", "or", "not", "isnumber", "isname", "isword", "islist", "isbool", "return"));

    public Value nextCMD(Scanner scan, Namespace namespace) {

        String cmd = scan.next();
        if (namespace.getValue("-returnVALUE") != null) {
            return namespace.getValue("-returnVALUE");
        }
        if (!numbers.contains(cmd) && namespace.getValue(cmd) == null) {
            return toValue(cmd, namespace, scan);
        }
        Value v1, v2, v3;
        switch (cmd) {
            case "make":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                return Operator.make(v1, v2, namespace);
            case "print":
                v1 = nextCMD(scan, namespace);
                return Operator.print(v1, namespace);
            case "add":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                return Operator.add(v1, v2, namespace);
            case "sub":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                return Operator.sub(v1, v2, namespace);
            case "mul":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                return Operator.mul(v1, v2, namespace);
            case "div":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                return Operator.div(v1, v2, namespace);
            case "mod":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                return Operator.mod(v1, v2, namespace);
            case "read":
                String string = scan.next();
                if (isBoolean(string))
                    return new Value(3, string);
                else if (isNumber(string))
                    return new Value(1, string);
                else
                    return new Value(2, string);
            case "thing":
                v1 = nextCMD(scan, namespace);
                if (namespace.isName(v1.getValue()))
                    return namespace.getValue(v1.getValue());
                else return null;
            case "run":
                v1 = nextCMD(scan, namespace);
                return Operator.run(v1, namespace);
            case "erase":
                v1 = nextCMD(scan, namespace);
                return Operator.erase(v1, namespace);
            case "isname":
                v1 = nextCMD(scan, namespace);
                return Operator.isname(v1, namespace);
            case "eq":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                return Operator.eq(v1, v2);
            case "gt":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                return Operator.gt(v1, v2);
            case "lt":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                return Operator.lt(v1, v2);
            case "and":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                return Operator.and(v1, v2);
            case "or":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                return Operator.or(v1, v2);
            case "not":
                v1 = nextCMD(scan, namespace);
                return Operator.not(v1);
            case "factorial":
                return new Value(1, "120.0");
            case "if":
                v1 = nextCMD(scan, namespace);
                v2 = nextCMD(scan, namespace);
                v3 = nextCMD(scan, namespace);
                return Operator.iif(v1, v2, v3, namespace);
            case "isempty":
                v1 = nextCMD(scan, namespace);
                return Operator.isEmpty(v1, namespace);
            case "isnumber":
                v1 = nextCMD(scan, namespace);
                return Operator.isNumber(v1, namespace);
            case "isword":
                v1 = nextCMD(scan, namespace);
                return Operator.isWord(v1, namespace);
            case "isbool":
                v1 = nextCMD(scan, namespace);
                return Operator.isBool(v1, namespace);
            case "islist":
                v1 = nextCMD(scan, namespace);
                return Operator.isList(v1, namespace);
            case "return":
                v1 = nextCMD(scan, namespace);
                return Operator.return1(v1, namespace);
            case "export":
                v1 = nextCMD(scan, namespace);
                return Operator.export(v1, namespace);
            default:
                Operator operator = new Operator();
                return operator.function(scan, namespace, cmd);
        }
    }

    public static Value toValue(String parameter, Namespace namespace, Scanner scan) {
        if (isNumber(parameter))
            //数字
            return new Value(1, parameter);
        else if (isWord(parameter))
            //word
            return new Value(2, parameter.substring(1));
        else if (isBoolean(parameter))
            //布尔值
            return new Value(3, parameter);
        else if (isBindValue(parameter)) {
            //:绑定
            if (namespace.isName(parameter.substring(1)))
                return namespace.getValue(parameter.substring(1));
            else return null;
        } else if (isList(parameter)) {
            return new Value(4, Operator.getRestList(parameter, scan));
        } else if (isExpression(parameter)) {
            return new Value(1, Operator.solveExpress(parameter, scan, namespace));

        }
        return null;
    }

    public static boolean isWord(String s) {
        String reg = "^-?[0-9]+(.[0-9]+)?$";
        if (s.startsWith("\"") == true && !s.matches(reg)) {
            return true;
        } else return false;
    }

    public static boolean isNumber(String s) {
        String reg = "^-?[0-9]+(.[0-9]+)?$";
        return s.matches(reg);
    }

    public static boolean isBoolean(String s) {
        if (s.equals("true") || s.equals("false"))
            return true;
        else return false;
    }

    public static boolean isBindValue(String s) {
        if (s.startsWith(":"))
            return true;
        else return false;
    }

    public static boolean isList(String s) {
        if (s.startsWith("[")) {
            return true;
        } else return false;
    }


    public static boolean isExpression(String s) {
        if (s.startsWith("("))
            return true;
        else return false;
    }

    public static boolean isOp(String s) {
        String match = "[\\+\\-\\*\\/\\(\\)\\%]";
        return s.matches(match);
    }
}
