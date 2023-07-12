package com.app.tykhe.localStorage;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.app.tykhe.localStorage.dao.UserDao;
import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.models.OnBoarding;

import java.util.List;

public class Repo {

    private UserDao userDao;
    private LiveData<List<User>> userAccount;

    public Repo( Application application){
        RoomDatabase db = RoomDatabase.getDatabase( application );
        this.userDao = db.userDao();

        this.userAccount =  db.userDao().getUser();
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

    public int updateUserFromSettings( String name, Integer age ){
        return this.userDao.updateUser(name , age);
    }

    public void createUser( ){
        RoomDatabase.databaseWriteExecutor.execute( () -> {
            User user = new User();
            user.user_id = 1;
            this.userDao.insert( user );
        });
    }

}

