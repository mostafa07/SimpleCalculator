package com.mx3.thirdwayvsimplecalculator.data.model;

public abstract class Operator {

    // Constructor

    public Operator() {
    }

    // Other abstract methods

    public abstract Float execute(Operand first, Operand second);

    public abstract Float reverse(Operand first, Operand second);

    public abstract char getChar();
}
