<?php 
	define('HOST','localhost');
	define('USER','sofmax_mahak');
	define('PASS','mukku@23');
	define('DB','softmax_db');
	 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
	if($_SERVER['REQUEST_METHOD']=='GET'){
	$username = $_GET['email'];
		
	
	$query=mysqli_query($con,"SELECT Student_name FROM student_record WHERE mobile='$username'");
	//$query="SELECT * FROM attendence WHERE rollno='$roll_num'";
	//$check = mysqli_fetch_array($query);
	//$result = mysqli_query($query, $con) or die("Selection Query Failed !!!");
	if(mysqli_num_rows($query)>0)
	{
		while($row=mysqli_fetch_array($query))
		{
			$flag[]=$row;
		}
		print(json_encode($flag));
	}
	else
	{
		$flag[]="failure";
		print(json_encode($flag));
		//echo "failure";
		
	}
	
	mysqli_close($con);
	}