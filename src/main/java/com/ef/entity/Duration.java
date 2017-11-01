package com.ef.entity;

/**
 * Created by Oyewole Abayomi Samuel on 9/29/2017.
 * Email: oyewoleabayomi@gmail.com
 * Phone: +234 0706 331 7344
 */
public enum Duration {

    HOURLY("HOURLY"),
    DAILY("DAILY");

    private String label;

    Duration(String label) {
        this.label = label;
    }

    Duration() {}

    public static Duration findByLabel(String byLabel){

        for(Duration type : Duration.values()){

            if(type.label.equalsIgnoreCase(byLabel.trim())){
                return type;
            }
        }
        return null;
    }

}
