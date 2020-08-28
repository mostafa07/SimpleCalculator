package com.mx3.thirdwayvsimplecalculator.ui.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mx3.thirdwayvsimplecalculator.data.model.Operand;
import com.mx3.thirdwayvsimplecalculator.data.model.Operation;
import com.mx3.thirdwayvsimplecalculator.data.model.factory.OperatorFactory;
import com.mx3.thirdwayvsimplecalculator.data.repository.CalculatorRepository;

import java.math.BigDecimal;

public class CalculatorViewModel extends ViewModel {

    private static final String LOG_TAG = CalculatorViewModel.class.getSimpleName();
    private static final Operand INITIAL_RESULT = new Operand(BigDecimal.ZERO);
    private static final String DOT_OPERAND = ".";

    private CalculatorRepository mCalculatorRepository;

    private MutableLiveData<Operation> mCurrentOperationMutableLiveData;
    private MutableLiveData<String> mSecondOperandStringMutableLiveData;

    private MutableLiveData<Boolean> mIsOperandButtonsEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsOperationButtonsEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsUndoButtonEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsRedoButtonEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsEqualButtonEnabledMutableLiveData;

    // Constructor
    public CalculatorViewModel() {
        mCalculatorRepository = CalculatorRepository.getInstance();

        mCurrentOperationMutableLiveData = new MutableLiveData<>(new Operation(INITIAL_RESULT, null, null));
        mSecondOperandStringMutableLiveData = new MutableLiveData<>("");

        mIsOperandButtonsEnabledMutableLiveData = new MutableLiveData<>(false);
        mIsOperationButtonsEnabledMutableLiveData = new MutableLiveData<>(true);
        mIsUndoButtonEnabledMutableLiveData = new MutableLiveData<>(false);
        mIsRedoButtonEnabledMutableLiveData = new MutableLiveData<>(false);
        mIsEqualButtonEnabledMutableLiveData = new MutableLiveData<>(false);
    }


    // Button Click Event Handlers

    public void onOperatorButtonClicked(View view) {
        final Operation currentOperation = mCurrentOperationMutableLiveData.getValue();
        if (currentOperation != null) {
            currentOperation.setOperator(OperatorFactory.getInstance().getOperator(view.getId()));
            mCurrentOperationMutableLiveData.setValue(currentOperation);

            mIsOperandButtonsEnabledMutableLiveData.setValue(true);
            mIsOperationButtonsEnabledMutableLiveData.setValue(false);
        }
    }

    public void onOperandButtonClicked(View view) {
        final String clickedOperand = view.getTag().toString();
        final String currentSecondOperandString = mSecondOperandStringMutableLiveData.getValue();

        if (clickedOperand.equals(DOT_OPERAND)
                && currentSecondOperandString != null && currentSecondOperandString.contains(DOT_OPERAND)) {
            return;
        }

        if (currentSecondOperandString != null) {
            mSecondOperandStringMutableLiveData.setValue(currentSecondOperandString.concat(clickedOperand));

            mIsEqualButtonEnabledMutableLiveData.setValue(true);
        }
    }

    public void onEqualButtonClicked() {
        final Operation currentOperation = mCurrentOperationMutableLiveData.getValue();
        if (currentOperation != null) {
            currentOperation.setSecondOperand(new Operand(mSecondOperandStringMutableLiveData.getValue()));
            final BigDecimal operationResult = currentOperation.evaluate();

            // TODO store result into stack

            final Operation nextOperation = new Operation(new Operand(operationResult), null, null);
            mCurrentOperationMutableLiveData.setValue(nextOperation);
            mSecondOperandStringMutableLiveData.setValue("");

            mIsOperandButtonsEnabledMutableLiveData.setValue(false);
            mIsOperationButtonsEnabledMutableLiveData.setValue(true);
            mIsUndoButtonEnabledMutableLiveData.setValue(true);
            mIsRedoButtonEnabledMutableLiveData.setValue(true);
            mIsEqualButtonEnabledMutableLiveData.setValue(false);
        }
    }

    public void onUndoButtonClicked() {
        // TODO implement undo functionality
    }

    public void onRedoButtonClicked() {
        // TODO implement redo functionality
    }


    // Getters and setters

    public LiveData<Operation> getCurrentOperationLiveData() {
        return mCurrentOperationMutableLiveData;
    }

    public void setCurrentOperationLiveData(Operation currentOperation) {
        this.mCurrentOperationMutableLiveData.setValue(currentOperation);
    }

    public LiveData<String> getSecondOperandStringLiveData() {
        return mSecondOperandStringMutableLiveData;
    }

    public void setSecondOperandStringMutableLiveData(String secondOperandString) {
        this.mSecondOperandStringMutableLiveData.setValue(secondOperandString);
    }

    public LiveData<Boolean> getIsOperandButtonsEnabledLiveData() {
        return mIsOperandButtonsEnabledMutableLiveData;
    }

    public void setIsOperandButtonsEnabledLiveData(boolean isEnabled) {
        this.mIsOperandButtonsEnabledMutableLiveData.setValue(isEnabled);
    }

    public LiveData<Boolean> getIsOperationButtonsEnabledLiveData() {
        return mIsOperationButtonsEnabledMutableLiveData;
    }

    public void setIsOperationButtonsEnabledLiveData(boolean isEnabled) {
        this.mIsOperationButtonsEnabledMutableLiveData.setValue(isEnabled);
    }

    public LiveData<Boolean> getIsUndoButtonEnabledLiveData() {
        return mIsUndoButtonEnabledMutableLiveData;
    }

    public void setIsUndoButtonEnabledLiveData(boolean isEnabled) {
        this.mIsUndoButtonEnabledMutableLiveData.setValue(isEnabled);
    }

    public LiveData<Boolean> getIsRedoButtonEnabledLiveData() {
        return mIsRedoButtonEnabledMutableLiveData;
    }

    public void setIsRedoButtonEnabledLiveData(boolean isEnabled) {
        this.mIsRedoButtonEnabledMutableLiveData.setValue(isEnabled);
    }

    public LiveData<Boolean> getIsEqualButtonEnabledLiveData() {
        return mIsEqualButtonEnabledMutableLiveData;
    }

    public void setIsEqualButtonEnabledLiveData(boolean isEnabled) {
        this.mIsEqualButtonEnabledMutableLiveData.setValue(isEnabled);
    }
}
