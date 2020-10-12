package com.starchee.simplecalculator.domain;

public class Validator implements ExpressionValidator {

    private static final char SUBTRACT = Operator.SUBTRACT.getToken().charAt(0);
    private static final char OPENING_BRACKET = Bracket.OPENING_BRACKET.getToken().charAt(0);
    private static final char CLOSING_BRACKET = Bracket.CLOSING_BRACKET.getToken().charAt(0);
    private static final char DOT = Separator.DOT.getToken().charAt(0);

    private int brackets;
    private boolean operatorEnabled;
    private boolean openingBracketEnabled;
    private boolean closingBracketEnabled;
    private boolean dotEnabled;
    private boolean subtractEnabled;

    @Override
    public void validate(String expression) {

        setDefaultFlags();

        char[] expressionArray = expression.toCharArray();

        for (int i = 0; i < expressionArray.length; i++) {
            char token = expressionArray[i];
            boolean lastToken = i == expression.length() - 1;

            if (token == DOT) {
                dotValidate(token, i, lastToken);
            } else if (isOperand(token)) {
                operandValidate();
            } else if (token == SUBTRACT) {
                subtractValidate(token, i, lastToken);
            } else if (isOperator(token)) {
                operatorValidate(token, i, lastToken);
            } else if (token == OPENING_BRACKET) {
                openingBracketValidate(token, i, lastToken);
            } else if (token == CLOSING_BRACKET) {
                closingBracketValidate(token, i, lastToken);
            }
        }

        if (brackets != 0){
            throw new InvalidMathExpressionException("Invalid bracket count");
        }
    }

    private void setDefaultFlags() {
        brackets = 0;
        operatorEnabled = false;
        openingBracketEnabled = true;
        closingBracketEnabled = false;
        dotEnabled = true;
        subtractEnabled = true;
    }

    private void dotValidate(char token, int position, boolean lastToken) {
        if (!dotEnabled || lastToken && !operatorEnabled) {
            throw new InvalidMathExpressionException(
                    "Separator " + token + " cannot be on " + position + " position");
        }
        dotEnabled = false;
        subtractEnabled = false;
        operatorEnabled = false;
        openingBracketEnabled = false;
        closingBracketEnabled = false;
    }

    private void operandValidate() {
        operatorEnabled = true;
        subtractEnabled = false;
        openingBracketEnabled = false;

        if (brackets > 0) {
            closingBracketEnabled = true;
        }
    }

    private void subtractValidate(char token, int position, boolean lastToken) {
        if (!subtractEnabled && !operatorEnabled  || lastToken) {
            throw new InvalidMathExpressionException(
                    "Operator " + token + " cannot be on " + position + " position");
        }
        subtractEnabled = false;
        operatorEnabled = false;
        dotEnabled = true;
        openingBracketEnabled = true;
        closingBracketEnabled = false;
    }

    private void operatorValidate(char token, int position, boolean lastToken) {
        if (!operatorEnabled  || lastToken) {
            throw new InvalidMathExpressionException(
                    "Operator " + token + " cannot be on " + position + " position");
        }
        operatorEnabled = false;
        subtractEnabled = false;
        dotEnabled = true;
        openingBracketEnabled = true;
        closingBracketEnabled = false;
    }

    private void openingBracketValidate(char token, int position, boolean lastToken) {
        if (!openingBracketEnabled  || lastToken) {
            throw new InvalidMathExpressionException(
                    "Opening bracket " + token + " cannot be on " + position + " position");
        }
        subtractEnabled = true;
        brackets++;
    }

    private void closingBracketValidate(char token, int position, boolean lastToken) {
        if (!closingBracketEnabled) {
            throw new InvalidMathExpressionException(
                    "Closing bracket " + token + " cannot be on " + position + " position"
            );
        }
        subtractEnabled = false;
        brackets--;
    }

    private boolean isOperator(char token) {
        return Operator.isOperator(String.valueOf(token));
    }

    private boolean isOperand(char token) {
        return !Operator.isOperator(String.valueOf(token)) && !Bracket.isBracket(String.valueOf(token));
    }

}