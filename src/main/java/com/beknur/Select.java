package com.beknur;

public class Select {
    public static int select(int[] a, int k, Metrics m) { // k 0-based
        return select(a, 0, a.length - 1, k, m);
    }

    private static int select(int[] a, int lo, int hi, int k, Metrics m) {
        m.enterDepth();
        if (lo == hi) {
            m.exitDepth();
            return a[lo];
        }
        // Median of medians
        int pivot = medianOfMedians(a, lo, hi, m);
        int p = Util.partition(a, lo, hi, m); // Assume Util has partition with given pivot
        if (k == p) {
            m.exitDepth();
            return a[p];
        } else if (k < p) {
            // Prefer smaller
            if (p - lo < hi - p) return select(a, lo, p - 1, k, m);
            else return select(a, p + 1, hi, k, m); // But swap if needed? Wait, logic to prefer recurse small
        } else {
            if (hi - p < p - lo) return select(a, p + 1, hi, k, m);
            else return select(a, lo, p - 1, k, m);
        }
        // Actually, to prefer recurse on small, calculate sizes and recurse small, tail-iterate large like QS
        // Adjust to loop like QS for bounded depth
    }

    private static int medianOfMedians(int[] a, int lo, int hi, Metrics m) {
        int n = hi - lo + 1;
        if (n <= 5) return medianOfSmall(a, lo, hi, m);
        int groups = (n + 4) / 5;
        for (int i = 0; i < groups; i++) {
            int groupLo = lo + i * 5;
            int groupHi = Math.min(groupLo + 4, hi);
            int med = medianOfSmall(a, groupLo, groupHi, m);
            Util.swap(a, lo + i, med); // Move medians to front
        }
        return select(a, lo, lo + groups - 1, (groups - 1) / 2, m); // Recurse on medians
    }

    private static int medianOfSmall(int[] a, int lo, int hi, Metrics m) {
        java.util.Arrays.sort(a, lo, hi + 1); // Small, ok
        return lo + (hi - lo) / 2;
    }
}