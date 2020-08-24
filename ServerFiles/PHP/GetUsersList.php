<?php

	//connect to database
	$host = "mysql3.gear.host";
	$user = "adminworkout";
	$pass = "Password~";
	$db = "workoutwfriends";
	$con = mysqli_connect($host, $user, $pass, $db);
	
	$username = $_POST["username"];
	
	
	$statement = "SELECT username FROM userinfo WHERE NOT username='$username'";
	
	$result = mysqli_query($con, $statement);
	
	$response = array();
	
	while($row = mysqli_fetch_array($result)){
		array_push($response, array("name"=>$row[0]));
	}
	
	echo json_encode(array("usersList"=>$response));
	
	mysqli_close($con);

?>