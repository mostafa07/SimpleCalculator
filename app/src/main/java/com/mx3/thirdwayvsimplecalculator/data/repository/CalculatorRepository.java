package com.mx3.thirdwayvsimplecalculator.data.repository;

import com.mx3.thirdwayvsimplecalculator.data.model.Operation;

import java.util.Stack;

import io.reactivex.rxjava3.core.Observable;

public class CalculatorRepository {

    private static final String LOG_TAG = CalculatorRepository.class.getSimpleName();

    private static CalculatorRepository sCalculatorRepositoryInstance;

    private Stack<Operation> mOperationsStack;
    private Stack<Operation> mPoppedOperationsStack;

    // Constructor

    private CalculatorRepository() {
        mOperationsStack = new Stack<>();
        mPoppedOperationsStack = new Stack<>();
    }

    public static CalculatorRepository getInstance() {
        synchronized (CalculatorRepository.class) {
            if (sCalculatorRepositoryInstance == null) {
                sCalculatorRepositoryInstance = new CalculatorRepository();
            }
            return sCalculatorRepositoryInstance;
        }
    }


    // TODO modify implementation as needed
    public Observable<Stack<Operation>> getOperationsStack() {
        return Observable.create(emitter -> {
            emitter.onNext(mOperationsStack);
        });
    }

    // TODO modify implementation as needed
    public Observable<Stack<Operation>> getPoppedOperationsStack() {
        return Observable.create(emitter -> {
            emitter.onNext(mPoppedOperationsStack);
        });
    }
}
