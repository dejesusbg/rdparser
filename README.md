# Recursive Descendant Parser

## Overview

This repository contains a Java implementation of a recursive descent parser designed to validate and evaluate arithmetic expressions. The project demonstrates core principles of compiler construction, including lexical analysis (tokenization), parsing, and expression evaluation. It uses a predictive parsing algorithm based on a predefined context-free grammar to check the syntax of an input expression. If the expression is valid, it is converted to postfix notation and evaluated.

## Features

*   **Syntax Validation:** Uses a predictive, stack-based parser to validate arithmetic expressions against a formal grammar.
*   **Expression Evaluation:** Calculates the result of syntactically correct expressions.
*   **Operator Support:** Supports addition (`+`), subtraction (`-`), multiplication (`*`), division (`/`), and exponentiation (`^`).
*   **Operator Precedence:** Correctly handles the standard order of operations.
*   **Parentheses:** Supports grouping of sub-expressions using `()`.
*   **Unary Minus:** Correctly interprets and processes negative numbers at the beginning of an expression or after an operator.
*   **Error Reporting:** Provides basic console feedback for syntactically incorrect expressions.

## How It Works

The application processes an input string through a pipeline of components to validate and evaluate it.

1.  **Tokenizer (`TokenMgr.java`)**
    The `TokenMgr` class acts as the lexical analyzer. It scans the raw input string character by character and converts it into a sequence of tokens. Each token represents a category like a number (`n`), an operator (`+`, `-`, `*`, `/`, `^`), or a parenthesis. It includes special logic to distinguish a binary subtraction operator from a unary minus, representing the latter with a unique character (`—`) to simplify parsing.

2.  **Parser (`Parser.java`)**
    The `Parser` implements a predictive recursive descent parser. It uses a stack and the stream of tokens from `TokenMgr` to determine if the expression is syntactically valid according to the grammar rules. If the sequence of tokens can be successfully derived from the grammar, the expression is considered valid.

3.  **Infix to Postfix Conversion (`Infix.java`)**
    Once an expression is validated, the `Infix` class converts it from standard infix notation to postfix notation (Reverse Polish Notation or RPN). This conversion is achieved using a variant of the Shunting-yard algorithm, which processes tokens and manages an operator stack to handle precedence and associativity correctly.

4.  **Postfix Evaluation (`Postfix.java`)**
    The `Postfix` class takes the RPN expression and evaluates it using a stack-based algorithm. It iterates through the postfix tokens, pushing numbers onto a stack. When an operator is encountered, it pops the required number of operands, performs the calculation, and pushes the result back onto the stack. The final result is the last value remaining on the stack.

## Grammar

The parser validates the input expression against the following context-free grammar productions:

1.  `E -> I T`
2.  `I -> + T | - T | ε`
3.  `T -> P F`
4.  `P -> * F | / F | ε`
5.  `F -> B A`
6.  `B -> ^ A | ε`
7.  `A -> ( E ) | N`
8.  `N -> n` (where `n` is any valid real number)

Each non-terminal represents:
*   `E`: Expression
*   `I`: Operation (addition or subtraction)
*   `T`: Term
*   `P`: Product (multiplication or division)
*   `F`: Factor
*   `B`: Base (exponentiation)
*   `A`: Atom
*   `N`: Number

## How to Run

The project is a standard Java application with a simple graphical user interface provided by `javax.swing.JOptionPane`.

1.  Compile the Java source files located in the `src` directory.
2.  Run the `main` method in the `test.RDParser` class.
3.  An input dialog box will appear, prompting you to enter an arithmetic expression.
4.  After submitting the expression, a message dialog will display the original input and the calculated result if the expression is valid. If it is syntactically incorrect, it will show a "Rejected input" message.
