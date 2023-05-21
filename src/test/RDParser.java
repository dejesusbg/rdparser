package test;

import data.TokenMgr;
import data.Postfix;
import data.Infix;
import data.Parser;
import javax.swing.JOptionPane;

public class RDParser {

    public static void main(String args[]) {
        while (true) {
            // press 'ESC' or 'cancel' to exit
            String str = JOptionPane.showInputDialog("Input the expression to validate: ");
            String msg = "Input: " + str + "\n";

            if (str == null) {
                break;
            }

            TokenMgr tm = new TokenMgr(str);
            Parser parser = new Parser(tm);

            if (parser.parse()) {
                Infix evalExp = new Infix(tm.getInput());
                Postfix result = new Postfix(evalExp.getPostfix());

                msg += " = " + result.getResult();
            } else {
                msg += "Rejected input";
            }

            JOptionPane.showMessageDialog(null, msg);
        }
    }
}
