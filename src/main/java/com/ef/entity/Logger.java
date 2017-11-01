package com.ef.entity;

import java.util.Objects;

/**
 * Created by Swifta System on 10/2/2017.
 * Email: ${USER_EMAIL}
 * Phone: ${USER_PHONE}
 * Website: ${USER_WEBSITE}
 */
public class Logger {

    private long _id;
    private String ipAddress;
    private String startDate;
    private String endDate;
    private Duration duration;
    private String comment;
    private String logTime;
    private int threshold;
    private int request;

    public Logger() {
    }

    public Logger(long _id, String ipAddress, String startDate, Duration duration, String comment, int threshold, int request) {
        this._id = _id;
        this.ipAddress = ipAddress;
        this.startDate = startDate;
        this.duration = duration;
        this.comment = comment;
        this.threshold = threshold;
        this.request = request;
    }

    public Logger(long _id, String ipAddress, String startDate, String endDate, Duration duration, String comment, int threshold, int request) {
        this._id = _id;
        this.ipAddress = ipAddress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.comment = comment;
        this.threshold = threshold;
        this.request = request;
    }

    public Logger(long _id, String ipAddress, String startDate, String endDate, Duration duration, String comment, String logTime, int threshold, int request) {
        this._id = _id;
        this.ipAddress = ipAddress;
        this.startDate = startDate;
        this.endDate = endDate;
        this.duration = duration;
        this.comment = comment;
        this.logTime = logTime;
        this.threshold = threshold;
        this.request = request;
    }

    public long get_id() {
        return _id;
    }

    public void set_id(long _id) {
        this._id = _id;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public void setDuration(Duration duration) {
        this.duration = duration;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getThreshold() {
        return threshold;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    public int getRequest() {
        return request;
    }

    public void setRequest(int request) {
        this.request = request;
    }

    public String getLogTime() {
        return logTime;
    }

    public void setLogTime(String logTime) {
        this.logTime = logTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Logger logger = (Logger) o;
        return threshold == logger.threshold &&
                Objects.equals(ipAddress, logger.ipAddress) &&
                Objects.equals(startDate, logger.startDate) &&
                Objects.equals(endDate, logger.endDate) &&
                Objects.equals(logTime, logger.logTime) &&
                duration == logger.duration &&
                Objects.equals(comment, logger.comment);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ipAddress, startDate, endDate, logTime, duration, comment, threshold);
    }

    @Override
    public String toString() {
        return "Logger{" +
                "_id=" + _id +
                ", ipAddress='" + ipAddress + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", duration=" + duration +
                ", comment='" + comment + '\'' +
                ", logTime='" + logTime + '\'' +
                ", threshold=" + threshold +
                ", request=" + request +
                '}';
    }
}
