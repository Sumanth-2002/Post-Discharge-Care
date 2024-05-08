<?php
// Include your database connection file
require "conn.php";

// Check if the POST parameter 'id' is set
if (isset($_POST['id'])) {
    $id = $_POST['id'];

    // Prepare and execute the query
    $query = "SELECT Name,Gender,Contact_No, Date_Of_Birth,Parent_Name,Profile_Pic FROM patient_details WHERE id = $id";
    $result = mysqli_query($conn, $query);

    // Check for errors
    if (!$result) {
        die('Error: ' . mysqli_error($conn));
    }

    // Fetch data as an associative array
    $data = mysqli_fetch_assoc($result);

    // Close the database connection
    mysqli_close($conn);

    // Return data in JSON format
    echo json_encode($data);
} else {
    // If 'id' is not set in the POST parameters
    echo "Error: 'id' parameter not set.";
}
?>
