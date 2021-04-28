package com.example.operationsunlight.modules.login;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.operationsunlight.NavigationActivity;
import com.example.operationsunlight.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class LoginFragment extends Fragment {
    static View root;
    SharedPreferences sharedPreferences;

    EditText login_username, login_password;
    Button login_button;

    DatabaseReference reference;
    User user;
    ArrayList<User> users = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_login, container, false);
        reference = FirebaseDatabase.getInstance().getReference().child("USER");
        reference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                users.add(snapshot.getValue(User.class));
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {}

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}

            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
        user = new User();
        login_username = root.findViewById(R.id.login_username);
        login_password = root.findViewById(R.id.login_password);
        login_button = root.findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User tempUser = new User(login_username.getText().toString(), login_password.getText().toString());
                for (User user : users)
                    if (tempUser.equals(user)) {
                        sharedPreferences = getActivity().getSharedPreferences("ACCOUNT", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USERNAME", user.getUsername());
                        editor.putString("EMAIL", user.getEmail());
                        editor.apply();
                        toMain();
                        return;
                    }
                Toast.makeText(root.getContext(), "User/Password Mismatch.", Toast.LENGTH_SHORT).show();
            }
        });
        return root;
    }

    private void toMain() {
        Intent intent = new Intent(getActivity(), NavigationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }
}