package com.app.tykhe.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.tykhe.misc.CurrentSavingsWrapper;

public class CurrentSavingsUpdater_ViewModel extends ViewModel {

    private final MutableLiveData<CurrentSavingsWrapper> currentSavings = new MutableLiveData<CurrentSavingsWrapper>();

    public LiveData<CurrentSavingsWrapper> getCurrentSavingsObject(){
        return this.currentSavings;
    }
    public void setCurrentSavings ( CurrentSavingsWrapper currentSavings ){
        this.currentSavings.setValue( currentSavings );
    }
}
