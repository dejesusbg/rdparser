/*

Grammar productions.

1. E -> I T
2. I -> + T | - T | ε
3. T -> P F
4. P -> * F | / F | ε
5. F -> B A
6. B -> ^ A | ε
7. A -> ( E ) | N
8. N -> n "any valid real number" | ε

Each nonterminal represents:

1. E -> Expression
2. I -> Operation (addition or subtraction)
3. T -> Term
4. P -> Product (multiplication or division)
5. F -> Factor
6. B -> Base (exponentiation)
7. A -> Atom
8. N -> Number

 */
package data;

import java.util.*;

public class Parser {

    private final TokenMgr tm; // token manager
    private final Stack<Character> stk; // stack machine
    private char currentToken; // current token

    public Parser(TokenMgr tm) {
        this.tm = tm;
        advance();
        stk = new Stack<>();
        stk.push('▼'); // empty stack symbol
        stk.push('E'); // first nonterminal of the grammar
    }

    private void advance() {
        currentToken = tm.getNextToken();
    }

    public boolean parse() {
        ArrayList<String> errors = new ArrayList<>();
        boolean done = false;

        while (!done) {
            switch (stk.peek()) {
                case 'E' -> {
                    switch (currentToken) {
                        case '(', 'n' -> {
                            stk.pop();
                            stk.add('I');
                            stk.add('T');
                        }
                        case '+', '-' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('I');
                        }
                        case '*', '/' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('P');
                        }
                        case '^' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('B');
                        }
                        case ')' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push(')');
                        }
                        case 'd', '┤' -> {
                            errors.add("Unfinished expression");
                            stk.pop();
                        }
                        default ->
                            done = true;
                    }
                }

                case 'I' -> {
                    switch (currentToken) {
                        case '+', '-' -> {
                            stk.add('T');
                            advance();
                        }
                        case ')', '┤' ->
                            stk.pop();
                        default ->
                            done = true;
                    }
                }

                case 'T' -> {
                    switch (currentToken) {
                        case '(', 'n' -> {
                            stk.pop();
                            stk.add('P');
                            stk.add('F');
                        }
                        case '+', '-' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('I');
                        }
                        case '*', '/' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('P');
                        }
                        case '^' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('B');
                        }
                        case ')' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push(')');
                        }
                        case 'd', '┤' -> {
                            errors.add("Unfinished expression");
                            stk.pop();
                        }
                        default ->
                            done = true;
                    }
                }

                case 'P' -> {
                    switch (currentToken) {
                        case '*', '/' -> {
                            stk.add('F');
                            advance();
                        }
                        case '+', '-', ')', '┤' ->
                            stk.pop();
                        default ->
                            done = true;
                    }
                }

                case 'F' -> {
                    switch (currentToken) {
                        case '(', 'n' -> {
                            stk.pop();
                            stk.add('B');
                            stk.add('A');
                        }
                        case '+', '-' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('I');
                        }
                        case '*', '/' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('P');
                        }
                        case '^' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('B');
                        }
                        case ')' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push(')');
                        }
                        case 'd', '┤' -> {
                            errors.add("Unfinished expression");
                            stk.pop();
                        }
                        default ->
                            done = true;
                    }
                }

                case 'B' -> {
                    switch (currentToken) {
                        case '^' -> {
                            stk.add('A');
                            advance();
                        }
                        case '+', '-', '*', '/', ')', '┤' ->
                            stk.pop();
                        case '(', 'n' -> {
                            if (currentToken == 'n') {
                                errors.add("Input: number, Expected: OP or (");
                            } else {
                                errors.add("Input: " + currentToken + ", Expected: OP or (");
                            }
                            stk.pop();
                            stk.push('A');
                        }
                        default ->
                            done = true;
                    }
                }

                case 'A' -> {
                    switch (currentToken) {
                        case '(' -> {
                            stk.pop();
                            stk.add(')');
                            stk.add('E');
                            advance();
                        }
                        case 'n' -> {
                            stk.pop();
                            stk.push('N');
                        }
                        case '+', '-', '^' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('I');
                        }
                        case '*', '/' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('P');
                        }
                        case ')' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push(')');
                        }
                        case 'd', '┤' -> {
                            errors.add("Unfinished expression");
                            stk.pop();
                        }
                        default ->
                            done = true;
                    }
                }

                case 'N' -> {
                    switch (currentToken) {
                        case '+', '-', '*', '/', ')', '^', '┤' ->
                            stk.pop();
                        case 'n' ->
                            advance();
                        case 'd' -> {
                            errors.add("Multiple punctuacion");
                            stk.pop();
                        }
                        case '(' -> {
                            errors.add("Input: " + currentToken + ", Expected: OP or (");
                            stk.pop();
                            stk.push('A');
                        }
                        default ->
                            done = true;
                    }
                }

                case ')' -> {
                    switch (currentToken) {
                        case ')' -> {
                            stk.pop();
                            advance();
                        }
                        case '┤' -> {
                            errors.add("Left parentheses is missing");
                            stk.pop();
                        }
                        default ->
                            done = true;
                    }
                }

                case '▼' -> {
                    switch (currentToken) {
                        case '+', '-' -> {
                            errors.add("Input: " + currentToken + ", not expected");
                            stk.push('I');
                        }
                        case ')' -> {
                            errors.add("Right parentheses not expected");
                            stk.push(')');
                        }
                        default ->
                            done = true;
                    }
                }

            }
        }

        for (int i = 0; i < errors.size(); i++) {
            System.out.println(errors.get(i));
        }

        return (currentToken == '┤' && stk.peek() == '▼' && errors.isEmpty());
    }
}
