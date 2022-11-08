package com.groupx.simplenote.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.groupx.simplenote.entity.Folder;

import java.util.List;

@Dao
public interface FolderDao {

    @Query("SELECT * FROM Folder WHERE accountId = :accountId ORDER BY id DESC")
    List<Folder> getAllMyFolder(int accountId);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Folder folder);

    @Update
    void update(Folder folder);

    @Delete
    void delete(Folder folder);
}
