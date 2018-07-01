package com.fortests.meet10practice;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Note.class},version = 1)
//@TypeConverters({Converters.class})
public abstract class NoteDatabase extends RoomDatabase {
    public abstract NotepadDAO daoAccess();
}
