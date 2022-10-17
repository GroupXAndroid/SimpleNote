package com.groupx.simplenote.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.groupx.simplenote.entity.Note;

import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note ORDER BY id DESC")
    List<Note> getAllMyNote();
}
