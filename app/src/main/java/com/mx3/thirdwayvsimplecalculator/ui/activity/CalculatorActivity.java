package com.mx3.thirdwayvsimplecalculator.ui.activity;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.mx3.thirdwayvsimplecalculator.R;
import com.mx3.thirdwayvsimplecalculator.data.model.Operation;
import com.mx3.thirdwayvsimplecalculator.databinding.ActivityCalculatorBinding;
import com.mx3.thirdwayvsimplecalculator.ui.adapter.OperationRecordAdapter;
import com.mx3.thirdwayvsimplecalculator.ui.viewmodel.CalculatorViewModel;

import java.util.ArrayList;

public class CalculatorActivity extends AppCompatActivity implements OperationRecordAdapter.OperationRecordClickHandler {

    private static final String LOG_TAG = CalculatorActivity.class.getSimpleName();

    private ActivityCalculatorBinding mBinding;
    private CalculatorViewModel mViewModel;
    private OperationRecordAdapter mOperationRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_calculator);

        setupViewModel();

        setupOperationRecordsRecyclerView();
    }


    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);
        mBinding.setViewModel(mViewModel);
    }

    private void setupOperationRecordsRecyclerView() {
        // TODO implement connection between viewmodel and adapter and its operations
        mOperationRecordAdapter = new OperationRecordAdapter(this, new ArrayList<>());
        mBinding.previousOperationsRecyclerView.setAdapter(mOperationRecordAdapter);
    }


    @Override
    public void onItemClick(Operation operation) {
        Log.d(LOG_TAG, "onItemClick: " + operation.getFullString());
    }
}
