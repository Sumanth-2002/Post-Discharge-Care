<?php
// Assuming you have a database connection
require "conn.php";

// Get course_number from the POST parameters
$courseNumber = $_POST['Course_Name'];
$id=$_POST['id'];

// SQL query to fetch medicine and duration based on course_number
$sql = "SELECT Medicine_Name, Duration FROM medicine WHERE Course_Name = '$courseNumber'and id='$id'";

$result = $conn->query($sql);

if ($result->num_rows > 0) {
    // Output data as JSON
    $output = array();
    while ($row = $result->fetch_assoc()) {
        $output[] = $row;
    }
    echo json_encode($output);
} else {
    echo json_encode(array("message" => "No results"));
}

$conn->close();
?>
