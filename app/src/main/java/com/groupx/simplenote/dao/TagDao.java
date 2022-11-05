package com.groupx.simplenote.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.groupx.simplenote.entity.Tag;

import java.util.List;

@Dao
public interface TagDao {

    @Query("SELECT * FROM TAG WHERE accountId == :accountId  ORDER BY ID DESC")
    List<Tag> getAllTagsOfAccount(int accountId);

    @Insert
    void insertTag(Tag tag);
}
