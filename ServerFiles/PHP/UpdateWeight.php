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
	$weight = $_POST["weight"];
	
	$response["success"] = false;
	

		$statement = mysqli_prepare($con, "UPDATE `userinfo` SET `weight`=? WHERE `username`=?");
		mysqli_stmt_bind_param($statement, "ss", $weight, $username);
		mysqli_stmt_execute($statement);
		mysqli_stmt_store_result($statement);
		
		$response["success"] = true;
	
	//send to app
	echo json_encode($response);
	
	mysqli_close($con);

?>