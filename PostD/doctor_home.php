<?php
require "conn.php";

// Check if raw POST data is provided
$inputJSON = file_get_contents('php://input');
$input = json_decode($inputJSON, true);

if (!empty($input['id'])) {
    $id = intval($input['id']);
    $query = "SELECT Name, doc_profile FROM doctor_login WHERE id = '$id'";
    $result = mysqli_query($conn, $query);

    if ($result) {
        while ($row = mysqli_fetch_assoc($result)) {
            $response = array(
                'Name' => $row['Name'],
                'Profile' => $row['doc_profile']
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
