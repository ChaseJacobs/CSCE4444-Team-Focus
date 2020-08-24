<?

$name = $_POST["name"];
$image = $_POST["image"];

$decodedImage = base64_decode("$image");

$stmt = mysqli_prepare($con, "UPDATE custom_workout SET photoLink=?  WHERE username=?");
		mysqli_stmt_bind_param($stmt, "ss", $decodedImage, $name);
		mysqli_stmt_execute($stmt);


?>