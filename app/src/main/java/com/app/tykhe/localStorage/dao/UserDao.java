package com.app.tykhe.localStorage.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.app.tykhe.localStorage.entities.User;
import com.app.tykhe.misc.SavingRateEnum;

import java.util.List;

@Dao
public interface UserDao {

    @Query("Select * from user")
    List<User> getUsers();

    @Query("Select * from user where user_id = 1")
    LiveData<List<User>> getUser();

    @Query("update user " +
            "set " +
            "name = :name, " +
            "age = :age " +
            "where user_id = 1" )
    int updateUser( String name, Integer age );

    @Query( "update user " +
            "set " +
            "name = :name," +
            "age  = :age," +
            "savingRate = :savingRate," +
            "contributionAmount  = :contributionAmount," +
            "interstRate = :interstRate," +
            "lengthOfInvestment = :lengthOfInvestment " +
            "where user_id = 1" )
    int onBoardingUpdate(
            String name,
            Integer age,
            SavingRateEnum.savingRate savingRate,
            double contributionAmount,
            double interstRate,
            Integer lengthOfInvestment
    );

    @Insert
    void insert( User user );

}
