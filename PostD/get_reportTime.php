<?php
require "conn.php";

// Check if the request method is POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Assuming you have a table named 'report_time' with columns 'id', 'report_time', and 'report_date'
    $patientId = $_POST['id'];

    // Fetch all report times and dates for the given patient ID
    $sql = "SELECT report_time, date     FROM report_time WHERE id = ?";
    
    // Use a prepared statement to prevent SQL injection
    $stmt = $conn->prepare($sql);
    $stmt->bind_param("i", $patientId);
    $stmt->execute();

    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        // Output data of each row
        $reportData = array();
        while ($row = $result->fetch_assoc()) {
            $reportData[] = array(
                'report_time' => $row["report_time"],
                'report_date' => $row["date"]
            );
        }

        // Return the report data as a JSON-encoded string
        header('Content-Type: application/json');
        echo json_encode($reportData);
    } else {
        echo json_encode(array('error' => 'No data found for the given ID'));
    }

    $stmt->close();
} else {
    echo json_encode(array('error' => 'Invalid request method'));
}

$conn->close();
?>
