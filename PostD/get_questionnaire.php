<?php
require("conn.php");

if (!empty($_POST['id'])) {
    $id = intval($_POST['id']);

    $sql = "SELECT Question_Category, Question FROM questionnaire_doc WHERE id = ? order by Question_Category DESC";
    
    $stmt = mysqli_prepare($conn, $sql);
    mysqli_stmt_bind_param($stmt, "i", $id);
    mysqli_stmt_execute($stmt);
    $result = mysqli_stmt_get_result($stmt);

    if ($result) {
        $rows = array();

        while ($row = mysqli_fetch_assoc($result)) {
            // Append each row to the array
            $rows[] = $row;
        }

        // Return JSON response with all records
        header('Content-Type: application/json');
        echo json_encode($rows);
    } else {
        echo json_encode(array('error' => 'Patient not found for the given ID'));
    }
} else {
    echo json_encode(array('error' => 'Failed to fetch patient details'));
}

mysqli_close($conn);
?>
