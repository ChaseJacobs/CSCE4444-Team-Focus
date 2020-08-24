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

	$nill = "NULL";
	$zero = "0";

	
	$response["success"] = false;
	
	$statement = mysqli_prepare($con, "SELECT custom1name, custom2name, custom3name, custom4name, custom5name FROM stats_workout WHERE username = ?");
	mysqli_stmt_bind_param($statement, "s", $username);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $custom1name, $custom2name, $custom3name, $custom4name,$custom5name);
	
	mysqli_stmt_fetch($statement);
	
	if($custom1name == $name){ //workout is able to be created
		$stmt1 = mysqli_prepare($con, "UPDATE stats_workout SET custom1name=?, custom1frequency=? WHERE username=?");
		mysqli_stmt_bind_param($stmt1, "sss", $nill, $zero, $username);
		mysqli_stmt_execute($stmt1);
	}
	else{ //check next custom workout availability
		if($custom2name == $name){ //workout is able to be created
			$stmt2 = mysqli_prepare($con, "UPDATE stats_workout SET custom2name=?, custom2frequency=? WHERE username=?");
			mysqli_stmt_bind_param($stmt2, "sss", $nill, $zero, $username);
			mysqli_stmt_execute($stmt2);
		}
		else{ //check next custom workout availability
			if($custom3name == $name){ //workout is able to be created
				$stmt3 = mysqli_prepare($con, "UPDATE stats_workout SET custom3name=?, custom3frequency=? WHERE username=?");
				mysqli_stmt_bind_param($stmt3, "sss", $nill, $zero, $username);
				mysqli_stmt_execute($stmt3);
			}
			else{ //check next custom workout availability
				if($custom4name == $name){ //workout is able to be created
					$stmt4 = mysqli_prepare($con, "UPDATE stats_workout SET custom4name=?, custom4frequency=? WHERE username=?");
					mysqli_stmt_bind_param($stmt4, "sss", $nill, $zero, $username);
					mysqli_stmt_execute($stmt4);
				}
				else{ //check next custom workout availability
					if($custom5name == $name){ //workout is able to be created
						$stmt5 = mysqli_prepare($con, "UPDATE stats_workout SET custom5name=?, custom5frequency=? WHERE username=?");
						mysqli_stmt_bind_param($stmt5, "sss", $nill, $zero, $username);
						mysqli_stmt_execute($stmt5);
					}
					else{ //custom workout does not exist
						$response["success"] = false;
						echo json_encode($response);
						mysqli_close($con);
					}
				}
			}
		}
	}	
	
	$statement = mysqli_prepare($con, "DELETE FROM custom_workout WHERE creator=? AND name=? AND exercise1=? AND time1=? AND exercise2=? AND time2=? AND exercise3=? AND time3=? 
								AND exercise4=? AND time4=? AND exercise5=? AND time5=? AND exercise6=? AND time6=?");
	mysqli_stmt_bind_param($statement, "ssssssssssssss", $username, $name, $exercise1, $time1, $exercise2, $time2, $exercise3, $time3, $exercise4, $time4, $exercise5, $time5, $exercise6, $time6);
	mysqli_stmt_execute($statement);
	
	$response["success"] = true;
	
	echo json_encode($response);
	
	mysqli_close($con);

?>