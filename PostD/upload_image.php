<?php  
include "conn.php";  

if(isset($_FILES['mediaFiles']['name'])){  
    $target_path = "Videos/";  
    $video_name = $_FILES['mediaFiles']['name'];
    $video_tmp = $_FILES['mediaFiles']['tmp_name'];
    $video_store = rand() . "_" . time() . "_" . $video_name;
    $target_path = $target_path . "/" . $video_store;
    if (move_uploaded_file($video_tmp, $target_path)) {
        $name = $_POST['name'];
        $select = "INSERT INTO `usersData`(`videos`, `name`) VALUES ('$video_store','$name')";
        $response = mysqli_query($con, $select);
        if ($response) {
            echo "Video Upload";
            mysqli_close($con);
        } else {
            echo "Something Wrong";
        }
    } else {
        echo "Error uploading video.";
    }
} else {
    echo "No video file received.";
}
?>
