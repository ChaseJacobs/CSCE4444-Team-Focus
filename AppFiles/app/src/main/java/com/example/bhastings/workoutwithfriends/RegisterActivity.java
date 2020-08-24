package com.example.bhastings.workoutwithfriends;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.bhastings.workoutwithfriends.DatabaseRequests.RegisterRequest;

import org.json.JSONException;
import org.json.JSONObject;

public class RegisterActivity extends AppCompatActivity {

    EditText etUsername, etFirstName, etLastName, etPassword, etReEnterPassword; //edit text fields
    Button bRegister; //register button
    String username, firstname, lastname, password, reEnterPassword; //returned from the getLoggedInUser function

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        setTitle("Workout With Friends");

        etUsername = (EditText) findViewById(R.id.etUsername);
        etFirstName = (EditText) findViewById(R.id.etFirstName);
        etLastName = (EditText) findViewById(R.id.etLastName);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etReEnterPassword = (EditText) findViewById(R.id.etReEnterPassword);
        bRegister = (Button) findViewById(R.id.bRegister);}

        public void userReg(View v){

        username = etUsername.getText().toString();
        firstname = etFirstName.getText().toString();
        lastname = etLastName.getText().toString();
        password = etPassword.getText().toString();
        reEnterPassword = etReEnterPassword.getText().toString();

            //if passwords match
        if(password.equals(reEnterPassword)) {

            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");

                        if(success){
                            //show main screen
                            Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            RegisterActivity.this.startActivity(registerIntent);
                            Toast.makeText(getApplicationContext(), "Registration Successful", Toast.LENGTH_LONG).show();
                        }
                        else {
                            AlertDialog alertDialog = new AlertDialog.Builder(RegisterActivity.this).create();
                            alertDialog.setTitle("Error");
                            alertDialog.setMessage("This username is already taken. Please enter a different username");
                            alertDialog.show();


                         //   Toast.makeText(getApplicationContext(), "Registration Failed", Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        Log.e("log_tag", "Error converting result " + e.toString());
                        e.printStackTrace();
                    }
                }
            };

            RegisterRequest registerRequest = new RegisterRequest(username, password, firstname, lastname, responseListener);
            RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
            queue.add(registerRequest);

        }
        else{
            Toast.makeText(getApplicationContext(), "Error: Passwords to not match", Toast.LENGTH_LONG).show();
        }
    }

}
