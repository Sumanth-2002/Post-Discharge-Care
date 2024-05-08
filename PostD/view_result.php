<?php
require "conn.php";
$id = $_GET['id'];

// Fetch data from the database
$sql = "SELECT * FROM questionnaire_response where id='$id'";
$result = $conn->query($sql);

$response_data = array();

// Check if there are rows in the result
if ($result->num_rows > 0) {
    while ($row = $result->fetch_assoc()) {
        $date = $row['date'];

        // Create a new entry for each date if it doesn't exist
        if (!isset($response_data[$date])) {
            $response_data[$date] = array(
                'date' => $date,
                'responses' => array()
            );
        }

        // Add responses to the corresponding date
        $response_data[$date]['responses'][] = array(
            'id' => $row['id'],
            'response1' => $row['response1'],
            'response2' => $row['response2'],
            'response3' => $row['response3'],
            'response4' => $row['response4'],
            'response5' => $row['response5'],
            'response6' => $row['response6'],
            'response7' => $row['response7'],
            'response8' => $row['response8'],
            'response9' => $row['response9'],
            'response10' => $row['response10'],
            'response11' => $row['response11'],
            'text1' => $row['text1'],
            'text2' => $row['text2'],
            'text3' => $row['text3'],
            'text4' => $row['text4'],
            'text5' => $row['text5'],
            'text6' => $row['text6'],
            'text7' => $row['text7'],
            'text8' => $row['text8'],
            'text9' => $row['text9'],
            'text10' => $row['text10'],
            'text11' => $row['text11'],
        );
    }
}

// Close the database connection
$conn->close();

// Send JSON response
header('Content-Type: application/json');
echo json_encode(array_values($response_data));
?>
