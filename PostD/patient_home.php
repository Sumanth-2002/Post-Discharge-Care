<?php
require "conn.php";

// Check if raw POST data is provided
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, true);

if (!empty($input['id'])) {
    $id = ($input['id']);
    $query = "SELECT * FROM patient_details WHERE id = '$id'";
    $result = mysqli_query($conn, $query);

    if ($result) {
        while ($row = mysqli_fetch_assoc($result)) {
            $response = array(
                'Name' => $row['Name'],
                'Profile_Pic' => $row['Profile_Pic']
            );
            echo json_encode($response);
        }
    } else {
        $response = array('error' => 'Error fetching data');
        echo json_encode($response);
    }
}

mysqli_close($conn);
?>
