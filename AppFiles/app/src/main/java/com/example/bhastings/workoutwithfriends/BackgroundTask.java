package com.example.bhastings.workoutwithfriends;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.widget.Toast;

import com.example.bhastings.workoutwithfriends.DatabaseRequests.ServerConstants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by bhastings on 11/8/2016.
 */


public class BackgroundTask extends AsyncTask<String, Void, String>{

    AlertDialog alertDialog;
    Context context;

    BackgroundTask(Context context){
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Error");
    }

    @Override
    protected String doInBackground(String... params) {
        String registrationURL = ServerConstants.CLOUD + "Register.php";
        String loginURL = ServerConstants.CLOUD + "Login.php";
        String method = params[0];
        if(method.equals("register")){
            String username = params[1];
            String password = params[2];
            String firstname = params[3];
            String lastname = params[4];


            try {
                URL url = new URL(registrationURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                OutputStream OS = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") +"="+URLEncoder.encode(username, "UTF-8") +"&"+
                        URLEncoder.encode("password", "UTF-8") +"="+URLEncoder.encode(password, "UTF-8") +"&"+
                        URLEncoder.encode("firstname", "UTF-8") +"="+URLEncoder.encode(firstname, "UTF-8") +"&"+
                        URLEncoder.encode("lastname", "UTF-8") +"="+URLEncoder.encode(lastname, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                OS.close();
                InputStream IS = httpURLConnection.getInputStream();
                IS.close();
                return "Registration Successful";
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if (method.equals("login")){
            String username = params[1];
            String password = params[2];
            try {
                URL url = new URL(loginURL);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setDoInput(true);
                OutputStream outputStream = httpURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                String data = URLEncoder.encode("username", "UTF-8") +"="+URLEncoder.encode(username, "UTF-8")+"&"+
                        URLEncoder.encode("password", "UTF-8") +"="+URLEncoder.encode(password, "UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String response = "";
                String line = "";
                while((line = bufferedReader.readLine()) != null){
                    response+= line;
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return response;

            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(String result) {

        if(result.equals("Registration Successful")){
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }
        else if(result.equals("Login Failed")){
            alertDialog.setMessage(result);
            alertDialog.show();
        }
        else if(result.equals("Login Successful")){
            Toast.makeText(context, result, Toast.LENGTH_LONG).show();
        }

    }

}
