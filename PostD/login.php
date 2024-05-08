<?php
require "conn.php";

// Check if the request method is POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get form data
    $id = isset($_POST['id']) ? trim(mysqli_real_escape_string($conn, $_POST['id'])) : '';
    $password = isset($_POST['password']) ? trim(mysqli_real_escape_string($conn, $_POST['password'])) : '';
    $device = isset($_POST['device_name']) ? $_POST['device_name'] : '';

    // Query to check if the provided 'id' and 'password' exist in the login table
    $sql = "SELECT * FROM login WHERE id = '$id' AND password = '$password'";
    $result = mysqli_query($conn, $sql);

    if ($result) {
        // Check if any rows are returned
        if (mysqli_num_rows($result) > 0) {
            // Fetch the user ID
            $row = mysqli_fetch_assoc($result);
            $userID = $row['id'];

            // Additional check based on your requirement
            $checkQuery = "SELECT * FROM login_status WHERE device_name = '$device'";
            $checkResult = mysqli_query($conn, $checkQuery);

            if ($checkResult && mysqli_num_rows($checkResult) > 0) {
                $updateQuery = "UPDATE login_status SET status = 'logged in', category = 'patient', id = '$id' WHERE device_name = '$device'";
        
                if(mysqli_query($conn, $updateQuery)){
                    $response = array('status' => 'success', 'id' => $userID);
                    echo json_encode($response);
                } else {
                    $response = array('status' => 'error');
                    echo json_encode($response);
                }
            } else {
                $insert = "INSERT INTO login_status (device_name, category, id) VALUES ('$device', 'patient', '$id')";
                if(mysqli_query($conn, $insert)){
                    $response = array('status' => 'success', 'id' => $id);
                    echo json_encode($response);
                } else {
                    $response = array('status' => 'error');
                    echo json_encode($response);
                }
            }
        } else {
            $response = array('status' => 'failure');
            echo json_encode($response);
        }
    } else {
        $response = array('status' => 'error');
        echo json_encode($response);
    }

    mysqli_free_result($result);
} else {
    $response = array('status' => 'error', 'message' => 'Invalid request method', 'id' => '');
    echo json_encode($response);
}

mysqli_close($conn);
?>
