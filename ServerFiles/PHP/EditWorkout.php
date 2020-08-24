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
	$name = $_POST["name"];
	$newname = $_POST["newname"];
	$exercise1 = $_POST["exercise1"];
	$exercise2 = $_POST["exercise2"];
	$exercise3 = $_POST["exercise3"];
	$exercise4 = $_POST["exercise4"];
	$exercise5 = $_POST["exercise5"];
	$exercise6 = $_POST["exercise6"];
	$time1 = $_POST["time1"];
	$time2 = $_POST["time2"];
	$time3 = $_POST["time3"];
	$time4 = $_POST["time4"];
	$time5 = $_POST["time5"];
	$time6 = $_POST["time6"];
	
	if($time1 == NULL){
		$time1 = 0;
	}
	
	if($time2 == NULL){
		$time2 = 0;
	}
	
	if($time3 == NULL){
		$time3 = 0;
	}
	
	if($time4 == NULL){
		$time4 = 0;
	}
	
	if($time5 == NULL){
		$time5 = 0;
	}
	
	if($time6 == NULL){
		$time6 = 0;
	}
	
	$creator = $username;

	$stmt = mysqli_prepare($con, "UPDATE custom_workout SET name=?, exercise1=?, time1=?, exercise2=?, time2=?, exercise3=?, time3=?, exercise4=?, time4=?,
							exercise5=?, time5=?, exercise6=?, time6=?  WHERE creator=? AND name=?");
	mysqli_stmt_bind_param($stmt, "sssssssssssssss", $newname, $exercise1, $time1, $exercise2, $time2, $exercise3, $time3, $exercise4, $time4,
							$exercise5, $time5, $exercise6, $time6, $username, $name);
	
	mysqli_stmt_execute($stmt);
	
	//send to app
	$response["success"] = true;
	
	echo json_encode($response);
	
	mysqli_close($con);

?>