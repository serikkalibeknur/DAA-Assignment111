package com.beknur;

public class QuickSort {
    public static void sort(int[] a, Metrics m) {
        Util.shuffle(a);
        sort(a, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int lo, int hi, Metrics m) {
        while (lo < hi) {
            m.enterDepth();
            int p = Util.partition(a, lo, hi, m);
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
}