package com.example.operationsunlight.modules.note;

import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.operationsunlight.R;
import com.example.operationsunlight.modules.login.User;
import com.example.operationsunlight.modules.plant.Plant;
import com.example.operationsunlight.modules.plant.PlantRecyclerAdapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class NoteFragment extends Fragment implements View.OnClickListener, OnNoteListener {
    View root;
    SharedPreferences preferences;
    private RecyclerView recyclerView;
    private NoteAdapter adapter;
    private ArrayList<Note> notes = new ArrayList<>();
    private OnNoteListener onNoteListener = this;

    DatabaseReference noteRef, userRef;
    Note note_to_update;

    EditText note_edittext;
    Button add_note_button, update_note_button;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_need_sign_in, container, false);
        preferences = getActivity().getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        if (preferences.getString("USERNAME", null) == null)
            return root;
        else
            root = inflater.inflate(R.layout.fragment_note, container, false);

        recyclerView = root.findViewById(R.id.recycler_view);
        adapter = new NoteAdapter(getContext(), onNoteListener, notes);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        noteRef = FirebaseDatabase.getInstance().getReference().child("NOTE");
        userRef = FirebaseDatabase.getInstance().getReference().child("USER").child(preferences.getString("USERNAME", null));
        note_edittext = root.findViewById(R.id.note_edittext);
        add_note_button = root.findViewById(R.id.add_note_button);
        add_note_button.setVisibility(View.VISIBLE);
        add_note_button.setOnClickListener(this::onClick);
        update_note_button = root.findViewById(R.id.update_note_button);
        update_note_button.setVisibility(View.GONE);
        update_note_button.setOnClickListener(this::onClick);
        getNotes();
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_note_button:
                String ID = noteRef.push().getKey();
                Note note = new Note(note_edittext.getText().toString(), Calendar.getInstance().getTime());
                note.key = ID;
                notes.add(note);
                noteRef.child(ID).setValue(note);
                adapter.notifyDataSetChanged();
                userRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        List<String> noteRefs = new ArrayList<>();
                        if(user.getNoteRefs() != null)
                            noteRefs.addAll(user.getNoteRefs());
                        noteRefs.add(note.key);
                        userRef.child("noteRefs").setValue(noteRefs);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {}
                });
                note_edittext.setText("");
            break;
            case R.id.update_note_button:
                noteRef.child(note_to_update.key).child("note").setValue(note_edittext.getText().toString());
                getNotes();
                adapter.notifyDataSetChanged();
                add_note_button.setVisibility(View.VISIBLE);
                update_note_button.setVisibility(View.GONE);
                note_edittext.setText("");
            break;
        }
    }

    @Override
    public void onNoteDelete(int position) {
        Note note = notes.remove(position);
        noteRef.child(note.key).setValue(null);
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                List<String> refs = user.getNoteRefs();
                refs.remove(note.key);
                userRef.child("noteRefs").setValue(refs);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onNoteUpdate(int position) {
        note_to_update = notes.get(position);
        note_edittext.setText(note_to_update.getNote());
        add_note_button.setVisibility(View.GONE);
        update_note_button.setVisibility(View.VISIBLE);
    }

    private void getNotes() {
        notes.clear();
        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                List<String> refs = user.getNoteRefs();
                if (refs == null)
                    return;
                refs.iterator().forEachRemaining(next -> {
                    noteRef.child(next).get().addOnSuccessListener(dataSnapshot -> {
                        Note note = dataSnapshot.getValue(Note.class);
                        note.key = dataSnapshot.getKey();
                        notes.add(note);
                        adapter.notifyDataSetChanged();
                    });
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}