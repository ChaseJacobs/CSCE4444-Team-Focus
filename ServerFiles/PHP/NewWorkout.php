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
	
	
	$statement = mysqli_prepare($con, "SELECT custom1name, custom2name, custom3name, custom4name, custom5name FROM stats_workout WHERE username = ?");
	mysqli_stmt_bind_param($statement, "s", $username);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $custom1name, $custom2name, $custom3name, $custom4name,$custom5name);
	
	$response = array();
	
	mysqli_stmt_fetch($statement);
	
	if($custom1name == "NULL"){ //workout is able to be created
		$stmt1 = mysqli_prepare($con, "UPDATE stats_workout SET custom1name=? WHERE username=?");
		mysqli_stmt_bind_param($stmt1, "ss", $name, $username);
		mysqli_stmt_execute($stmt1);
	}
	else{ //check next custom workout availability
		if($custom2name == "NULL"){ //workout is able to be created
			$stmt2 = mysqli_prepare($con, "UPDATE stats_workout SET custom2name=? WHERE username=?");
			mysqli_stmt_bind_param($stmt2, "ss", $name, $username);
			mysqli_stmt_execute($stmt2);
		}
		else{ //check next custom workout availability
			if($custom3name == "NULL"){ //workout is able to be created
				$stmt3 = mysqli_prepare($con, "UPDATE stats_workout SET custom3name=? WHERE username=?");
				mysqli_stmt_bind_param($stmt3, "ss", $name, $username);
				mysqli_stmt_execute($stmt3);
			}
			else{ //check next custom workout availability
				if($custom4name == "NULL"){ //workout is able to be created
					$stmt4 = mysqli_prepare($con, "UPDATE stats_workout SET custom4name=? WHERE username=?");
					mysqli_stmt_bind_param($stmt4, "ss", $name, $username);
					mysqli_stmt_execute($stmt4);
				}
				else{ //check next custom workout availability
					if($custom5name == "NULL"){ //workout is able to be created
						$stmt5 = mysqli_prepare($con, "UPDATE stats_workout SET custom5name=? WHERE username=?");
						mysqli_stmt_bind_param($stmt5, "ss", $name, $username);
						mysqli_stmt_execute($stmt5);
					}
					else{ //custom workouts are full
						$response["success"] = false;
						echo json_encode($response);
						mysqli_close($con);
					}
				}
			}
		}
	}	

	$statement = mysqli_prepare($con, "INSERT INTO custom_workout (creator, name, exercise1, time1, exercise2, time2, exercise3, time3, exercise4, time4, 
		exercise5, time5, exercise6, time6) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
	mysqli_stmt_bind_param($statement, "ssssssssssssss", $username, $name, $exercise1, $time1, $exercise2, $time2, $exercise3, $time3, $exercise4, $time4, 
		$exercise5, $time5, $exercise6, $time6);
	mysqli_stmt_execute($statement);
	
	//send to app
	$response["success"] = true;
	
	echo json_encode($response);
	
	mysqli_close($con);

?>