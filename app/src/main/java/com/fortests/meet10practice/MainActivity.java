package com.fortests.meet10practice;


import android.arch.persistence.room.Room;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MyAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    //private DBManager dbManager;

    private List<Note> notes;

    private NoteDatabase mNoteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //mNoteDatabase = Room.databaseBuilder(getApplicationContext(),NoteDatabase.class,"notepad_db").allowMainThreadQueries().build();
        mNoteDatabase = Room.databaseBuilder(getApplicationContext(),NoteDatabase.class,"notepad_db").build();

        init();
        updateUI();
    }

    private void init() {
        mRecyclerView = findViewById(R.id.recycler_view_id);
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        //dividerText
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this,DividerItemDecoration.VERTICAL);
        mRecyclerView.addItemDecoration(itemDecoration);

        //dbManager = new DBManager(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.new_note:
                Intent intent = Activity3.newIntent(this);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void updateUI() {
        //List<Note> notes = dbManager.getNotepad();

        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... voids) {
                notes = mNoteDatabase.daoAccess().getNotepad();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                if (mAdapter == null){
                    mAdapter = new MyAdapter(notes);
                    mRecyclerView.setAdapter(mAdapter);
                } else {
                    mAdapter.setNotes(notes);
                    mAdapter.notifyDataSetChanged();
                }
            }
        }.execute();

/*        List<Note> notes = mNoteDatabase.daoAccess().getNotepad();
        if (mAdapter == null){
            mAdapter = new MyAdapter(notes);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.setNotes(notes);
            mAdapter.notifyDataSetChanged();
        }*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateUI();
    }
}
