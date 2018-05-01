package com.example.ivanw.cryptocurrencyapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

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

        //START Information buttons
            ImageButton informationBitcoin;
            ImageButton informationEthereum;
            ImageButton informationLitecoin;
        //END Information buttons

        //START Setting button
            Button settingsButton;
        //END Setting button

        //START Refresh button
            Button refreshButton;
        //END Refresh button

        //START increase, decrease indicators.
            ImageView increaseBitcoin;
            ImageView decreaseBitcoin;

            ImageView increaseEthereum;
            ImageView decreaseEthereum;

            ImageView increaseLitecoin;
            ImageView decreaseLitecoin;
        //END increase, decrease indicators.

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

        //START Assign information buttons.
            informationBitcoin = (ImageButton) findViewById(R.id.informationBitcoin);
            informationEthereum = (ImageButton) findViewById(R.id.informationEthereum);
            informationLitecoin = (ImageButton) findViewById(R.id.informationLitecoin);
        //START Assign information buttons.

        //START assign settings button
            settingsButton = (Button) findViewById(R.id.btnsettings);
        //END assign settings button

        //START assign settings button
            refreshButton = (Button) findViewById(R.id.btnrefresh);
        //END assign settings button

        //START assign indicator images.
        increaseBitcoin = (ImageView) findViewById(R.id.increaseBitcoin);
        decreaseBitcoin = (ImageView) findViewById(R.id.decreaseBitcoin);

        increaseEthereum = (ImageView) findViewById(R.id.increaseEthereum);
        decreaseEthereum = (ImageView) findViewById(R.id.decreaseEthereum);

        increaseLitecoin = (ImageView) findViewById(R.id.increaseLitecoin);
        decreaseLitecoin = (ImageView) findViewById(R.id.decreaseLitecoin);
        //END assign indicator images.

        //START onclick for bitcoin's information button
        informationBitcoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(1);
            }
        });
        //END onclick for bitcoin's information button

        //START onclick for ethereum's information button
        informationEthereum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(2);
            }
        });
        //END onclick for ethereum's information button

        //START onclick for ethereum's information button
        informationEthereum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(2);
            }
        });
        //END onclick for ethereum's information button

        //START onclick for ethereum's information button
        informationLitecoin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openDialog(3);
            }
        });
        //END onclick for ethereum's information button

        //START onclick for the settings button.
        settingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(myIntent);
            }
        });
        //END onclick for the settings button.

        //START onclick for the settings button.
        refreshButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFetchTask = new FetchCryptoValues();
                mFetchTask.execute();
            }
        });
        //END onclick for the settings button.
    }

    //Method used to open the dialog
    public void openDialog(int i)
    {
        Dialog dialog = new Dialog();

        //Assign dialog variables


        //Depending on the number passed, render out the correpsonding cryptocurrency information.
        switch (i)
        {
            case 1:
                dialog.setTitleBody("Bitcoin information", "Bitcoin is a new currency that was created in 2009 by an unknown person using the alias Satoshi Nakamoto. Transactions are made with no middle men – meaning, no banks! Bitcoin can be used to book hotels on Expedia, shop for furniture on Overstock and buy Xbox games. But much of the hype is about getting rich by trading it. The price of bitcoin skyrocketed into the thousands in 2017.");
                break;
            case 2:
                dialog.setTitleBody("Ethereum information","In many ways, Ethereum is similar to Bitcoin. It’s a public, peer-to-peer network or blockchain with its own digital currency called Ether. Ethereum was created by Vitalik Buterin in 2014 and the purpose of Ethereum is to be a platform on which smart contracts can be built and run.\n" +
                        "\n" +
                        "Put very simply, Ethereum is intended to be a world computer.");
                break;
            case 3:
                dialog.setTitleBody("Litecoin","Often referred to as the little brother of bitcoin, litecoin is a peer-to-peer cryptocurrency that has gained fairly widespread adoption since its inception in 2011. A form of digital money that utilizes a blockchain to easily maintain a public ledger of all transactions, litecoin is used to transfer funds directly between individuals or businesses without the need for an intermediary such as a bank or payment processing service.");
                break;
        }

        dialog.show(getSupportFragmentManager(), "Test");
    }



    @Override
    public void onStart(){
        super.onStart();

        //Set all the indicators to be invisible as we don't whether the currency went up or down.
        increaseBitcoin.setVisibility(View.INVISIBLE);;
        decreaseBitcoin.setVisibility(View.INVISIBLE);

        increaseEthereum.setVisibility(View.INVISIBLE);
        decreaseEthereum.setVisibility(View.INVISIBLE);

        increaseLitecoin.setVisibility(View.INVISIBLE);
        decreaseLitecoin.setVisibility(View.INVISIBLE);

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
                URL url = new URL("http://10.0.0.17/CryptoCurrencyAPI/get_pair_value.php");
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

                            //Change indicators accordingly.
                            if(json_data.getString("indicator").equals("up"))
                            {
                                increaseBitcoin.setVisibility(View.VISIBLE);
                            }
                            else if(json_data.getString("indicator").equals("down"))
                            {
                                decreaseBitcoin.setVisibility(View.VISIBLE);
                            }
                            break;

                        case "Ethereum":

                            ethereumTitle.setText(json_data.getString("CryptocurrencyName"));
                            ethereumValue.setText(json_data.getString("value") +" "+ json_data.getString("Fiat_Currency_Name"));
                            ethereumExchange.setText("Exchange: " + json_data.getString("Exchange"));
                            ethereumLastupdated.setText("Last updated: " + json_data.getString("time"));

                            if(json_data.getString("indicator").equals("up"))
                            {
                                increaseEthereum.setVisibility(View.VISIBLE);
                            }
                            else if(json_data.getString("indicator").equals("down"))
                            {
                                decreaseEthereum.setVisibility(View.VISIBLE);
                            }

                            break;

                        case "Litecoin":

                            litecoinTitle.setText(json_data.getString("CryptocurrencyName"));
                            litecoinValue.setText(json_data.getString("value") +" "+ json_data.getString("Fiat_Currency_Name"));
                            litecoinExchange.setText("Exchange: " + json_data.getString("Exchange"));
                            litecoinLastupdated.setText("Last updated: " + json_data.getString("time"));

                            if(json_data.getString("indicator").equals("up"))
                            {
                                increaseLitecoin.setVisibility(View.VISIBLE);
                            }
                            else if(json_data.getString("indicator").equals("down"))
                            {
                                decreaseLitecoin.setVisibility(View.VISIBLE);
                            }
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
