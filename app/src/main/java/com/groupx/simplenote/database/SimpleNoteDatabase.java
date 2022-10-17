package com.groupx.simplenote.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.groupx.simplenote.common.Converters;
import com.groupx.simplenote.dao.NoteDao;
import com.groupx.simplenote.entity.Note;

@Database(entities  = {Note.class}, version = 1, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class SimpleNoteDatabase extends RoomDatabase {

    private static SimpleNoteDatabase snoteDatabase;

    public static synchronized SimpleNoteDatabase getSNoteDatabase(Context context){
        if(snoteDatabase == null){
            snoteDatabase = Room.databaseBuilder(
                    context,
                    SimpleNoteDatabase.class,
                    "simple_note_db"
            ).build();
        }
        return snoteDatabase;
    }

    public abstract NoteDao noteDao();
}
