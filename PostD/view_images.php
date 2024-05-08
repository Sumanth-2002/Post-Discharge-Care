<?php
require "conn.php";

// Fetch images based on the provided ID
$id = isset($_GET['id']) ? intval($_GET['id']) : 0;

// Use prepared statements to prevent SQL injection
$sql = "SELECT image FROM dis_image WHERE patient_id = ?";
$stmt = $conn->prepare($sql);
$stmt->bind_param("i", $id);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $images = array();

    while ($row = $result->fetch_assoc()) {
        $row['image'] = trim($row['image']);
        $images[] = $row;
    }

    // Return the images as JSON
    header('Content-Type: application/json');
    echo json_encode($images);
} else {
    // No images found
    echo json_encode(array('error' => 'No images found for the provided ID.'));
}

$stmt->close();
$conn->close();
?>
