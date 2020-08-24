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
	$currentPassword = $_POST["currentpassword"];
	$password = $_POST["password"];
	
	$result = mysqli_query($con, "SELECT username FROM userinfo WHERE username= '$username' AND password= '$currentPassword'");
		
	if(mysqli_num_rows($result) > 0){	

		$statement = mysqli_prepare($con, "UPDATE `userinfo` SET `password`=? WHERE `username`=?");
		mysqli_stmt_bind_param($statement, "ss", $password, $username);
		mysqli_stmt_execute($statement);
		
		//send to app
		$response["success"] = true;
	}	
	else
		$response["success"] = false;
	
	echo json_encode($response);
	
	mysqli_close($con);

?>