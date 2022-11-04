package com.groupx.simplenote.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.groupx.simplenote.entity.Account;

@Dao
public interface AccountDao {

    @Insert
    void insert(Account account);

    @Query("SELECT * FROM ACCOUNT WHERE ACCOUNT.username == :email")
    Account getAccountByEmail(String email);
    @Update
    void update(Account account);
}
