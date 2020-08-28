package com.mx3.thirdwayvsimplecalculator.data.model;

import java.math.BigDecimal;

public class Operand {

    private BigDecimal mValue;

    // Constructors

    public Operand() {
    }

    public Operand(BigDecimal value) {
        this.mValue = value;
    }

    public Operand(String valueString) {
        this.mValue = new BigDecimal(valueString);
    }


    // Helper methods

    public String getString() {
        return String.valueOf(mValue);
    }


    // Getters and setters

    public BigDecimal getValue() {
        return mValue;
    }

    public void setValue(BigDecimal value) {
        this.mValue = value;
    }
}
