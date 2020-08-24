<?php

	//connect to database
	$host = "mysql3.gear.host";
	$user = "adminworkout";
	$pass = "Password~";
	$db = "workoutwfriends";
	$con = mysqli_connect($host, $user, $pass, $db);
	
	$username = $_POST["username"];
	
	
	$statement = "SELECT follower FROM friend_relationships WHERE followee='$username'";
	
	$result = mysqli_query($con, $statement);
	
	$response = array();
	
	while($row = mysqli_fetch_array($result)){
		array_push($response, array("name"=>$row[0]));
	}
	
	echo json_encode(array("followersList"=>$response));
	
	mysqli_close($con);

?>