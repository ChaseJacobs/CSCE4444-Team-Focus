<?php

	//connect to database
	$host = "mysql3.gear.host";
	$user = "adminworkout";
	$pass = "Password~";
	$db = "workoutwfriends";
	$con = mysqli_connect($host, $user, $pass, $db);

	//connection failure
	if(!$con){
		die('Count not connect: ' . mysql_error());
	}
	
	//user info variables
	$username = $_POST["username"];
	$customname = $_POST["customname"];
	$frequency = $_POST["frequency"];
	
	$response["success"] = false;
	
	//get custom workouts for requested user
	$statement = mysqli_prepare($con, "SELECT custom1name, custom2name, custom3name, custom4name, custom5name FROM stats_workout WHERE username=?");
	mysqli_stmt_bind_param($statement, "s", $username);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $custom1name, $custom2name, $custom3name, $custom4name, $custom5name);	
	mysqli_stmt_fetch($statement);
	
	if($customname == $custom1name){
			//update frequency for custom1
			$stmt = mysqli_prepare($con, "UPDATE stats_workout SET custom1frequency=? WHERE username=?");
			mysqli_stmt_bind_param($stmt, "ss", $frequency, $username);
			mysqli_stmt_execute($stmt);
			
			//send to app
			$response["success"] = true;
	}
	else if ($customname == $custom2name){
			//update frequency for custom2
			$stmt = mysqli_prepare($con, "UPDATE stats_workout SET custom2frequency=? WHERE username=?");
			mysqli_stmt_bind_param($stmt, "ss", $frequency, $username);
			mysqli_stmt_execute($stmt);
			
			//send to app
			$response["success"] = true;
	}
	else if ($customname == $custom3name){
			//update frequency for custom3
			$stmt = mysqli_prepare($con, "UPDATE stats_workout SET custom3frequency=? WHERE username=?");
			mysqli_stmt_bind_param($stmt, "ss", $frequency, $username);
			mysqli_stmt_execute($stmt);
			
			//send to app
			$response["success"] = true;
	}
	else if ($customname == $custom4name){
			//update frequency for custom4
			$stmt = mysqli_prepare($con, "UPDATE stats_workout SET custom4frequency=? WHERE username=?");
			mysqli_stmt_bind_param($stmt, "ss", $frequency, $username);
			mysqli_stmt_execute($stmt);
			
			//send to app
			$response["success"] = true;
	}
	else if($customname == $custom5name){
			//update frequency for custom5
			$stmt = mysqli_prepare($con, "UPDATE stats_workout SET custom5frequency=? WHERE username=?");
			mysqli_stmt_bind_param($stmt, "ss", $frequency, $username);
			mysqli_stmt_execute($stmt);
			
			//send to app
			$response["success"] = true;
	}
	
	echo json_encode($response);
	
	mysqli_close($con);

?>