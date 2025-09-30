package com.beknur;

import java.util.Random;

public class Util {
    private static final Random rand = new Random();

    public static void swap(int[] a, int i, int j) {
        if (a == null || i < 0 || j < 0 || i >= a.length || j >= a.length) {
            throw new IllegalArgumentException("Invalid array indices");
        }
        int t = a[i];
        a[i] = a[j];
        a[j] = t;
    }

    public static void shuffle(int[] a) {
        if (a == null) throw new IllegalArgumentException("Array cannot be null");
        for (int i = a.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(a, i, j);
        }
    }

    public static int partition(int[] a, int lo, int hi, Metrics m) {
        if (a == null || lo < 0 || hi >= a.length || lo > hi) {
            throw new IllegalArgumentException("Invalid partition range");
        }
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (a[j] < pivot) {
                swap(a, i, j);
                i++;
                if (m != null) m.incCompare();
            }
        }
        swap(a, i, hi);
        return i;
    }
}