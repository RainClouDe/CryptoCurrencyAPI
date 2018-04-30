package com.example.ivanw.cryptocurrencyapp;

import android.content.Intent;
import android.media.Image;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class SettingsActivity extends AppCompatActivity {

    //Global variables

    //Save button
    Button SaveButton;

    //activity Spinner
    Spinner dropdown;

    //Back button
    ImageButton BackButton;

    //The return value from the script call
    String JSON_string="";

    private SavePreference mFetchTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        //get the spinner from the xml.
        dropdown = (Spinner) findViewById(R.id.fiatspinner);
        //create a list of items for the spinner.
        String[] items = new String[]{"usd", "eur", "zar"};
        //create an adapter to describe how the items are displayed, adapters are used in several places in android.
        //There are multiple variations of this, but this is the basic variant.
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        //set the spinners adapter to the previously created one.
        dropdown.setAdapter(adapter);

        SaveButton = (Button) findViewById(R.id.button);

        BackButton = (ImageButton) findViewById(R.id.imageButton);

        //START onclick for the settings button.
        SaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //Get the values from the select.

                //Execute the save function
                mFetchTask = new SavePreference();
                mFetchTask.execute();

            }
        });
        //END onclick for the settings button.

        //START onclick for the back button.
        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Get the values from the select.
                Intent myIntent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(myIntent);
            }
        });
        //END onclick for the back button.

    }

    class SavePreference extends AsyncTask<Void, Void, String> {

        private String  pair = "";
        StringBuilder stringBuilder = null;

        protected String doInBackground(Void...  voids)
        {
            try
            {
                URL url = new URL("http://10.0.0.17/CryptoCurrencyAPI/save_preference.php?pref="+dropdown.getSelectedItem().toString());
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();


                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                stringBuilder = new StringBuilder();

                while((JSON_string = bufferedReader.readLine()) != null)
                {
                    stringBuilder.append(JSON_string);
                }

                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();

                return stringBuilder.toString().trim();


            }
            catch(MalformedURLException e)
            {
                e.printStackTrace();
            }
            catch(IOException e)
            {
                e.printStackTrace();
            }



            return null;


        }

        protected void onPostExecute(String result)
        {
            super.onPostExecute(result);

            Dialog dialog = new Dialog();

            dialog.setTitleBody("Sucessful", result);

            dialog.show(getSupportFragmentManager(), "Test");

        }
    }
}
