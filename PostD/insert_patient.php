<?php
require("conn.php");

if (!empty($_POST['Id']) && !empty($_POST['password']) && !empty($_POST['Name']) && !empty($_POST['Gender']) && !empty($_POST['Contact_No']) && !empty($_POST['Date_of_Birth']) && !empty($_POST['Parent_Name']) && !empty($_POST['Height']) && !empty($_POST['Weight']) && !empty($_POST['Admitted_On']) && !empty($_POST['Discharge_On']) && !empty($_POST['Profile_pic'])) {
    $id = $_POST['Id'];
    $password = $_POST['password'];
    $name = $_POST['Name'];
    $gender = $_POST['Gender'];
    $contactNo = $_POST['Contact_No'];
    $dateOfBirth = $_POST['Date_of_Birth'];
    $parentName = $_POST['Parent_Name'];
    $height = $_POST['Height'];
    $weight = $_POST['Weight'];
    $admittedOn = $_POST['Admitted_On'];
    $dischargeOn = $_POST['Discharge_On'];

    // Retrieve old image path from the database
    $q1 = "SELECT Profile_pic FROM patient_details WHERE id='$id'";
    $result1 = mysqli_query($conn, $q1);
    $oldImagePath = '';
    if ($row = mysqli_fetch_assoc($result1)) {
        $oldImagePath = $row['Profile_pic'];
    }
    
    // Generate new image path
    $imagePath = 'profile_pics/' . uniqid() . '.jpg';

    // Save the new image and update the database
    if (file_put_contents($imagePath, base64_decode($_POST['Profile_pic']))) {
        // Delete the old image
        if (!empty($oldImagePath) && file_exists($oldImagePath)) {
            unlink($oldImagePath);
        }

        if (mysqli_num_rows($result1) === 0) {
            // Insert new record
            $sql1 = "INSERT INTO login (Id, password) VALUES ('$id', '$password')";
            $sql2 = "INSERT INTO patient_details (Id, Name, Contact_No, Gender, Date_of_Birth, Parent_Name, Height, Weight, Admitted_On, Discharge_On, Profile_pic)
                VALUES ('$id', '$name', '$contactNo', '$gender', '$dateOfBirth', '$parentName', '$height', '$weight', '$admittedOn', '$dischargeOn', '$imagePath')";

            if (mysqli_query($conn, $sql1) && mysqli_query($conn, $sql2)) {
                echo 'Success';
            } else {
                echo 'Failed to insert';
            }
        } else {
            // Update existing record
            $sql3 = "UPDATE patient_details SET Height='$height', Weight='$weight', Admitted_On='$admittedOn', Discharge_On='$dischargeOn', Profile_pic='$imagePath' WHERE Id='$id'";
            mysqli_query($conn, $sql3);
            echo 'Success';
        }
    } else {
        echo 'Failed to save the image';
    }
} else {
    echo 'Missing parameters';
}
?>
