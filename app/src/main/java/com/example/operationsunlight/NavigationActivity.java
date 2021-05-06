package com.example.operationsunlight;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.Button;
import android.widget.TextView;

import com.example.operationsunlight.modules.login.LoginActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AppCompatDelegate;
import androidx.customview.widget.Openable;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener {
    private SharedPreferences preferences;
    private AppBarConfiguration mAppBarConfiguration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = getSharedPreferences("PREFERENCES", MODE_PRIVATE);
        setTheme();
        setDarkMode();
        setContentView(R.layout.activity_navigation);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        preferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
        View header = navigationView.getHeaderView(0);
        TextView nav_username = header.findViewById(R.id.nav_username);
        nav_username.setText(preferences.getString("USERNAME", null));
        TextView nav_email = header.findViewById(R.id.nav_email);
        nav_email.setText(preferences.getString("EMAIL", null));
        Button sign_in_button = findViewById(R.id.sign_in_button);
        sign_in_button.setText(preferences.getString("EMAIL", null) == null? R.string.sign_in : R.string.sign_out);
        sign_in_button.setCompoundDrawablesWithIntrinsicBounds((preferences.getString("EMAIL", null) == null? R.drawable.ic_login : R.drawable.ic_logout), 0, 0, 0);
        sign_in_button.setOnClickListener(this::onClick);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_plant, R.id.nav_weather, R.id.nav_garden, R.id.nav_greenhouse_view, R.id.nav_greenhouse_controller, R.id.nav_note)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        switch (item.getItemId()) {
            case R.id.action_settings:
                navController.navigate(R.id.nav_settings);
                return true;
            default: return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    private void setTheme() {
        int theme_id = preferences.getInt("THEME_ID", R.style.Theme_OperationSunlight_NoActionBar);
        setTheme(theme_id);
    }

    private void setDarkMode() {
        boolean isDeviceDark = (this.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES;
        boolean isDarkMode = preferences.getBoolean("IS_DARK_MODE", isDeviceDark);
        AppCompatDelegate.setDefaultNightMode(isDarkMode? AppCompatDelegate.MODE_NIGHT_YES : AppCompatDelegate.MODE_NIGHT_NO);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sign_in_button:
                Button btn = (Button) v;
                if (btn.getText().toString().equals("Sign In")) {
                    Intent intent = new Intent(NavigationActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                } else if (btn.getText().toString().equals("Sign Out")) {
                    preferences = getSharedPreferences("ACCOUNT", MODE_PRIVATE);
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.putString("USERNAME", null);
                    editor.putString("EMAIL", null);
                    editor.apply();
                    Intent intent = new Intent(NavigationActivity.this, NavigationActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                }
                break;
        }
    }
}