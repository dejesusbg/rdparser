package data;

public class TokenMgr {

    private String input;
    private int index;

    public TokenMgr(String input) {
        this.input = input;
        this.index = 0;
    }

    public String getInput() {
        return input;
    }

    public char getNextToken() {
        if (index < input.length()) {
            char c;

            switch (input.charAt(index)) {
                case '0', '1', '2', '3', '4', '5','6','7','8','9' ->
                    c = 'n';
                case '.' ->
                    c = isValidDecimal() ? 'n' : 'd';
                case '-' ->
                    c = isNegative() ? 'n' : '-';
                default ->
                    c = input.charAt(index);
            }

            index++;
            return c;
        } else {
            return '┤'; // end of string symbol
        }
    }

    private boolean isValidDecimal() {
        // check if there is multiple punctuation
        for (int i = index - 1; i >= 0; i--) {
            if (input.charAt(i) == '.') {
                return false;
            } else if (!Character.isDigit(input.charAt(i))) {
                break;
            }
        }

        // check if token is a valid decimal number
        return (index - 1 >= 0 && Character.isDigit(input.charAt(index - 1)))
                || (index + 1 < input.length() && Character.isDigit(input.charAt(index + 1)));
    }

    private boolean isNegative() {
        String leftChar = "+-*/^()";
        String rightChar = "0123456789.";

        // check if token is a valid negative number
        boolean result = ((index - 1 < 0 || leftChar.contains(input.charAt(index - 1) + "")) && index + 1 != input.length()
                && (index + 1 >= input.length() || rightChar.contains(input.charAt(index + 1) + "")));

        if (result) {
            char[] c = input.toCharArray();
            c[index] = '—'; // em-dash (to represent negative numbers)
            input = String.valueOf(c);
        }

        return result;
    }
}
