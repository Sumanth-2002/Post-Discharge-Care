 <?php  
 include"connection.php";  
 $sql = "SELECT * FROM usersData";  
 $result = mysqli_query($con,$sql);  
 $data = array();  
 foreach($result as $item){  
   $id = $item['id'];  
   $name = $item['name'];  
   $images = $item['images'];  
   $userInfo['id'] = $id;  
   $userInfo['name'] = $name;  
   $userInfo['images'] = $images;  
   array_push($data,$userInfo);  
 }  
 echo json_encode($data);  
 ?>  