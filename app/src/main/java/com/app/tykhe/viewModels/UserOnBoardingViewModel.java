package com.app.tykhe.viewModels;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.tykhe.models.OnBoarding;


public class UserOnBoardingViewModel extends ViewModel {

    public MutableLiveData<OnBoarding> accountOnBoarding;
    public LiveData<OnBoarding> getOnBoardingForm() {
        if( this.accountOnBoarding == null ) {
            this.createOnBoardingForm();
            return this.accountOnBoarding;
        }
        return this.accountOnBoarding;
    }

    private void createOnBoardingForm(){
        this.accountOnBoarding = new MutableLiveData< OnBoarding >();

        this.accountOnBoarding.setValue( new OnBoarding() );
    }

    public void setAccountName( String accountName ) {
        this.accountOnBoarding.setValue(
                this.accountOnBoarding.getValue().setAccountHoldersName( accountName )
        );


    }
    public void setAccountAge( Integer age ){
        this.accountOnBoarding.setValue(
                this.accountOnBoarding.getValue().setAccountHoldersAge( age )
        );
    }

    public void selectWeekly(){
        OnBoarding curr = this.accountOnBoarding.getValue();
        curr.setWeekly();
        this.accountOnBoarding.setValue( curr );
    }
    public void selectBiWeekly(){
        OnBoarding curr = this.accountOnBoarding.getValue();
        curr.setBiWeekly();
        this.accountOnBoarding.setValue( curr );
    }
    public void selectMonthly(){
        OnBoarding curr = this.accountOnBoarding.getValue();
        curr.setMonthly();
        this.accountOnBoarding.setValue( curr );
    }

    public void setContributionAmount( String amount ){
        try {
            this.accountOnBoarding.setValue(
                    this.accountOnBoarding.getValue().setContributionAmount( Double.parseDouble( amount ) )
            );
        }
        catch( NumberFormatException e ) {

        }
    }
    public void setInterestRate( String rate ){
        try {
            this.accountOnBoarding.setValue(
                    this.accountOnBoarding.getValue().setIntrestRate( Double.parseDouble( rate ) )
            );
        }
        catch( NumberFormatException e ) {

        }
    }
    public void setLengthOfInvestment( Integer length ){
        this.accountOnBoarding.setValue(
                this.accountOnBoarding.getValue().setLengthOfInvestment( length )
        );
    }

    public void setCurrentSavings( double amount ){
        this.accountOnBoarding.setValue(
                this.accountOnBoarding.getValue().setCurrentSavings( amount )
        );
    }

    public void previousPage(){
        OnBoarding curr = this.accountOnBoarding.getValue();
        curr.previousPage();
        this.accountOnBoarding.setValue( curr );
    }

    public void nextPage(){
        OnBoarding curr = this.accountOnBoarding.getValue();
        Log.d("onBoardingForm", this.accountOnBoarding.getValue().accountHoldersName );
        Log.d("onBoardingForm", String.valueOf( this.accountOnBoarding.getValue().accountHoldersAge ) );

        curr.nextPage();
        this.accountOnBoarding.setValue( curr );


    }

    public void start(){
        this.nextPage();
    }

}
