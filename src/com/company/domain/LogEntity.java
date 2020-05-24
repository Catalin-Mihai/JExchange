package com.company.domain;

public class LogEntity {
    //TODO: Subiect etc
    Integer id;
    String numeActiune;
    String timeStamp;
    LogLevel logLevel;
    String logMessage;

    /**
     * Log pentru obiecte de schimb valutar
     */

    public String getNumeActiune() {
        return numeActiune;
    }

    public void setNumeActiune(String numeActiune) {
        this.numeActiune = numeActiune;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public LogLevel getLogLevel() {
        return logLevel;
    }

    public void setLogLevel(LogLevel logLevel) {
        this.logLevel = logLevel;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(String logMessage) {
        this.logMessage = logMessage;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public enum LogLevel {
        LOW,
        MEDIUM,
        HIGH
    }

}
