package com.beknur;

import java.util.Arrays;

public class MergeSort {
    public static void sort(int[] a, Metrics m) {
        int[] aux = new int[a.length]; // Reusable buffer
        m.incAlloc(); // Count allocation
        sort(a, aux, 0, a.length - 1, m);
    }

    private static void sort(int[] a, int[] aux, int lo, int hi, Metrics m) {
        m.enterDepth();
        if (hi - lo < 16) { // Cutoff
            insertionSort(a, lo, hi, m);
            m.exitDepth();
            return;
        }
        int mid = lo + (hi - lo) / 2;
        sort(a, aux, lo, mid, m);
        sort(a, aux, mid + 1, hi, m);
        merge(a, aux, lo, mid, hi, m);
        m.exitDepth();
    }

    private static void merge(int[] a, int[] aux, int lo, int mid, int hi, Metrics m) {
        System.arraycopy(a, lo, aux, lo, hi - lo + 1);
        int i = lo, j = mid + 1;
        for (int k = lo; k <= hi; k++) {
            if (i > mid) a[k] = aux[j++];
            else if (j > hi) a[k] = aux[i++];
            else if (aux[j] < aux[i]) { a[k] = aux[j++]; m.incCompare(); }
            else { a[k] = aux[i++]; m.incCompare(); }
        }
    }

    private static void insertionSort(int[] a, int lo, int hi, Metrics m) {
        for (int i = lo + 1; i <= hi; i++) {
            int v = a[i];
            int j = i;
            while (j > lo && a[j - 1] > v) {
                a[j] = a[j - 1];
                j--;
                m.incCompare();
            }
            a[j] = v;
        }
    }
}