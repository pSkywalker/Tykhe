package com.app.tykhe.localStorage.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.app.tykhe.localStorage.entities.Reminder;

import java.util.List;

@Dao
public interface ReminderDao {

    @Insert
    void insert(Reminder reminder);

    @Query("UPDATE Reminder SET chosenType = :newChosenType, status = :newStatus, weeklyChoosenDay = :newWeeklyChosenDay, biweeklyChosenDay = :newBiweeklyChosenDay, monthlyChosenDay = :newMonthlyChosenDay, time = :newTime WHERE pk = 1")
    void update(String newChosenType, boolean newStatus, Integer newWeeklyChosenDay, Integer newBiweeklyChosenDay, Integer newMonthlyChosenDay, String newTime);

    @Query("Select * from reminder")
    LiveData<List<Reminder>> getAllReminders();

}
