<?php
require("conn.php");

// Get the raw POST data
$postData = file_get_contents("php://input");

// Decode the JSON data
$requestData = json_decode($postData, true);

// Check if "Medicine_Name" is present in the decoded data
if (!empty($requestData['Medicine_Name']) && isset($requestData['id'])) {
    // Trim whitespaces from the Medicine_Name
    $medicineName = trim($requestData['Medicine_Name']);
    $id = trim($requestData['id']);

    // Sanitize input
    $medicineName = mysqli_real_escape_string($conn, $medicineName);
    $id = mysqli_real_escape_string($conn, $id);

    $sql = "SELECT Frequency, Guidelines ,date FROM medicine WHERE Medicine_Name = ? AND id = ?";
    
    $stmt = mysqli_prepare($conn, $sql);
    mysqli_stmt_bind_param($stmt, "ss", $medicineName, $id);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);

    if ($result) {
        // Check if there are any rows
        if (mysqli_num_rows($result) > 0) {
            $row = mysqli_fetch_assoc($result);

            // Return JSON response with the retrieved data
            header('Content-Type: application/json');
            echo json_encode($row);
        } else {
            echo json_encode(array('error' => 'No data found for the given Medicine_Name and id'));
        }
    } else {
        echo json_encode(array('error' => 'Failed to fetch medicine details'));
    }
} else {
    echo json_encode(array('error' => 'Missing or invalid parameters'));
}


mysqli_close($conn);
?>
