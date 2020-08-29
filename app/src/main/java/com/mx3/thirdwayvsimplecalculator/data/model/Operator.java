package com.mx3.thirdwayvsimplecalculator.data.model;

import java.math.BigDecimal;

public abstract class Operator {

    // Constructor
    protected Operator() {
    }


    // Other abstract methods

    public abstract BigDecimal execute(Operand first, Operand second);

    public abstract BigDecimal reverse(Operand first, Operand second);

    public abstract char getChar();

    public abstract char getInverseChar();
}
