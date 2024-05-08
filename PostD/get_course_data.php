<?php
require "conn.php";

// Get the id parameter from the request
$id = $_POST['id'];

// Fetch data from the database based on the id
$sql = "SELECT Course_Name FROM medicine WHERE id = '$id'";
$result = $conn->query($sql);

// Check if the query was successful
if ($result) {
    $data = array();

    // Fetch the data and store it in an array
    while ($row = $result->fetch_assoc()) {
        $data[] = $row;
    }

    // Return the data as JSON
    echo json_encode($data);
} else {
    // Return an error message if the query fails
    echo "Error: " . $sql . "<br>" . $conn->error;
}

// Close the connection
$conn->close();
?>
