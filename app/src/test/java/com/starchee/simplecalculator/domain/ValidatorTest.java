package com.starchee.simplecalculator.domain;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;


public class ValidatorTest {

    private Validator validator;
    private List<String> expressions;

    @Before
    public void setUp() {
        validator = new Validator();
        expressions = new ArrayList<>();
        expressions.add("÷");
        expressions.add("×");
        expressions.add("-");
        expressions.add("+");
        expressions.add("(");
        expressions.add(")");

        expressions.add("÷55");
        expressions.add("×55");
        expressions.add("+55");
        expressions.add("(55");
        expressions.add(")55");

        expressions.add("55÷÷55");
        expressions.add("55÷×55");
        expressions.add("55÷+55");
        expressions.add("55÷-55");

        expressions.add("55××55");
        expressions.add("55×÷55");
        expressions.add("55×+55");
        expressions.add("55×-55");

        expressions.add("55+×55");
        expressions.add("55+÷55");
        expressions.add("55++55");
        expressions.add("55+-55");

        expressions.add("55-×55");
        expressions.add("55-÷55");
        expressions.add("55-+55");
        expressions.add("55--55");

        expressions.add(".+.");
        expressions.add("55+.");
        expressions.add(".+55");

        expressions.add("55..55+55");
        expressions.add("..55+55");
        expressions.add("55..+55");

        expressions.add("(55+55)(55+55)");
        expressions.add("55(55+55)");
        expressions.add("(55+55))");
        expressions.add("((55+55)");

        expressions.add("(55+55÷)");
        expressions.add("(55+55×)");
        expressions.add("(55+55+)");
        expressions.add("(55+55-)");

        expressions.add("(÷55+55)");
        expressions.add("(×55+55)");
        expressions.add("(+55+55)");
    }

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Test
    public void validate() {
        expressions.forEach(it -> {
            try{
                validator.validate(it);
                fail("Method din`t throw exception on this expression " + it);
            } catch (Exception e) {
                assertTrue(e instanceof  InvalidMathExpressionException);
            }

        });
    }

}