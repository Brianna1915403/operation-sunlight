package com.example.operationsunlight.ui.plant;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.operationsunlight.R;

public class PlantBioFragment extends Fragment {
    private static final String TAG = "ARGS" ; // Can be removed only for debug purposes
    private View root;

    // TODO: Setup the fragment view for the bio.
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        root = inflater.inflate(R.layout.fragment_plant_bio, container, false);
        Bundle bundle = getArguments();
        Log.d(TAG, "onCreateView: " + bundle);
        return root;
    }
}