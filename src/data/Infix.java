package data;

import java.util.Stack;

public class Infix {

    private Stack<String> in; // input
    private Stack<String> out; // output
    private Stack<String> op; // operators
    private String r; // postfix notation

    public Infix(String a) {
        this.in = new Stack<>();
        this.out = new Stack<>();
        this.op = new Stack<>();

        String expr = debug(a);
        String[] init = expr.split(" ");

        for (int i = init.length - 1; i >= 0; i--) {
            in.push(init[i]);
        }

        while (!in.isEmpty()) {
            switch (priority(in.peek())) {
                case 1 ->
                    op.push(in.pop());
                case 2 -> {
                    while (!op.peek().equals("(")) {
                        out.push(op.pop());
                    }
                    op.pop();
                    in.pop();
                }
                case 3, 4, 5 -> {
                    while (priority(op.peek()) >= priority(in.peek())) {
                        out.push(op.pop());
                    }
                    op.push(in.pop());
                }
                case 6 -> {
                    out.push("O");
                    in.pop();
                }
                default ->
                    out.push(in.pop());
            }
        }

        // evaluation of unary minus operator
        for (int index = 0; index < out.size(); index++) {
            if (out.get(index).equals("O")) {
                out.set(index, "0");
                if (index + 1 < out.size()) {
                    out.add(index + 2, "-");
                    index += 2;
                }
            }
        }

        r = out.toString().replaceAll("[\\]\\[,]", "");

    }

    // final postfix notation
    public String getPostfix() {
        System.out.println("Postfix: " + r);
        return (r != null) ? r : "1";
    }

    // expression debugging
    private String debug(String s) {
        s = s.replaceAll("\\s+", "");
        s = "(" + s + ")";
        String symbols = "+-*/^()—";
        String str = "";

        for (int i = 0; i < s.length(); i++) {
            if (symbols.contains("" + s.charAt(i))) {
                str += " " + s.charAt(i) + " ";
            } else {
                str += s.charAt(i);
            }
        }

        return str.replaceAll("\\s+", " ").trim();
    }

    // preference of operators
    private int priority(String op) {
        int n = 7;

        switch (op) {
            case "—" -> // unary minus operator
                n = 6;
            case "^" ->
                n = 5;
            case "*", "/" ->
                n = 4;
            case "+", "-" ->
                n = 3;
            case ")" ->
                n = 2;
            case "(" ->
                n = 1;
        }

        return n;
    }
}
