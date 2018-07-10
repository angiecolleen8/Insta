package com.codepath.acfoley.insta;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {

    //TODO - add drawables from the lab (gradient drawables, etc.)

    private EditText usernameInput;
    private EditText passwordInput;
    private Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameInput = findViewById(R.layout.et_username);
        passwordInput = findViewById(R.layout.et_password);
        btnLogin = findViewById(R.layout.btn_login);
    }
}
