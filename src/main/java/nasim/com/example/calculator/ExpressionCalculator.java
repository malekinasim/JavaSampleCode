package nasim.com.example.calculator;

public class ExpressionCalculator {
    private static String expression;

    public static double calculate(String input) {
        if (input == null || input.replaceAll("\\s+", "").isEmpty())
            return 0;
        expression = input.replaceAll("\\s+", "");
        return solve(expression);

    }

    private static double solve(String expression) {
        expression = removeParentheses(expression);
        int operatorIndex = findOperatorFromRight(expression, '+', '-');
        if (operatorIndex != -1) {
            return solvedOperatorFromRight(expression, operatorIndex);
        }
        operatorIndex = findOperatorFromRight(expression, '*', '/');
        if (operatorIndex != -1) {
            return  solvedOperatorFromRight(expression, operatorIndex);
        }
        operatorIndex = findOperatorFromRight(expression, '^');
        if (operatorIndex != -1) {
            return solvedOperatorFromRight(expression, operatorIndex);
        }
        return Double.parseDouble(expression);

    }

    private static double solvedOperatorFromRight(String expression, int operatorIndex) {

            double leftOperand = solve(expression.substring(0, operatorIndex));
            double rightOperand = solve(expression.substring(operatorIndex + 1));
            char operator = expression.charAt(operatorIndex);
            if (operator == '+') {
                return leftOperand + rightOperand;
            } else if (operator == '-') {
                return leftOperand - rightOperand;
            }
            if (operator == '*') {
                return leftOperand * rightOperand;
            } else if (operator == '/') {
                if (rightOperand == 0) {
                    throw new ArithmeticException("Division by zero is not allowed.");
                }
                return leftOperand / rightOperand;
            }
            else  {
                return Math.pow(leftOperand , rightOperand);
            }

    }

    private static int findOperatorFromRight(String expression, char... operators) {
        for (int i = expression.length() - 1; i >= 0; i--) {
            char current = expression.charAt(i);

            for (char operator : operators) {
                if (current == operator) {

                    if (current == '-' && isNegativeSign(expression, i)) {
                        continue;
                    }

                    return i;
                }
            }
        }

        return -1;
    }

    private static boolean isNegativeSign(String expression, int index) {
        if (index == 0) {
            return true;
        }

        char previous = expression.charAt(index - 1);

        return previous == '+'
                || previous == '-'
                || previous == '*'
                || previous == '/'
                || previous == '^';
    }

    private static String removeParentheses(String expression) {

        if (expression.contains("(")) {
            int openIndex = expression.lastIndexOf("(");
            int closeIndex = expression.indexOf(")", openIndex);
            if (closeIndex == -1) {
                throw new IllegalArgumentException("Missing closing parenthesis.");
            }
            String inside = expression.substring(openIndex + 1, closeIndex);
            expression = removeParentheses(expression.substring(0, openIndex) +
                    solve(inside) + expression.substring(closeIndex + 1));
        }
        return expression;
    }
}
