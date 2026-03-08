public class Exam11 {
    public static void main(String[] args) {
        String infixExpression = "( 15 + 5 ) * 4 - 20 / 2";
        String postfixExpression = "15 5 + 4 * 20 2 / -";
        String prefixExpression = "- * + 15 5 4 / 20 2";

        System.out.println("=== Test Same Equation ===");
        System.out.println("Expected Final Result : 70.0\n");

        ExpressionEvaluator eval = new ExpressionEvaluator(infixExpression);
        System.out.println("1. Infix Input   : " + infixExpression);
        System.out.println("   Calculated    : " + eval.evaluateInfix());

        eval.setExpression(postfixExpression);
        System.out.println("2. Postfix Input : " + postfixExpression);
        System.out.println("   Calculated    : " + eval.evaluatePostfix());

        eval.setExpression(prefixExpression);
        System.out.println("3. Prefix Input  : " + prefixExpression);
        System.out.println("   Calculated    : " + eval.evaluatePrefix());
    }

}
