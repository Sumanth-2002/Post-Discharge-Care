<?php
if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $currentDate = date('Y-m-d');
    
    // Assuming you have a database connection
    require_once "conn.php";

    // Fetch patient ids based on the current date from the first table
    $firstSql = "SELECT id FROM report_time WHERE date = '$currentDate'";
    $firstResult = $conn->query($firstSql);

    if ($firstResult->num_rows > 0) {
        $response['status'] = 'Success';
        $response['data'] = array();

        while ($firstRow = $firstResult->fetch_assoc()) {
            $patientId = $firstRow['id'];
            // Fetch additional details from the second table based on patient id
            $secondSql = "SELECT id, Name, Contact_No, Gender, Profile_Pic FROM patient_details WHERE id = '$patientId'";
            $secondResult = $conn->query($secondSql);

            if ($secondResult->num_rows > 0) {
                while ($secondRow = $secondResult->fetch_assoc()) {
                    $patientData['id'] = $secondRow['id'];
                    $patientData['Name'] = $secondRow['Name'];
                    $patientData['Contact_No'] = $secondRow['Contact_No'];
                    $patientData['Gender'] = $secondRow['Gender'];
                    $patientData['Profile_Pic'] = $secondRow['Profile_Pic'];

                    array_push($response['data'], $patientData);
                }
            }
        }
    } else {
        $response['status'] = 'NoData';
        $response['message'] = 'No patient details found for the current date.';
    }
} else {
    $response['status'] = 'Error';
    $response['message'] = 'Invalid request method.';
}

echo json_encode($response);
?>
