package com.app.tykhe.onBoarding;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import com.app.tykhe.OnBoardingActivity;
import com.app.tykhe.R;
import com.app.tykhe.viewModels.UserOnBoardingViewModel;

public class WelcomeFragment extends Fragment {

    private UserOnBoardingViewModel onBoardingForm;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //View view = (ViewGroup) inflater.inflate(
        //        R.layout.fragment_welcome_screen, container, false);

        ViewDataBinding bindingHandle = DataBindingUtil.inflate(inflater, R.layout.fragment_welcome_screen, container, false);
        bindingHandle.setVariable(1, ((OnBoardingActivity) getActivity()).getOnBoardingDataSource());

        return bindingHandle.getRoot();

    }

    @Override
    public void onViewCreated( View view, @Nullable Bundle savedInstanceState ){
        //this.onBoardingForm = ;

    }



}
