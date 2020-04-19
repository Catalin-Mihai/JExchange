package com.company.service.io;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Seriviciu de citire generic.
 * Returneaza continutul sub forma unei liste de linii citite.
 */

public class FileReaderService {

    private static FileReaderService instance = null;
    private File file;
    private FileReader fileReader;

    private FileReaderService() {
    }

    public static FileReaderService getInstance(String filePath) throws IOException {
        if (instance == null) {
            instance = new FileReaderService();
        }
        instance.file = new File(filePath);
        instance.fileReader = new FileReader(instance.file);
        return instance;
    }

    public ArrayList<String> read() throws IOException {
        ArrayList<String> lines = new ArrayList<>();
        try (BufferedReader bufferedReader = new BufferedReader(fileReader)) {
            String currentLine;
            while ((currentLine = bufferedReader.readLine()) != null) {
                lines.add(currentLine);
            }
        }
        return lines;
    }

    public boolean isEmptyFile() {
        return file.length() == 0;
    }

    public void closeFile() throws IOException {
        fileReader.close();
    }

    public void changeFile(String filePath) throws IOException {
        closeFile();
        this.file = new File(filePath);
        fileReader = new FileReader(filePath);
    }
}
