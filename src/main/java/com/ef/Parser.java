package com.ef;

import com.ef.entity.Duration;
import com.ef.entity.InputRecord;
import com.ef.entity.Logger;
import com.ef.util.LogParser;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Oyewole Abayomi Samuel on 9/29/2017.
 * Email: oyewoleabayomi@gmail.com
 * Phone: +234 0706 331 7344
 */
public class Parser {

    public static void main(String[] args) {
        /**
         * Expected params
         * 1. startDate field[0]
         * 2. duration field[1]
         * 3. threshold field[3]
         */

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss", Locale.US);
        LogParser parser = new LogParser();

        if(args.length > 0) {

            InputRecord logger = new InputRecord();

            for (String s : args) {

                String[] params = s.split("=");

                switch (params[0]) {

                    case "--startDate":
                        try {
                            Date date = format.parse(params[1]);
                            logger.setStartDate(format.format(date));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;

                    case "--duration":
                        //Check if duration is null
                        if (Duration.findByLabel(params[1].toUpperCase()) == null) {
                            throw new IllegalArgumentException("Argument" + params[0] + " must be an hourly or daily");
                        }
                        logger.setDuration(Duration.findByLabel(params[1].toUpperCase()));
                        break;

                    case "--threshold":
                        if (Integer.valueOf(params[1]) <= 0) {
                            throw new IllegalArgumentException("Argument" + params[0] + " must be an integer");
                        }
                        logger.setThreshold(Integer.valueOf(params[1]));
                        break;

                    case "--accesslog":
                        String file_path = params[1];
                        if(!new File(file_path).exists()){
                            throw new IllegalArgumentException("Error: " + file_path + " does not exist.");
                        }
                        logger.setFile_path(file_path);
                        break;

                    case "--dbUser":
                        if(!params[1].equals("")){
                            logger.setDbUser(params[1].trim());
                        }
                        break;

                    case "--dbPass":
                        if(!params[1].equals("")){
                            logger.setDbPass(params[1].trim());
                        }
                        break;
                }
            }


            parser.setEndDate(logger);

            parser.parse(logger);

            //System.out.println(logger.toString());


        }

    }
}
