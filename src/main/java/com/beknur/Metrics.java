package com.beknur;

public class Metrics {
    public long startTime;
    public long endTime;
    public int depth = 0;
    public int maxDepth = 0;
    public long comparisons = 0;
    public long allocations = 0; // Track array creations if possible

    public void start() { startTime = System.nanoTime(); }
    public void end() { endTime = System.nanoTime(); }
    public long getTimeNs() { return endTime - startTime; }
    public void enterDepth() { depth++; maxDepth = Math.max(maxDepth, depth); }
    public void exitDepth() { depth--; }
    public void incCompare() { comparisons++; }
    public void incAlloc() { allocations++; }
}