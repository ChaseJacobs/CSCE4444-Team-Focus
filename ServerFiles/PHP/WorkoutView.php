<?php

	//connect to database
	$host = "mysql3.gear.host";
	$user = "adminworkout";
	$pass = "Password~";
	$db = "workoutwfriends";
	$con = mysqli_connect($host, $user, $pass, $db);
	
	$username = $_POST["username"];
	$name = $_POST["name"];
	
	//find user
	$statement = mysqli_prepare($con, "SELECT * FROM custom_workout WHERE creator=? AND name=?");
	mysqli_stmt_bind_param($statement, "ss", $username, $name);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $id, $creator, $name, $exercise1, $time1, $exercise2, $time2, $exercise3, $time3, $exercise4, $time4,
							$exercise5, $time5, $exercise6, $time6);
	
	$response = array();
	$response["success"] = false;
	
	//send to app
	while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["username"] = $creator;
		$response["name"] = $name;
		$response["exercise1"] = $exercise1;
		$response["exercise2"] = $exercise2;
		$response["exercise3"] = $exercise3;
		$response["exercise4"] = $exercise4;
		$response["exercise5"] = $exercise5;
		$response["exercise6"] = $exercise6;
		$response["time1"] = $time1;
		$response["time2"] = $time2;
		$response["time3"] = $time3;
		$response["time4"] = $time4;
		$response["time5"] = $time5;
		$response["time6"] = $time6;
	}
	
	echo json_encode($response);
	
	mysqli_close($con);

?>