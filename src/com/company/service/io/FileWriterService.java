package com.company.service.io;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Serviciu de scriere in fisiere generic.
 * Poate scrie o singura data cu inchidere automata de fisier
 * Poate scrie de mai multe ori, fiind necesara inchiderea manuala de catre utilizator
 */

public class FileWriterService {

    private static FileWriterService instance = null;
    private File file;
    private boolean append = false;
    private BufferedWriter bufferedWriter;

    private FileWriterService() {
    }

    public static FileWriterService getInstance(String filePath, boolean append) throws IOException {
        if (instance == null) {
            instance = new FileWriterService();
        }
        instance.append = append;
        instance.file = new File(filePath);
        return instance;
    }

    public void write(String text) throws IOException {
        FileWriter fileWriter = new FileWriter(instance.file, append);
        try (BufferedWriter bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(text);
            bufferedWriter.newLine();
        }
    }

    public void write(String text, boolean closeFile) throws IOException {
        if (closeFile) {
            write(text);
        }
        if (bufferedWriter == null) {
            FileWriter fileWriter = new FileWriter(instance.file, append);
            bufferedWriter = new BufferedWriter(fileWriter);
        }
        bufferedWriter.write(text);
        bufferedWriter.newLine();
    }

    public boolean isEmptyFile() {
        return file.length() == 0;
    }

    public void closeFile() throws IOException {
        if (bufferedWriter != null) {
            bufferedWriter.close();
            bufferedWriter = null;
        }
    }

    public boolean existsFile() {
        return file.exists();
    }

    public void changeFile(String filePath, boolean append) throws IOException {
        closeFile();
        this.file = new File(filePath);
    }
}
