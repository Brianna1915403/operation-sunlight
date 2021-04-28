package com.example.operationsunlight.modules.login;

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

import com.example.operationsunlight.R;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class RegisterFragment extends Fragment {
    static View root;

    EditText register_email, register_username, register_password, register_password_confirmation;
    Button register_button;

    DatabaseReference reference;
    User user;
    ArrayList<User> users = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_register, container, false);
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
        register_email = root.findViewById(R.id.register_email);
        register_username = root.findViewById(R.id.register_username);
        register_password = root.findViewById(R.id.register_password);
        register_password_confirmation = root.findViewById(R.id.register_password_confirmation);
        register_button = root.findViewById(R.id.register_button);
        register_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = register_email.getText().toString();
                String username = register_username.getText().toString();
                String password = register_password.getText().toString();
                String confirmation = register_password_confirmation.getText().toString();
                if (isEmailValid(email) && isUsernameValid(username) && isPasswordValid(password, confirmation)) {
                    user.setEmail(email);
                    user.setUsername(username);
                    user.setPassword(PasswordEncoder.password_hash(password));
                    reference.child(user.getUsername()).setValue(user);
                    // TODO: Do we auto login?
                    Toast.makeText(root.getContext(), "Registration Successful!", Toast.LENGTH_SHORT).show();
                    clear();
                }
            }
        });
        return root;
    }

    private void clear() {
        register_email.setText("");
        register_username.setText("");
        register_password.setText("");
        register_password_confirmation.setText("");
    }

    private boolean isUsernameValid(String username) {
        if (username == null || username.isEmpty()) {
            Toast.makeText(root.getContext(), "Username cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (usernameExists(username)) {
            Toast.makeText(root.getContext(), "Username already in use.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (username.matches(".*[\\.#$\\[\\]].*")) {
            Toast.makeText(root.getContext(), "Username cannot contain: '.', '#', '$', '[', or ']'.", Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    private boolean usernameExists(String username) {
        if(users.isEmpty())
            return false;
        for (User user : users)
            if (user.getUsername().equals(username))
                return true;
        return false;
    }

    private boolean isEmailValid(String email) {
        if (email == null || email.isEmpty()) {
            Toast.makeText(root.getContext(), "Email cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (emailExists(email)) {
            Toast.makeText(root.getContext(), "Email already in use.", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!email.matches("^(.+)@(.+)$")) {
            Toast.makeText(root.getContext(), "Invalid email format.", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private boolean emailExists(String email) {
        if(users.isEmpty())
            return false;
        for (User user : users)
            if (user.getEmail().equals(email))
                return true;
        return false;
    }

    // TODO: Do we add regex?
    private boolean isPasswordValid(String password, String confirmation) {
        if ((password == null || password.isEmpty()) || (confirmation == null || confirmation.isEmpty())) {
            Toast.makeText(root.getContext(), "Password fields cannot be empty!", Toast.LENGTH_SHORT).show();
            return false;
        } else if (!confirmation.equals(password)) {
            Toast.makeText(root.getContext(), "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return false;
        }
//        } else if (email.matches("^(.+)@(.+)$")) {
//            Toast.makeText(root.getContext(), "Invalid email format.", Toast.LENGTH_SHORT).show();
//            return false;
//        }
        return true;
    }
}