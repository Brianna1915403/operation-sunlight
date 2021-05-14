package com.example.operationsunlight;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
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

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.MODE_PRIVATE;

public class SettingsFragment extends Fragment implements TimePickerDialog.OnTimeSetListener {
    SharedPreferences sharedPreferences;
    int theme_id;
    boolean isDarkMode;
    View root;

    TimePickerDialog.OnTimeSetListener onTimeSetListener = this::onTimeSet;

    Button set_time;
    ToggleButton dark_mode, notifications;
    Spinner theme;

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

        // NOTIFICATIONS
        notifications = root.findViewById(R.id.notification_togglebtn);
        notifications.setChecked(sharedPreferences.getBoolean("HAS_NOTIFICATIONS", false));
        set_time = root.findViewById(R.id.set_time_button);
        set_time.setEnabled(notifications.isChecked());
        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("HAS_NOTIFICATIONS", isChecked);
                editor.apply();
                set_time.setEnabled(isChecked);
            }
        });
        set_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel("Plant Care", "Plant Care",
                            NotificationManager.IMPORTANCE_DEFAULT);
                    NotificationManager manager = getActivity().getSystemService(NotificationManager.class);
                    manager.createNotificationChannel(channel);
                }
                TimePicker timePicker = new TimePicker(onTimeSetListener);
                timePicker.show(getActivity().getSupportFragmentManager(), "Time Picker");
            }
        });
        return root;
    }

    public int themeNameToID(String theme) {
        switch (theme.toUpperCase()) {
            case "GREEN": return R.style.Theme_OperationSunlight_NoActionBar;
            case "BLUE": return R.style.Theme_OperationSunlight_Blue_NoActionBar;
            case "RED": return R.style.Theme_OperationSunlight_Red_NoActionBar;
            case "PINK": return R.style.Theme_OperationSunlight_Pink_NoActionBar;
            default: return -1;
        }
    }

    public int themeIDToPosition(int id) {
        switch (id) {
            case R.style.Theme_OperationSunlight_NoActionBar: return 0;
            case R.style.Theme_OperationSunlight_Blue_NoActionBar: return 1;
            case R.style.Theme_OperationSunlight_Red_NoActionBar: return 2;
            case R.style.Theme_OperationSunlight_Pink_NoActionBar: return 3;
            default: return -1;
        }
    }

    @Override
    public void onTimeSet(android.widget.TimePicker view, int hourOfDay, int minute) {
        Toast.makeText(view.getContext(), "Time Set!", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(getContext(), NotificationService.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getContext(), 0, intent, 0);
        AlarmManager alarmManager = (AlarmManager) getActivity().getSystemService(ALARM_SERVICE);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), AlarmManager.INTERVAL_DAY, pendingIntent);
    }
}