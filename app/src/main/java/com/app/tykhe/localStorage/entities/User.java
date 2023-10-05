package com.app.tykhe.localStorage.entities;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.app.tykhe.misc.SavingRateEnum;

@Entity( tableName = "User" )
public class User {

    @PrimaryKey(autoGenerate = true)
    public int pk;

    @ColumnInfo(name = "user_id")
    public int user_id;

    @ColumnInfo(name = "name")
    public String name;

    @ColumnInfo(name = "age" )
    public Integer age;

    @ColumnInfo( name = "savingRate")
    public SavingRateEnum.savingRate savingRate;

    @ColumnInfo( name = "contributionAmount" )
    public double contributionAmount;

    @ColumnInfo ( name = "interstRate" )
    public double interstRate;

    @ColumnInfo ( name = "lengthOfInvestment" )
    public Integer lengthOfInvestment;

    @ColumnInfo ( name = "currentSavings" )
    public Double currentSavings;

    @Override
    public String toString(){
        return
                this.name + " " +
                this.age + " " +
                this.savingRate + " " +
                this.contributionAmount + " " +
                this.interstRate + " " +
                this.lengthOfInvestment + " ";
    }


}
