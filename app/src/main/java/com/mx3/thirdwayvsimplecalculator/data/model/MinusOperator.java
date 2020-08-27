package com.mx3.thirdwayvsimplecalculator.data.model;

public class MinusOperator extends Operator {

    @Override
    public Float execute(Operand first, Operand second) {
        return first.getValue() - second.getValue();
    }

    @Override
    public Float reverse(Operand first, Operand second) {
        return first.getValue() + second.getValue();
    }

    @Override
    public char getChar() {
        return '-';
    }
}
