package com.fortests.meet10practice;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.util.Date;

@Entity
public class Note {
    @PrimaryKey(autoGenerate=true)
    public int id;
    private String mName;
    //private Date mTime;
    private String mTime;
    private String mContent;
    @Ignore
    public Note(String name, String content) {
        mName = name;
        mContent = content;
        mTime = String.valueOf(android.text.format.DateFormat.format("EEE MMM d HH:mm:ss",new Date().getTime()));
    }
    public Note(){
        mTime = String.valueOf(android.text.format.DateFormat.format("EEE MMM d HH:mm:ss",new Date().getTime()));
    }

    public String getTime() {
        return mTime;
    }

    public void setTime(String time) {
        mTime = time;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getContent() {
        return mContent;
    }

    public void setContent(String content) {
        mContent = content;
    }
}
