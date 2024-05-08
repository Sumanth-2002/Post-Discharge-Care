<?php
require("conn.php");

if ($_SERVER['REQUEST_METHOD'] === 'POST') {
    if (!empty($_POST['id'])) {
        $id = intval($_POST['id']);

        $sql = "SELECT * FROM patient_details WHERE id = ?";
        
        $stmt = mysqli_prepare($conn, $sql);
        mysqli_stmt_bind_param($stmt, "i", $id);
        mysqli_stmt_execute($stmt);
        $result = mysqli_stmt_get_result($stmt);

        if ($result) {
            $row = mysqli_fetch_assoc($result);
                // Return JSON response as an array
                header('Content-Type: application/json');
                echo json_encode(array($row));
            } else {
                echo json_encode(array('error' => 'Patient not found for the given ID'));
            }
      

        mysqli_stmt_close($stmt);
        mysqli_close($conn);
    } else {
        echo json_encode(array('error' => 'ID not provided'));
    }
} else {
    echo json_encode(array('error' => 'Invalid request method'));
}
?>
