package com.beknur;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class MergeSortTest {
    @Test
    void testSort() {
        int[] a = {5, 3, 8, 1};
        Metrics m = new Metrics();
        MergeSort.sort(a, m);
        assertArrayEquals(new int[]{1,3,5,8}, a);
    }
    // Add more: random arrays, duplicates, sorted, reverse-sorted
}