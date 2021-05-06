package com.example.operationsunlight.modules.note;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.operationsunlight.R;

import static android.content.Context.MODE_PRIVATE;

public class NoteFragment extends Fragment {
    View root;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_need_sign_in, container, false);
        SharedPreferences preferences = getActivity().getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        if (preferences.getString("USERNAME", null) == null)
            return root;
        else
            root = inflater.inflate(R.layout.fragment_note, container, false);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}