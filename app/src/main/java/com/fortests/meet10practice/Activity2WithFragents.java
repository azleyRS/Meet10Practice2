package com.fortests.meet10practice;

import android.arch.persistence.room.Room;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

public class Activity2WithFragents extends AppCompatActivity {
    private int mNotePosition;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity2);

        if (savedInstanceState == null){
            initFragment1();
        } else {
            //do nothing i suppose
        }
    }

    private void initFragment1() {
        mNotePosition = getIntent().getIntExtra("position",0);
        FragmentManager fragmentManager = getSupportFragmentManager();
        MyFragment1 myFragment1 = new MyFragment1();
        Bundle bundle = new Bundle();
        bundle.putInt("position",mNotePosition);
        myFragment1.setArguments(bundle);
        fragmentManager.beginTransaction().add(R.id.fragment_container,myFragment1).commit();
    }

    public static Intent newIntent(Context context, int position){
        Intent intent = new Intent(context, Activity2WithFragents.class);
        intent.putExtra("position", position);
        return intent;
    }

    public static class MyFragment1 extends Fragment{
        private TextView mF1Name;
        private TextView mF1Time;
        private TextView mF1Content;
        private Note note;
        private int mPosition;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment1,null);
            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            mF1Name = view.findViewById(R.id.fragment1_name);
            mF1Time = view.findViewById(R.id.fragment1_time);
            mF1Content = view.findViewById(R.id.fragment1_content);

            mPosition = getArguments().getInt("position");

            final NoteDatabase mNoteDatabase = Room.databaseBuilder(getContext(),NoteDatabase.class,"notepad_db").build();
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    note = mNoteDatabase.daoAccess().getNote(mPosition+1);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mF1Name.setText(note.getName());
                    mF1Content.setText(note.getContent());
                    mF1Time.setText(note.getTime());
                }
            }.execute();

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MyFragment2 myFragment2 = new MyFragment2();
                    Bundle bundle = new Bundle();
                    bundle.putInt("position",mPosition);
                    myFragment2.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,myFragment2).commit();
                }
            });
        }
    }

    public static class MyFragment2 extends Fragment{
        private EditText mF2Name;
        private TextView mF2Time;
        private EditText mF2Content;
        private NoteDatabase mNoteDatabase;
        private Note note;
        private int mPosition2;

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment2,null);
            return view;
        }

        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            mF2Name = view.findViewById(R.id.fragment2_name);
            mF2Time = view.findViewById(R.id.fragment2_time);
            mF2Content = view.findViewById(R.id.fragment2_content);

            mPosition2 = getArguments().getInt("position");
            mNoteDatabase = Room.databaseBuilder(getContext(),NoteDatabase.class,"notepad_db").build();

            getNoteAsync();

            mF2Name.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    note.setName(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            mF2Content.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    note.setContent(charSequence.toString());
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
        }

        private void getNoteAsync() {
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    note = mNoteDatabase.daoAccess().getNote(mPosition2+1);
                    return null;
                }

                @Override
                protected void onPostExecute(Void aVoid) {
                    super.onPostExecute(aVoid);
                    mF2Name.setText(note.getName());
                    mF2Content.setText(note.getContent());
                    mF2Time.setText(note.getTime());
                }
            }.execute();
        }

        @Override
        public void onPause() {
            super.onPause();

            //manager.update(note, mPosition2);
            new AsyncTask<Void, Void, Void>() {
                @Override
                protected Void doInBackground(Void... voids) {
                    mNoteDatabase.daoAccess().update(note);
                    return null;
                }
            }.execute();
            //mNoteDatabase.daoAccess().update(note);
        }
    }
}
