<?php 
	define('HOST','localhost');
	define('USER','ofmax_mahak');
	define('PASS','mukku@23');
	define('DB','softmax_db');
	 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
	if($_SERVER['REQUEST_METHOD']=='POST'){
	$username = $_POST['email'];
		
	$password = $_POST['password'];
	$sql = "SELECT * FROM student_record WHERE phone='$mobile' AND password='$student_id'";
	
	$result = mysqli_query($con,$sql);
	$check = mysqli_fetch_array($result);
	
	if(isset($check))
	{
	echo "success";
	}
	else{
	echo "failure";
	}
		mysqli_close($con);
	}