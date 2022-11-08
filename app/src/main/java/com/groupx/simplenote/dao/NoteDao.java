package com.groupx.simplenote.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.groupx.simplenote.dto.NoteShareWithMeDTO;
import com.groupx.simplenote.entity.Account;
import com.groupx.simplenote.entity.Note;
import com.groupx.simplenote.entity.NoteAccount;
import com.groupx.simplenote.entity.NoteTag;

import java.util.Date;
import java.util.List;

@Dao
public interface NoteDao {
    @Query("SELECT * FROM note INNER JOIN NoteAccount na ON note.noteId = na.noteId WHERE na.permission = 'CREATED'" +
            "AND na.accountId = :accountId AND note.statusKey in (:status) ORDER BY noteId DESC")
    List<Note> getAllMyNote(int accountId, int[] status);

    @Query("SELECT * FROM note ORDER BY noteId DESC LIMIT 1")
    Note getNewestNote();

    @Query("SELECT COUNT(noteId) FROM note")
    int getSize();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Note note);

    @Update
    void update(Note note);

    @Delete
    void deleteNote(Note note);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertWithNoteAccount(NoteAccount noteAccount);

    @Query("SELECT * FROM noteaccount ORDER BY noteId LIMIT 1")
    NoteAccount getLatestNoteAccount();

    @Query("SELECT n.*, ac.*, na.permission  FROM noteaccount na INNER JOIN note n ON n.noteId = na.noteId" +
            "  INNER JOIN account a ON na.accountId = a.accountId " +
            " JOIN noteaccount nac ON nac.noteId = na.noteId JOIN account ac ON ac.accountId=nac.accountId AND nac.permission = 'CREATED'" +
            "  WHERE a.accountId = :accountId " +
                "AND na.permission IN (:permissions) AND n.statusKey IN (:noteStatus)" +
            " ORDER BY n.lastUpdate DESC")
    List<NoteShareWithMeDTO> getNoteShareForMe(int accountId, String[] permissions, int[] noteStatus);

    @Query("SELECT * FROM note WHERE ((since between :start and :end) OR (reminderTime between :start and :end)) AND (statusKey = :normal OR statusKey = :favourite)")
    List<Note> getTodayNote(Date start, Date end, int normal, int favourite);
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertNoteTag(List<NoteTag> noteTagList);

    @Delete
    void deleteAllTag(List<NoteTag> noteTagList);

    @Query("SELECT * FROM notetag WHERE noteId == :noteId")
    List<NoteTag> findNoteTagOf(int noteId);

    @Query("SELECT * FROM note WHERE statusKey = :status")
    List<Note> getNoteByStatus(int status);

    @Query("select * from note where title LIKE '%' || :search || '%' or  subTitle LIKE '%' || :search || '%' or note LIKE '%' || :search || '%'")
    List<Note> searchNote(String search);

    @Query("SELECT * FROM note WHERE ((since between :start and :end) OR (reminderTime between :start and :end))")
    List<Note> getTodayNote(Date start, Date end);

    @Query("SELECT * FROM note WHERE reminderTime not null")
    List<Note> getAllReminders();

    @Query("SELECT * FROM note WHERE noteId = :id")
    Note getNoteById(int id);

    @Query("SELECT * FROM note WHERE folderId = :folderId AND statusKey NOT IN (:notStatus) ORDER BY noteId DESC")
    List<Note> getNotesByFolder(int folderId, int[] notStatus);
}
