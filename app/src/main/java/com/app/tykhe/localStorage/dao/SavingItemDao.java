package com.app.tykhe.localStorage.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.app.tykhe.localStorage.entities.SavingItem;

import java.util.List;

@Dao
public interface SavingItemDao {

    @Query( "Select * from SavingItems" )
    LiveData< List<SavingItem> > getSavingItems();

    @Insert()
    void insert( SavingItem savingItem );

    @Query( "update Savingitems " +
            "set " +
            "savingItemStatus = :newSavingItemStatus " +
            "where pk = :savingItemId"
    )
    int updateSavingItemStatus( int newSavingItemStatus, int savingItemId );

}
