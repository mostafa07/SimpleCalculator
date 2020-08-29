package com.mx3.thirdwayvsimplecalculator.data.model;

import java.math.BigDecimal;

public class PlusOperator extends Operator {

    @Override
    public BigDecimal execute(Operand first, Operand second) {
        return first.getValue().add(second.getValue());
    }

    @Override
    public BigDecimal reverse(Operand first, Operand second) {
        return new MinusOperator().execute(first, second);
    }

    @Override
    public char getChar() {
        return '+';
    }

    @Override
    public char getInverseChar() {
        return '-';
    }
}
