package com.fortests.meet10practice;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;

public class Activity3 extends AppCompatActivity {
    private Note mNote;
    private TextView mA3Name;
    private TextView mA3Time;
    private TextView mA3Content;

    private NoteDatabase mNoteDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity3);

        //mNoteDatabase = Room.databaseBuilder(getApplicationContext(),NoteDatabase.class,"notepad_db").allowMainThreadQueries().build();
        mNoteDatabase = Room.databaseBuilder(getApplicationContext(),NoteDatabase.class,"notepad_db").build();


        mNote = new Note();
        initViews();
        mA3Time.setText(mNote.getTime());
        initTextWatchers();
    }

    private void initTextWatchers() {
        mA3Name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setName(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        mA3Content.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mNote.setContent(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void initViews() {
        mA3Name = findViewById(R.id.activity3_name);
        mA3Content = findViewById(R.id.activity3_content);
        mA3Time = findViewById(R.id.activity3_time);
    }

    public static Intent newIntent(Context context/*, int position*/){
        Intent intent = new Intent(context, Activity3.class);
        //intent.putExtra("position", position);
        return intent;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                mNoteDatabase.daoAccess().addNote(mNote);
                return null;
            }
        }.execute();
    }
}
