package com.codepath.acfoley.insta;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.parse.ParseUser;

public class SignupActivity extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private EditText et_email;
    private Button btn_finish_sign_up;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        //find views by id
        usernameInput = (EditText) findViewById(R.id.et_username);
        passwordInput = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);
        btn_finish_sign_up = (Button) findViewById(R.id.btn_finish_sign_up);


        btn_finish_sign_up.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // Create the ParseUser
                ParseUser user = new ParseUser();

                // Set core properties for new user
                user.setUsername(usernameInput.getText().toString());
                user.setPassword(passwordInput.getText().toString());


                Intent intent = new Intent(SignupActivity.this, HomeActivity.class);
                startActivity(intent);
                finish(); //necessary?
            }
        });



    }
}
