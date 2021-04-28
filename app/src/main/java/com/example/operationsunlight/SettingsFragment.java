package com.example.operationsunlight;

import android.content.Context;
import android.content.Intent;
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

import com.example.operationsunlight.modules.login.LoginActivity;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment {
    SharedPreferences sharedPreferences;
    int theme_id;
    boolean isDarkMode;
    View root;

    ToggleButton dark_mode;
    Spinner theme;
    Button logout_button;

    ArrayAdapter<CharSequence> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root =  inflater.inflate(R.layout.fragment_settings, container, false);
        sharedPreferences = getActivity().getSharedPreferences("PREFERENCES", MODE_PRIVATE);

        // DARK MODE
        dark_mode = root.findViewById(R.id.dark_mode_togglebtn);
        boolean isDeviceDark = (root.getContext().getResources().getConfiguration().uiMode &
                Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        isDarkMode = sharedPreferences.getBoolean("IS_DARK_MODE", isDeviceDark);
        dark_mode.setChecked(isDarkMode);
        dark_mode.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                AppCompatDelegate.setDefaultNightMode(isChecked? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("IS_DARK_MODE", isChecked);
                editor.apply();
            }
        });

        // THEME
        theme = (Spinner) root.findViewById(R.id.theme_spinner);
        adapter = ArrayAdapter.createFromResource(getContext(), R.array.themes, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        theme.setAdapter(adapter);
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
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // LOGOUT
        logout_button = root.findViewById(R.id.logout_button);
        logout_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preferences = getActivity().getSharedPreferences("ACCOUNT", MODE_PRIVATE);
                SharedPreferences.Editor editor = preferences.edit();
                editor.putString("USERNAME", null);
                editor.putString("EMAIL", null);
                editor.apply();
                toLogin();
            }
        });
        return root;
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

    private void toLogin() {
        Intent intent = new Intent(getActivity(), LoginActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}