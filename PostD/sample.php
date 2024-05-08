<?php
require("conn.php");

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $id = $_POST['Id'];
    $password=$_POST['password'];
    $name = $_POST['Name'];
    $contactNo = $_POST['Contact_No'];
    $gender = $_POST['Gender'];
    $dob = $_POST['Date_of_Birth'];
    $height = $_POST['Height'];
    $weight = $_POST['Weight'];
    $parentName = $_POST['Parent_Name'];
    $admittedOn = $_POST['Admitted_On'];
    $dischargeOn = $_POST['Discharge_On'];

    $profilePicName = ""; // Initialize to an empty string by default

    if (isset($_FILES['Profile_Pic']) && $_FILES['Profile_Pic']['size'] > 0) {
        $profilePicName = "profile_pics/$id.jpg"; // You can save it with the patient identifier

        if (move_uploaded_file($_FILES['Profile_Pic']['tmp_name'], $profilePicName)) {
            // File saved successfully
        } else {
            $profilePicName = ""; // Set it to an empty string if file saving fails
        }
    }
    $sql1="INSERT INTO LOGIN VALUES ('$id','$password')";
    mysqli_query($conn,$sql1);

    // Insert patient details into the database, including the profile picture
    $sql2 = "INSERT INTO patient_details (Id, Name, Contact_No, Gender, Date_of_Birth, Height, Weight, Parent_Name, Admitted_On, Discharge_On, Profile_Pic)
            VALUES ('$id', '$name', '$contactNo', '$gender', '$dob', '$height', '$weight', '$parentName', '$admittedOn', '$dischargeOn', '$profilePicName')";

    if (mysqli_query($conn, $sql2)) {
        $response = array('status' => 'success', 'message' => 'Data inserted successfully');
        echo json_encode($response);
    } else {
        $response = array('status' => 'failure', 'message' => 'Data not inserted');
        echo json_encode($response);
    }
}
?>
