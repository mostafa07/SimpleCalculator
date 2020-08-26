package com.mx3.thirdwayvsimplecalculator.ui.data.model;

public class Operand {

    private Float mValue;

    // Constructors

    public Operand() {

    }

    public Operand(Float value) {
        this.mValue = value;
    }


    // Helper methods

    public String getString() {
        return String.valueOf(mValue);
    }


    // Getters and setters

    public Float getValue() {
        return mValue;
    }

    public void setValue(Float value) {
        this.mValue = value;
    }
}
