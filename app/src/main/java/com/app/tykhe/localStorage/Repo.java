package com.app.tykhe.localStorage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.app.tykhe.localStorage.dao.ReminderDao;
import com.app.tykhe.localStorage.dao.SavingItemDao;
import com.app.tykhe.localStorage.dao.UserDao;
import com.app.tykhe.localStorage.entities.Reminder;
import com.app.tykhe.localStorage.entities.SavingItem;
import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.misc.SavingRateEnum;
import com.app.tykhe.models.OnBoarding;

import java.util.List;

public class Repo {

    private UserDao userDao;
    private ReminderDao reminderDao;
    private SavingItemDao savingItemDao;

    private LiveData<List<User>> userAccount;
    private  LiveData<List<Reminder>> currentReminder;

    public Repo( Application application){
        RoomDatabase db = RoomDatabase.getDatabase( application );
        this.userDao = db.userDao();
        this.reminderDao = db.reminderDao();
        this.savingItemDao = db.savingItemDao();
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
                onBoarding.lengthOfInvestment,
                onBoarding.currentSavings
        );
    }

    public int updateCurrentSavings( double newCurrent ){
        return this.userDao.updateCurrentSavings( newCurrent );
    }

    public int updateSavingRate ( SavingRateEnum.savingRate savingRate ){
        return this.userDao.updateSavingRate( savingRate );
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
        this.reminderDao.update(
                reminder.chosenType,
                reminder.status,
                reminder.weeklyChoonenDay,
                reminder.biweeklyChosenDay,
                reminder.monthlyChosenDay,
                reminder.time
        );
    }

    public void createReminder(){
        Reminder reminder = new Reminder();
        reminder.user_fk = 1;
        reminder.chosenType = "weekly";
        reminder.weeklyChoonenDay = 1;
        reminder.biweeklyChosenDay = 1;
        reminder.monthlyChosenDay = 1;
        reminder.time = "1:00 pm";
        reminder.status = true;

        this.reminderDao.insert(
                reminder
        );
    }


    public int updateUserFromSettings( String name, Integer age ){
        return this.userDao.updateUser(name , age);
    }

    public int updateSavingsFromSettings(SavingRateEnum.savingRate savingRate, double contributionAmount, double interestRate, Integer lengthOfInvestment, double currentSavings ){
        return this.userDao.updateSavings(
                savingRate,
                contributionAmount,
                interestRate,
                lengthOfInvestment,
                currentSavings
        );
    }

    public void createSavingItem(SavingItem savingItem){
        this.savingItemDao.insert( savingItem );
    }

    public LiveData<List<SavingItem>> getAllSavingItems(){
        return this.savingItemDao.getSavingItems();
    }

    public int updateSavingItemStatus( int newStatus, int savingItemId ){
        return this.savingItemDao.updateSavingItemStatus( newStatus, savingItemId );
    }

}

