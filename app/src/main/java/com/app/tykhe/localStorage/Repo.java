package com.app.tykhe.localStorage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.app.tykhe.localStorage.dao.ReminderDao;
import com.app.tykhe.localStorage.dao.UserDao;
import com.app.tykhe.localStorage.entities.Reminder;
import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.misc.SavingRateEnum;
import com.app.tykhe.models.OnBoarding;

import java.util.List;

public class Repo {

    private UserDao userDao;
    private ReminderDao reminderDao;

    private LiveData<List<User>> userAccount;
    private  LiveData<List<Reminder>> currentReminder;

    public Repo( Application application){
        RoomDatabase db = RoomDatabase.getDatabase( application );
        this.userDao = db.userDao();
        this.reminderDao = db.reminderDao();
        this.userAccount =  db.userDao().getUser();
        this.currentReminder = db.reminderDao().getAllReminders();
    }
    public LiveData<List<User>> getUser(){
        return this.userAccount;
    }

    public int updateUser(OnBoarding onBoarding){
        return this.userDao.onBoardingUpdate(
                onBoarding.accountHoldersName,
                onBoarding.accountHoldersAge,
                onBoarding.savingRate,
                onBoarding.contributionAmount,
                onBoarding.intrestRate,
                onBoarding.lengthOfInvestment
        );
    }
    public void createUser( ){
        RoomDatabase.databaseWriteExecutor.execute( () -> {
            User user = new User();
            user.user_id = 1;
            this.userDao.insert( user );
        });
    }

    public LiveData<List<Reminder>> getReminder(){
        return this.currentReminder;
    }

    public void updateReminder(Reminder reminder){
        this.reminderDao.update(reminder);
    }

    public void createReminder(){
        Reminder reminder = new Reminder();
        reminder.user_fk = 1;
        reminder.chosenType = "weekly";
        reminder.weeklyChoonenDay = 1;
        reminder.biweeklyChosenDay = 1;
        reminder.monthlyChosenDay = 1;
        reminder.status = true;

        this.reminderDao.insert(
                reminder
        );
    }


    public int updateUserFromSettings( String name, Integer age ){
        return this.userDao.updateUser(name , age);
    }

    public int updateSavingsFromSettings(SavingRateEnum.savingRate savingRate, double contributionAmount, double interestRate, Integer lengthOfInvestment ){
        return this.userDao.updateSavings(
                savingRate,
                contributionAmount,
                interestRate,
                lengthOfInvestment
        );
    }



}

