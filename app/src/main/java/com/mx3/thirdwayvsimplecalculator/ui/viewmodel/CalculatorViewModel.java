package com.mx3.thirdwayvsimplecalculator.ui.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mx3.thirdwayvsimplecalculator.data.model.Operand;
import com.mx3.thirdwayvsimplecalculator.data.model.Operation;
import com.mx3.thirdwayvsimplecalculator.data.repository.CalculatorRepository;

public class CalculatorViewModel extends ViewModel {

    private static final String LOG_TAG = CalculatorViewModel.class.getSimpleName();
    private static final Operand INITIAL_RESULT = new Operand(0f);

    private CalculatorRepository mCalculatorRepository;

    private MutableLiveData<String> mCurrentResultMutableLiveData;
    private MutableLiveData<Operation> mCurrentOperationMutableLiveData;

    private MutableLiveData<Boolean> mIsOperandButtonsEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsOperationButtonsEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsUndoButtonEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsRedoButtonEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsEqualButtonEnabledMutableLiveData;

    // Constructor
    public CalculatorViewModel() {
        mCalculatorRepository = CalculatorRepository.getInstance();

        mCurrentResultMutableLiveData = new MutableLiveData<>(INITIAL_RESULT.getString());
        mCurrentOperationMutableLiveData = new MutableLiveData<>(new Operation(INITIAL_RESULT, null, null));

        mIsOperandButtonsEnabledMutableLiveData = new MutableLiveData<>(false);
        mIsOperationButtonsEnabledMutableLiveData = new MutableLiveData<>(true);
        mIsUndoButtonEnabledMutableLiveData = new MutableLiveData<>(false);
        mIsRedoButtonEnabledMutableLiveData = new MutableLiveData<>(false);
        mIsEqualButtonEnabledMutableLiveData = new MutableLiveData<>(false);
    }


    // Other methods

    public void enterFirstOperand() {

    }

    public void enterOperator() {

    }

    public void enterSecondOperand() {

    }

    public void evaluateOperation() {

    }


    // Getters and setters

    public LiveData<String> getCurrentResultLiveData() {
        return mCurrentResultMutableLiveData;
    }

    public void setCurrentResultLiveData(String currentResult) {
        this.mCurrentResultMutableLiveData.setValue(currentResult);
    }

    public LiveData<Operation> getCurrentOperationLiveData() {
        return mCurrentOperationMutableLiveData;
    }

    public void setCurrentOperationLiveData(Operation currentOperation) {
        this.mCurrentOperationMutableLiveData.setValue(currentOperation);
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
