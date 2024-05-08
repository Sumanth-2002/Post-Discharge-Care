<?php
require "conn.php";

// Check if data is received
if ($_SERVER['REQUEST_METHOD'] === 'POST') {

    // Get JSON data from the request body
    $json_data = file_get_contents("php://input");

    // Decode JSON data
    $data = json_decode($json_data);

    // Check if data is decoded successfully
    if ($data !== null) {
        // Extract values
        $startDate = $data->startDate;
        $endDate = $data->endDate;
        $id = $data->id; // Replace 'id' with the actual name of the ID field in your database table

        // Your SQL query to retrieve distinct dates within the specified date range for the given ID
     
                     

        $sql = "SELECT * 
                FROM questionnaire_response 
                WHERE id = '$id' 
                AND date >= '$startDate' AND date <='$endDate'";

        $result = $conn->query($sql);

        if ($result !== false) {
            $response_data['status'] = 'Success';
            $response_data['message'] = 'Data retrieved successfully';

            // Fetch all the results and organize them in the desired format
            $response_data['data'] = array();
            while ($row = $result->fetch_assoc()) {
                $response_data['data'][] = $row;
            }

            // If you only want the first set of data, you can use $response_data['data'] = $response_data['data'][0];
        } else {
            $response_data['status'] = 'error';
            $response_data['message'] = 'Error executing query: ' . $conn->error;
        }

        // Close the database result set
        $result->close();
        
        // Close the database connection
        $conn->close();
    } else {
        $response_data['status'] = 'error';
        $response_data['message'] = 'Error decoding JSON data';
    }
} else {
    $response_data['status'] = 'error';
    $response_data['message'] = 'Invalid request method';
}

// Send JSON response with the desired format
header('Content-Type: application/json');
echo json_encode($response_data);
?>
