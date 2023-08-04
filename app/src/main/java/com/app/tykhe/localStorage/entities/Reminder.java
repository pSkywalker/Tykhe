package com.app.tykhe.localStorage.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity( tableName = "Reminder" )
public class Reminder {

    @PrimaryKey(autoGenerate = true)
    public int pk;


    @ColumnInfo(name = "user_id")
    public int user_fk;




    @ColumnInfo(name = "chosenType")
    public String chosenType;

    @ColumnInfo(name = "status")
    public boolean status;

    @ColumnInfo(name = "weeklyChoosenDay")
    public Integer weeklyChoonenDay;

    @ColumnInfo(name = "biweeklyChosenDay")
    public Integer biweeklyChosenDay;

    @ColumnInfo(name = "monthlyChosenDay")
    public Integer monthlyChosenDay;

    @ColumnInfo(name = "time")
    public String time;


}
