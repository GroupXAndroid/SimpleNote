package com.groupx.simplenote.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.groupx.simplenote.entity.Account;

@Dao
public interface AccountDao {

    @Query("SELECT * FROM ACCOUNT WHERE ACCOUNT.username == :email")
    Account getAccountByEmail(String email);
}
