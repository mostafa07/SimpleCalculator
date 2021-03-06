package com.mx3.thirdwayvsimplecalculator.ui.viewmodel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.mx3.thirdwayvsimplecalculator.R;
import com.mx3.thirdwayvsimplecalculator.data.model.DivideOperator;
import com.mx3.thirdwayvsimplecalculator.data.model.Message;
import com.mx3.thirdwayvsimplecalculator.data.model.Operand;
import com.mx3.thirdwayvsimplecalculator.data.model.Operation;
import com.mx3.thirdwayvsimplecalculator.data.model.Operator;
import com.mx3.thirdwayvsimplecalculator.data.model.factory.OperatorFactory;
import com.mx3.thirdwayvsimplecalculator.data.repository.CalculatorRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class CalculatorViewModel extends ViewModel {

    private static final String LOG_TAG = CalculatorViewModel.class.getSimpleName();
    private static final Operand INITIAL_RESULT = new Operand(BigDecimal.ZERO);
    private static final String DOT_OPERAND = ".";
    private static final int ZERO_POSITION = 0;

    private CalculatorRepository mCalculatorRepository;

    private MutableLiveData<List<Operation>> mOperationRecordsMutableLiveData;
    private MutableLiveData<Operation> mCurrentOperationMutableLiveData;
    private MutableLiveData<String> mSecondOperandStringMutableLiveData;

    private MutableLiveData<Boolean> mIsOperandButtonsEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsOperationButtonsEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsUndoButtonEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsRedoButtonEnabledMutableLiveData;
    private MutableLiveData<Boolean> mIsEqualButtonEnabledMutableLiveData;

    private MutableLiveData<Message> mErrorMessageMutableLiveData;

    // Constructor
    public CalculatorViewModel() {
        mCalculatorRepository = CalculatorRepository.getInstance();

        mOperationRecordsMutableLiveData = new MutableLiveData<>(new ArrayList<>());
        mCurrentOperationMutableLiveData = new MutableLiveData<>(new Operation(INITIAL_RESULT, null, null));
        mSecondOperandStringMutableLiveData = new MutableLiveData<>("");

        mIsOperandButtonsEnabledMutableLiveData = new MutableLiveData<>(false);
        mIsOperationButtonsEnabledMutableLiveData = new MutableLiveData<>(true);
        mIsUndoButtonEnabledMutableLiveData = new MutableLiveData<>(false);
        mIsRedoButtonEnabledMutableLiveData = new MutableLiveData<>(false);
        mIsEqualButtonEnabledMutableLiveData = new MutableLiveData<>(false);

        mErrorMessageMutableLiveData = new MutableLiveData<>();
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
            if (currentOperation.getOperator() instanceof DivideOperator
                    && mSecondOperandStringMutableLiveData.getValue().equals(BigDecimal.ZERO.toString())) {
                mErrorMessageMutableLiveData.setValue(new Message(R.string.error_message_cannot_divide_by_zero));

                final Operand currentResultAsNextFirstOperand = mCurrentOperationMutableLiveData.getValue().getFirstOperand();
                final Operation nextOperation = new Operation(currentResultAsNextFirstOperand, null, null);
                mCurrentOperationMutableLiveData.setValue(nextOperation);
            } else {
                currentOperation.setSecondOperand(new Operand(mSecondOperandStringMutableLiveData.getValue()));
                final BigDecimal operationResult = currentOperation.evaluate();

                mCalculatorRepository.insertOperation(currentOperation).subscribe(operation -> {
                    final List<Operation> operationRecordsList = mOperationRecordsMutableLiveData.getValue();
                    operationRecordsList.add(ZERO_POSITION, operation);
                    mOperationRecordsMutableLiveData.setValue(operationRecordsList);
                }, Throwable::printStackTrace);

                final Operation nextOperation = new Operation(new Operand(operationResult), null, null);
                mCurrentOperationMutableLiveData.setValue(nextOperation);
            }

            resetToInitialStates();
        }

    }

    public void onUndoButtonClicked() {
        undoLastOperation();
    }

    public void onRedoButtonClicked() {
        mCalculatorRepository.redoOperation().subscribe(operation -> {
            final BigDecimal operationResult = operation.evaluate();
            final Operation nextOperation = new Operation(new Operand(operationResult), null, null);
            mCurrentOperationMutableLiveData.setValue(nextOperation);

            final List<Operation> operationRecordsList = mOperationRecordsMutableLiveData.getValue();
            operationRecordsList.add(ZERO_POSITION, operation);
            mOperationRecordsMutableLiveData.setValue(operationRecordsList);

            resetToInitialStates();
        }, Throwable::printStackTrace);
    }

    public void undoClickedOperationRecordItem(int position) {
        if (position == ZERO_POSITION) {
            undoLastOperation();
        } else {
            mCalculatorRepository.undoOperation(position).subscribe(clickedOperation -> {
                final Operand firstOperand = new Operand(mCurrentOperationMutableLiveData.getValue().getFirstOperand().getValue());
                final Operand secondOperand = new Operand(clickedOperation.getSecondOperand().getValue());
                final Operator inverseOperator = OperatorFactory.getInstance().getOperator(clickedOperation.getOperator().getInverseChar());
                final Operation operationUndo = new Operation(firstOperand, secondOperand, inverseOperator);
                final BigDecimal operationUndoResult = operationUndo.evaluate();

//                mCalculatorRepository.insertOperation(operationUndo).subscribe(operation -> {
//                    final List<Operation> operationRecordsList = mOperationRecordsMutableLiveData.getValue();
//                    operationRecordsList.add(ZERO_POSITION, operation);
//                    mOperationRecordsMutableLiveData.setValue(operationRecordsList);
//                }, Throwable::printStackTrace);

                final Operation nextOperation = new Operation(new Operand(operationUndoResult), null, null);
                mCurrentOperationMutableLiveData.setValue(nextOperation);

                final List<Operation> operationRecordsList = mOperationRecordsMutableLiveData.getValue();
                operationRecordsList.remove(position);
                mOperationRecordsMutableLiveData.setValue(operationRecordsList);

                resetToInitialStates();
            }, Throwable::printStackTrace);
        }
    }


    // Helper Methods

    private void resetToInitialStates() {
        mSecondOperandStringMutableLiveData.setValue("");

        mIsOperandButtonsEnabledMutableLiveData.setValue(false);
        mIsOperationButtonsEnabledMutableLiveData.setValue(true);

        mIsEqualButtonEnabledMutableLiveData.setValue(false);
        mCalculatorRepository.getExecutedOperationsSize().subscribe(size -> {
            if (size > 0) {
                mIsUndoButtonEnabledMutableLiveData.setValue(true);
            } else {
                mIsUndoButtonEnabledMutableLiveData.setValue(false);
            }
        });
        mCalculatorRepository.getPoppedOperationsSize().subscribe(size -> {
            if (size > 0) {
                mIsRedoButtonEnabledMutableLiveData.setValue(true);
            } else {
                mIsRedoButtonEnabledMutableLiveData.setValue(false);
            }
        });
    }

    private void undoLastOperation() {
        mCalculatorRepository.undoOperation(ZERO_POSITION).subscribe(operation -> {
            final BigDecimal operationUndoResult = mOperationRecordsMutableLiveData.getValue().size() == 1 ?
                    BigDecimal.ZERO : operation.undo();
            final Operation nextOperation = new Operation(new Operand(operationUndoResult), null, null);
            mCurrentOperationMutableLiveData.setValue(nextOperation);

            final List<Operation> operationRecordsList = mOperationRecordsMutableLiveData.getValue();
            operationRecordsList.remove(ZERO_POSITION);
            mOperationRecordsMutableLiveData.setValue(operationRecordsList);

            resetToInitialStates();
        }, Throwable::printStackTrace);
    }

    // Getters and setters

    public LiveData<List<Operation>> getOperationRecordsLiveData() {
        return mOperationRecordsMutableLiveData;
    }

    public void setOperationRecordsLiveData(List<Operation> operationRecordsList) {
        this.mOperationRecordsMutableLiveData.setValue(operationRecordsList);
    }

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

    public LiveData<Message> getErrorMessageLiveData() {
        return mErrorMessageMutableLiveData;
    }

    public void setErrorMessageLiveData(Message message) {
        this.mErrorMessageMutableLiveData.setValue(message);
    }
}
