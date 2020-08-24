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
	$meal = $_POST["meal"];
	$caloriesEntered = $_POST["caloriesEntered"];
	
	$response["success"] = false;
	
	if($meal == "Breakfast"){
		$statement = mysqli_prepare($con, "UPDATE `stats_diet` SET `Breakfast`=? WHERE `username`=?");
		mysqli_stmt_bind_param($statement, "ss", $caloriesEntered, $username);
		mysqli_stmt_execute($statement);
		mysqli_stmt_store_result($statement);
		
		$response["success"] = true;
	}
	else if($meal == "Lunch"){
		$statement = mysqli_prepare($con, "UPDATE `stats_diet` SET `Lunch`=? WHERE `username`=?");
		mysqli_stmt_bind_param($statement, "ss", $caloriesEntered, $username);
		mysqli_stmt_execute($statement);
		mysqli_stmt_store_result($statement);
		
		$response["success"] = true;
	}
	else if($meal == "Dinner"){
		$statement = mysqli_prepare($con, "UPDATE `stats_diet` SET `Dinner`=? WHERE `username`=?");
		mysqli_stmt_bind_param($statement, "ss", $caloriesEntered, $username);
		mysqli_stmt_execute($statement);
		mysqli_stmt_store_result($statement);
		
		$response["success"] = true;
	}
	else if($meal == "Snack"){
		$statement = mysqli_prepare($con, "UPDATE `stats_diet` SET `Snack`=? WHERE `username`=?");
		mysqli_stmt_bind_param($statement, "ss", $caloriesEntered, $username);
		mysqli_stmt_execute($statement);
		mysqli_stmt_store_result($statement);
		
		$response["success"] = true;
	}
	
	//send to app
//	$response["success"] = true;
	echo json_encode($response);
	
	mysqli_close($con);

?>