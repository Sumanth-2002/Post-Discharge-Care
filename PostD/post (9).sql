-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: May 08, 2024 at 11:56 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `post`
--

-- --------------------------------------------------------

--
-- Table structure for table `chief_complaints`
--

CREATE TABLE `chief_complaints` (
  `id` varchar(200) NOT NULL,
  `chief_complaints` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `chief_complaints`
--

INSERT INTO `chief_complaints` (`id`, `chief_complaints`) VALUES
('009mlm', 'kjk'),
('12234', 'Fever x 8 days\nCold and cough x 1 week\nVomiting x 3 days\nAbdominal pain x 3 days\n'),
('733720', 'Fever x 8 days\r\nCold and cough x 1 week\r\nVomiting x 3 days\r\nAbdominal pain x 3 days\r\n'),
('949194', 'Fever x 8 days\r\nCold and cough x 1 week\r\nVomiting x 3 days\r\nAbdominal pain x 3 days\r\n');

-- --------------------------------------------------------

--
-- Table structure for table `course_dis`
--

CREATE TABLE `course_dis` (
  `id` varchar(200) NOT NULL,
  `Course_in_Hospital` text NOT NULL,
  `Course_in_PICU` text NOT NULL,
  `Course_in_Ward` text NOT NULL,
  `Advice_on_Discharge` text NOT NULL,
  `Review` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `course_dis`
--

INSERT INTO `course_dis` (`id`, `Course_in_Hospital`, `Course_in_PICU`, `Course_in_Ward`, `Advice_on_Discharge`, `Review`) VALUES
('12234', 'Child came with the complaints of fever, cold, cough, vomiting and abdominal pian. On examination, there was right hypochondriac tenderness on palpation of the abdomen. Investigations were sent which showed reduced total counts with severe thrombocytopenia with elevated hematocrit. USG abdomen was done which revealed mild hepatomegaly. The child was shifted to the PICU i/v/o low platelet counts (17000/mm3).', 'The child was shifted i/v/o thrombocytopenia. The child’s vitals were monitored. The child was started on IV antibiotics (Ceftriaxone) and oral Doxycycline i/v/o prolonged period with thrombocytopenia suspecting scrub typhus. Dengue serology was sent and reports were positive for Dengue IgG and IgM. Serial CBC reports showed rising platelet trend and the child was shifted to ward for further management on day 4 of admission', 'The child was monitored and repeat investigations were done which showed normal platelet counts. The child was afebrile from Day 2 of admission. The child improved symptomatically and was hemodynamically stable and hence is being discharged with the following advice:', '1.Tab. DOLO 650mg PO SOS\r\n2.Syp. Fourts B 5ml PO OD x 14 days\r\n', 'Paediatric III OPD after 1 week / SOS'),
('733720', 'Child came with the complaints of fever, cold, cough, vomiting and abdominal pian. On examination, there was right hypochondriac tenderness on palpation of the abdomen. Investigations were sent which showed reduced total counts with severe thrombocytopenia with elevated hematocrit. USG abdomen was done which revealed mild hepatomegaly. The child was shifted to the PICU i/v/o low platelet counts (17000/mm3).', 'The child was shifted i/v/o thrombocytopenia. The child’s vitals were monitored. The child was started on IV antibiotics (Ceftriaxone) and oral Doxycycline i/v/o prolonged period with thrombocytopenia suspecting scrub typhus. Dengue serology was sent and reports were positive for Dengue IgG and IgM. Serial CBC reports showed rising platelet trend and the child was shifted to ward for further management on day 4 of admission', 'The child was monitored and repeat investigations were done which showed normal platelet counts. The child was afebrile from Day 2 of admission. The child improved symptomatically and was hemodynamically stable and hence is being discharged with the following advice:', '1.Tab. DOLO 650mg PO SOS\r\n2.Syp. Fourts B 5ml PO OD x 14 days\r\n', 'Paediatric III OPD after 1 week / SOS'),
('949194', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives');

-- --------------------------------------------------------

--
-- Table structure for table `development_history`
--

CREATE TABLE `development_history` (
  `id` varchar(200) NOT NULL,
  `Gross_Motor` text NOT NULL,
  `Fine_Motor` text NOT NULL,
  `Language` text NOT NULL,
  `Social_and_Cognition` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `development_history`
--

INSERT INTO `development_history` (`id`, `Gross_Motor`, `Fine_Motor`, `Language`, `Social_and_Cognition`) VALUES
('009mlm', 'lm', 'lmlm', 'lml', 'ml'),
('12234', 'Appropriate for age', 'Appropriate for age', 'Appropriate for age', 'Appropriate for age'),
('733720', 'Appropriate for age', 'Appropriate for age', 'Appropriate for age', 'Appropriate for age'),
('949194', 'hi', 'nn', 'jn', 'n');

-- --------------------------------------------------------

--
-- Table structure for table `discharge_summary`
--

CREATE TABLE `discharge_summary` (
  `id` varchar(200) NOT NULL,
  `Name` text NOT NULL,
  `Sex` text NOT NULL,
  `Address` text NOT NULL,
  `Department` text NOT NULL,
  `Consultant` text NOT NULL,
  `Date_of_admission` text NOT NULL,
  `Date_of_Discharge` text NOT NULL,
  `Diagnosis` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `discharge_summary`
--

INSERT INTO `discharge_summary` (`id`, `Name`, `Sex`, `Address`, `Department`, `Consultant`, `Date_of_admission`, `Date_of_Discharge`, `Diagnosis`) VALUES
('009mlm', 'ijiojlm', 'jlml', 'kjmlm', 'jijml', 'kjml', '2023-11-10', '2023-12-10', 'kjl'),
('12234', 'SASHWANTH', 'MALE', 'KAKALUR', 'PAEDIATRICS', 'DR. BALAMMA SUJATHA', '2023-12-10', '2023-12-25', 'DENGUE FEVER (IgM, IgG POSITIVE)'),
('733720', 'SASHWANTH', 'MALE', 'KAKALUR', 'PAEDIATRICS', 'DR. BALAMMA SUJATHA', '2023-02-08', '2023-02-13', 'DENGUE FEVER (IgM, IgG POSITIVE)'),
('949194', 'sumanth', 'male', 'NEFKN', 'dihi', 'hro', '13/10/2023', '22/11/2023', 'dengue');

-- --------------------------------------------------------

--
-- Table structure for table `dis_image`
--

CREATE TABLE `dis_image` (
  `sno` int(11) NOT NULL,
  `patient_id` bigint(20) NOT NULL,
  `image` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `dis_image`
--

INSERT INTO `dis_image` (`sno`, `patient_id`, `image`) VALUES
(1, 12234, 'uploads/12234img1.jpeg'),
(2, 949194, 'uploads/949194img1.jpg'),
(3, 12234, 'uploads/12234img2.png\n'),
(4, 12234, 'uploads/12234img3.jpeg'),
(5, 12234, 'uploads/12234img4.jpeg'),
(6, 9, 'uploads/009mlmimg1.jpg'),
(7, 0, 'uploads/img1.jpg'),
(8, 900, 'uploads/10665-thank you powerpoint-THANK YOU.png'),
(9, 100, 'uploads/ig.png'),
(10, 100, 'uploads/10665-thank you powerpoint-THANK YOU.png');

-- --------------------------------------------------------

--
-- Table structure for table `doctor_login`
--

CREATE TABLE `doctor_login` (
  `id` varchar(200) NOT NULL,
  `password` text NOT NULL,
  `Name` text NOT NULL,
  `Gender` text NOT NULL,
  `Department` text NOT NULL,
  `Experience` text NOT NULL,
  `Contact_Number` text NOT NULL,
  `doc_profile` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `doctor_login`
--

INSERT INTO `doctor_login` (`id`, `password`, `Name`, `Gender`, `Department`, `Experience`, `Contact_Number`, `doc_profile`) VALUES
('1890', 'hi', 'Ashwini', 'Female', 'Pediatrics', '3 years', '987684221', 'doctor_Profile/SXceQIHZrE_1709539540.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `history_illness`
--

CREATE TABLE `history_illness` (
  `sno` int(11) NOT NULL,
  `id` text NOT NULL,
  `illness` text NOT NULL,
  `Past_history` text NOT NULL,
  `Antennal_history` text NOT NULL,
  `Natal_history` text NOT NULL,
  `Postnatal_history` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `history_illness`
--

INSERT INTO `history_illness` (`sno`, `id`, `illness`, `Past_history`, `Antennal_history`, `Natal_history`, `Postnatal_history`) VALUES
(1, '12234', 'The child was apparently normal 1 week back when he developed fever for 8 days, high grade, intermittent, reducing on taking medications, associated with chills and rigors. C/o cough for 1 week, no diurnal variation. C/o vomiting x 3 days containing food particles, non-projectile, non-blood / bile tinged. C/o abdominal pain x 3 days in the right hypochondriac region. No h/o burning micturition, loose stools, constipation.\r\n\r\n', 'No h/o similar complaints in the past. ', 'Uneventful, no h/o GDM, HTN, hypothyroidism. \r\nAnomaly scan done and normal.\r\n', 'Term/LSCS/ birth weight: 3.3 kg / cried immediately after birth.', 'no h/o NICU admission and exclusively breastfed for 6month'),
(6, '949194', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives'),
(7, '009mlm', 'jk', 'jlml', 'lmlm', 'lmlmlm', 'lmlm'),
(9, '733720', 'The child was apparently normal 1 week back when he developed fever for 8 days, high grade, intermittent, reducing on taking medications, associated with chills and rigors. C/o cough for 1 week, no diurnal variation. C/o vomiting x 3 days containing food particles, non-projectile, non-blood / bile tinged. C/o abdominal pain x 3 days in the right hypochondriac region. No h/o burning micturition, loose stools, constipation.', 'No h/o similar complaints in the past. ', 'Uneventful, no h/o GDM, HTN, hypothyroidism. \r\nAnomaly scan done and normal.\r\n', 'Term/LSCS/ birth weight: 3.3 kg / cried immediately after birth', 'no h/o NICU admission and exclusively breastfed for 6month');

-- --------------------------------------------------------

--
-- Table structure for table `immunization_history`
--

CREATE TABLE `immunization_history` (
  `id` varchar(200) NOT NULL,
  `history` text NOT NULL,
  `Anthropometry` text NOT NULL,
  `weightt` text NOT NULL,
  `height` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `immunization_history`
--

INSERT INTO `immunization_history` (`id`, `history`, `Anthropometry`, `weightt`, `height`) VALUES
('12234', 'immunized as per NIS.', 'Excellent', '52 Kg', '154cm'),
('733720', '', 'Excellent', '52 Kg', '154cm'),
('949194', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'Excellent', 'j', 'kk');

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE `login` (
  `id` bigint(20) NOT NULL,
  `password` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`id`, `password`) VALUES
(8, '233'),
(9, '09-9'),
(1111, '1111'),
(3562, '3562'),
(8000, '8000'),
(12234, '2234'),
(12345, '2345'),
(15293, '5293'),
(20200, '0200'),
(89000, '9000'),
(89192, '9192'),
(90909, '0909'),
(93400, '3400'),
(123444, '3444'),
(733720, '3720'),
(777777, '7777'),
(800000, '0000'),
(888888, '8888'),
(909090, '9090'),
(929292, '9292'),
(8888888, '8888'),
(9999000, '9000'),
(9999990, '9990'),
(9999999, '9999');

-- --------------------------------------------------------

--
-- Table structure for table `login_status`
--

CREATE TABLE `login_status` (
  `device_name` varchar(400) NOT NULL,
  `category` text NOT NULL,
  `id` text NOT NULL,
  `status` text NOT NULL DEFAULT 'logged in '
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `login_status`
--

INSERT INTO `login_status` (`device_name`, `category`, `id`, `status`) VALUES
('12234', 'oj', 'iiij', 'logged'),
('Google sdk_gphone64_x86_64', 'patient', '12234', 'logged out'),
('OnePlus EB2101', 'patient', '12234', 'logged in'),
('Realme RMX3092', 'doctor', '1890', 'logged in'),
('Vivo I2202', 'patient', '12234', 'logged in ');

-- --------------------------------------------------------

--
-- Table structure for table `medicine`
--

CREATE TABLE `medicine` (
  `sno` bigint(20) NOT NULL,
  `id` text NOT NULL,
  `Course_Name` text NOT NULL,
  `Medicine_Name` text NOT NULL,
  `Duration` text NOT NULL,
  `Frequency` text NOT NULL,
  `Guidelines` text NOT NULL,
  `Date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `medicine`
--

INSERT INTO `medicine` (`sno`, `id`, `Course_Name`, `Medicine_Name`, `Duration`, `Frequency`, `Guidelines`, `Date`) VALUES
(1, '12234', '1', 'Paracetamol', 'First 3 months', 'Once a day', 'After meal', '2024-02-19'),
(2, '733720', '2', 'Paracetamol', '3 months', 'Once a day', 'After meal', '2024-02-19'),
(3, '733720', '1', 'Tablet 1', '2 months', 'Twice a day', 'Before Meal', '2024-02-19'),
(4, '733720', '1', 'Tablet 2 ', '1 month', 'Thrice a day', 'After Meal', '2024-02-19'),
(10, '12234', '1', 'Medicine 2', '3 months', 'Twice a day', 'After Meal', '2024-02-19'),
(11, '12234', '2', 'Tablet 3 ', '5 months', 'Thrice a day', 'After Meal', '2024-03-04');

-- --------------------------------------------------------

--
-- Table structure for table `patient_details`
--

CREATE TABLE `patient_details` (
  `id` varchar(200) NOT NULL,
  `Name` text NOT NULL,
  `Contact_No` bigint(20) NOT NULL,
  `Gender` text NOT NULL,
  `Date_Of_Birth` date NOT NULL,
  `Height` text NOT NULL,
  `Weight` text NOT NULL,
  `Parent_Name` text NOT NULL,
  `Admitted_On` date NOT NULL,
  `Discharge_On` date NOT NULL,
  `Profile_Pic` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `patient_details`
--

INSERT INTO `patient_details` (`id`, `Name`, `Contact_No`, `Gender`, `Date_Of_Birth`, `Height`, `Weight`, `Parent_Name`, `Admitted_On`, `Discharge_On`, `Profile_Pic`) VALUES
('12234', 'Hari', 9876543211, 'Male', '2016-11-04', '103cm', '18kg', 'suresh', '2023-11-10', '2023-12-01', 'profile_pics/65e45d827cce3.jpg'),
('123444', 'oj', 9238204000, 'Male', '2024-03-28', '99i', 'khkh', 'ihihih', '2024-03-28', '2024-03-28', 'profile_pics/65e45d827cce3.jpg'),
('12345', 'hif', 9239924402, 'Male', '2016-02-10', '105cm', '25kg', 'Ramesh', '2024-03-26', '2024-03-28', 'profile_pics/65e45d827cce3.jpg'),
('15293', 'Vamsi', 8309849546, 'Male', '2017-01-19', '120cm', '20kg', 'Raja', '2024-02-07', '2024-02-21', 'profile_pics/65e45d827cce3.jpg'),
('3562', 'Ramesh', 9876542310, 'Male', '2016-04-12', '89cm', '30kg', 'Virat', '2024-04-01', '2024-04-08', 'profile_pics/661376a8cb063.jpg'),
('733720', 'Amar', 7337207416, 'Male', '2015-07-24', '117cm', '20kg', 'vijaya', '2023-11-10', '2023-12-01', 'profile_pics/65e45d827cce3.jpg');

-- --------------------------------------------------------

--
-- Table structure for table `questionnaire_doc`
--

CREATE TABLE `questionnaire_doc` (
  `sno` int(11) NOT NULL,
  `id` bigint(20) NOT NULL,
  `Question_category` text NOT NULL,
  `Question` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questionnaire_doc`
--

INSERT INTO `questionnaire_doc` (`sno`, `id`, `Question_category`, `Question`) VALUES
(1, 90909, 'danger', 'Abnormal Body Movements'),
(2, 90909, 'general', 'Not Drinking'),
(3, 90909, 'danger', 'Fast Breathing'),
(4, 90909, 'general', 'Unconsciousness'),
(5, 90909, 'danger', 'Fever with rash'),
(6, 90909, 'general', 'Lethargy'),
(7, 90909, 'danger', 'Fatigue'),
(8, 90909, 'general', 'Convulsions'),
(9, 90909, 'danger', 'Irritable'),
(10, 90909, 'general', 'Vomits Everything'),
(11, 90909, 'danger', 'Ear with discharge/pain'),
(12, 1111, 'danger', 'Abnormal Body Movements'),
(13, 1111, 'general', 'Not Drinking'),
(14, 1111, 'danger', 'Fast Breathing'),
(15, 1111, 'general', 'Unconsciousness'),
(16, 1111, 'danger', 'Fever with rash'),
(17, 1111, 'general', 'Lethargy'),
(18, 1111, 'danger', 'Fatigue'),
(19, 1111, 'general', 'Convulsions'),
(20, 1111, 'danger', 'Irritable'),
(21, 1111, 'general', 'Vomits Everything'),
(22, 1111, 'danger', 'Ear with discharge/pain'),
(23, 12234, 'danger', 'Abnormal Body Movements'),
(24, 12234, 'general', 'Not Drinking'),
(25, 12234, 'danger', 'Fast Breathing'),
(26, 12234, 'general', 'Unconsciousness'),
(27, 12234, 'danger', 'Fever with rash'),
(28, 12234, 'general', 'Lethargy'),
(29, 12234, 'danger', 'Fatigue'),
(30, 12234, 'general', 'Convulsions'),
(31, 12234, 'danger', 'Irritable'),
(32, 12234, 'general', 'Vomits Everything'),
(33, 12234, 'danger', 'Ear with discharge/pain');

-- --------------------------------------------------------

--
-- Table structure for table `questionnaire_response`
--

CREATE TABLE `questionnaire_response` (
  `sno` int(11) NOT NULL,
  `id` text NOT NULL,
  `date` text NOT NULL,
  `symptom` text NOT NULL,
  `response` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `questionnaire_response`
--

INSERT INTO `questionnaire_response` (`sno`, `id`, `date`, `symptom`, `response`) VALUES
(364, '12234', '2024-03-05', 'Drink', 'No'),
(365, '12234', '2024-03-05', 'அதில', 'No'),
(366, '12234', '2024-03-05', 'Sloth', 'No'),
(367, '12234', '2024-03-05', 'Fit', 'No'),
(368, '12234', '2024-03-05', 'Everything is vomiting', 'No'),
(369, '12234', '2024-03-05', 'Unusual physical movements', 'No'),
(370, '12234', '2024-03-05', 'Fast respiration', 'No'),
(371, '12234', '2024-03-05', 'Fever fever', 'No'),
(372, '12234', '2024-03-05', 'Fatigue', 'No'),
(373, '12234', '2024-03-05', 'Irritant', 'No'),
(374, '12234', '2024-03-05', 'Ear with discharge / pain', 'No'),
(375, '12234', '2024-03-05', 'others', 'I suffer from severe physical pain and fever'),
(376, '12234', '2024-03-05', 'Drink', 'No'),
(377, '12234', '2024-03-05', 'அதில', 'Yes'),
(378, '12234', '2024-03-05', 'Sloth', 'No'),
(379, '12234', '2024-03-05', 'Fit', 'No'),
(380, '12234', '2024-03-05', 'Everything is vomiting', 'No'),
(381, '12234', '2024-03-05', 'Unusual physical movements', 'Yes'),
(382, '12234', '2024-03-05', 'Fast respiration', 'No'),
(383, '12234', '2024-03-05', 'Fever fever', 'No'),
(384, '12234', '2024-03-05', 'Fatigue', 'No'),
(385, '12234', '2024-03-05', 'Irritant', 'No'),
(386, '12234', '2024-03-05', 'Ear with discharge / pain', 'No'),
(387, '12234', '2024-03-05', 'others', 'I am suffering from physical pain'),
(388, '12234', '2024-03-05', 'Drink', 'No'),
(389, '12234', '2024-03-05', 'அதில', 'No'),
(390, '12234', '2024-03-05', 'Sloth', 'No'),
(391, '12234', '2024-03-05', 'Fit', 'Yes'),
(392, '12234', '2024-03-05', 'Everything is vomiting', 'No'),
(393, '12234', '2024-03-05', 'Unusual physical movements', 'No'),
(394, '12234', '2024-03-05', 'Fast respiration', 'Yes'),
(395, '12234', '2024-03-05', 'Fever fever', 'No'),
(396, '12234', '2024-03-05', 'Fatigue', 'No'),
(397, '12234', '2024-03-05', 'Irritant', 'No'),
(398, '12234', '2024-03-05', 'Ear with discharge / pain', 'No'),
(399, '12234', '2024-03-05', 'others', 'My'),
(400, '12234', '2024-03-05', 'Drink', 'No'),
(401, '12234', '2024-03-05', 'அதில', 'No'),
(402, '12234', '2024-03-05', 'Sloth', 'No'),
(403, '12234', '2024-03-05', 'Fit', 'No'),
(404, '12234', '2024-03-05', 'Everything is vomiting', 'No'),
(405, '12234', '2024-03-05', 'Unusual physical movements', 'No'),
(406, '12234', '2024-03-05', 'Fast respiration', 'No'),
(407, '12234', '2024-03-05', 'Fever fever', 'No'),
(408, '12234', '2024-03-05', 'Fatigue', 'No'),
(409, '12234', '2024-03-05', 'Irritant', 'No'),
(410, '12234', '2024-03-05', 'Ear with discharge / pain', 'No'),
(411, '12234', '2024-03-05', 'others', 'Fever'),
(412, '12234', '2024-03-05', 'Drink', 'No'),
(413, '12234', '2024-03-05', 'அதில', 'No'),
(414, '12234', '2024-03-05', 'Sloth', 'Yes'),
(415, '12234', '2024-03-05', 'Fit', 'No'),
(416, '12234', '2024-03-05', 'Everything is vomiting', 'No'),
(417, '12234', '2024-03-05', 'Unusual physical movements', 'Yes'),
(418, '12234', '2024-03-05', 'Fast respiration', 'No'),
(419, '12234', '2024-03-05', 'Fever fever', 'No'),
(420, '12234', '2024-03-05', 'Fatigue', 'No'),
(421, '12234', '2024-03-05', 'Irritant', 'No'),
(422, '12234', '2024-03-05', 'Ear with discharge / pain', 'No'),
(423, '12234', '2024-03-05', 'others', 'Fever'),
(424, '12234', '2024-03-20', 'Drink', 'No'),
(425, '12234', '2024-03-20', 'அதில', 'No'),
(426, '12234', '2024-03-20', 'Sloth', 'No'),
(427, '12234', '2024-03-20', 'Fit', 'No'),
(428, '12234', '2024-03-20', 'Everything is vomiting', 'No'),
(429, '12234', '2024-03-20', 'Unusual physical movements', 'No'),
(430, '12234', '2024-03-20', 'Fast respiration', 'No'),
(431, '12234', '2024-03-20', 'Fever fever', 'No'),
(432, '12234', '2024-03-20', 'Fatigue', 'No'),
(433, '12234', '2024-03-20', 'Irritant', 'No'),
(434, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(435, '12234', '2024-03-20', 'others', 'nothing'),
(436, '12234', '2024-03-20', 'Drink', 'No'),
(437, '12234', '2024-03-20', 'அதில', 'No'),
(438, '12234', '2024-03-20', 'Sloth', 'No'),
(439, '12234', '2024-03-20', 'Fit', 'No'),
(440, '12234', '2024-03-20', 'Everything is vomiting', 'No'),
(441, '12234', '2024-03-20', 'Unusual physical movements', 'No'),
(442, '12234', '2024-03-20', 'Fast respiration', 'No'),
(443, '12234', '2024-03-20', 'Fever fever', 'No'),
(444, '12234', '2024-03-20', 'Fatigue', 'No'),
(445, '12234', '2024-03-20', 'Irritant', 'No'),
(446, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(447, '12234', '2024-03-20', 'others', 'Nothing'),
(448, '12234', '2024-03-20', 'Drink', 'No'),
(449, '12234', '2024-03-20', 'அதில', 'No'),
(450, '12234', '2024-03-20', 'Sloth', 'No'),
(451, '12234', '2024-03-20', 'Fit', 'No'),
(452, '12234', '2024-03-20', 'Everything is vomiting', 'No'),
(453, '12234', '2024-03-20', 'Unusual physical movements', 'No'),
(454, '12234', '2024-03-20', 'Fast respiration', 'No'),
(455, '12234', '2024-03-20', 'Fever fever', 'No'),
(456, '12234', '2024-03-20', 'Fatigue', 'No'),
(457, '12234', '2024-03-20', 'Irritant', 'No'),
(458, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(459, '12234', '2024-03-20', 'others', 'Nothing'),
(460, '12234', '2024-03-20', 'Drink', 'No'),
(461, '12234', '2024-03-20', 'அதில', 'No'),
(462, '12234', '2024-03-20', 'Sloth', 'No'),
(463, '12234', '2024-03-20', 'Fit', 'No'),
(464, '12234', '2024-03-20', 'Everything is vomiting', 'No'),
(465, '12234', '2024-03-20', 'Unusual physical movements', 'No'),
(466, '12234', '2024-03-20', 'Fast respiration', 'No'),
(467, '12234', '2024-03-20', 'Fever fever', 'No'),
(468, '12234', '2024-03-20', 'Fatigue', 'No'),
(469, '12234', '2024-03-20', 'Irritant', 'No'),
(470, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(471, '12234', '2024-03-20', 'others', 'Nothing'),
(472, '12234', '2024-03-20', 'Drink', 'No'),
(473, '12234', '2024-03-20', 'அதில', 'No'),
(474, '12234', '2024-03-20', 'Sloth', 'No'),
(475, '12234', '2024-03-20', 'Fit', 'No'),
(476, '12234', '2024-03-20', 'Everything is vomiting', 'No'),
(477, '12234', '2024-03-20', 'Unusual physical movements', 'No'),
(478, '12234', '2024-03-20', 'Fast respiration', 'No'),
(479, '12234', '2024-03-20', 'Fever fever', 'No'),
(480, '12234', '2024-03-20', 'Fatigue', 'No'),
(481, '12234', '2024-03-20', 'Irritant', 'No'),
(482, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(483, '12234', '2024-03-20', 'others', 'Nothing'),
(484, '12234', '2024-03-20', 'Drink', 'No'),
(485, '12234', '2024-03-20', 'அதில', 'No'),
(486, '12234', '2024-03-20', 'Sloth', 'No'),
(487, '12234', '2024-03-20', 'Fit', 'No'),
(488, '12234', '2024-03-20', 'Everything is vomiting', 'No'),
(489, '12234', '2024-03-20', 'Unusual physical movements', 'No'),
(490, '12234', '2024-03-20', 'Fast respiration', 'No'),
(491, '12234', '2024-03-20', 'Fever fever', 'No'),
(492, '12234', '2024-03-20', 'Fatigue', 'No'),
(493, '12234', '2024-03-20', 'Irritant', 'No'),
(494, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(495, '12234', '2024-03-20', 'others', 'Nothing'),
(496, '12234', '2024-03-20', 'Drink', 'No'),
(497, '12234', '2024-03-20', 'அதில', 'No'),
(498, '12234', '2024-03-20', 'Sloth', 'No'),
(499, '12234', '2024-03-20', 'Fit', 'No'),
(500, '12234', '2024-03-20', 'Everything is vomiting', 'No'),
(501, '12234', '2024-03-20', 'Unusual physical movements', 'No'),
(502, '12234', '2024-03-20', 'Fast respiration', 'No'),
(503, '12234', '2024-03-20', 'Fever fever', 'No'),
(504, '12234', '2024-03-20', 'Fatigue', 'No'),
(505, '12234', '2024-03-20', 'Irritant', 'No'),
(506, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(507, '12234', '2024-03-20', 'others', 'Nothing'),
(508, '12234', '2024-03-20', 'Not drinking', 'No'),
(509, '12234', '2024-03-20', 'Consciousness', 'No'),
(510, '12234', '2024-03-20', 'Sloth', 'No'),
(511, '12234', '2024-03-20', 'Convulsions', 'No'),
(512, '12234', '2024-03-20', 'Everything in vomiting', 'No'),
(513, '12234', '2024-03-20', 'Unusual body movements', 'No'),
(514, '12234', '2024-03-20', 'Fast breathing', 'No'),
(515, '12234', '2024-03-20', 'Fever with rash', 'No'),
(516, '12234', '2024-03-20', 'Fatigue', 'No'),
(517, '12234', '2024-03-20', 'Irritable', 'No'),
(518, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(519, '12234', '2024-03-20', 'others', 'Nothing'),
(520, '12234', '2024-03-20', 'Drink', 'No'),
(521, '12234', '2024-03-20', 'அதில', 'No'),
(522, '12234', '2024-03-20', 'Sloth', 'No'),
(523, '12234', '2024-03-20', 'Fit', 'No'),
(524, '12234', '2024-03-20', 'Everything is vomiting', 'No'),
(525, '12234', '2024-03-20', 'Unusual physical movements', 'No'),
(526, '12234', '2024-03-20', 'Fast respiration', 'No'),
(527, '12234', '2024-03-20', 'Fever fever', 'No'),
(528, '12234', '2024-03-20', 'Fatigue', 'No'),
(529, '12234', '2024-03-20', 'Irritant', 'No'),
(530, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(531, '12234', '2024-03-20', 'others', 'Nothing'),
(532, '12234', '2024-03-20', 'Drink', 'No'),
(533, '12234', '2024-03-20', 'அதில', 'No'),
(534, '12234', '2024-03-20', 'Sloth', 'No'),
(535, '12234', '2024-03-20', 'Fit', 'No'),
(536, '12234', '2024-03-20', 'Everything is vomiting', 'No'),
(537, '12234', '2024-03-20', 'Unusual physical movements', 'No'),
(538, '12234', '2024-03-20', 'Fast respiration', 'No'),
(539, '12234', '2024-03-20', 'Fever fever', 'No'),
(540, '12234', '2024-03-20', 'Fatigue', 'No'),
(541, '12234', '2024-03-20', 'Irritant', 'No'),
(542, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(543, '12234', '2024-03-20', 'others', 'Nothing'),
(544, '12234', '2024-03-20', 'Not drinking', 'No'),
(545, '12234', '2024-03-20', 'Consciousness', 'No'),
(546, '12234', '2024-03-20', 'Sloth', 'No'),
(547, '12234', '2024-03-20', 'Convulsions', 'No'),
(548, '12234', '2024-03-20', 'Everything in vomiting', 'No'),
(549, '12234', '2024-03-20', 'Unusual body movements', 'No'),
(550, '12234', '2024-03-20', 'Fast breathing', 'No'),
(551, '12234', '2024-03-20', 'Fever with rash', 'No'),
(552, '12234', '2024-03-20', 'Fatigue', 'No'),
(553, '12234', '2024-03-20', 'Irritable', 'No'),
(554, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(555, '12234', '2024-03-20', 'others', 'Nothing'),
(556, '12234', '2024-03-20', 'Not drinking', 'No'),
(557, '12234', '2024-03-20', 'Consciousness', 'No'),
(558, '12234', '2024-03-20', 'Sloth', 'No'),
(559, '12234', '2024-03-20', 'Convulsions', 'No'),
(560, '12234', '2024-03-20', 'Everything in vomiting', 'No'),
(561, '12234', '2024-03-20', 'Unusual body movements', 'No'),
(562, '12234', '2024-03-20', 'Fast breathing', 'No'),
(563, '12234', '2024-03-20', 'Fever with rash', 'No'),
(564, '12234', '2024-03-20', 'Fatigue', 'No'),
(565, '12234', '2024-03-20', 'Irritable', 'No'),
(566, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(567, '12234', '2024-03-20', 'others', 'Nothing'),
(568, '12234', '2024-03-20', 'Do not drink', 'No'),
(569, '12234', '2024-03-20', 'Unconsciousness', 'No'),
(570, '12234', '2024-03-20', 'Idling', 'No'),
(571, '12234', '2024-03-20', 'Convulsions', 'No'),
(572, '12234', '2024-03-20', 'Everything vomits', 'No'),
(573, '12234', '2024-03-20', 'Abnormal body movement', 'No'),
(574, '12234', '2024-03-20', 'Breathing', 'No'),
(575, '12234', '2024-03-20', 'Fever with grain', 'No'),
(576, '12234', '2024-03-20', 'Fatigue', 'No'),
(577, '12234', '2024-03-20', 'Irritable', 'No'),
(578, '12234', '2024-03-20', 'Ear with holiday / pain', 'No'),
(579, '12234', '2024-03-20', 'others', 'Nothing'),
(580, '12234', '2024-03-20', 'Drink', 'No'),
(581, '12234', '2024-03-20', 'அதில', 'No'),
(582, '12234', '2024-03-20', 'Sloth', 'No'),
(583, '12234', '2024-03-20', 'Fit', 'No'),
(584, '12234', '2024-03-20', 'Everything is vomiting', 'No'),
(585, '12234', '2024-03-20', 'Unusual physical movements', 'No'),
(586, '12234', '2024-03-20', 'Fast respiration', 'No'),
(587, '12234', '2024-03-20', 'Fever fever', 'No'),
(588, '12234', '2024-03-20', 'Fatigue', 'No'),
(589, '12234', '2024-03-20', 'Irritant', 'No'),
(590, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(591, '12234', '2024-03-20', 'others', 'Nothing'),
(592, '12234', '2024-03-20', 'Not drinking', 'No'),
(593, '12234', '2024-03-20', 'Consciousness', 'No'),
(594, '12234', '2024-03-20', 'Sloth', 'No'),
(595, '12234', '2024-03-20', 'Convulsions', 'No'),
(596, '12234', '2024-03-20', 'Everything in vomiting', 'No'),
(597, '12234', '2024-03-20', 'Unusual body movements', 'No'),
(598, '12234', '2024-03-20', 'Fast breathing', 'No'),
(599, '12234', '2024-03-20', 'Fever with rash', 'No'),
(600, '12234', '2024-03-20', 'Fatigue', 'No'),
(601, '12234', '2024-03-20', 'Irritable', 'No'),
(602, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(603, '12234', '2024-03-20', 'others', 'Nothing'),
(604, '12234', '2024-03-13', 'Not drinking', 'No'),
(605, '12234', '2024-03-13', 'Consciousness', 'No'),
(606, '12234', '2024-03-13', 'Sloth', 'No'),
(607, '12234', '2024-03-13', 'Convulsions', 'No'),
(608, '12234', '2024-03-13', 'Everything in vomiting', 'No'),
(609, '12234', '2024-03-13', 'Unusual body movements', 'No'),
(610, '12234', '2024-03-13', 'Fast breathing', 'No'),
(611, '12234', '2024-03-13', 'Fever with rash', 'No'),
(612, '12234', '2024-03-13', 'Fatigue', 'No'),
(613, '12234', '2024-03-13', 'Irritable', 'No'),
(614, '12234', '2024-03-13', 'Ear with discharge / pain', 'No'),
(615, '12234', '2024-03-13', 'others', 'Nothing'),
(616, '12234', '2024-03-20', 'Not drinking', 'No'),
(617, '12234', '2024-03-20', 'Consciousness', 'No'),
(618, '12234', '2024-03-20', 'Sloth', 'No'),
(619, '12234', '2024-03-20', 'Convulsions', 'No'),
(620, '12234', '2024-03-20', 'Everything in vomiting', 'No'),
(621, '12234', '2024-03-20', 'Unusual body movements', 'No'),
(622, '12234', '2024-03-20', 'Fast breathing', 'No'),
(623, '12234', '2024-03-20', 'Fever with rash', 'No'),
(624, '12234', '2024-03-20', 'Fatigue', 'No'),
(625, '12234', '2024-03-20', 'Irritable', 'No'),
(626, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(627, '12234', '2024-03-20', 'others', 'Nothing'),
(628, '12234', '2024-03-20', 'Not drinking', 'No'),
(629, '12234', '2024-03-20', 'Consciousness', 'No'),
(630, '12234', '2024-03-20', 'Sloth', 'No'),
(631, '12234', '2024-03-20', 'Convulsions', 'No'),
(632, '12234', '2024-03-20', 'Everything in vomiting', 'No'),
(633, '12234', '2024-03-20', 'Unusual body movements', 'No'),
(634, '12234', '2024-03-20', 'Fast breathing', 'No'),
(635, '12234', '2024-03-20', 'Fever with rash', 'No'),
(636, '12234', '2024-03-20', 'Fatigue', 'No'),
(637, '12234', '2024-03-20', 'Irritable', 'No'),
(638, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(639, '12234', '2024-03-20', 'others', 'Nothing'),
(640, '12234', '2024-03-20', 'Drink', 'No'),
(641, '12234', '2024-03-20', 'அதில', 'No'),
(642, '12234', '2024-03-20', 'Sloth', 'No'),
(643, '12234', '2024-03-20', 'Fit', 'No'),
(644, '12234', '2024-03-20', 'Everything is vomiting', 'No'),
(645, '12234', '2024-03-20', 'Unusual physical movements', 'No'),
(646, '12234', '2024-03-20', 'Fast respiration', 'No'),
(647, '12234', '2024-03-20', 'Fever fever', 'No'),
(648, '12234', '2024-03-20', 'Fatigue', 'No'),
(649, '12234', '2024-03-20', 'Irritant', 'No'),
(650, '12234', '2024-03-20', 'Ear with discharge / pain', 'No'),
(651, '12234', '2024-03-20', 'others', 'Nothing');

-- --------------------------------------------------------

--
-- Table structure for table `report_time`
--

CREATE TABLE `report_time` (
  `sno` int(11) NOT NULL,
  `id` text NOT NULL,
  `report_time` text NOT NULL,
  `date` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `report_time`
--

INSERT INTO `report_time` (`sno`, `id`, `report_time`, `date`) VALUES
(1, '12234', '1', '2024-04-08'),
(2, '12234', '2', '2024-03-05'),
(3, '733720', '1', '2024-03-03'),
(4, '90909', '1', '2023-12-12'),
(5, '1111', '1', '2024-02-02'),
(6, '733720', '2', '2024-03-04'),
(7, '15293', '1', '2024-03-03'),
(8, '15293', '2', '2024-03-04');

-- --------------------------------------------------------

--
-- Table structure for table `treatment`
--

CREATE TABLE `treatment` (
  `id` varchar(200) NOT NULL,
  `Head_to_toe_examination` text NOT NULL,
  `General_Examination` text NOT NULL,
  `Systematic_Examination` text NOT NULL,
  `Treatment_given` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `treatment`
--

INSERT INTO `treatment` (`id`, `Head_to_toe_examination`, `General_Examination`, `Systematic_Examination`, `Treatment_given`) VALUES
('12234', 'no gross dysmorphic feature', 'no pallor, icterus, clubbing, pedal edema and lymphadenopathy', 'CVS: S1 AND S2 heard and no murmur\r\nRS: BAE+, no added sounds\r\nCNS: NFND\r\nABDOMEN: soft, no organomegaly, Tenderness + in the right hypochondriac region\r\n', 'Inj. XONE 2gm IV BD\r\nTab. DOXYCYCLINE 100mg PO BD\r\nInj. PAN 40mg IV OD\r\nInj. EMESET 4mg IV SOS\r\nTab. DOLO 650mg PO SOS\r\nZYTEE GEL for L/A TDS\r\n'),
('733720', 'no gross dysmorphic feature', 'no pallor, icterus, clubbing, pedal edema and lymphadenopathy', 'CVS: S1 AND S2 heard and no murmur\r\nRS: BAE+, no added sounds\r\nCNS: NFND\r\nABDOMEN: soft, no organomegaly, Tenderness + in the right hypochondriac region\r\n', 'Inj. XONE 2gm IV BD\r\nTab. DOXYCYCLINE 100mg PO BD\r\nInj. PAN 40mg IV OD\r\nInj. EMESET 4mg IV SOS\r\nTab. DOLO 650mg PO SOS\r\nZYTEE GEL for L/A TDS\r\n'),
('949194', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives', 'amar chanbdra hiv patients , doctor told they shouldnt have hopes on their lives');

-- --------------------------------------------------------

--
-- Table structure for table `vitals_at_admission`
--

CREATE TABLE `vitals_at_admission` (
  `id` varchar(200) NOT NULL,
  `Heart_rate` text NOT NULL,
  `Temperature` text NOT NULL,
  `CRT` text NOT NULL,
  `RR` text NOT NULL,
  `SPO2` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `vitals_at_admission`
--

INSERT INTO `vitals_at_admission` (`id`, `Heart_rate`, `Temperature`, `CRT`, `RR`, `SPO2`) VALUES
('12234', '101 beats per minute', '101.4°F', '<3sec', '24/min', '99%'),
('733720', '101 beats per minute', '101.4°F', '<3sec', '24/min', '99%'),
('949194', 'lm', 'ok', 'lok', 'oko', 'ok');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `chief_complaints`
--
ALTER TABLE `chief_complaints`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `course_dis`
--
ALTER TABLE `course_dis`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `development_history`
--
ALTER TABLE `development_history`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `discharge_summary`
--
ALTER TABLE `discharge_summary`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `dis_image`
--
ALTER TABLE `dis_image`
  ADD PRIMARY KEY (`sno`);

--
-- Indexes for table `doctor_login`
--
ALTER TABLE `doctor_login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `history_illness`
--
ALTER TABLE `history_illness`
  ADD PRIMARY KEY (`sno`);

--
-- Indexes for table `immunization_history`
--
ALTER TABLE `immunization_history`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login`
--
ALTER TABLE `login`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `login_status`
--
ALTER TABLE `login_status`
  ADD PRIMARY KEY (`device_name`);

--
-- Indexes for table `medicine`
--
ALTER TABLE `medicine`
  ADD PRIMARY KEY (`sno`);

--
-- Indexes for table `patient_details`
--
ALTER TABLE `patient_details`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `id` (`id`);

--
-- Indexes for table `questionnaire_doc`
--
ALTER TABLE `questionnaire_doc`
  ADD PRIMARY KEY (`sno`);

--
-- Indexes for table `questionnaire_response`
--
ALTER TABLE `questionnaire_response`
  ADD PRIMARY KEY (`sno`);

--
-- Indexes for table `report_time`
--
ALTER TABLE `report_time`
  ADD PRIMARY KEY (`sno`);

--
-- Indexes for table `treatment`
--
ALTER TABLE `treatment`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vitals_at_admission`
--
ALTER TABLE `vitals_at_admission`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `dis_image`
--
ALTER TABLE `dis_image`
  MODIFY `sno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `history_illness`
--
ALTER TABLE `history_illness`
  MODIFY `sno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT for table `medicine`
--
ALTER TABLE `medicine`
  MODIFY `sno` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `questionnaire_doc`
--
ALTER TABLE `questionnaire_doc`
  MODIFY `sno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT for table `questionnaire_response`
--
ALTER TABLE `questionnaire_response`
  MODIFY `sno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=652;

--
-- AUTO_INCREMENT for table `report_time`
--
ALTER TABLE `report_time`
  MODIFY `sno` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
