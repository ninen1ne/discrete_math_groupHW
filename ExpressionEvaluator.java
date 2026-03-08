import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

public class ExpressionEvaluator {
    private String expression;
    private HashSet<Character> operatorsSet = new HashSet<>(Set.of('+', '-', '*', '/'));

    public ExpressionEvaluator(String expression) {
        this.expression = expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String infixToPostfix(String infixExpression) {
        Stack<Character> myStack = new Stack<>();
        StringBuilder postfixFormatString = new StringBuilder();
        int i = 0;

        while (i < infixExpression.length()) {
            char currChar = infixExpression.charAt(i);

            if (currChar == ' ') {
                i++;
                continue;
            }

            if (Character.isDigit(currChar)) {
                while (i < infixExpression.length() && Character.isDigit(infixExpression.charAt(i))) {
                    postfixFormatString.append(infixExpression.charAt(i));
                    i++;
                }
                postfixFormatString.append(' ');
                i--;
            } else if (operatorsSet.contains(currChar)) {
                while (!myStack.isEmpty() && myStack.peek() != '('
                        && getPrecedence(currChar) <= getPrecedence(myStack.peek())) {
                    postfixFormatString.append(myStack.pop()).append(' ');
                }
                myStack.push(currChar);
            } else if (currChar == '(') {
                myStack.push(currChar);
            } else if (currChar == ')') {
                while (!myStack.isEmpty() && myStack.peek() != '(') {
                    postfixFormatString.append(myStack.pop()).append(' ');
                }
                if (!myStack.isEmpty() && myStack.peek() == '(') {
                    myStack.pop();
                }
            }
            i++;
        }

        while (!myStack.isEmpty()) {
            postfixFormatString.append(myStack.pop()).append(' ');
        }

        return postfixFormatString.toString().trim();
    }

    private int getPrecedence(char operator) {
        switch (operator) {
            case '+':
            case '-':
                return 1;
            case '*':
            case '/':
                return 2;
            case '^':
                return 3;
            default:
                return -1;
        }
    }

    public double evaluateInfix() {
        String postfixExpr = infixToPostfix(this.expression);
        return evaluatePostfix(postfixExpr);
    }

    public double evaluatePrefix() {
        Stack<Double> myStack = new Stack<>();
        String[] tokens = this.expression.trim().split("\\s+");

        int i = tokens.length - 1;
        while (i >= 0) {
            String token = tokens[i];
            if (token.length() == 1 && operatorsSet.contains(token.charAt(0))) {
                double a = myStack.pop();
                double b = myStack.pop();
                double val = performAction(token.charAt(0), a, b);
                myStack.push(val);
            } else {
                myStack.push(Double.parseDouble(token));
            }
            i--;
        }
        return myStack.pop();
    }

    public double evaluatePostfix() {
        return evaluatePostfix(this.expression);
    }

    private double evaluatePostfix(String expr) {
        Stack<Double> myStack = new Stack<>();
        String[] tokens = expr.trim().split("\\s+");

        int i = 0;
        while (i < tokens.length) {
            String token = tokens[i];
            if (token.length() == 1 && operatorsSet.contains(token.charAt(0))) {
                double a = myStack.pop();
                double b = myStack.pop();
                double val = performAction(token.charAt(0), b, a);
                myStack.push(val);
            } else {
                myStack.push(Double.parseDouble(token));
            }
            i++;
        }
        return myStack.pop();
    }

    private double performAction(char operator, double a, double b) {
        double value = 0;
        switch (operator) {
            case '+':
                value = a + b;
                break;
            case '-':
                value = a - b;
                break;
            case '*':
                value = a * b;
                break;
            case '/':
                value = a / b;
                break;
            default:
                break;
        }
        return value;
    }
}