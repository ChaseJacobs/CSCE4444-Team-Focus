<?php

	//connect to database
	$host = "mysql3.gear.host";
	$user = "adminworkout";
	$pass = "Password~";
	$db = "workoutwfriends";
	$con = mysqli_connect($host, $user, $pass, $db);
	
	$username = $_POST["username"];
	$name = $_POST["name"];

	
	$response = array();
	$response["success"] = false;
	
	//find user's weight
	$statement = mysqli_prepare($con, "SELECT weight FROM userinfo WHERE username=?");
	mysqli_stmt_bind_param($statement, "s", $username);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $user_weight);
	
	mysqli_stmt_fetch($statement);
	
	$response["weight"] = $user_weight;
	
	//find frequency
	$statement2 = mysqli_prepare($con, "SELECT custom1name, custom1frequency, custom2name, custom2frequency, custom3name, custom3frequency, custom4name, custom4frequency, custom5name, custom5frequency FROM stats_workout WHERE username=?");
	mysqli_stmt_bind_param($statement2, "s", $username);
	mysqli_stmt_execute($statement2);
	mysqli_stmt_store_result($statement2);
	mysqli_stmt_bind_result($statement2, $custom1name, $custom1frequency, $custom2name, $custom2frequency, $custom3name, $custom3frequency, $custom4name, $custom4frequency, $custom5name, $custom5frequency);
	
	mysqli_stmt_fetch($statement2);
	
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
	
	//find exercises
	$statement1 = mysqli_prepare($con, "SELECT * FROM custom_workout WHERE creator=? AND name=?");
	mysqli_stmt_bind_param($statement1, "ss", $username, $name);
	mysqli_stmt_execute($statement1);
	mysqli_stmt_store_result($statement1);
	mysqli_stmt_bind_result($statement1, $id, $creator, $name2, $exercise1, $time1, $exercise2, $time2, $exercise3, $time3, $exercise4, $time4, $exercise5, $time5, $exercise6, $time6);
	mysqli_stmt_fetch($statement1);
	
	$response["time1"] = $time1;
	$response["time2"] = $time2;
	$response["time3"] = $time3;
	$response["time4"] = $time4;
	$response["time5"] = $time5;
	$response["time6"] = $time6;
	
	//find calories1
	$find_calories1 = mysqli_prepare($con, "SELECT calories FROM exercises WHERE name = ?");
	mysqli_stmt_bind_param($find_calories1, "s", $exercise1);
	mysqli_stmt_execute($find_calories1);
	mysqli_stmt_store_result($find_calories1);
	mysqli_stmt_bind_result($find_calories1, $calories1);
	mysqli_stmt_fetch($find_calories1);
	$response["calories1"] = $calories1;

	
	//find calories2
	$find_calories2 = mysqli_prepare($con, "SELECT calories FROM exercises WHERE name = ?");
	mysqli_stmt_bind_param($find_calories2, "s", $exercise2);
	mysqli_stmt_execute($find_calories2);
	mysqli_stmt_store_result($find_calories2);
	mysqli_stmt_bind_result($find_calories2, $calories2);
	mysqli_stmt_fetch($find_calories2);
	$response["calories2"] = $calories2;
	
	
	//find calories3
	$find_calories3 = mysqli_prepare($con, "SELECT calories FROM exercises WHERE name = ?");
	mysqli_stmt_bind_param($find_calories3, "s", $exercise3);
	mysqli_stmt_execute($find_calories3);
	mysqli_stmt_store_result($find_calories3);
	mysqli_stmt_bind_result($find_calories3, $calories3);
	mysqli_stmt_fetch($find_calories3);
	$response["calories3"] = $calories3;
	
	//find calories4
	$find_calories4 = mysqli_prepare($con, "SELECT calories FROM exercises WHERE name = ?");
	mysqli_stmt_bind_param($find_calories4, "s", $exercise4);
	mysqli_stmt_execute($find_calories4);
	mysqli_stmt_store_result($find_calories4);
	mysqli_stmt_bind_result($find_calories4, $calories4);
	mysqli_stmt_fetch($find_calories4);
	$response["calories4"] = $calories4;
	
	//find calories5
	$find_calories5 = mysqli_prepare($con, "SELECT calories FROM exercises WHERE name = ?");
	mysqli_stmt_bind_param($find_calories5, "s", $exercise5);
	mysqli_stmt_execute($find_calories5);
	mysqli_stmt_store_result($find_calories5);
	mysqli_stmt_bind_result($find_calories5, $calories5);
	mysqli_stmt_fetch($find_calories5);
	$response["calories5"] = $calories5;
	
	//find calories6
	$find_calories6 = mysqli_prepare($con, "SELECT calories FROM exercises WHERE name = ?");
	mysqli_stmt_bind_param($find_calories6, "s", $exercise6);
	mysqli_stmt_execute($find_calories6);
	mysqli_stmt_store_result($find_calories6);
	mysqli_stmt_bind_result($find_calories6, $calories6);
	mysqli_stmt_fetch($find_calories6);
	$response["calories6"] = $calories6;
	
	$response["success"] = true;
	
	echo json_encode($response);
	
	mysqli_close($con);

?>