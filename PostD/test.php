<?php
require("conn.php");

$response = array();

if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $images = $_FILES["images"];
    $id = $_POST["id"];

    function uploadImages($files) {
        $targetDirectory = "uploads/";
        $uploadedPaths = array();

        foreach ($files["tmp_name"] as $key => $tmp_name) {
            $file_name = $files["name"][$key];
            $targetFile = $targetDirectory . basename($file_name);
            if (move_uploaded_file($tmp_name, $targetFile)) {
                $uploadedPaths[] = $targetFile;
            } else {
                $uploadedPaths[] = null;
            }
        }

        return $uploadedPaths;
    }

    $uploadedImagePaths = uploadImages($images);

    if (!empty($uploadedImagePaths)) {
        $checkQuery = "SELECT * FROM dis_image WHERE patient_id=?";
        $stmt = $conn->prepare($checkQuery);
        $stmt->bind_param("s", $id);
        $stmt->execute();
        $result = $stmt->get_result();

        if ($result->num_rows == 0) {
            $insertQuery = "INSERT INTO dis_image (patient_id, image) VALUES (?, ?)";
            foreach ($uploadedImagePaths as $imagePath) {
                $stmt = $conn->prepare($insertQuery);
                $stmt->bind_param("ss", $id, $imagePath);
                $stmt->execute();
                $stmt->close();
            }
        } else {
            $updateQuery = "UPDATE dis_image SET image=? WHERE patient_id=?";
            foreach ($uploadedImagePaths as $imagePath) {
                $stmt = $conn->prepare($updateQuery);
                $stmt->bind_param("ss", $imagePath, $id);
                $stmt->execute();
                $stmt->close();
            }
        }

        $response["status"] = "success";
        $response["message"] = "Data inserted successfully";
    } else {
        $response["status"] = "error";
        $response["message"] = "Image upload failed";
    }
} else {
    $response["status"] = "error";
    $response["message"] = "Invalid request method";
}

header('Content-Type: application/json; charset=UTF-8');
echo json_encode($response);
?>
    