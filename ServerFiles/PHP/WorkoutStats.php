<?php

	//connect to database
	$host = "mysql3.gear.host";
	$user = "adminworkout";
	$pass = "Password~";
	$db = "workoutwfriends";
	$con = mysqli_connect($host, $user, $pass, $db);
	
	$username = $_POST["username"];
	
	
	//find user
	$statement = mysqli_prepare($con, "SELECT bestdistance, besttime, custom1name, custom1frequency, custom2name, custom2frequency, custom3name, custom3frequency, custom4name, custom4frequency, custom5name, custom5frequency FROM stats_workout WHERE username = ?");
	mysqli_stmt_bind_param($statement, "s", $username);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $bestdistance, $besttime, $custom1name, $custom1frequency, $custom2name, $custom2frequency, $custom3name, $custom3frequency, $custom4name, $custom4frequency, $custom5name, $custom5frequency);
	
	$response = array();
	$response["success"] = false;
	
	//send to app
	while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["bestdistance"] = $bestdistance;
		$response["besttime"] = $besttime;
		$response["custom1name"] = $custom1name;
		$response["custom1frequency"] = $custom1frequency;
		$response["custom2name"] = $custom2name;
		$response["custom2frequency"] = $custom2frequency;
		$response["custom3name"] = $custom3name;
		$response["custom3frequency"] = $custom3frequency;
		$response["custom4name"] = $custom4name;
		$response["custom4frequency"] = $custom4frequency;
		$response["custom5name"] = $custom5name;
		$response["custom5frequency"] = $custom5frequency;
	}
	
	echo json_encode($response);
	
	mysqli_close($con);

?>