# Web-Server-Access-Log-Parser
The goal is to write a parser in Java that parses web server access log file, loads the log to MySQL and checks if a given IP makes more than a certain number of requests for the given duration. 
<h3>Solution</h3>
<img src="https://github.com/samsoft00/Web-Server-Access-Log-Parser/blob/master/Parser/cmd_example.png" alt="Web Server Access Log Parser">
==========================================================
<p>Developer: Oyewole Abayomi Samuel.<br/>
Position: Senior Java Developer.<br/>
Country: Nigeria.<br/>
Skype: oyewoleabayomi@gmail.com<br/>
Date: 2017-05-10</p>
---------------------------------
Deliverables
---------------------------------

<h4>(1) Java program that can be run from command line</h4>
<h3>HOW TO RUN JAVA PROGRAM FROM CMD - USER MANUAL</h3>
==================================================

<p>1. Extract the .jar file into a directory e.g Desktop/Download directory<br/>
2. Launch MYSQL client using Xampp OR Wampp, ensure MYSQL is on runing port - 3306<br/>
3. Create Database using this SQL below (See full information below)<br/>
   CREATE DATABASE weblogs;<br/>
4. Next, open your favorite command line, cd into directory where you have parser.jar and run the following command.<br/>
   java -cp "parser.jar" com.ef.Parser --accesslog="/path/to/file" --startDate=2017-01-01.13:05:00 --duration=hourly --threshold=100<br/>
   
Please note that the default MYSQL credential is<br/>
MYSQL Username: 'root'<br/>
MYSQL Password: ''<br/>
</P>
<p>You can config your MYSQL client to use above credentials or run the program to dynamically by including MYSQL credentials at run time like this<br/>
java -cp "parser.jar" com.ef.Parser --accesslog="/path/to/file" --startDate=2017-01-01.13:05:00 --duration=hourly --threshold=100 --dbUser=root --dbPass=mysql</p>

<a href="https://github.com/samsoft00/Web-Server-Access-Log-Parser/blob/master/Parser/readme.txt">See full Manual</a>