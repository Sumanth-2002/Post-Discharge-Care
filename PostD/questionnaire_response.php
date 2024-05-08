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
        $date = $data->date;
        $id = $data->id; // Replace 'id' with the actual name of the ID field in your database table
        $others = $data->others;

        // Insert 11 records
        for ($i = 1; $i <= 11; $i++) {
            // Assuming 'question1', 'question2', ..., 'question11'
            $question = $data->{'text' . $i};
            
            // Assuming 'response1', 'response2', ..., 'response11'
            $response = $data->{'response' . $i};

            // Your SQL query
            $sql = "INSERT INTO questionnaire_response (id, date, symptom, response) VALUES ('$id', '$date', '$question', '$response')";

            // Execute the query
            if ($conn->query($sql) !== TRUE) {
                $response_data['status'] = 'error';
                $response_data['message'] = 'Error inserting data: ' . $conn->error;
                break;  // Exit the loop if an error occurs
            }
        }

        // Insert record for "others"
        $sqlOthers = "INSERT INTO questionnaire_response (id, date, symptom, response) VALUES ('$id', '$date', 'others', '$others')";
        if ($conn->query($sqlOthers) !== TRUE) {
            $response_data['status'] = 'error';
            $response_data['message'] = 'Error inserting "others" data: ' . $conn->error;
        }

        if (!isset($response_data['status'])) {
            $response_data['status'] = 'Success';
            $response_data['message'] = 'Submitted successfully';
        }
        
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

// Include the original data in the response
$response_data['original_data'] = $data;

// Send JSON response
header('Content-Type: application/json');
echo json_encode($response_data);
?>
