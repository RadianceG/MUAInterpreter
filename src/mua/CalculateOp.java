package mua;

public enum CalculateOp {
    PLUS {
        double eval(Value x, Value y) {
            p = Double.valueOf(x.getValue());
            q = Double.valueOf(y.getValue());
            return p + q;
        }
    },
    MINUS {
        double eval(Value x, Value y) {
            p = Double.valueOf(x.getValue());
            q = Double.valueOf(y.getValue());
            return p - q;
        }
    },
    TIMES {
        double eval(Value x, Value y) {
            p = Double.valueOf(x.getValue());
            q = Double.valueOf(y.getValue());
            return p * q;
        }
    },
    DIVIDE {
        double eval(Value x, Value y) {
            p = Double.valueOf(x.getValue());
            q = Double.valueOf(y.getValue());
            return p / q;
        }
    },
    MOD {
        double eval(Value x, Value y) {
            p = Double.valueOf(x.getValue());
            q = Double.valueOf(y.getValue());
            return p % q;
        }
    };

    // Do arithmetic op represented by this constant
    abstract double eval(Value x, Value y);

    double p, q;
}
