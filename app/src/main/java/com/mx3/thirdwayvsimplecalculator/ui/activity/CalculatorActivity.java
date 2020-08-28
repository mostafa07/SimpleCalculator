package com.mx3.thirdwayvsimplecalculator.ui.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import com.mx3.thirdwayvsimplecalculator.R;
import com.mx3.thirdwayvsimplecalculator.databinding.ActivityCalculatorBinding;
import com.mx3.thirdwayvsimplecalculator.ui.viewmodel.CalculatorViewModel;

public class CalculatorActivity extends AppCompatActivity {

    private ActivityCalculatorBinding mBinding;
    private CalculatorViewModel mViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_calculator);

        setupViewModel();
    }


    private void setupViewModel() {
        mViewModel = new ViewModelProvider(this).get(CalculatorViewModel.class);
        mBinding.setViewModel(mViewModel);
    }
}
