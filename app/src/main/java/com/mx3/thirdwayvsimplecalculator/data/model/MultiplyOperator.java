package com.mx3.thirdwayvsimplecalculator.data.model;

import java.math.BigDecimal;

public class MultiplyOperator extends Operator {

    @Override
    public BigDecimal execute(Operand first, Operand second) {
        return first.getValue().multiply(second.getValue());
    }

    @Override
    public BigDecimal reverse(Operand first, Operand second) {
        return new DivideOperator().execute(first, second);
    }

    @Override
    public char getChar() {
        return 'x';
    }
}
