Developer: Oyewole Abayomi Samuel.
Position: Senior Java Developer.
Country: Nigeria.
Skype: oyewoleabayomi@gmail.com
Date: 2017-05-10
---------------------------------
Deliverables
---------------------------------

(1) Java program that can be run from command line
--------------------------------------------------
HOW TO RUN JAVA PROGRAM FROM CMD - USER MANUAL
==================================================

1. Extract the .jar file into a directory e.g Desktop/Download directory
2. Launch MYSQL client using Xampp OR Wampp, ensure MYSQL is on runing port - 3306
3. Create Database using this SQL below (See full information below)
   CREATE DATABASE weblogs;
4. Next, open your favorite command line, cd into directory where you have parser.jar and run the following command.
   java -cp "parser.jar" com.ef.Parser --accesslog="/path/to/file" --startDate=2017-01-01.13:05:00 --duration=hourly --threshold=100
   
Please note that the default MYSQL credential is
MYSQL Username: 'root'
MYSQL Password: ''

You can config your MYSQL client to use above credentials or run the program to dynamically by including MYSQL credentials at run time like this
java -cp "parser.jar" com.ef.Parser --accesslog="/path/to/file" --startDate=2017-01-01.13:05:00 --duration=hourly --threshold=100 --dbUser=root --dbPass=mysql

(2) MySQL schema used for the log data
---------------------------------------
INSERT INTO `weblogs.hourlylog` (`IP_ADDRESS`, `DURATION`, `THRESHOLD`, `REQUESTS`, `REASON`, `LOG_TIME`) VALUES ('174.129.239.67','HOURLY', 100, 151, 'Made more than 100 requests in 1hour', '2017-01-01.13:00:00')

INSERT INTO `weblogs.dailylog` (`IP_ADDRESS`, `DURATION`, `THRESHOLD`, `REQUESTS`, `REASON`, `LOG_TIME`) VALUES ('174.129.239.67','DAILY', 2500, 272, 'Made more than 250 requests in 24 hour', '2017-01-01.13:00:00')

(3) SQL queries for SQL test
-------------------------------
(i) MySQL query to find IPs that made more than a certain number of requests for a given time period
SELECT * FROM `hourlylog` WHERE LOG_TIME BETWEEN '2017-01-01.13:00:00' AND '2017-01-01.14:00:00' AND REQUESTS >= '100'
SELECT * FROM `dailylog` WHERE LOG_TIME BETWEEN '2017-01-01.13:00:00' AND '2017-01-01.14:00:00' AND REQUESTS >= '250'

(ii) Write MySQL query to find requests made by a given IP. 
SELECT * FROM `hourlylog` WHERE IP_ADDRESS = '174.129.239.67';
SELECT * FROM `dailylog` WHERE IP_ADDRESS = '174.129.239.67';

============================================================================================
TO MANUALLY SETUP THE DATABASE, USE THE SQL QUERIES BELOW - NOT IMPORTANT
============================================================================================
-- Create Database
CREATE DATABASE weblogs;

-- Table structure for table `hourlylog` and 
`dailylog`
CREATE TABLE `hourlylog` (
  `_ID` int(11) NOT NULL,
  `IP_ADDRESS` varchar(50) NOT NULL,
  `DURATION` varchar(10) NOT NULL,
  `THRESHOLD` int(10) NOT NULL,
  `REQUESTS` int(10) NOT NULL,
  `REASON` text NOT NULL,
  `LOG_TIME` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE `dailylog` (
  `_ID` int(11) NOT NULL,
  `IP_ADDRESS` varchar(50) NOT NULL,
  `DURATION` varchar(10) NOT NULL,
  `THRESHOLD` int(10) NOT NULL,
  `REQUESTS` int(10) NOT NULL,
  `REASON` text NOT NULL,
  `LOG_TIME` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- Indexes for table `hourlylog` and `dailylog`
ALTER TABLE `hourlylog`
  ADD PRIMARY KEY (`_ID`),
  ADD UNIQUE KEY `IP_ADDRESS` (`IP_ADDRESS`);
ALTER TABLE `dailylog`
  ADD PRIMARY KEY (`_ID`),
  ADD UNIQUE KEY `IP_ADDRESS` (`IP_ADDRESS`);

-- AUTO_INCREMENT for table `hourlylog` and `dailylog`
ALTER TABLE `hourlylog`
  MODIFY `_ID` int(11) NOT NULL AUTO_INCREMENT;
ALTER TABLE `dailylog`
  MODIFY `_ID` int(11) NOT NULL AUTO_INCREMENT;