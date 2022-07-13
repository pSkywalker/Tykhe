package com.app.tykhe.onBoarding;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.app.tykhe.OnBoardingActivity;
import com.app.tykhe.R;
import com.app.tykhe.viewModels.UserOnBoardingViewModel;

public class InitialSetYourSavingsFragment extends Fragment {

    private EditText editText_contributions;
    private EditText editText_interestRates;

    private SeekBar seekBar_lengthOfInvestment;

    private UserOnBoardingViewModel onBoardingViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("from on create view", "");

            this.onBoardingViewModel = ((OnBoardingActivity) getActivity()).getOnBoardingDataSource();

            ViewDataBinding bindingHandle = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_set_you_savings_init, container, false);
            bindingHandle.setVariable(1, this.onBoardingViewModel );

            return bindingHandle.getRoot();

    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ){
        this.editText_contributions = view.findViewById(R.id.input_contributionAmount);
        this.editText_interestRates = view.findViewById(R.id.input_interestRate);

        this.seekBar_lengthOfInvestment = view.findViewById(R.id.input_durationSeekBar);

        this.editText_contributions.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onBoardingViewModel.setContributionAmount( editable.toString() );
            }
        });

        this.editText_interestRates.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                onBoardingViewModel.setInterestRate( editable.toString() );
            }
        });

        this.seekBar_lengthOfInvestment.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                onBoardingViewModel.setLengthOfInvestment( i );
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });


    }
}
