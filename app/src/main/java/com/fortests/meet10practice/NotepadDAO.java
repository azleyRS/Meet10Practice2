package com.fortests.meet10practice;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface NotepadDAO {
    @Insert
    void addNote(Note note);

    @Query("SELECT * FROM note WHERE id = :id")
    Note getNote(int id);

    @Query("SELECT * FROM note")
    List<Note> getNotepad();

    @Update
    void update(Note note);
}
