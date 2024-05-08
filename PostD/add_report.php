<?php
require "conn.php";

// Check if the request method is POST
if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    
    // Retrieve the data sent from the Android app
    $id = $_POST['id'];
    $dates = $_POST['dates'];
    $counts = $_POST['counts'];

    // Connect to your database
    $conn->begin_transaction(); // Start a transaction

    // Delete existing records with matching id
    $delete_sql = "DELETE FROM report_time WHERE id = '$id'";
    if ($conn->query($delete_sql) !== TRUE) {
        // echo json_encode(["status" => "error", "message" => "Error deleting existing data: " . $conn->error]);
        $conn->rollback(); // Rollback transaction if delete operation fails
        exit(); // Terminate script
    }

    // Insert new data into the database
    for ($i = 0; $i < count($dates); $i++) {
        $sql = "INSERT INTO report_time (id, report_time, date) VALUES ('$id', '$counts[$i]', '$dates[$i]')";
        if ($conn->query($sql) !== TRUE) {
            echo json_encode(["status" => "error", "message" => "Error inserting data: " . $conn->error]);
            $conn->rollback(); // Rollback transaction if insert operation fails
            exit(); // Terminate script
        }
    }

    // Commit transaction if all operations succeed
    $conn->commit();

    echo json_encode(["status" => "success", "message" => "Data inserted successfully"]);

    // Close the database connection
    $conn->close();
} else {
    // If the request method is not POST, respond accordingly
    echo json_encode(["status" => "error", "message" => "Invalid request method"]);
}
?>
