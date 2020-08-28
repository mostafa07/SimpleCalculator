package com.mx3.thirdwayvsimplecalculator.data.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class DivideOperator extends Operator {

    private static final int BIG_DECIMAL_DIVISION_SCALE = 2;

    // TODO do further testing
    @Override
    public BigDecimal execute(Operand first, Operand second) {
        if (!second.getValue().equals(BigDecimal.ZERO)) {
            return first.getValue().divide(second.getValue(), BIG_DECIMAL_DIVISION_SCALE, RoundingMode.HALF_UP);
        }
        return null;
    }

    @Override
    public BigDecimal reverse(Operand first, Operand second) {
        return new MultiplyOperator().execute(first, second);
    }

    @Override
    public char getChar() {
        return '÷';
    }
}
