package com.groupx.simplenote.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.groupx.simplenote.entity.Folder;
import com.groupx.simplenote.entity.Tag;

import java.util.List;

@Dao
public interface TagDao {

    @Query("SELECT * FROM tag WHERE accountId == :accountId  ORDER BY ID DESC")
    List<Tag> getAllTagsOfAccount(int accountId);
    @Query("SELECT * FROM TAG ORDER BY ID DESC")
    List<Tag> getAllTags();

    @Insert
    void insertTag(Tag tag);
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Tag tag);

    @Update
    void update(Tag tag);

    @Delete
    void delete(Tag tag);
}
