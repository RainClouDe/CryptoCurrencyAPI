<?php

//Connect to the database
$db = mysqli_connect('localhost', 'root', '', 'crypto');


//Get the users preference for the fiat currency.
$result = mysqli_query($db, "SELECT user_setting FROM user_setting WHERE id=1");

$user_pref = mysqli_fetch_array($result);

//Get all the pairs.
$result1 =  mysqli_query($db, "SELECT * FROM pairs WHERE Fiat_Currency_Name='".strtoupper($user_pref['user_setting'])."'");

//Make the array that we will pass.
$response = array();

while($pair = mysqli_fetch_array($result1))
{
	//Check to see if the value_text of the pair contains the fiat that was passed

	    $result3 = mysqli_query($db, "SELECT Y_COORD, Value_At_Time FROM crypto_currency_stats WHERE P_ID=".$pair['id']." ORDER BY Value_At_Time DESC LIMIT 2");

	    $indicator = "";
	    $value1 = "";
	    $value2 = "";
	    $count = 0;
	    while($current_value = mysqli_fetch_array($result3))
		{

			if($count == 0)
			{
				$value1 = $current_value['Y_COORD'];
			}
			else
			{
				$value2 = $current_value['Y_COORD'];
			}
			
			$count++;
		}

		if($value1 <= $value2)
		{
			$indicator = "down";
		}
		else
		{
			$indicator = "up";
		}


		//Get the latest data from the cryto_currency_stats table
    	$result2 = mysqli_query($db, "SELECT Y_COORD, Value_At_Time FROM crypto_currency_stats WHERE P_ID=".$pair['id']." ORDER BY Value_At_Time DESC LIMIT 1");

    	while($current_value = mysqli_fetch_array($result2))
		{

    	array_push($response, array("Fiat_Currency_Name" => $pair['Fiat_Currency_Name'],"CryptocurrencyName" => $pair['Cryptocurrency_name'], "time" => $current_value['Value_At_Time'] ,"value" => $current_value['Y_COORD'], "Exchange" => $pair['Exchange'] , "indicator" => $indicator));

		}



	

}

echo json_encode(array("server_response" =>$response));

mysqli_close($db);

?>