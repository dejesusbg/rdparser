package data;

import java.util.*;

public class Postfix {

    private final String r; // resultado final
    private final Stack<Float> s; // pila de evaluación
    private final String[] t; // tokens

    public Postfix(String str) {
        float n2, n1;

        s = new Stack<>();
        t = str.split(" ");

        for (String token : t) {
            if (isOperator(token)) {
                n2 = s.pop();
                n1 = s.pop();
                s.push(eval(token, n1, n2));
            } else {
                s.push(Float.valueOf(token));
            }
        }

        r = s.pop().toString();
    }

    private boolean isOperator(String token) {
        String op = "+-*/^";
        return op.contains(token);
    }

    public String getResult() {
        return r;
    }

    private float eval(String op, float n1, float n2) {
        float result = 0;

        switch (op) {
            case "+" ->
                result = n1 + n2;
            case "-" ->
                result = n1 - n2;
            case "*" ->
                result = n1 * n2;
            case "/" ->
                result = n1 / n2;
            case "^" ->
                result = (float) (Math.pow(n1, n2));
        }

        return result;
    }
}
