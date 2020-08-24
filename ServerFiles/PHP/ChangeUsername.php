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
	
	
	//user info variables
	$username = $_POST["username"];
	$newusername = $_POST["newusername"];
		
	$query = mysqli_query($con, "SELECT * FROM userinfo WHERE username='$newusername'");

	if(mysqli_num_rows($query) > 0){ //new exists

		$response["success"] = false;
		$response["reason"] = "exists";
		echo json_encode($response);
		mysqli_close($con);
	}
	
	$query = mysqli_query($con, "SELECT * FROM userinfo WHERE username='$username'");

	if(mysqli_num_rows($query) == 0){ //old does not exist

		$response["success"] = false;
		$response["reason"] = "username not found";
		echo json_encode($response);
		mysqli_close($con);
	}
	else{ //update with new username
		$statement = mysqli_prepare($con, "UPDATE `userinfo` SET `username`=? WHERE `username`=?");
		mysqli_stmt_bind_param($statement, "ss", $newusername, $username);
		mysqli_stmt_execute($statement);
		
		//send to app
		$response["success"] = true;
		echo json_encode($response);
		mysqli_close($con);
	}

?>