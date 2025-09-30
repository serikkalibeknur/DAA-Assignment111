package com.beknur;

import java.io.IOException;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        // Parse command-line arguments
        if (args.length < 2) {
            System.out.println("Usage: java -jar target/assignment1.jar <algorithm> <n> [runs]");
            System.out.println("Algorithms: mergesort, quicksort, select, closest");
            return;
        }

        String algorithm = args[0].toLowerCase();
        int n = Integer.parseInt(args[1]);
        int runs = (args.length > 2) ? Integer.parseInt(args[2]) : 1;

        // Generate random array
        Random rand = new Random();
        int[] a = new int[n];
        for (int i = 0; i < n; i++) a[i] = rand.nextInt(1000); // Random values 0-999

        // Metrics collection
        Metrics m = new Metrics();
        m.start();

        // Run the specified algorithm
        switch (algorithm) {
            case "mergesort":
                MergeSort.sort(a.clone(), m); // Clone to preserve original
                break;
            case "quicksort":
                QuickSort.sort(a.clone(), m);
                break;
            case "select":
                Select.select(a.clone(), n / 2, m); // Median as example k
                break;
            case "closest":
                Point[] points = new Point[n];
                for (int i = 0; i < n; i++) points[i] = new Point(rand.nextDouble() * 1000, rand.nextDouble() * 1000);
                ClosestPair.closest(points, m);
                break;
            default:
                System.out.println("Unknown algorithm: " + algorithm);
                return;
        }

        m.end();

        // Write metrics to CSV
        String[] headers = {"Algorithm", "Size", "Runs", "Time(ns)", "MaxDepth", "Comparisons"};
        String[][] data = new String[runs][6];
        for (int i = 0; i < runs; i++) {
            data[i][0] = algorithm;
            data[i][1] = String.valueOf(n);
            data[i][2] = String.valueOf(runs);
            data[i][3] = String.valueOf(m.getTimeNs());
            data[i][4] = String.valueOf(m.maxDepth);
            data[i][5] = String.valueOf(m.comparisons);
        }

        try (CSVWriter writer = new CSVWriter(new FileWriter("results.csv", true))) {
            if (runs == 1) writer.writeNext(headers); // Write headers only once
            writer.writeAll(java.util.Arrays.asList(data));
        } catch (IOException e) {
            System.err.println("Error writing to CSV: " + e.getMessage());
        }

        System.out.println("Completed " + algorithm + " for n=" + n + " in " + m.getTimeNs() + " ns");
    }
}