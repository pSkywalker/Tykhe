package com.app.tykhe.localStorage.entities;

import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity( tableName = "User" )
public class User {

    @ColumnInfo( name = "name" )
    private String name;

    @ColumnInfo( name = "age" )
    private Integer age;



}
