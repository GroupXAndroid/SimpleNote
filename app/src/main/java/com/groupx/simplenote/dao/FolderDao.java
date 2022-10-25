package com.groupx.simplenote.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.groupx.simplenote.entity.Folder;

import java.util.List;

@Dao
public interface FolderDao {

    @Query("SELECT * FROM Folder ORDER BY id DESC")
    List<Folder> getAllMyFolder();

    @Insert
    void insert(Folder folder);
}
