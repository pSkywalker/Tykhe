package com.app.tykhe.localStorage.entities;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity( tableName = "SavingItems")
public class SavingItem {

    @PrimaryKey( autoGenerate = true )
    public int pk;


    @ColumnInfo(  name = "savingItemDate" )
    public String SavingItmeDate;

    @ColumnInfo( name = "savingItemAmounts" )
    public Double SavingItemAmounts;

    @ColumnInfo( name = "savingItemStatus" )
    public int SavingItemStatus;
    /*
        0 - uncomplete
        1 - complete
        2 - overdue
     */


}
