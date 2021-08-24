package com.example.foodonation;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

public class login extends AppCompatActivity {

    EditText username, password;
    Button login,signup;
    Database DB;

    LoginButton fb;
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        setContentView(R.layout.activity_login);


        username=(EditText)findViewById(R.id.login_name);
        password=(EditText)findViewById(R.id.login_password);

        password.setTransformationMethod(new PasswordTransformationMethod());

        login=(Button)findViewById(R.id.login);
        DB=new Database(this);

        signup=(Button)findViewById(R.id.signup);

        //fb code
        fb=(LoginButton)findViewById(R.id.fb);
         callbackManager=CallbackManager.Factory.create();
       fb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
           @Override
           public void onSuccess(LoginResult loginResult) {
               Intent i=new Intent(login.this, dashboard.class);
               startActivity(i);
           }

           @Override
           public void onCancel() {

           }

           @Override
           public void onError(FacebookException error) {

           }
       });

        //---------------------------------
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(login.this, signup.class);
                startActivity(i);
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString();
                String pass=password.getText().toString();

                if(user.equals("") || pass.equals(""))
                    Toast.makeText(login.this, "Please enter all the fields", Toast.LENGTH_LONG).show();
                else {
                    Boolean checkuserpass = DB.checkUsernamepassword(user,pass);
                    if(checkuserpass==true)
                    {
                        Toast.makeText(login.this, "SUCCESSFUL SIGN IN", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(login.this, Animation.class);
                        startActivity(i);
                    }
                    else
                    {
                        Toast.makeText(login.this, "Invalid credentials", Toast.LENGTH_LONG).show();
                    }

                }
                    }
        });

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}