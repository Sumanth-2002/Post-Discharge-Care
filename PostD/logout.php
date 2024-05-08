<?php
require "conn.php";

// Check if required fields are present in the POST request
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $deviceName = isset($_POST['device_name']) ? $_POST['device_name'] : '';

    // Your SQL query to update the status in the login_status table
    $updateQuery = "UPDATE login_status SET status = 'logged out' WHERE device_name = '$deviceName'";

    // Execute the query
    $result = mysqli_query($conn, $updateQuery);

    if ($result) {
        // Update successful
        $response = array('status' => 'Success', 'message' => 'Status updated to logged out');
    } else {
        // Failed to execute the query
        $response = array('status' => 'Error', 'message' => 'Failed to update status');
    }
} else {
    // Missing required fields
    $response = array('status' => 'Error', 'message' => 'Missing required fields');
}

// Return the response as JSON
echo json_encode($response);

// Close the database connection
mysqli_close($conn);
?>
