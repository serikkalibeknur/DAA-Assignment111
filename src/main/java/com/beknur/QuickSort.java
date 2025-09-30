package com.beknur;

import java.util.Random;

public class QuickSort {
    private static final Random rand = new Random();

    public static void sort(int[] a, Metrics m) {
        shuffle(a); // Randomized
        sort(a, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            m.enterDepth();
            int p = partition(a, lo, hi, m);
            // Recurse smaller, iterate larger
            if (p - lo < hi - p) {
                sort(a, lo, p - 1, m);
                lo = p + 1;
            } else {
                sort(a, p + 1, hi, m);
                hi = p - 1;
            }
            m.exitDepth();
        }
    }

    private static int partition(int[] a, int lo, int hi, Metrics m) {
        int pivot = a[hi];
        int i = lo;
        for (int j = lo; j < hi; j++) {
            if (a[j] < pivot) {
                swap(a, i, j);
                i++;
                m.incCompare();
            }
        }
        swap(a, i, hi);
        return i;
    }

    private static void swap(int[] a, int i, int j) {
        int t = a[i]; a[i] = a[j]; a[j] = t;
    }

    private static void shuffle(int[] a) {
        for (int i = a.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);
            swap(a, i, j);
        }
    }
}