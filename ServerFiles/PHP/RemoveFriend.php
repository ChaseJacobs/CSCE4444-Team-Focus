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

	
	//variables
	$follower = $_POST["follower"];
	$followee = $_POST["followee"];


	
	$response["success"] = false;
	
	$statement = mysqli_prepare($con, "DELETE FROM friend_relationships WHERE followee=? AND follower=? ");
	mysqli_stmt_bind_param($statement, "ss", $followee, $follower);
	mysqli_stmt_execute($statement);
	
	$response["success"] = true;
	
	echo json_encode($response);
	
	mysqli_close($con);

?>