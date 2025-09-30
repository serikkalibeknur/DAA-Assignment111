package com.beknur;

import java.util.Arrays;

class Point implements Comparable<Point> {
    double x, y;
    Point(double x, double y) { this.x = x; this.y = y; }
    public int compareTo(Point p) { return Double.compare(x, p.x); } // For x-sort
}

public class ClosestPair {
    public static double closest(Point[] points, Metrics m) {
        Point[] px = points.clone();
        Arrays.sort(px); // By x
        Point[] py = points.clone();
        Arrays.sort(py, (p1, p2) -> Double.compare(p1.y, p2.y)); // By y
        return closest(px, py, 0, points.length - 1, m);
    }

    private static double closest(Point[] px, Point[] py, int lo, int hi, Metrics m) {
        m.enterDepth();
        if (hi - lo < 3) { // Small: brute
            double min = brute(px, lo, hi, m);
            m.exitDepth();
            return min;
        }
        int mid = lo + (hi - lo) / 2;
        Point midP = px[mid];
        double dl = closest(px, subsetY(py, lo, mid, midP.x), lo, mid, m);
        double dr = closest(px, subsetY(py, mid + 1, hi, midP.x), mid + 1, hi, m);
        double d = Math.min(dl, dr);
        Point[] strip = strip(py, midP.x, d);
        double ds = stripClosest(strip, d, m);
        m.exitDepth();
        return Math.min(d, ds);
    }

    private static Point[] subsetY(Point[] py, int lo, int hi, double midX) {
        // Filter py for left/right, but since py is y-sorted, need to copy relevant
        // Implement as array copy based on x <= midX or >
        Point[] sub = new Point[hi - lo + 1];
        int j = 0;
        for (Point p : py) if ((p.x <= midX && lo <= hi) /* adjust */) sub[j++] = p; // Pseudocode, implement carefully
        return sub;
    }

    private static double brute(Point[] p, int lo, int hi, Metrics m) {
        double min = Double.POSITIVE_INFINITY;
        for (int i = lo; i <= hi; i++) for (int j = i + 1; j <= hi; j++) {
            min = Math.min(min, dist(p[i], p[j]));
            m.incCompare();
        }
        return min;
    }

    private static Point[] strip(Point[] py, double midX, double d) {
        int count = 0;
        for (Point p : py) {
            if (Math.abs(p.x - midX) < d) {
                count++;
            }
        }

        Point[] strip = new Point[count];
        int index = 0;
        for (Point p : py) {
            if (Math.abs(p.x - midX) < d) {
                strip[index++] = p;
            }
        }
        return strip;
    }

    private static double stripClosest(Point[] strip, double d, Metrics m) {
        double min = d;
        for (int i = 0; i < strip.length; i++) for (int j = i + 1; j < strip.length && (strip[j].y - strip[i].y) < min; j++) {
            min = Math.min(min, dist(strip[i], strip[j]));
            m.incCompare();
        }
        return min;
    }

    private static double dist(Point p1, Point p2) {
        return Math.sqrt((p1.x - p2.x)*(p1.x - p2.x) + (p1.y - p2.y)*(p1.y - p2.y));
    }
}