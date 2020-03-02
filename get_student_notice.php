<?php 
	define('HOST','localhost');
	define('USER','sofmax_mahak');
	define('PASS','mukku@23');
	define('DB','softmax_db');
	 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
	if($_SERVER['REQUEST_METHOD']=='GET'){
	$school_name = $_GET['school'];
	$roll_num= $_GET['rollnum'];
	$phone_num = $_GET['mobile'];
	
		
	
	$query=mysqli_query($con,"SELECT * FROM notice where school_name='$school_name'");
	//$query=mysqli_query($con,"SELECT * FROM my_notice where student_id='$roll_num' and school_name='$school'and mobile='$phone_num' ");
	//$check = mysqli_fetch_array($query);
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