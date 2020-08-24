<?php

	//connect to database
	$host = "mysql3.gear.host";
	$user = "adminworkout";
	$pass = "Password~";
	$db = "workoutwfriends";
	$con = mysqli_connect($host, $user, $pass, $db);
	
	$username = $_POST["username"];
	
//	$statement = "SELECT username FROM userinfo WHERE username= '$username'";
	
	//find user
	$statement = mysqli_prepare($con, "SELECT * FROM userinfo WHERE username = ?");
	mysqli_stmt_bind_param($statement, "s", $username);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $username, $password, $firstName, $lastName, $weight, $goalWeight, $height, $age, $bio, $photoLink);
	
	$response = array();
	$response["success"] = false;
	
	//send to app
	while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["username"] = $username;
		$response["firstname"] = $firstName;
		$response["lastname"] = $lastName;
		$response["age"] = $age;
		$response["weight"] = $weight;
		$response["height"] = $height;
		$response["bio"] = $bio;
	}
	
	echo json_encode($response);
	
	mysqli_close($con);

?>