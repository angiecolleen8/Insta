package com.codepath.acfoley.insta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

public class LoginActivity extends AppCompatActivity {

    //TODO - add drawables from the lab (gradient drawables, etc.)

    private EditText usernameInput;
    private EditText passwordInput;
    private Button btnLogin;
    private Button btn_sign_up;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //todo wire buttons
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        usernameInput = (EditText) findViewById(R.id.et_username);
        passwordInput = (EditText) findViewById(R.id.et_password);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btn_sign_up = (Button) findViewById(R.id.btn_sign_up);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String username = usernameInput.getText().toString();
                final String password = passwordInput.getText().toString();
                login(username, password);
            }
        });

        btn_sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
                finish(); //necessary?
            }
        });
    }

    public void login(String username, String password) {
        ParseUser.logInInBackground(username, password, new LogInCallback() { //if this wasn't async, app would freeze
            @Override
            public void done(ParseUser user, ParseException e) {
                if (e == null) {
                    Log.d("Login activity", "Login successful.");
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish(); //don't let user log out by just pressing back button
                } else {
                    Log.e("Login activity", "Login failed.");
                    e.printStackTrace();
                }
            }
        });
    }
}
