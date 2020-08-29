package com.mx3.thirdwayvsimplecalculator.data.model;

import java.math.BigDecimal;

public class MinusOperator extends Operator {

    @Override
    public BigDecimal execute(Operand first, Operand second) {
        return first.getValue().subtract(second.getValue());
    }

    @Override
    public BigDecimal reverse(Operand first, Operand second) {
        return new PlusOperator().execute(first, second);
    }

    @Override
    public char getChar() {
        return '-';
    }

    @Override
    public char getInverseChar() {
        return '+';
    }
}
