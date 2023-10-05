package com.app.tykhe.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.tykhe.OnBoardingActivity;
import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.misc.SavingRateEnum;

import java.util.ArrayList;



public class OnBoarding {

    // TODO
    // ****  bind the view model to the room api   ****
    public Integer currentStep;

    public String accountHoldersName;
    public Integer accountHoldersAge;

    public SavingRateEnum.savingRate savingRate;

    public double contributionAmount;
    public double intrestRate;

    public Integer lengthOfInvestment;

    public double currentSavings;

    public OnBoarding(){
        this.currentStep = OnBoardingActivity.FIRST_PAGE;
        this.accountHoldersName = "";
        this.accountHoldersAge = 25;
        this.contributionAmount = 0.00;
        this.intrestRate = 0.00;
        this.lengthOfInvestment = 25;
    }

    public void loadOnBoardingForm(User user){
        if( !user.name.isEmpty() ) {
            this.accountHoldersName = user.name;
        }
    }

    public void setWeekly(){
        this.savingRate = SavingRateEnum.savingRate.Weekly;
    }
    public void setBiWeekly(){ this.savingRate = SavingRateEnum.savingRate.Biweekly; }
    public void setMonthly(){ this.savingRate = SavingRateEnum.savingRate.Monthly; }

    public void previousPage(){
        if( this.currentStep == OnBoardingActivity.FIRST_PAGE) {
            this.currentStep = OnBoardingActivity.FIRST_PAGE;
        }
        else {
            this.currentStep--;
        }
    }

    public void nextPage( ) {
        //if( this.currentStep + 1 > OnBoardingActivity.NUM_PAGES ) {
        //    this.currentStep = OnBoardingActivity.FIRST_PAGE;
        //}
        //else {
            this.currentStep++;
        //}
    }

    public String getAccountHoldersName() {
        return accountHoldersName;
    }

    public OnBoarding setAccountHoldersName(String accountHoldersName) {
        this.accountHoldersName = accountHoldersName;
        return this;
    }

    public Integer getAccountHoldersAge() {
        return accountHoldersAge;
    }

    public OnBoarding setAccountHoldersAge(Integer accountHoldersAge) {
        this.accountHoldersAge = accountHoldersAge;
        return this;
    }

    public SavingRateEnum.savingRate getSavingRate() {
        return savingRate;
    }

    public void setSavingRate(SavingRateEnum.savingRate savingRate) {
        this.savingRate = savingRate;
    }

    public double getContributionAmount() {
        return contributionAmount;
    }

    public OnBoarding setContributionAmount(double contributionAmount) {
        this.contributionAmount = contributionAmount;
        return this;
    }

    public double getIntrestRate() {
        return intrestRate;
    }

    public OnBoarding setIntrestRate(double intrestRate) {
        this.intrestRate = intrestRate;
        return this;
    }

    public Integer getLengthOfInvestment() {
        return lengthOfInvestment;
    }

    public OnBoarding setLengthOfInvestment(Integer lengthOfInvestment) {
        this.lengthOfInvestment = lengthOfInvestment;
        return this;
    }

    public OnBoarding setCurrentSavings( double currentSavings ){
        this.currentSavings = currentSavings;
        return this;
    }

    public Integer getCurrentStep() {
        return currentStep;
    }


    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

}
