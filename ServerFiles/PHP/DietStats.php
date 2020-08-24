<?php

	//connect to database
	$host = "mysql3.gear.host";
	$user = "adminworkout";
	$pass = "Password~";
	$db = "workoutwfriends";
	$con = mysqli_connect($host, $user, $pass, $db);
	
	$username = $_POST["username"];
	
	//find user
	$statement = mysqli_prepare($con, "SELECT * FROM stats_diet WHERE username = ?");
	mysqli_stmt_bind_param($statement, "s", $username);
	mysqli_stmt_execute($statement);
	
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $username, $Breakfast, $Lunch, $Dinner, $Snack, $daytotal);
	
	$response = array();
	$response["success"] = false;
	
	//send to app
	while(mysqli_stmt_fetch($statement)){
		$response["success"] = true;
		$response["username"] = $username;
		$response["Breakfast"] = $Breakfast;
		$response["Lunch"] = $Lunch;
		$response["Dinner"] = $Dinner;
		$response["Snack"] = $Snack;
	}
	
	echo json_encode($response);
	
	mysqli_close($con);

?>