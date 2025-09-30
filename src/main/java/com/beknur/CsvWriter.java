package com.beknur;

import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;

public class CsvWriter {
    public static void write(String file, String[] headers, String[][] data) throws IOException {
        try (CSVWriter writer = new CSVWriter(new FileWriter(file))) {
            writer.writeNext(headers);
            for (String[] row : data) writer.writeNext(row);
        }
    }
}