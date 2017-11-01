package com.ef.util;

/**
 * Created by Oyewole Abayomi Samuel on 9/29/2017.
 * Email: oyewoleabayomi@gmail.com
 * Phone: +234 0706 331 7344
 */
public class Config {

    public static final long HOUR = 3600*1000;

    //Database credentials( PLEASE CHANGE)
    public static final String MYSQL_DB_USER = "root";
    public static final String MYSQL_DB_PASSWORD = "";

    //Database information
    public static final String MYSQL_DB_URL = "jdbc:mysql://localhost:3306/";
    public static final String MYSQL_TABLE_NAME = "weblogs";
    public static final String MYSQL_DB_NAME = "log";

    public static final String HOURLY = "hourly";
    public static final String DAILY = "daily";

    //Database column
    public static final String _ID = "_ID";
    public static final String IP_ADDRESS = "IP_ADDRESS";
    public static final String DURATION = "DURATION";
    public static final String THRESHOLD = "THRESHOLD";
//    public static final String START_DATE = "START_DATE";
//    public static final String END_DATE = "END_DATE";
    public static final String LOG_TIME = "LOG_TIME";
    public static final String COMMENT = "REASON";
    public static final String REQUESTS = "REQUESTS";
}
