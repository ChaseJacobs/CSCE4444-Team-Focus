<?php

	//connect to database
	$host = "mysql3.gear.host";
	$user = "adminworkout";
	$pass = "Password~";
	$db = "workoutwfriends";
	$con = mysqli_connect($host, $user, $pass, $db);
	
	$username = $_POST["username"];
	$password = $_POST["password"];

//	$username = "bhastings";
//	$password = "pass";
	
	$result = mysqli_query($con, "SELECT username FROM userinfo WHERE username= '$username' AND password= '$password'");
	
	
	if(mysqli_num_rows($result) > 0){
		
		
		$statement = mysqli_prepare($con, "SELECT * FROM userinfo WHERE username = ?");
		mysqli_stmt_bind_param($statement, "s", $username);
		mysqli_stmt_execute($statement);
	
		mysqli_stmt_store_result($statement);
		mysqli_stmt_bind_result($statement, $username, $password, $firstName, $lastName, $weight, $goalWeight, $height, $age, $bio, $photoLink);
	
		$response = array();
		$response["success"] = false;
	
		while(mysqli_stmt_fetch($statement)){
			$response["success"] = true;
			$response["username"] = $username;
		}	
	
		echo json_encode($response);			
	}
	else {
			$response["success"] = false;
			$response["username"] = "null";
			echo json_encode($response);
	}
	mysqli_close($con);

?>