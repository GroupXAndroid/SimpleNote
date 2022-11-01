package com.groupx.simplenote.dao;

import androidx.room.Dao;
import androidx.room.Update;

import com.groupx.simplenote.entity.Account;

@Dao
public interface AccountDao {

    @Update
    void update(Account account);
}
