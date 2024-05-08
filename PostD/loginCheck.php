<?php
require "conn.php";

// Check if required fields are present in the POST request
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $deviceName = isset($_POST['device_name']) ? $_POST['device_name'] : '';

    // Your SQL query to retrieve data from the table
    $query = "SELECT category, id FROM login_status WHERE device_name = '$deviceName' AND status = 'logged in'";

    // Execute the query
    $result = mysqli_query($conn, $query);

    if ($result) {
        // Check if any rows are returned
        if (mysqli_num_rows($result) > 0) {
            // Data retrieval successful
            $row = mysqli_fetch_assoc($result);
            $response = array('status' => 'Success', 'category' => $row['category'], 'id' => $row['id']);
        } else {
            // No matching records found
            $response = array('status' => 'Error', 'message' => 'No matching records found');
        }
    } else {
        // Failed to execute the query
        $response = array('status' => 'Error', 'message' => 'Failed to execute the query');
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
