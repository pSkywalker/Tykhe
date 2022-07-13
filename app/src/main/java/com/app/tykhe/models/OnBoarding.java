package com.app.tykhe.models;

import androidx.databinding.BaseObservable;
import androidx.databinding.Bindable;

import com.app.tykhe.OnBoardingActivity;

enum SavingRate {
    Weekly,
    Biweekly,
    Monthly
}


public class OnBoarding {

    // TODO
    // ****  bind the view model to the room api   ****
    public Integer currentStep;

    public String accountHoldersName;
    public Integer accountHoldersAge;

    public SavingRate savingRate;

    public double contributionAmount;
    public double intrestRate;

    public Integer lengthOfInvestment;

    public OnBoarding(){
        this.currentStep = OnBoardingActivity.FIRST_PAGE;
        this.accountHoldersName = "";
        this.accountHoldersAge = 25;
        this.contributionAmount = 0.00;
        this.intrestRate = 0.00;
    }

    public void setWeekly(){
        this.savingRate = SavingRate.Weekly;
    }
    public void setBiWeekly(){ this.savingRate = SavingRate.Biweekly; }
    public void setMonthly(){ this.savingRate = SavingRate.Monthly; }

    public void previousPage(){
        if( this.currentStep == OnBoardingActivity.FIRST_PAGE) {
            this.currentStep = OnBoardingActivity.FIRST_PAGE;
        }
        else {
            this.currentStep--;
        }
    }

    public void nextPage( ) {
        if( this.currentStep + 1 > OnBoardingActivity.NUM_PAGES ) {
            this.currentStep = OnBoardingActivity.FIRST_PAGE;
        }
        else {
            this.currentStep++;
        }
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

    public SavingRate getSavingRate() {
        return savingRate;
    }

    public void setSavingRate(SavingRate savingRate) {
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

    public Integer getCurrentStep() {
        return currentStep;
    }

    public void setCurrentStep(Integer currentStep) {
        this.currentStep = currentStep;
    }

}
