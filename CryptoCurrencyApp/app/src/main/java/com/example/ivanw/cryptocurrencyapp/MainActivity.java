package com.example.ivanw.cryptocurrencyapp;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import static java.lang.System.in;





public class MainActivity extends AppCompatActivity {

    //Global variables.

        //START Bitcoin screen elements
            TextView bitcoinTitle;
            TextView bitcoinValue;
            TextView bitcoinExchange;
            TextView bitcoinLastupdated;
        //END Bitcoin screen elements

        //START Ethereum screen elements
            TextView ethereumTitle;
            TextView ethereumValue;
            TextView ethereumExchange;
            TextView ethereumLastupdated;
        //END Ethereum screen elements

        //START Ethereum screen elements
            TextView litecoinTitle;
            TextView litecoinValue;
            TextView litecoinExchange;
            TextView litecoinLastupdated;
        //END Ethereum screen elements

    private FetchCryptoValues mFetchTask;
    String JSON_string;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //START Assign the variables for ethereum.
            bitcoinTitle = (TextView) findViewById(R.id.BitcoinTitle);
            bitcoinValue = (TextView) findViewById(R.id.BitcoinValue);
            bitcoinExchange = (TextView) findViewById(R.id.BitcoinExchange);
            bitcoinLastupdated = (TextView) findViewById(R.id.BitcoinLastUpdated);
        //END Assign the variables for ethereum.

        //START Assign the variables for bitcoin.
            ethereumTitle = (TextView) findViewById(R.id.EthereumTitle);
            ethereumValue = (TextView) findViewById(R.id.EthereumValue);
            ethereumExchange = (TextView) findViewById(R.id.EthereumExchange);
            ethereumLastupdated = (TextView) findViewById(R.id.EthereumLastUpdated);
        //End Assign the variables for bitcoin.

        //START Assign the variables for litecoin.
            litecoinTitle = (TextView) findViewById(R.id.LitecoinTitle);
            litecoinValue = (TextView) findViewById(R.id.LitecoinValue);
            litecoinExchange = (TextView) findViewById(R.id.LitecoinExchange);
            litecoinLastupdated = (TextView) findViewById(R.id.LitecoinLastUpdated);
        //END Assign the variables for litecoin.
    }


    @Override
    public void onStart(){
        super.onStart();

        mFetchTask = new FetchCryptoValues();
        mFetchTask.execute();


    }

    class FetchCryptoValues extends AsyncTask<Void, Void, String> {

        private String  pair = "";
        StringBuilder stringBuilder = null;

        protected String doInBackground(Void...  voids)
        {
            try
            {
                URL url = new URL("http://10.0.0.17/CryptoCurrencyAPI/get_pair_value.php?fiat=usd");
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
            try
            {
                JSONObject json = new JSONObject(result);
                JSONArray jArray = json.getJSONArray("server_response");
                //Go through each cryptocurrency and assign to the information to the text views.
                for(int i = 0;i < jArray.length();i++)
                {
                    JSONObject json_data = jArray.getJSONObject(i);

                    switch (json_data.getString("CryptocurrencyName"))
                    {
                        case "Bitcoin":

                            bitcoinTitle.setText(json_data.getString("CryptocurrencyName"));
                            bitcoinValue.setText(json_data.getString("value") +" "+ json_data.getString("Fiat_Currency_Name"));
                            bitcoinExchange.setText("Exchange: " + json_data.getString("Exchange"));
                            bitcoinLastupdated.setText("Last updated: " + json_data.getString("time"));

                            break;

                        case "Ethereum":

                            ethereumTitle.setText(json_data.getString("CryptocurrencyName"));
                            ethereumValue.setText(json_data.getString("value") +" "+ json_data.getString("Fiat_Currency_Name"));
                            ethereumExchange.setText("Exchange: " + json_data.getString("Exchange"));
                            ethereumLastupdated.setText("Last updated: " + json_data.getString("time"));

                            break;

                        case "Litecoin":

                            litecoinTitle.setText(json_data.getString("CryptocurrencyName"));
                            litecoinValue.setText(json_data.getString("value") +" "+ json_data.getString("Fiat_Currency_Name"));
                            litecoinExchange.setText("Exchange: " + json_data.getString("Exchange"));
                            litecoinLastupdated.setText("Last updated: " + json_data.getString("time"));


                            break;
                    }

                }

            }
            catch(JSONException e)
            {
                e.printStackTrace();
            }


        }
    }


}
