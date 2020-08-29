package com.mx3.thirdwayvsimplecalculator.data.model;

import java.math.BigDecimal;

public class Operation {

    private Operand mFirstOperand;
    private Operand mSecondOperand;
    private Operator mOperator;

    // Constructors

    public Operation() {
    }

    public Operation(Operand firstOperand, Operand secondOperand, Operator operator) {
        this.mFirstOperand = firstOperand;
        this.mSecondOperand = secondOperand;
        this.mOperator = operator;
    }


    // Other methods

    public BigDecimal evaluate() {
        return mOperator.execute(mFirstOperand, mSecondOperand);
    }

    public BigDecimal undo() {
        final Operand resultAsFirstOperand = new Operand(mOperator.execute(mFirstOperand, mSecondOperand));
        return mOperator.reverse(resultAsFirstOperand, mFirstOperand);
    }

    public String getOperatorAndSecondOperandString() {
        return mOperator.getChar() + " " + mSecondOperand.getValue();
    }

    public String getFullString() {
        return mFirstOperand.getString() + ' ' + mOperator.getChar() + ' ' + mSecondOperand.getString();
    }


    // Getters and setters

    public Operand getFirstOperand() {
        return mFirstOperand;
    }

    public void setFirstOperand(Operand firstOperand) {
        this.mFirstOperand = firstOperand;
    }

    public Operand getSecondOperand() {
        return mSecondOperand;
    }

    public void setSecondOperand(Operand operand) {
        this.mSecondOperand = operand;
    }

    public Operator getOperator() {
        return mOperator;
    }

    public void setOperator(Operator operator) {
        this.mOperator = operator;
    }
}
