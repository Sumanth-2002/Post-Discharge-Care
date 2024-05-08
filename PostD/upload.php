<?php
require("conn.php");
$uploadDir = "uploads/";
$response = array();
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_FILES["images"])) {
    $images = $_FILES["images"];
    $id = $_POST["id"];
    $name = $_POST["name"];
    $gender = $_POST["Gender"];
    $department = $_POST["Department"];
    $consultant = $_POST["Consultant"];
    $address = $_POST["Address"];
    $Doa = $_POST["Date_Of_Admission"];
    $Dod = $_POST["Date_Of_Discharge"];
    $diagnosis = $_POST["Diagnosis"];
    $chief = $_POST["Chief_Complaints"];
    $hipo = $_POST["History_of_present_illness"];
    $ph = $_POST["Past_History"];
    $ah = $_POST["Antennal_history"];
    $nh = $_POST["Natal_History"];
    $pn = $_POST["PostNatal_History"];
    $gm = $_POST["Gross_Motor"];
    $fm = $_POST["Fine_Motor"];
    $lan = $_POST["Language"];
    $sac = $_POST["Social_and_Congnitiont"];
    $ih = $_POST["Immunization_history"];
    $ant = $_POST["Anthropometry"];
    $weight = $_POST["Weight"];
    $height = $_POST["Height"];
    $heart_rate = $_POST["Heart_rate"];
    $temp = $_POST["Temperature"];
    $crt = $_POST["crt"];
    $rr = $_POST["rr"];
    $spo2 = $_POST["spo2"];
    $hte = $_POST["Head_to_Toe_Examination"];
    $ge = $_POST["General_Examination"];
    $se = $_POST["Systematic_Examination"];
    $Tg = $_POST["Treatment_Given"];
    $hsp = $_POST["Course_in_Hospital"];
    $picu = $_POST["Course_in_Picu"];
    $ward = $_POST["Course_in_ward"];
    $ad = $_POST["Advice_on_Discharge"];
    $review = $_POST["Review"];
    foreach ($images["tmp_name"] as $key => $tmp_name) {
        $imageExtension = pathinfo($images["name"][$key], PATHINFO_EXTENSION);
        $uploadFile = $uploadDir . $id . 'img' . ($key + 1) . '.' . $imageExtension;
    
        if (move_uploaded_file($tmp_name, $uploadFile)) {
            $imagePath = $uploadDir . $id . 'img' . ($key + 1) . '.' . $imageExtension;
    
            $stmt = $conn->prepare("SELECT * FROM dis_image WHERE patient_id = ?");
            $stmt->bind_param("s", $id);
            $stmt->execute();
            $result = $stmt->get_result();
            if($result->num_rows == 0){
            $insertQuery = "INSERT INTO dis_image (patient_id, image) VALUES (?, ?)";
            $stmt = $conn->prepare($insertQuery);
            $stmt->bind_param("ss", $id, $imagePath);
            if ($stmt->execute()) {
                $response[] = "File path inserted into the database.";
            } else {
                $response[] = "Failed to insert file path into the database.";
            }
            $stmt->close();
        }else{
                $insertQuery = "UPDATE dis_image SET image='$imagePath' where patient_id='$id'";
               
                 if( mysqli_query($conn,$insertQuery)){
                    $response[] = "File path inserted into the database.";
                 }
                 else{
                    $response[] = "Failed to insert file path into the database.";
                 }
            }
        
            $q2="Select * from discharge_summary where id ='$id'";
            $result2=mysqli_query($conn,$q2);
            if(mysqli_num_rows($result2)==0){
            $patientDataQuery = "INSERT INTO discharge_summary (id, Name, Sex, Address, Department, Consultant, Date_of_admission, Date_of_Discharge, Diagnosis) VALUES (?, ?,?,?,?,?,?,?,?)";
            $stmtPatient = $conn->prepare($patientDataQuery);
            $stmtPatient->bind_param("sssssssss", $id, $name, $gender, $address, $department, $consultant, $Doa, $Dod, $diagnosis);

            if ($stmtPatient->execute()) {
                $response[] = "Patient data inserted into 'discharge_summary' table.";
            } else {    
                $response[] = "Failed to insert patient data into 'discharge_summary' table.";
            }
            $stmtPatient->close();
        }
        else{
            $upda="UPDATE discharge_summary SET Address='$address', SET Department='$department',SET Consultant='$consultant', SET Date_of_admission='$Doa',SET Date_of_Discharge='$Dod',SET Diagnosis='$diagnosis' where id='$id'";
            if(mysqli_query($conn,$upda)){
                $response[] = "Patient data inserted into 'discharge_summary' table.";
            }
            else{
                $response[] = "Failed to insert patient data into 'discharge_summary' table.";
            }
        }
        $q3="Select * from chief_complaints where id ='$id'";
        $result3=mysqli_query($conn,$q3);
        if(mysqli_num_rows($result3)==0){
            $chiefComplaintQuery = "INSERT INTO chief_complaints (id, chief_complaints) VALUES (?, ?)";
            $stmtChiefComplaint = $conn->prepare($chiefComplaintQuery);
            $stmtChiefComplaint->bind_param("ss", $id, $chief);
            if ($stmtChiefComplaint->execute()) {
                $response[] = "Chief complaints inserted into 'chief_complaints' table.";
            } else {
                $response[] = "Failed to insert chief complaints into 'chief_complaints' table.";
            }
            $stmtChiefComplaint->close();
        }
        else{
            $upda2="UPDATE chief_complaints set chief_compalints ='$chief' where id ='$id'";
            if(mysqli_query($conn,$upda2)){
                $response[] = "Chief complaints inserted into 'chief_complaints' table.";
            }
            else {
                $response[] = "Failed to insert chief complaints into 'chief_complaints' table.";
            }
        }
        $q4="select * from history_illness where id='$id'";
        $result4=mysqli_query($conn,$q4);
        if(mysqli_num_rows($result4)==0){
            $illnessHistoryQuery = "INSERT INTO history_illness (id, Illness, Past_history, Antennal_history, Natal_history, Postnatal_history) VALUES (?,?,?,?,?,?)";
            $stmtIllnessHistory = $conn->prepare($illnessHistoryQuery);
            $stmtIllnessHistory->bind_param("ssssss", $id, $hipo, $ph, $ah, $nh, $pn);
            if ($stmtIllnessHistory->execute()) {
                $response[] = "Illness history inserted into 'history_illness' table.";
            } else {
                $response[] = "Failed to insert illness history into 'history_illness' table.";
            }
            $stmtIllnessHistory->close();
        }
        else{
            $upda3="UPDATE history_illness set Illness ='$hipo',set Past_history='$ph',set Antennal_history ='$ah',set Natal_history='$nh',set Postnatal_history='$pn' where id='$id'";
            if(mysqli_query($upda3)){
                $response[] = "Illness history inserted into 'history_illness' table.";
            }
            else{
                $response[] = "Failed to insert illness history into 'history_illness' table.";
            }
        }
            $q5="SELECT * from development_history where id='$id'";
            $result5=mysqli_query($conn,$q5);
            if(mysqli_num_rows($result5)==0){
            $developmentQuery = "INSERT INTO development_history (id, Gross_Motor, Fine_Motor, Language, Social_and_Cognition) VALUES (?,?,?,?,?)";
            $stmtDevelopment = $conn->prepare($developmentQuery);
            $stmtDevelopment->bind_param("sssss", $id, $gm, $fm, $lan, $sac);

            if ($stmtDevelopment->execute()) {
                $response[] = "Development history inserted into 'development_history' table.";
            } else {
                $response[] = "Failed to insert development history into 'development_history' table.";
            }
            $stmtDevelopment->close();
        }
        else{
            $upda4="UPDATE development set Gross_Motor ='$gm', set Fine_Motor='$fm', set Language='$lan',set Social_and_Cognition ='$sac' where id='$id'";
            if(mysqli_query($conn,$upda4)){
                $response[] = "Development history inserted into 'development_history' table.";
            }
            else{
                $response[] = "Failed to insert development history into 'development_history' table.";
            }
        }
        $q6="Select * from immunization_history where id='$id'";
        $result6=mysqli_query($conn,$q6);
        if(mysqli_num_rows($result6)==0){        
                 $immunizationQuery = "INSERT INTO immunization_history (id, history,Anthropometry, weightt, height) VALUES (?, ?, ?, ?)";
                 $stmtImmunization = $conn->prepare($immunizationQuery);
                 $stmtImmunization->bind_param("ssss", $id, $ih,$ant, $weight, $height);
     
                 if ($stmtImmunization->execute()) {
                     $response[] = "Immunization history inserted into 'immunization_history' table.";
                 } else {
                     $response[] = "Failed to insert immunization history into 'immunization_history' table.";
                 }
                 $stmtImmunization->close();
                }
                else{
                    $upda5="UPDATE immunization_history set history='$ih', set Anthropometry='$ant',set weightt='$weight',set height='$height' where id='$id'";
                    if(mysqli_num_rows($upda5)){
                        $response[] = "Immunization history inserted into 'immunization_history' table.";
                    }
                    else{
                        $response[] = "Failed to insert immunization history into 'immunization_history' table.";
                    }
                }
                $q7="Select * from vitals_at_admission where id='$id'";
                $result7=mysqli_query($conn,$q7);
                if(mysqli_num_rows($result7)){
                    $vitalsQuery = "INSERT INTO vitals_at_admission (id, Heart_rate, Temperature, CRT, RR, SPO2) VALUES (?, ?,?,?,?,?)";
                  $stmtVitals = $conn->prepare($vitalsQuery);
                  $stmtVitals->bind_param("ssssss", $id, $heart_rate, $temp, $crt, $rr, $spo2);
                  if ($stmtVitals->execute()) {
                      $response[] = "Vitals at admission inserted into 'vitals_at_admission' table.";
                  } else {
                      $response[] = "Failed to insert vitals at admission into 'vitals_at_admission' table.";
                  }
                  $stmtVitals->close();
                }else{
                    $upda6="UPDATE vitals_at_admission set Heart_rate='$heart_rate', set Temperature ='$temp', set CRT='$crt', set RR='$rr', set SPO2='$spo2' where id='$id'";
                    if(mysqli_query($conn,$upda6)){
                        $response[] = "Vitals at admission inserted into 'vitals_at_admission' table.";
                    }
                    else{
                        $response[] = "Failed to insert vitals at admission into 'vitals_at_admission' table.";
                    }
                }
                  $q8="SELECT * from treatment where id='$id'";
                  $result8=mysqli_query($conn,$q8);
                  if(mysqli_num_rows($result8)==0){
                    $treatmentQuery = "INSERT INTO treatment (id, Head_to_toe_examination, General_Examination, Systematic_Examination, Treatment_given) VALUES (?, ?,?,?,?)";
                    $stmtTreatment = $conn->prepare($treatmentQuery);
                    $stmtTreatment->bind_param("sssss", $id, $hte, $ge, $se, $Tg);
                    if ($stmtTreatment->execute()) {
                        $response[] = "Treatment data inserted into 'treatment' table.";
                    } else {
                        $response[] = "Failed to insert treatment data into 'treatment' table.";
                    }
                    $stmtTreatment->close();
                  }
                  else{
                    $upda7="UPDATE treatment set Head_to_toe_examination='$hte',set General_Examination='$ge',set Systematic_Examination='$se',set Treatment_given='$Tg' where id='$id'";
                    if(mysqli_query($conn,$upda7)){
                        $response[] = "Treatment data inserted into 'treatment' table.";
                    }
                    else{
                        $response[] = "Failed to insert treatment data into 'treatment' table.";
                    }
                }
         $q9="Select * from course_dis where id='$id'";
         $result9=mysqli_query($conn,$q9);
         if(mysqli_num_rows($result9)==0){
            $courseQuery = "INSERT INTO course_dis (id, Course_in_Hospital, Course_in_PICU, Course_in_Ward, Advice_on_Discharge, Review) VALUES (?, ?,?,?,?,?)";
            $stmtCourse = $conn->prepare($courseQuery);
            $stmtCourse->bind_param("ssssss", $id, $hsp, $picu, $ward, $ad, $review);
            if ($stmtCourse->execute()) {
                $response[] = "Course data inserted into 'course_dis' table.";
            } else {
                $response[] = "Failed to insert course data into 'course_dis' table.";
            }
            $stmtCourse->close();
         }else{
            $upda8="UPDATE course_dis set Course_in_Hospital ='$hsp', set Course_in_PICU='$picu',set Course_in_Ward='$ward',set Advice_on_Discharge='$ad',set Review='$review' where id='$id'";
            if(mysqli_query($conn,$upda8)){
                $response[] = "Course data inserted into 'course_dis' table."; 
            }
            else{
                $response[] = "Failed to insert course data into 'course_dis' table.";
            }
         }
            } else {
            $response[] = "Failed to upload file: " . $images["name"][$key];
        }
    }
} else {
    $response[] = "Invalid request.";
}
$conn->close();
echo json_encode($response);
?>
