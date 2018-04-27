<?php

//Connect to the database
$db = mysqli_connect('localhost', 'root', '', 'crypto');

//Recieve the pair thats sent through the URL parameter.
$pair = $_GET['pair'];

//Get the pairs ID.
$result1 =  mysqli_query($db, "SELECT id FROM pairs WHERE Value_Text='".$pair."'");

$pair_id = mysqli_fetch_array($result1);

//Get the pairs latest value from the the crypto_currency_stats. 
$result2 = mysqli_query($db, "SELECT Y_COORD FROM crypto_currency_stats WHERE P_ID=".$pair_id['id']."");

$current_value = mysqli_fetch_array($result2);

echo $current_value['Y_COORD'];
?>