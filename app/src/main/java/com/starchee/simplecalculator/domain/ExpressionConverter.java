package com.starchee.simplecalculator.domain;

import java.util.List;

public interface ExpressionConverter {

    List<String> convertToList(String expression);

    List<String> convertToPRMExpression(List<String> expression);

}
