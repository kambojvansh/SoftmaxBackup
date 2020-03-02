<?php 
	define('HOST','localhost');
	define('USER','sofmax_mahak');
	define('PASS','mukku@23');
	define('DB','softmax_db');
	 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
	if($_SERVER['REQUEST_METHOD']=='GET'){
	$roll_num = $_POST['rollnum'];
		
	
	$query=mysqli_query($con,"SELECT * FROM attendence WHERE rollno='$roll_num'");
	if($query)
	{
		while($row=mysqli_fetch_array($query))
		{
			$flag[]=$row;
		}
		print(json_encode($flag));
	}
	mysqli_close($con);
	}