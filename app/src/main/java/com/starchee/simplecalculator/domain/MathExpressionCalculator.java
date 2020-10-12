package com.starchee.simplecalculator.domain;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

public class MathExpressionCalculator {

    private ExpressionValidator expressionValidator;
    private ExpressionConverter expressionConverter;

    public MathExpressionCalculator(ExpressionValidator expressionValidator, ExpressionConverter expressionConverter) {
        this.expressionValidator = expressionValidator;
        this.expressionConverter = expressionConverter;
    }

    public String calculate(String expression) {
        expressionValidator.validate(expression);
        List<String> expressionList = expressionConverter.convertToList(expression);
        double answer = answerFromRPN(expressionConverter.convertToPRMExpression(expressionList));
        return answer == (int) answer ? String.valueOf((int) answer) : String.valueOf(answer);
    }


    private double answerFromRPN(List<String> expressionRPNList) {
        Deque<Double> stack = new ArrayDeque<>();

        for (String token : expressionRPNList) {
            if (Operator.isOperator(token)) {
                double a = stack.pop();
                double b = stack.pop();
                double result = Operator.getOperator(token).apply(b, a);
                stack.push(result);
            } else {
                stack.push(Double.parseDouble(token));
            }
        }
        return stack.pop();
    }


}
