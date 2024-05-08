<?php
require "conn.php";

$response = array();

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    // Retrieve patient ID from the POST request
    $patientId = $_POST["id"];

    // Fetch patient details from the database
    $selectQuery = "SELECT ds.*,cf.*,hi.*,dh.*,ih.*,vi.*, td.*, ci.* FROM discharge_summary ds
                    LEFT JOIN vitals_at_admission vi ON vi.id=ds.id
                    LEFT JOIN immunization_history ih ON ih.id=ds.id
                    LEFT JOIN development_history dh ON dh.id=ds.id
                    LEFT JOIN chief_complaints cf ON cf.id=ds.id
                    LEFT JOIN history_illness hi ON ds.id=hi.id
                    LEFT JOIN treatment td ON ds.id = td.id
                    LEFT JOIN course_dis ci ON ds.id = ci.id
                    WHERE ds.id = ?";
    
    // Prepare the statement
    $stmt = $conn->prepare($selectQuery);
    $stmt->bind_param("s", $patientId);

    // Execute the statement
    $stmt->execute();

    // Get the result
    $result = $stmt->get_result();

    if ($result->num_rows > 0) {
        // Output patient details as JSON
        $row = $result->fetch_assoc();
        $response[] = $row;
    } else {
        $response[] = "No data found for the given ID.";
    }

    // Close the statement
    $stmt->close();
} else {
    $response[] = "Invalid request method.";
}

// Close the connection
$conn->close();

echo json_encode($response);
?>
