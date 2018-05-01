<?php

//Namespace for the  ice library.
namespace ice3x\ice3x_v2_php;

//Include all the ice3x API functions
include('../includes/ice3x_functions.php');

//Setting up the API class
$iceApi = new iceApi("YSOBNSZSSDXLBP5TIQCC10VJVJAOICIE6K1ULYQNTZPBWPXBNKZVQDZXM2MBJPUP","lehqenephfloev1shylxiphbifdvjayehxbw0auf7084uz22f04ulk4r6hhmalol");

//Get the latest data from the ice3x exchange.
$ice3xarray = $iceApi->getMarketDepthBtcav();

//In the this file the cryptocurrency values will be updated using the cryptowatch API.

//Connect to the database
$db = mysqli_connect('localhost', 'root', '', 'crypto');

//Get all the pairs from the pairs table.
$pairs = mysqli_query($db ,"SELECT * FROM pairs");

if(mysqli_num_rows($pairs) == 0) // The query is empty. 
{
	echo "Error reading from database.";
	die;
}

else // Successful connection
{
	$arrayToWriteToDatabase = array();
	while($array = mysqli_fetch_array($pairs))
	{
		
		//If the exchange is empty it means that we have to use the other API to get the data.
		if($array['Exchange']!="ice3x") 
		{

			
			$test = file_get_contents("https://api.cryptowat.ch/markets/".$array['Exchange']."/".$array['Value_Text']."/price");

			$arraydata = json_decode($test, true);

			//Push all the new prices to an array to later be written into the database.
			array_push($arrayToWriteToDatabase, $array[0].",".$arraydata['result']['price'].",".$array['id']);
		}
		//We know  at this point that we will looking at the ice exchange for  the remining values.
		else
		{
			//Insert the remaining data that does'nt go through Cryptowatch.
			if($array['Value_Text']=="ethzar")
			{
				
				array_push($arrayToWriteToDatabase, $array[0].",".(int)$ice3xarray['response']['entities'][3]['last_price'].",".$array['id']);
			}else
			{
				array_push($arrayToWriteToDatabase, $array[0].",".(int)$ice3xarray['response']['entities'][2]['last_price'].",".$array['id']);
			}
			
		}
	}

	//Now that array is filled with all the new values copy them to the datbase
	for ($x = 0; $x < sizeof($arrayToWriteToDatabase); $x++) 
	{
    	
    	$pieces = explode(",", $arrayToWriteToDatabase[$x]);

    
    	mysqli_query($db ,"INSERT INTO crypto_currency_stats (X_COORD, Y_COORD, Value_At_Time, P_ID)
		VALUES ('".($x+1)."', '".$pieces[1]."', NOW(), '".$pieces[2]."')");


	}
	
}

?>