-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 29, 2023 at 11:12 PM
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
-- Database: `vagohotel`
--

DELIMITER $$
--
-- Procedures
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `GenerateReservationReference` (OUT `ref_number` VARCHAR(255))   BEGIN
    DECLARE timestamp_str VARCHAR(14);
    DECLARE counter INT DEFAULT 0;

    -- Use timestamp and a counter to generate a unique reference number
    SET timestamp_str = DATE_FORMAT(NOW(), '%Y%m%d%H%i%s');
    SET ref_number = CONCAT('RES', timestamp_str, counter);

    -- Ensure uniqueness using a counter
    WHILE EXISTS (SELECT 1 FROM reservations WHERE reference_number = ref_number) DO
        SET counter = counter + 1;
        SET ref_number = CONCAT('RES', timestamp_str, counter);
    END WHILE;

    -- Mark the generated reference number as used
    INSERT INTO reservations (reference_number) VALUES (ref_number);

    -- Debug information
    SELECT 'Generated Reference Number:', ref_number AS GeneratedReference;

END$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `employees`
--

CREATE TABLE `employees` (
  `employee_id` int(11) NOT NULL,
  `first_name` varchar(50) NOT NULL,
  `last_name` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `department` varchar(50) NOT NULL,
  `position` varchar(50) NOT NULL,
  `hire_date` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `employees`
--

INSERT INTO `employees` (`employee_id`, `first_name`, `last_name`, `email`, `department`, `position`, `hire_date`) VALUES
(1, 'Mohamed', 'Benali', 'mohamed.benali@example.com', 'Sales', 'Sales Representative', '2023-01-14'),
(2, 'Lina', 'Ait Khouya', 'lina.aitkhouya@example.com', 'Marketing', 'Coordinator', '2022-02-01'),
(3, 'Youssef', 'Zouhri', 'youssef.zouhri@example.com', 'IT', 'Developer', '2021-03-12'),
(4, 'Sara', 'Laroussi', 'sara.laroussi@example.com', 'Finance', 'Accountant', '2022-04-20'),
(6, 'Fatima', 'Najib', 'fatima.najib@example.com', 'Sales', 'Sales Manager', '2021-06-05'),
(7, 'Brahim', 'Bensouda', 'brahim.bensouda@example.com', 'IT', 'System Analyst', '2021-07-18'),
(8, 'Laila', 'Ouahbi', 'laila.ouahbi@example.com', 'Finance', 'Financial Analyst', '2022-08-09'),
(9, 'Youssef', 'El Haddad', 'youssef.haddad@example.com', 'Marketing', 'Marketing Manager', '2021-09-30'),
(10, 'Nawal', 'Chahbouni', 'nawal.chahbouni@example.com', 'Sales', 'Sales Representative', '2022-10-17'),
(11, 'Karim', 'Lahlou', 'karim.lahlou@example.com', 'Sales', 'Sales Representative', '2022-08-15'),
(12, 'Hiba', 'Arbel', 'hiba.arbel@example.com', 'Marketing', 'Coordinator', '2022-09-01'),
(13, 'Aya', 'Elmghari', 'aya.elmghari@example.com', 'IT', 'Developer', '2021-10-12'),
(14, 'Sofia', 'Tazi', 'sofia.tazi@example.com', 'Finance', 'Accountant', '2022-03-20'),
(15, 'Hamza', 'Fassi', 'hamza.fassi@example.com', 'IT', 'Database Administrator', '2022-04-28'),
(16, 'Sara', 'Hakimi', 'sara.hakimi@example.com', 'HR', 'HR Manager', '2022-03-05'),
(17, 'Othman', 'Berrada', 'othman.berrada@example.com', 'Marketing', 'Marketing Specialist', '2021-10-28'),
(18, 'Lina', 'Kharbouch', 'lina.kharbouch@example.com', 'Sales', 'Sales Representative', '2021-08-14'),
(19, 'Nabil', 'Sahraoui', 'nabil.sahraoui@example.com', 'IT', 'Software Engineer', '2021-12-30'),
(20, 'Asmae', 'El Ouazzani', 'asmae.ouazzani@example.com', 'Finance', 'Financial Controller', '2022-05-14'),
(21, 'Hassan', 'Bakrim', 'hassan.bakrim@example.com', 'HR', 'HR Specialist', '2021-06-22'),
(22, 'Samia', 'Idrissi', 'samia.idrissi@example.com', 'Marketing', 'Marketing Coordinator', '2022-07-08'),
(23, 'Adil', 'Moussaoui', 'adil.moussaoui@example.com', 'Sales', 'Sales Representative', '2023-07-07'),
(29, 'Omar', 'Abdallah', 'omar.abdallah@example.com', 'Marketing', 'Marketing Coordinator', '2022-01-15'),
(30, 'Amina', 'Taleb', 'amina.taleb@example.com', 'IT', 'Software Engineer', '2022-02-01'),
(31, 'Ali', 'Bouziane', 'ali.bouziane@example.com', 'Sales', 'Sales Representative', '2022-03-12'),
(32, 'Houda', 'Mahmoudi', 'houda.mahmoudi@example.com', 'Finance', 'Financial Analyst', '2022-04-20'),
(33, 'Khalid', 'El Fassi', 'khalid.elfassi@example.com', 'HR', 'HR Specialist', '2021-05-25'),
(34, 'Nadia', 'Sahraoui', 'nadia.sahraoui@example.com', 'Sales', 'Sales Manager', '2021-06-05'),
(35, 'Ahmed', 'Hassani', 'ahmed.hassani@example.com', 'IT', 'System Analyst', '2021-07-18'),
(36, 'Leila', 'Zerouali', 'leila.zerouali@example.com', 'Finance', 'Accountant', '2022-08-09'),
(37, 'Mehdi', 'El Khadir', 'mehdi.elkhadir@example.com', 'Marketing', 'Marketing Manager', '2021-09-30'),
(38, 'Hajar', 'Hafidi', 'hajar.hafidi@example.com', 'Sales', 'Sales Representative', '2022-10-17'),
(39, 'Yassine', 'El Amrani', 'yassine.elamrani@example.com', 'IT', 'Developer', '2022-08-15'),
(40, 'Sara', 'Boudraa', 'sara.boudraa@example.com', 'Marketing', 'Coordinator', '2022-09-01'),
(41, 'Mustapha', 'Lemrabet', 'mustapha.lemrabet@example.com', 'IT', 'Database Administrator', '2021-10-12'),
(42, 'Rania', 'Bennani', 'rania.bennani@example.com', 'Finance', 'Financial Controller', '2022-03-20'),
(43, 'Imane', 'Berrichi', 'imane.berrichi@example.com', 'Sales', 'Sales Manager', '2021-11-05'),
(44, 'Mehdi', 'El Mansouri', 'mehdi.elmansouri@example.com', 'IT', 'System Analyst', '2021-12-18'),
(45, 'Amina', 'Sahraoui', 'amina.sahraoui@example.com', 'Finance', 'Financial Analyst', '2022-01-09'),
(46, 'Hassan', 'El Fassi', 'hassan.elfassi@example.com', 'Marketing', 'Marketing Manager', '2021-09-30'),
(47, 'Sanaa', 'Chahbouni', 'sanaa.chahbouni@example.com', 'Sales', 'Sales Representative', '2022-02-17'),
(48, 'Yassir', 'Boutaleb', 'yassir.boutaleb@example.com', 'IT', 'Developer', '2022-04-28'),
(49, 'Nisrine', 'Hafidi', 'nisrine.hafidi@example.com', 'HR', 'HR Manager', '2022-03-05'),
(50, 'Omar', 'El Khattabi', 'omar.elkhattabi@example.com', 'Marketing', 'Marketing Specialist', '2021-10-28'),
(51, 'Leila', 'El Bahja', 'leila.elbahja@example.com', 'Sales', 'Sales Representative', '2021-08-14'),
(52, 'Nizar', 'Laroussi', 'nizar.laroussi@example.com', 'IT', 'Software Engineer', '2021-12-30'),
(53, 'Sara', 'El Ghazali', 'sara.elghazali@example.com', 'Finance', 'Financial Controller', '2022-05-14'),
(54, 'Ahmed', 'Bakrim', 'ahmed.bakrim@example.com', 'HR', 'HR Specialist', '2021-06-22'),
(55, 'Selma', 'Idrissi', 'selma.idrissi@example.com', 'Marketing', 'Marketing Coordinator', '2022-07-08'),
(56, 'ANASS', 'KASSIDE', 'anassqassid@outlook.com', 'IT', 'DEVELOPER', '2023-12-07'),
(58, 'HIBA', 'KASSIDE', 'hibaqassid@outlook.com', 'FINANCE', 'SENIOR MANAGER', '2022-11-30'),
(59, 'testname', 'testlastname', 'testemail@mail.com', 'testdepartment', 'testposition', '2023-12-14'),
(62, 'testname1', 'testlastname1', 'testemail1@mail.com', 'testdepartment1', 'testposition1', '2023-12-14'),
(63, 'testname2', 'testlastname2', 'testemail2@mail.com', 'testdepartment2', 'testposition2', '2023-12-04'),
(64, 'AHMED', 'FATINE', 'ahmedfatine@outlook.com', 'Marketing', 'Specialist', '2023-12-07'),
(65, 'ANASS', 'KASSIDE', 'anass.qassid@gmail.com', 'Marketing', 'Super Visor', '2023-12-05'),
(67, 'testname3', 'testlastname3', 'testname3@gmail.com', 'IT', 'Manager', '2023-12-19'),
(68, 'testempfirstname1', 'testemplastname1', 'testempfirstname1@gmail.com', 'Tech', 'Worker', '2023-12-06');

-- --------------------------------------------------------

--
-- Table structure for table `guests`
--

CREATE TABLE `guests` (
  `guest_id` int(11) NOT NULL,
  `first_name` varchar(255) NOT NULL,
  `last_name` varchar(255) NOT NULL,
  `email` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `guests`
--

INSERT INTO `guests` (`guest_id`, `first_name`, `last_name`, `email`, `password`) VALUES
(2, 'Amina', 'ElFassi', 'amina.elfassi@example.com', 'password456'),
(3, 'Youssef', 'Makrane', 'youssef.makrane@example.com', 'password789'),
(4, 'Fatima', 'ElIdrissi', 'fatima.elidrissi@example.com', ''),
(5, 'Ahmed', 'Bouaziz', 'ahmed.bouaziz@example.com', 'passworddef'),
(6, 'Hafsa', 'Amrani', 'hafsa.amrani@example.com', 'passwordeg'),
(7, 'Abdelali', 'Chakir', 'abdelali.chakir@example.com', 'passwordhij'),
(8, 'Sofia', 'Mernissi', 'sofia.mernissi@example.com', 'passwordklm'),
(9, 'Younes', 'El Amrani', 'younes.elamrani@example.com', 'passwordnop'),
(10, 'Laila', 'Zahiri', 'laila.zahiri@example.com', 'passwordqrs'),
(11, 'Khalid', 'Zouhair', 'khalid.zouhair@example.com', 'passwordtuv'),
(12, 'Houda', 'Ait Bouzid', 'houda.aitbouzid@example.com', 'passwordwxy'),
(13, 'Rachid', 'Bouabid', 'rachid.bouabid@example.com', 'password123z'),
(14, 'Naima', 'Chraibi', 'naima.chraibi@example.com', 'password456z'),
(15, 'Karim', 'Belkadi', 'karim.belkadi@example.com', 'password789z'),
(16, 'Zineb', 'Hassani', 'zineb.hassani@example.com', 'passwordabcz'),
(17, 'Mehdi', 'AitLahcen', 'mehdi.aitlahcen@example.com', 'password475s'),
(18, 'Leila', 'El Moudden', 'leila.elmoudden@example.com', 'passwordegz'),
(19, 'Abderrahim', 'El Khattabi', 'abderrahim.elkhattabi@example.com', 'passwordhijz'),
(20, 'Samira', 'Fakir', 'samira.fakir@example.com', 'passwordklmz'),
(21, 'Bilal', 'El Kaddouri', 'bilal.elkaddouri@example.com', 'passwordnopz'),
(22, 'Nawal', 'Choukri', 'nawal.choukri@example.com', 'passwordqrsz'),
(23, 'Mustapha', 'Hajji', 'mustapha.hajji@example.com', 'passwordtuvz'),
(24, 'Nadia', 'Lazaar', 'nadia.lazaar@example.com', 'passwordwxyz'),
(25, 'Hamza', 'Rahmani', 'hamza.rahmani@example.com', 'password123zz'),
(26, 'Sara', 'Zouiten', 'sara.zouiten@example.com', 'password456zz'),
(27, 'Reda', 'Ben Ali', 'reda.benali@example.com', 'password789zz'),
(28, 'Fatiha', 'Benjelloun', 'fatiha.benjelloun@example.com', 'passwordabca'),
(29, 'Adil', 'El Majdoub', 'adil.elmajdoub@example.com', 'passworddeff'),
(30, 'Saida', 'El Bouzidi', 'saida.elbouzidi@example.com', 'passwordghij'),
(31, 'Ismail', 'Choukrane', 'ismail.choukrane@example.com', 'passwordklmn'),
(32, 'Asmaa', 'Jabri', 'asmaa.jabri@example.com', 'passwordopqr'),
(33, 'Mehdi', 'Bouhadda', 'mehdi.bouhadda@example.com', 'passwordstuv'),
(34, 'Naima', 'Bentayeb', 'naima.bentayeb@example.com', 'passwordwxyz1'),
(35, 'Yassine', 'Boukhriss', 'yassine.boukhriss@example.com', 'password2345'),
(36, 'Latifa', 'El Hassani', 'latifa.elhassani@example.com', 'password6789'),
(37, 'Mohammed', 'Boudraa', 'mohammed.boudraa@example.com', 'password0ab'),
(38, 'Fadwa', 'El Fakir', 'fadwa.elfakir@example.com', 'passwordcd12'),
(39, 'Hassan', 'Bennani', 'hassan.bennani@example.com', 'password34ef'),
(40, 'Karima', 'Chkiri', 'karima.chkiri@example.com', 'passwordgh56'),
(41, 'Youness', 'Boulmane', 'youness.boulmane@example.com', 'passwordij78'),
(42, 'Nadia', 'El Fatihi', 'nadia.elfatihi@example.com', 'passwordkl90'),
(43, 'Soukaina', 'Amer', 'soukaina.amer@example.com', 'passwordmnop'),
(44, 'Amir', 'Benguerir', 'amir.benguerir@example.com', 'passwordqrst'),
(45, 'Rim', 'El Gara', 'rim.elgara@example.com', 'passworduvwx'),
(46, 'Mohammed', 'Boulmane', 'mohammed.boulmane2@example.com', 'passwordyz12'),
(47, 'Zahra', 'Sefrou', 'zahra.sefrou@example.com', 'password3456'),
(48, 'Omar', 'Oujda', 'omar.oujda@example.com', 'password7890'),
(49, 'Nisrine', 'Errachidia', 'nisrine.errachidia@example.com', 'passwordabcf'),
(50, 'Kamal', 'Agadir', 'kamal.agadir@example.com', 'passworddefg'),
(51, 'Naima', 'El Jadida', 'naima.eljadida@example.com', 'passwordhijk'),
(52, 'Hicham', 'Taroudant', 'hicham.taroudant@example.com', 'passwordlmno'),
(53, 'Hanane', 'Guelmim', 'hanane.guelmim@example.com', 'passwordpqrst'),
(54, 'Mehdi', 'Kenitra', 'mehdi.kenitra@example.com', 'passworduvwxy'),
(55, 'Noureddine', 'Laayoune', 'noureddine.laayoune@example.com', 'passwordz123'),
(56, 'Asmaa', 'Marrakech', 'asmaa.marrakech@example.com', 'password4567'),
(57, 'Mohammed', 'Mohammedia', 'mohammed.mohammedia@example.com', 'password8901'),
(59, 'Ali', 'Dakhla', 'ali.dakhla@example.com', 'password6789b'),
(60, 'Salma', 'Tangier', 'salma.tangier@example.com', 'password0abc'),
(61, 'Abdelkader', 'Taza', 'abdelkader.taza@example.com', 'passwordcdef'),
(62, 'Khadija', 'Tetouan', 'khadija.tetouan@example.com', 'passwordghijg'),
(63, 'Nabil', 'Essaouira', 'nabil.essaouira@example.com', 'passwordklmnoh'),
(64, 'Fatima', 'Safi', 'fatima.safi@example.com', 'passwordpqrstu'),
(65, 'Mouad', 'Fes', 'mouad.fes@example.com', 'passwordvwxyzu'),
(66, 'Amina', 'Rabat', 'amina.rabat@example.com', 'password1234a'),
(67, 'Omar', 'Casablanca', 'omar.casablanca@example.com', 'password5678b'),
(68, 'Sanaa', 'Agadir', 'sanaa.agadir@example.com', 'password90abc'),
(69, 'Ali', 'Marrakech', 'ali.marrakech@example.com', 'passworddefgh'),
(70, 'Yasmina', 'Tangier', 'yasmina.tangier@example.com', 'passwordijklm'),
(71, 'Rachid', 'Tetouan', 'rachid.tetouan@example.com', 'passwordnopqr'),
(73, 'Mohammed', 'Safi', 'mohammed.safi@example.com', 'passwordxyz12'),
(74, 'Abdelmajid', 'Kasside', 'abdelmajid.kasside@example.com', 'password777'),
(75, 'testclientname', 'testclientlastname', 'testclient@gmail.com', 'passowrdclient1234');

-- --------------------------------------------------------

--
-- Table structure for table `reservations`
--

CREATE TABLE `reservations` (
  `reservation_id` int(11) NOT NULL,
  `reference_number` varchar(255) DEFAULT NULL,
  `room_number` int(11) DEFAULT NULL,
  `checkin_date` date DEFAULT NULL,
  `checkout_date` date DEFAULT NULL,
  `guest_id` int(11) DEFAULT NULL,
  `number_of_guests` int(11) DEFAULT NULL,
  `reservation_status` enum('Confirmed','Not Confirmed','Cancelled','Ended') DEFAULT 'Not Confirmed'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `reservations`
--

INSERT INTO `reservations` (`reservation_id`, `reference_number`, `room_number`, `checkin_date`, `checkout_date`, `guest_id`, `number_of_guests`, `reservation_status`) VALUES
(1, 'RES00001', 8, '2024-04-16', '2024-06-10', 11, 4, 'Confirmed'),
(2, 'RES00002', 20, '2024-01-23', '2024-12-02', 39, 2, 'Confirmed'),
(3, 'RES00003', 32, '2024-09-16', '2024-08-14', 63, 3, 'Confirmed'),
(4, 'RES00004', 5, '2024-05-10', '2024-09-03', 7, 3, 'Confirmed'),
(5, 'RES00005', 17, '2024-07-11', '2024-02-17', 27, 4, 'Confirmed'),
(6, 'RES00006', 29, '2024-08-13', '2024-04-25', 59, 2, 'Confirmed'),
(8, 'RES00008', 14, '2024-07-19', '2024-11-01', 23, 3, 'Confirmed'),
(9, 'RES00009', 26, '2024-03-23', '2024-04-05', 47, 4, 'Confirmed'),
(10, 'RES00010', 11, '2024-04-28', '2024-04-22', 19, 2, 'Confirmed'),
(11, 'RES00011', 23, '2024-05-29', '2024-04-17', 43, 3, 'Confirmed'),
(12, 'RES00012', 35, '2024-12-15', '2024-12-08', 67, 4, 'Confirmed'),
(13, 'RES2023122715562445', 10, '2023-12-28', '2024-01-05', 9, 2, 'Not Confirmed'),
(14, 'RES202312271613051', 1, '2024-01-03', '2024-01-05', 56, 2, 'Not Confirmed'),
(15, 'RES202312271620411', 3, '2023-12-28', '2024-01-12', 23, 2, 'Not Confirmed');

-- --------------------------------------------------------

--
-- Table structure for table `rooms`
--

CREATE TABLE `rooms` (
  `room_number` int(11) NOT NULL,
  `room_type` varchar(255) NOT NULL,
  `capacity` int(11) NOT NULL,
  `amenities` varchar(255) NOT NULL,
  `availability_status` varchar(255) NOT NULL,
  `price_per_night` double NOT NULL,
  `guest_id` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `rooms`
--

INSERT INTO `rooms` (`room_number`, `room_type`, `capacity`, `amenities`, `availability_status`, `price_per_night`, `guest_id`) VALUES
(1, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Booked', 100, NULL),
(2, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Booked', 100, 15),
(3, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Booked', 100, 23),
(4, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Available', 150, NULL),
(5, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Booked', 150, 7),
(6, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Not Available', 150, NULL),
(7, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Available', 200, NULL),
(8, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Booked', 200, 11),
(9, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Not Available', 200, NULL),
(10, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Booked', 100, NULL),
(11, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Booked', 100, 19),
(12, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Not Available', 100, NULL),
(13, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Available', 150, NULL),
(14, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Booked', 150, 23),
(15, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Not Available', 150, NULL),
(16, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Available', 200, NULL),
(17, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Booked', 200, 27),
(18, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Not Available', 200, NULL),
(19, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Available', 100, NULL),
(20, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Booked', 100, 39),
(21, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Not Available', 100, NULL),
(22, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Available', 150, NULL),
(23, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Booked', 150, 43),
(24, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Not Available', 150, NULL),
(25, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Available', 200, NULL),
(26, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Booked', 200, 47),
(27, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Not Available', 200, NULL),
(28, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Available', 100, NULL),
(29, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Booked', 100, 59),
(30, 'Standard', 2, 'Wi-Fi, TV, Bathroom', 'Not Available', 100, NULL),
(31, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Available', 150, NULL),
(32, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Booked', 150, 63),
(33, 'Deluxe', 3, 'Wi-Fi, TV, Bathroom, Balcony', 'Not Available', 150, NULL),
(34, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Available', 200, NULL),
(35, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Booked', 200, 67),
(36, 'Suite', 4, 'Wi-Fi, TV, Bathroom, Living Room, Kitchenette', 'Not Available', 200, NULL),
(99, 'Standard', 2, 'Wi-Fi,  TV,  Bathroom', 'Not Available', 170, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `full_name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `role` enum('ADMIN','AGENT') NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`user_id`, `username`, `password`, `full_name`, `email`, `role`, `created_at`) VALUES
(1, 'admin', 'adminpass', 'YOUNESS KASSIDE', 'kasside.youness@emsi-edu.ma', 'ADMIN', '2023-12-12 20:24:49');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `employees`
--
ALTER TABLE `employees`
  ADD PRIMARY KEY (`employee_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `guests`
--
ALTER TABLE `guests`
  ADD PRIMARY KEY (`guest_id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- Indexes for table `reservations`
--
ALTER TABLE `reservations`
  ADD PRIMARY KEY (`reservation_id`),
  ADD UNIQUE KEY `reference_number` (`reference_number`),
  ADD KEY `room_number` (`room_number`),
  ADD KEY `guest_id` (`guest_id`);

--
-- Indexes for table `rooms`
--
ALTER TABLE `rooms`
  ADD PRIMARY KEY (`room_number`),
  ADD KEY `guest_id` (`guest_id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`user_id`),
  ADD UNIQUE KEY `unique_username` (`username`),
  ADD UNIQUE KEY `unique_email` (`email`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `employees`
--
ALTER TABLE `employees`
  MODIFY `employee_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=69;

--
-- AUTO_INCREMENT for table `guests`
--
ALTER TABLE `guests`
  MODIFY `guest_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=76;

--
-- AUTO_INCREMENT for table `reservations`
--
ALTER TABLE `reservations`
  MODIFY `reservation_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `rooms`
--
ALTER TABLE `rooms`
  MODIFY `room_number` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=100;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `reservations`
--
ALTER TABLE `reservations`
  ADD CONSTRAINT `reservations_ibfk_1` FOREIGN KEY (`room_number`) REFERENCES `rooms` (`room_number`),
  ADD CONSTRAINT `reservations_ibfk_2` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`guest_id`);

--
-- Constraints for table `rooms`
--
ALTER TABLE `rooms`
  ADD CONSTRAINT `rooms_ibfk_1` FOREIGN KEY (`guest_id`) REFERENCES `guests` (`guest_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
