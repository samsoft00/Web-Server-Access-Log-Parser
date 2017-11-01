package com.ef.util;

import com.ef.db.MysqlDb;
import com.ef.entity.Duration;
import com.ef.entity.InputRecord;
import com.ef.entity.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.ef.util.Config.HOUR;

/**
 * Created by Oyewole Abayomi Samuel on 9/29/2017.
 * Email: oyewoleabayomi@gmail.com
 * Phone: +234 0706 331 7344
 */
public class LogParser {

    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss", Locale.US);

    private Date convertTimetoEpoch(String date) {
        try {
            return format.parse(date);
        } catch (ParseException e) {
            System.out.println(" "+e.getMessage() );
        }
        return new Date();
    }

    private String formatDateTimeToProperString(String date){
        try {
            String dateTime = date;
            //check if date contain space
            if(date.contains(" ")){
                dateTime = date.replace(" ", ".");
            }

            return format.format( format.parse(dateTime) );
        } catch (ParseException e) {
            System.out.println(""+ e.getMessage());
        }

        return null;
    }

    private String accessLogRegex()
    {
//        String regex1 = "^([\\d.]+)"; // digits and dots: the IP
//        String regex2 = " \\|"; // why match any character if you *know* there is a pipe?
//        String regex3 = " ((?:\\d+[-:.])+\\d+)"; // match the date; don't capture the inner group as we are only interested in the full date
//        String regex4 = " \\|"; // pipe
//        String regex5 = " \"(.+)\""; // request method and url
//        String regex6 = " \\|"; // pipe
//        String regex7 = " (\\d{3})"; // HTTP code
//        String regex8 = " \\|"; // pipe
//        String regex9 = " (\\d+)"; // Number of bytes
//        String regex10 = " \\|"; // pipe again
//        String regex11 = " (.+)"; // The rest of the line is the user agent

//        return regex1+regex2+regex3;//+regex4+regex5+regex6+regex7+regex8+regex9+regex10+regex11;

//        return "^([\\d.]+) (\\S+) (\\S+) \\[(((?:\\d+[-:.])+\\d+)+\\s[+-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"";

        return "^([\\d.]+) (\\S+) (\\S+) \\[(((?:\\d+[-:.])+\\d+)+\\s[+-]\\d{4})\\] \"(.+?)\" (\\d{3}) (\\d+) \"([^\"]+)\" \"([^\"]+)\"";

    }

    public void setEndDate( InputRecord logParser){

        int durationInt = logParser.getDuration().equals(Duration.HOURLY) ? 1 : 24;

        Date mData = null;
        try {

            mData = format.parse( logParser.getStartDate() );
            Date newDate = new Date(mData.getTime() + durationInt * HOUR);
            logParser.setEndDate(format.format(newDate));

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    public void parse(InputRecord log) {

        String line = "";
        int index = 1;
        BufferedReader reader = null;
        List<Logger> logEntityList      = new ArrayList<>();
        Map<Logger, Integer> logData    = new HashMap<>();
        final String delimiterPattern = "\\|";

        MysqlDb mysqlDb = new MysqlDb(log.getDbUser(), log.getDbPass());
        mysqlDb.init();

//        Date startingEpoch  = convertTimetoEpoch(log.getStartDate());
//        Date endingEpoch    = convertTimetoEpoch(log.getEndDate());

        Pattern accessLogPattern = Pattern.compile(accessLogRegex());
        Matcher entryMatcher;

        try {

            reader = new BufferedReader(new FileReader(log.getFile_path()));
            while ((line = reader.readLine()) != null){

                String[] payload = line.split(delimiterPattern);//split
                String logTime = formatDateTimeToProperString(payload[0]);

                if(this.checkDateRange(log.getStartDate(), log.getEndDate(), logTime)){

                    Logger newLogEntity = new Logger();
                    newLogEntity.setIpAddress( payload[1] );
                    newLogEntity.setDuration( log.getDuration() );
                    newLogEntity.setThreshold( log.getThreshold() );
                    newLogEntity.setStartDate( log.getStartDate() );
                    String comment = Duration.DAILY.equals(log.getDuration()) ? "Made more than "+log.getThreshold()+" requests in 24hours": "Made more than "+log.getThreshold()+" requests in 1 hour";
                    newLogEntity.setComment( comment );
                    newLogEntity.setEndDate( log.getEndDate() );
                    newLogEntity.setLogTime(logTime);

                    if(logData.containsKey(newLogEntity)){
                        logData.put(newLogEntity, logData.get(newLogEntity)+1);
                    }else{
                        logData.put(newLogEntity, 1);
                    }
                }

                index++;
            }


        } catch (IOException e) {
            e.printStackTrace();
        }catch (IllegalStateException ie){}


        //PERFORM LOGIC
        if(!logData.isEmpty()){

            for (Map.Entry<Logger, Integer> entry : logData.entrySet()) {
                Logger logger = entry.getKey();
                Integer value = entry.getValue();

                if(value >= log.getThreshold()){
                    logger.setRequest(value);
                    logEntityList.add(logger);
                }
            }
        }

        //Print and insert to database
        if(logEntityList.isEmpty()){
            mysqlDb.print(logEntityList);
            System.out.println("Printer: There's no data to display.");
            System.out.println("Database: No data to insert to database.");
        }else{

            mysqlDb.print(logEntityList);

            Iterator<Logger> me = logEntityList.iterator();
            while (me.hasNext()){
                mysqlDb.insertQuery(me.next());
            }
            System.out.println("All done!");
        }

    }

    public boolean checkDateRange(String startDate, String endDate, String logTime){

        try {
            Date sdate = format.parse(startDate);
            DateTime start = new DateTime(sdate);

            Date edate = format.parse(endDate);
            DateTime end = new DateTime(edate);

            Date ldate = format.parse(logTime);
            DateTime log = new DateTime(ldate);

            if (log.isAfter(start) && log.isBefore(end)) {
                return true;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;

    }

}
