<?php
require "conn.php";

// Retrieve the JSON data from the request
$data = json_decode(file_get_contents("php://input"), true);

// Check if records exist for the current date
$currentDate = date("Y-m-d");
$id = isset($data['id']) ? $data['id'] : null;

if ($id !== null) {
    // Delete records that are not in the current date

        // Insert new records
        foreach ($data['data'] as $medicine) {
            $id = isset($medicine['id']) ? $medicine['id'] : null;
            $course_name = isset($medicine['course_name']) ? $medicine['course_name'] : null;
            $medicine_name = isset($medicine['medicine_name']) ? $medicine['medicine_name'] : null;
            $course_duration = isset($medicine['course_duration']) ? $medicine['course_duration'] : null;
            $frequency = isset($medicine['frequency']) ? $medicine['frequency'] : null;
            $guidelines = isset($medicine['guidelines']) ? $medicine['guidelines'] : null;

            if ($id !== null && $course_name !== null && $medicine_name !== null
                && $course_duration !== null && $frequency !== null && $guidelines !== null) {

                $sql = "INSERT INTO medicine (id, Course_Name, Medicine_Name, Duration, Frequency, Guidelines, Date)
                        VALUES ('$id', '$course_name', '$medicine_name', '$course_duration', '$frequency', '$guidelines', '$currentDate')";

                if ($conn->query($sql) === TRUE) {
                    $response = array("status" => "success");
                } else {
                    $response = array("status" => "error", "message" => $conn->error);
                }
            } else {
                $response = array("status" => "error", "message" => "Invalid data format");
                break; // Stop the loop if invalid data format is encountered
            }
        }
    }  else {
    $response = array("status" => "error", "message" => "Missing or invalid 'id' parameter");
}

// Return the response as JSON
header('Content-Type: application/json');
echo json_encode($response);

// Close the database connection
$conn->close();
?>
