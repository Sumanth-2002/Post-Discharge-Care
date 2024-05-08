<?php

require("conn.php");
$query = "SELECT course_name FROM course where id =$id";
$result = mysqli_query($conn, $query);
$courses = array();
if($result){
    while ($row = mysqli_fetch_assoc($result)) {
    $courses[] = $row['course_name'];
}
}
// Return the data as JSON
header('Content-Type: application/json');
echo json_encode($courses);
?>