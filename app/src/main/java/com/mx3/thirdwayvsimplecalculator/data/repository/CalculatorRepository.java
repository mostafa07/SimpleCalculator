package com.mx3.thirdwayvsimplecalculator.data.repository;

import com.mx3.thirdwayvsimplecalculator.data.model.Operation;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import rx.Observable;

public class CalculatorRepository {

    private static final String LOG_TAG = CalculatorRepository.class.getSimpleName();
    private static final int ZERO_POSITION = 0;

    private static CalculatorRepository sCalculatorRepositoryInstance;

    private List<Operation> mExecutedOperations;
    private Stack<Operation> mPoppedOperations;

    // Constructor

    private CalculatorRepository() {
        mExecutedOperations = new ArrayList<>();
        mPoppedOperations = new Stack<>();
    }

    public static CalculatorRepository getInstance() {
        synchronized (CalculatorRepository.class) {
            if (sCalculatorRepositoryInstance == null) {
                sCalculatorRepositoryInstance = new CalculatorRepository();
            }
            return sCalculatorRepositoryInstance;
        }
    }


    public Observable<List<Operation>> getExecutedOperations() {
        return Observable.create(emitter -> emitter.onNext(mExecutedOperations));
    }

    public Observable<List<Operation>> getPoppedOperations() {
        return Observable.create(emitter -> emitter.onNext(mPoppedOperations));
    }

    public Observable<Operation> insertOperation(Operation operation) {
        return Observable.create(emitter -> {
            mExecutedOperations.add(ZERO_POSITION, operation);
            emitter.onNext(operation);
        });
    }

    public Observable<Operation> undoOperation(int position) {
        return Observable.create(emitter -> {
            final Operation operation = mExecutedOperations.remove(position);
            mPoppedOperations.push(operation);
            emitter.onNext(operation);
        });
    }

    public Observable<Operation> redoOperation() {
        return Observable.create(emitter -> {
            final Operation operation = mPoppedOperations.pop();
            mExecutedOperations.add(ZERO_POSITION, operation);
            emitter.onNext(operation);
        });
    }

    public Observable<Integer> getExecutedOperationsSize() {
        return Observable.create(emitter -> {
            emitter.onNext(mExecutedOperations.size());
        });
    }

    public Observable<Integer> getPoppedOperationsSize() {
        return Observable.create(emitter -> {
            emitter.onNext(mPoppedOperations.size());
        });
    }
}
