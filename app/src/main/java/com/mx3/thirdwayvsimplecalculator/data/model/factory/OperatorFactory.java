package com.mx3.thirdwayvsimplecalculator.data.model.factory;

import com.mx3.thirdwayvsimplecalculator.R;
import com.mx3.thirdwayvsimplecalculator.data.model.DivideOperator;
import com.mx3.thirdwayvsimplecalculator.data.model.MinusOperator;
import com.mx3.thirdwayvsimplecalculator.data.model.MultiplyOperator;
import com.mx3.thirdwayvsimplecalculator.data.model.Operator;
import com.mx3.thirdwayvsimplecalculator.data.model.PlusOperator;

public class OperatorFactory {

    private static final String LOG_TAG = OperatorFactory.class.getSimpleName();

    private static OperatorFactory sOperatorFactoryInstance;

    // Constructor
    private OperatorFactory() {

    }

    public static OperatorFactory getInstance() {
        synchronized (OperatorFactory.class) {
            if (sOperatorFactoryInstance == null) {
                sOperatorFactoryInstance = new OperatorFactory();
            }
            return sOperatorFactoryInstance;
        }
    }


    public Operator getOperator(int resourceId) {
        switch (resourceId) {
            case (R.id.plus_button):
                return new PlusOperator();
            case (R.id.minus_button):
                return new MinusOperator();
            case (R.id.multiply_button):
                return new MultiplyOperator();
            case (R.id.divide_button):
                return new DivideOperator();
            default:
                return null;
        }
    }
}
