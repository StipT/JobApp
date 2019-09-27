package com.example.jobapp.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.jobapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText editTextEmail, editTextPassword;
    FirebaseAuth mAuth;
    CheckBox checkBox;
    ProgressBar progressBar;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.login_email);
        editTextPassword = findViewById(R.id.login_password);
        checkBox = findViewById(R.id.remember_checkbox);
        progressBar = findViewById(R.id.progressBar);

        sharedPreferences = getBaseContext().getSharedPreferences("login", 0);
        editTextEmail.setText(sharedPreferences.getString(getString(R.string.email), ""));
        editTextPassword.setText(sharedPreferences.getString(getString(R.string.password), ""));
        checkBox.setChecked(sharedPreferences.getBoolean(getString(R.string.check), false));
        findViewById(R.id.login_sign_up).setOnClickListener(this);
        findViewById(R.id.login_button).setOnClickListener(this);


        mAuth = FirebaseAuth.getInstance();
    }

    private void userLogin(){
        progressBar.setVisibility(View.VISIBLE);
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();

        if(email.isEmpty()){
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            editTextEmail.setError("Enter a valid email");
            editTextEmail.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if(password.isEmpty()){
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }

        if(password.length() < 6){
            editTextPassword.setError("Minimum lenght of password should be 6");
            editTextPassword.requestFocus();
            progressBar.setVisibility(View.INVISIBLE);
            return;
        }
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {

                if(task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, AdListActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    if (checkBox.isChecked()) {
                        sharedPreferences = getBaseContext().getSharedPreferences("login", 0);
                        SharedPreferences.Editor sharedPreferencesEdit = sharedPreferences.edit();
                        sharedPreferencesEdit.putString("email", email);
                        sharedPreferencesEdit.putString("password", password);
                        sharedPreferencesEdit.putBoolean("check", true);
                        sharedPreferencesEdit.apply();
                        checkBox.setChecked(false);
                    } else {
                        SharedPreferences.Editor sharedPreferencesEdit = sharedPreferences.edit();
                        sharedPreferencesEdit.putString("email", "");
                        sharedPreferencesEdit.putString("password", "");
                        sharedPreferencesEdit.putBoolean("check", false);
                        sharedPreferencesEdit.apply();
                    }
                    progressBar.setVisibility(View.INVISIBLE);
                    startActivity(intent);


                } else {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(getApplicationContext(), task.getException().getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.login_sign_up:
                startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                break;

            case R.id.login_button:
                userLogin();
                break;
        }

    }
}

