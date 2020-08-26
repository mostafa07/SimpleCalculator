package com.mx3.thirdwayvsimplecalculator.ui.data.model;

public class PlusOperator extends Operator {

    @Override
    public Float execute(Operand first, Operand second) {
        return first.getValue() + second.getValue();
    }

    @Override
    public Float reverse(Operand first, Operand second) {
        return first.getValue() - second.getValue();
    }

    @Override
    public char getChar() {
        return '+';
    }
}
