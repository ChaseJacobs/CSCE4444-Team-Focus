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
	$follower = $_POST["follower"];
	$followee = $_POST["followee"];

	$query = mysqli_query($con, "SELECT * FROM friend_relationships WHERE follower='$follower' AND followee='$followee'");

	if(mysqli_num_rows($query) > 0){
		$response["success"] = true;
		echo json_encode($response);		
	}
	else{
		$response["success"] = false;
		echo json_encode($response);
	}	
	
	
	
	mysqli_close($con);

?>