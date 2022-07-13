package com.app.tykhe.onBoarding;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.app.tykhe.OnBoardingActivity;
import com.app.tykhe.R;
import com.app.tykhe.models.OnBoarding;
import com.app.tykhe.viewModels.UserOnBoardingViewModel;

public class InitialAccountInfoSetupFragment extends Fragment {

    private String accountHoldersName;
    private Integer accountHoldersAge = 25;

    private EditText accountHoldersEditText;
    private SeekBar accountHoldersAgeSeekBar;
    private Button nextButton;


    private UserOnBoardingViewModel onBoardingViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //return (ViewGroup) inflater.inflate(
        //        R.layout.fragment_welcome_account_info_screen, container, false);

        this.onBoardingViewModel = ((OnBoardingActivity) getActivity()).getOnBoardingDataSource();

        ViewDataBinding bindingHandle = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_account_info_screen, container, false);
        bindingHandle.setLifecycleOwner(this);
        bindingHandle.setVariable(1, this.onBoardingViewModel );

        return bindingHandle.getRoot();
    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ){
        this.accountHoldersAgeSeekBar =getView().findViewById( R.id.ageSeekBar );
        this.accountHoldersEditText =  getView().findViewById( R.id.accountHoldersName );
        //this.nextButton = getView().findViewById( R.id.nextButton );

        this.accountHoldersEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.d("editable value", editable.toString());
                onBoardingViewModel.setAccountName( editable.toString() );
            }
        });

        this.accountHoldersAgeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                onBoardingViewModel.setAccountAge( i );
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
