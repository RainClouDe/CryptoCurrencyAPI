<?php

//Connect to the database
$db = mysqli_connect('localhost', 'root', '', 'crypto');

//Recieve the fiat currency.
$fiatcurrency = $_GET['fiat'];

//Get all the pairs.
$result1 =  mysqli_query($db, "SELECT * FROM pairs");

//Make the array that we will pass.
$response = array();

while($pair = mysqli_fetch_array($result1))
{
	//Check to see if the value_text of the pair contains the fiat that was passed
	if (strpos($pair['Value_Text'], $fiatcurrency) !== false) 
	{ //It does contain what we need.
    	
		//Get the latest data from the cryto_currency_stats table
    	$result2 = mysqli_query($db, "SELECT Y_COORD, Value_At_Time FROM crypto_currency_stats WHERE P_ID=".$pair['id']." ORDER BY Value_At_Time DESC LIMIT 1");

    	while($current_value = mysqli_fetch_array($result2))
		{

    	array_push($response, array("Fiat_Currency_Name" => $pair['Fiat_Currency_Name'],"CryptocurrencyName" => $pair['Cryptocurrency_name'], "time" => $current_value['Value_At_Time'] ,"value" => $current_value['Y_COORD'], "Exchange" => $pair['Exchange']));

		}



	}

}

echo json_encode(array("server_response" =>$response));

mysqli_close($db);

/*//Recieve the pair thats sent through the URL parameter.
$pair = $_GET['pair'];

//Get the pairs ID.
$result1 =  mysqli_query($db, "SELECT * FROM pairs WHERE Value_Text='".$pair."'");

$pair = mysqli_fetch_array($result1);

//Get the pairs latest value from the the crypto_currency_stats. 
$result2 = mysqli_query($db, "SELECT Y_COORD, Value_At_Time FROM crypto_currency_stats WHERE P_ID=".$pair['id']."");

$response = array();

while($current_value = mysqli_fetch_array($result2))
{
	array_push($response, array("Fiat_Currency_Name" => $pair['Fiat_Currency_Name'],"CryptocurrencyName" => $pair['Cryptocurrency_name'], "time" => $current_value['Value_At_Time'] ,"value" => $current_value['Y_COORD'], "Exchange" => $pair['Exchange']));
}

echo json_encode(array("server_response" =>$response));

mysqli_close($db);*/
?>