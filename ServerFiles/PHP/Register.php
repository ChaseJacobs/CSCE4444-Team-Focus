<?php

//connect to database
	$host = "mysql3.gear.host";
	$user = "adminworkout";
	$pass = "Password~";
	$db = "workoutwfriends";
	$con = mysqli_connect($host, $user, $pass, $db);

	if(!$con){
		die('Count not connect: ' . mysql_error());
	}
	
//send data to database
	
	//user info variables
	$username = $_POST["username"];
	$password = $_POST["password"];
	$firstName = $_POST["firstname"];
	$lastName = $_POST["lastname"];
	$weight = "NULL";
	$goalWeight = "NULL";
	$height = "NULL";
	$age = "NULL";
	$photoLink = "NULL";
	
	//diet stats variables
	$breakfasttotal = "0";
	$lunchtotal = "0";
	$dinnertotal = "0";
	$snacktotal = "0";
	$daytotal = "0";
	
	//workout stats variables
	$bestdistance = "0";
	$besttime = "0";
	$custom1frequency = "0";
	$custom1name = "NULL";
	$custom2frequency = "0";
	$custom2name = "NULL";
	$custom3frequency = "0";
	$custom3name = "NULL";
	$custom4frequency = "0";
	$custom4name = "NULL";
	$custom5frequency = "0";
	$custom5name = "NULL";
	$default1frequency = "0";
	$default2frequency = "0";
	$default3frequency = "0";

		
		
	$query = mysqli_query($con, "SELECT * FROM userinfo WHERE username='$username'");

if(mysqli_num_rows($query) > 0){

    $response["success"] = false;
	echo json_encode($response);
	
}
	else {
		//initalize user info table
		$user_info_statement = mysqli_prepare($con, "INSERT INTO userinfo (username, password, firstName, lastName, weight, goalWeight, height, age, photoLink) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)");
		mysqli_stmt_bind_param($user_info_statement, "ssssiiiis", $username, $password, $firstName, $lastName, $weight, $goalWeight, $height, $age, $photoLink);
		mysqli_stmt_execute($user_info_statement);
		
		//initalize user in diet stats table
		$stats_diet_statement = mysqli_prepare($con, "INSERT INTO stats_diet VALUES (?, ?, ?, ?, ?, ?)");
		mysqli_stmt_bind_param($stats_diet_statement, "siiiii", $username, $breakfasttotal, $lunchtotal, $dinnertotal, $snacktotal, $daytotal);
		mysqli_stmt_execute($stats_diet_statement);
		
		//initalize user in workout stats table
		$stats_workout_statement = mysqli_prepare($con, "INSERT INTO stats_workout VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
		mysqli_stmt_bind_param($stats_workout_statement, "sissisisisisiiii", $username, $bestdistance, $besttime, $custom1name, $custom1frequency, $custom2name, $custom2frequency, $custom3name, $custom3frequency, $custom4name, $custom4frequency, $custom5name, $custom5frequency, $default1frequency, $default2frequency, $default3frequency);
		mysqli_stmt_execute($stats_workout_statement);

		$response["success"] = true;
		echo json_encode($response);
	}
	
	mysqli_close($con);

?>