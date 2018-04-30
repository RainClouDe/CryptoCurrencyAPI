<?php

//Connect to the database
$db = mysqli_connect('localhost', 'root', '', 'crypto');

//Recieve users the fiat currency.
$user_preference_fiat = $_GET['pref'];

mysqli_query($db, "UPDATE user_setting SET user_setting='".$user_preference_fiat."'");

echo "Fiat Currency updated.";

mysqli_close($db);

?>