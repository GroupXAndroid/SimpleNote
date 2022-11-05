package com.groupx.simplenote.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.groupx.simplenote.common.Converters;
import com.groupx.simplenote.dao.AccountDao;
import com.groupx.simplenote.dao.FolderDao;
import com.groupx.simplenote.dao.NoteDao;
import com.groupx.simplenote.dao.TagDao;
import com.groupx.simplenote.entity.Account;
import com.groupx.simplenote.entity.Folder;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.entity.NoteAccount;
import com.groupx.simplenote.entity.NoteStatus;
import com.groupx.simplenote.entity.NoteTag;
import com.groupx.simplenote.entity.Tag;

@Database(entities = {Account.class, Folder.class, Note.class, NoteAccount.class,
        NoteStatus.class, Tag.class, NoteTag.class}, version = 6, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class NoteDatabase extends RoomDatabase {
    private static NoteDatabase database;

    public static synchronized NoteDatabase getSNoteDatabase(Context context) {
        if (database == null) {
            database = Room.databaseBuilder(
                    context,
                    NoteDatabase.class,
                    "simple_note_db"
            ).fallbackToDestructiveMigration().allowMainThreadQueries().build();
        }
        return database;
    }

    public abstract NoteDao noteDao();

    public abstract AccountDao accountDao();

    public abstract FolderDao folderDao();

    public abstract TagDao tagDao();
}
