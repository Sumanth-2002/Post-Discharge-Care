<?php
require("conn.php");
if($_SERVER["REQUEST_METHOD"] == "POST" ) {
    // Decode the JSON data
    $json = file_get_contents('php://input');
    $data1 = json_decode($json, true);
    if(isset($_POST["Id"]) && isset($_POST["password"])&& isset($_POST["Name"])&& isset($_POST["Gender"])
    && isset($_POST["Contact_No"])&& isset($_POST["Height"])&& isset($_POST["Weight"])&& isset($_POST["Parent_Name"])
    && isset($_POST["Admitted_On"])&& isset($_POST["Discharged_On"])&& isset($_POST["Profile_Pic"])&& isset($_POST["Date_of_Birth"])){
        $id = $_POST['Id'];
        $password = $_POST["password"];
       
$name = $_POST['Name'];
$contactNo = $_POST['Contact_No'];
$gender = $_POST['Gender'];
$dob = $_POST['Date_of_Birth'];
$height = $_POST['Height'];
$weight = $_POST['Weight'];
$parentName = $_POST['Parent_Name'];
$admittedOn = $_POST['Admitted_On'];
$dischargedOn = $_POST['Discharged_On'];
$profilePic = $_POST['Profile_Pic'];

// You can add more validation for the data if needed.
$sql1 = "insert into login values('$id','$password')";
mysqli_query($conn,$sql1);
// Insert data into the database
$sql2 = "INSERT INTO patient_details (Id, Name, Contact_No, Gender, Date_of_Birth, Height, Weight, Parent_Name, Admitted_On, Discharged_On, Profile_Pic)
VALUES ('$id', '$name', '$contactNo', '$gender', '$dob', '$height', '$weight', '$parentName', '$admittedOn', '$dischargedOn', '$profilePic')";
        if(mysqli_query($conn, $sql2)){
            $response = array('status' => 'success', 'message' => 'data inserted succesfully');
          echo json_encode($response);
    }
    else{
        $response = array('status'=> 'fail','message'=>"data not intrested");
    }
    
    }
}

?>