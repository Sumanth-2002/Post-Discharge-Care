<?php
require "conn.php";

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    // Get data from the POST request
    $id = $_POST['id'];
    $category = $_POST['category'];
    $text = $_POST['text'];

    // Extract the category and question number from the category string
    list($actualCategory, $questionNumber) = explode("_", $category);

    // Delete previous records for the same id, category, and question
    $deleteStmt = $conn->prepare("DELETE FROM questionnaire_doc WHERE id = ? AND Question_category = ? AND Question = ?");
    $deleteStmt->bind_param("iss", $id, $actualCategory, $text);
    $deleteStmt->execute();
    $deleteStmt->close();

    // Insert the new record
    $insertStmt = $conn->prepare("INSERT INTO questionnaire_doc (id, Question_category, Question) VALUES (?, ?, ?)");
    $insertStmt->bind_param("iss", $id, $actualCategory, $text);

    // Execute the statement
    if ($insertStmt->execute()) {
        // Insert successful
        echo "Data inserted successfully: ID = $id, Category = $actualCategory, Question Number = $questionNumber, Text = $text";
    } else {
        // Insert failed
        echo "Error: " . $insertStmt->error;
    }

    // Close the statement
    $insertStmt->close();
} else {
    // Handle other types of requests or provide an error message
    echo "Invalid request method";
}
?>
