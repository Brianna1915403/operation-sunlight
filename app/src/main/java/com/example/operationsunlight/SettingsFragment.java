package com.example.operationsunlight;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {
    SharedPreferences sharedPreferences;
    int theme_id;
    View root;
    ToggleButton dark_mode;
    Spinner theme;
    ArrayAdapter<CharSequence> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.fragment_settings, container, false);
        dark_mode = root.findViewById(R.id.dark_mode_togglebtn);
        dark_mode.setChecked((root.getContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES); // Checks if Dark mode is on
        dark_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppCompatDelegate.setDefaultNightMode(isChecked? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
            }
        });
        theme = (Spinner) root.findViewById(R.id.theme_spinner);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theme.setAdapter(adapter);
        sharedPreferences = getActivity().getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        theme_id = sharedPreferences.getInt("THEME_ID", R.style.Theme_OperationSunlight_NoActionBar);
        theme.setSelection(themeIDToPosition(theme_id));
        theme.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (theme_id == themeNameToID(parent.getItemAtPosition(position).toString()))
                    return;
                editor.putInt("THEME_ID", themeNameToID(parent.getItemAtPosition(position).toString()));
                editor.apply();
                getActivity().finish();
                getActivity().startActivity(getActivity().getIntent());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    public int themeNameToID(String theme) {
        switch (theme.toUpperCase()) {
            case "GREEN": return R.style.Theme_OperationSunlight_NoActionBar;
            case "BLUE": return R.style.Theme_OperationSunlight_Blue_NoActionBar;
            default: return -1;
        }
    }

    public int themeIDToPosition(int id) {
        switch (id) {
            case R.style.Theme_OperationSunlight_NoActionBar: return 0;
            case R.style.Theme_OperationSunlight_Blue_NoActionBar: return 1;
            default: return -1;
        }
    }
}