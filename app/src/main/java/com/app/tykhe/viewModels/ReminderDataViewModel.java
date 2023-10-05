package com.app.tykhe.viewModels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.app.tykhe.models.Reminder;

public class ReminderDataViewModel extends ViewModel {

    private final MutableLiveData<Reminder> currentReminderObject = new MutableLiveData<Reminder>();

    public void updateReminderObject( Reminder reminder ){
        this.currentReminderObject.setValue(reminder);
    }

    public LiveData<Reminder> getSelectedReminderObject(){
        return this.currentReminderObject;
    }




}
