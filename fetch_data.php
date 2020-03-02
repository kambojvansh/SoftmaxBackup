<?php 
	define('HOST','localhost');
	define('USER','sofmax_mahak');
	define('PASS','mukku@23');
	define('DB','softmax_db');
	 $con = mysqli_connect(HOST,USER,PASS,DB) or die('Unable to Connect');
	
	if(mysqli_connect_error($con))
	{
		echo "Faild to connect";
	}
	$query=mysqli_query($con,"SELECT * FROM student_record");
	if($query)
	{
		while($row=mysqli_fetch_array($query))
		{
			$flag[]=$row;
		}
		print(json_encode($flag));
	}
	mysqli_close($con);