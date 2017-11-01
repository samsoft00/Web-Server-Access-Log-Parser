package com.ef.entity;

/**
 * Created by Swifta System on 10/2/2017.
 * Email: ${USER_EMAIL}
 * Phone: ${USER_PHONE}
 * Website: ${USER_WEBSITE}
 */
public class InputRecord {

    private String file_path;
    private String startDate;
    private String endDate;
    private Duration duration;
    private String dbUser;
    private String dbPass;
    private int threshold;

    public InputRecord(){}

    public InputRecord(String file_path, String startDate, String endDate, Duration duration, int threshold) {
        this.file_path = file_path;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.threshold = threshold;
    }

    public String getDbUser() {
        return dbUser;
    }

    public void setDbUser(String dbUser) {
        this.dbUser = dbUser;
    }

    public String getDbPass() {
        return dbPass;
    }

    public void setDbPass(String dbPass) {
        this.dbPass = dbPass;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    @Override
    public String toString() {
        return "InputRecord{" +
                "file_path='" + file_path + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", duration=" + duration +
                ", dbUser='" + dbUser + '\'' +
                ", dbPass='" + dbPass + '\'' +
                ", threshold=" + threshold +
                '}';
    }
}
