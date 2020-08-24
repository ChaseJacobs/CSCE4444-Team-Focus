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
	$firstName = $_POST["firstname"];
	$lastName = $_POST["lastname"];
	$weight = $_POST["weight"];
	$height = $_POST["height"];
	$age = $_POST["age"];
	$bio = $_POST["bio"];
		
	
	

	$statement = mysqli_prepare($con, "UPDATE `userinfo` SET `firstName`=?, `lastName`=?, `weight`=?, `height`=?, `age`=?, `bio`=? WHERE `username`=?");
	mysqli_stmt_bind_param($statement, "sssssss", $firstName, $lastName, $weight, $height, $age, $bio, $username);
	echo mysqli_error($statement);
	mysqli_stmt_execute($statement);
	
	echo mysqli_error($statement);
	
	mysqli_stmt_store_result($statement);
	
	//send to app
	$response["success"] = true;
	
	echo json_encode($response);
	
	mysqli_close($con);

?>