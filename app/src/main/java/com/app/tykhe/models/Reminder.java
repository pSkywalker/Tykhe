package com.app.tykhe.models;

public class Reminder {

    public String savingRate;
    public Integer weeklyDay;
    public Integer biWeeklyDay;
    public Integer monthlyDay;
    public String time;
    public boolean status;

    public void setSelfFromEntity( com.app.tykhe.localStorage.entities.Reminder entityReminder ) {
        this.savingRate = entityReminder.chosenType;
        this.weeklyDay = entityReminder.weeklyChoonenDay;
        this.biWeeklyDay = entityReminder.biweeklyChosenDay;
        this.monthlyDay = entityReminder.monthlyChosenDay;
        this.time = entityReminder.time;
        this.status = entityReminder.status;
    }

}
