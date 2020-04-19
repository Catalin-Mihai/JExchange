package com.company.service;

import com.company.domain.Log;
import com.company.service.io.FileWriterService;
import com.company.util.factory.LogFactory;

import java.io.IOException;

public class LogService {

    //Se vrea stocarea Log-urilor?
    //LogRepository logRepository = new LogRepository();

    private static LogService instance = null;
    private final String logFileHeader = "\"Data\", \"Nivel\", \"Actiune\", \"Mesaj\"";
    private final String defaultLogFileName = "logs.csv";
    private String logFileName;

    private LogService() { }

    public static LogService getInstance() {
        if (instance == null) {
            instance = new LogService();
        }
        return instance;
    }

    public static LogService getInstance(String logFileName) {
        LogService instance = getInstance();
        instance.logFileName = logFileName;
        return instance;
    }

    private void writeToFile(Log log) {
        try {
            FileWriterService fileWriterService = FileWriterService.getInstance(logFileName, true);
            if (fileWriterService.isEmptyFile()) {
                //Scriem numele coloanelor daca fisierul e gol
                fileWriterService.write(logFileHeader);
            }
            // Formatare text
            String text = "[\"" + log.getTimeStamp() + "\"] "
                    + "[\"" + log.getLogLevel() + "\"] "
                    + "[\"" + log.getNumeActiune() + "\"] "
                    + "[\"" + log.getLogMessage() + "\"]";
            fileWriterService.write(text);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void Log(LogTypes logType, String... params) {
        Log log = LogFactory.getLog(logType, params);
        writeToFile(log);
    }

    public void setLogFileName(String logFileName) {
        this.logFileName = logFileName;
    }

    public enum LogTypes {

        CLIENT_CHANGE_LASTNAME,
        CLIENT_CHANGE_FIRSTNAME,
        CLIENT_RESET_MONEY,
        CLIENT_RESET_ALL_MONEY,
        CLIENT_ADD_MONEY,
        CLIENT_DECREASE_MONEY,
        CLIENT_INCREASE_MONEY,
        CLIENT_ADD_CLIENT,

        CURRENCY_ADD_CURRENCY,
        CURRENCY_DELETE_CURRENCY,
        CURRENCY_CHANGE_CURRENCY_NAME,
        CURRENCY_CHANGE_CURRENCY_SYMBOL,
        CURRENCY_CHANGE_CURRENCY_COUNTRY,

        OFFICE_ADD_MONEY,
        OFFICE_INCREASE_MONEY,
        OFFICE_DECREASE_MONEY,
        OFFICE_RESET_MONEY,

        EXCHANGE_EXCHANGE_MONEY,
        EXCHANGE_ADD_EXCHANGE_RATE
    }
}
