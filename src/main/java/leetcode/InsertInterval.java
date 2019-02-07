package leetcode;

import java.util.*;

/*
    Given a set of non-overlapping intervals, insert a new interval into the intervals (merge if necessary).

    You may assume that the intervals were initially sorted according to their start times.

    Example:
    Input: intervals = [[1,3], [6,9]], newInterval = [2,5]
    Output: [[1,5], [6,9]]
 */

public class InsertInterval {

    public static class Interval {
        int start;
        int end;

        Interval() {
            start = 0;
            end = 0;
        }

        Interval(int s, int e) {
            start = s;
            end = e;
        }

        @Override
        public String toString() {
            return "[" + this.start + "," + this.end + "]";
        }
    }

    private static List<Interval> insert(List<Interval> intervals, Interval newInterval) {
        if (newInterval == null) {
            return intervals;
        }

        if (intervals == null || intervals.size() == 0) {
            return Arrays.asList(newInterval);
        }

        List<Interval> res = new ArrayList<>();

        int i = 0;

        // intervals before new interval
        while (i < intervals.size()) {
            if (intervals.get(i).end >= newInterval.start)
                break;
            res.add(intervals.get(i++));
        }

        if (i < intervals.size() && intervals.get(i).start <= newInterval.end) {
            // merging new interval with existing (overlapping)
            int start = Math.min(newInterval.start, intervals.get(i).start);
            int end = 0;

            // move forward until we see first not overlapping interval
            while (i < intervals.size() && newInterval.end >= intervals.get(i).start) {
                i++;
            }

            // merge with previous (the last overlapping interval)
            end = Math.max(newInterval.end, intervals.get(i-1).end);

            res.add(new Interval(start, end));
        } else {
            // inserting new interval (not overlapping)
            res.add(newInterval);
        }

        // intervals after new interval
        while (i < intervals.size()) {
            res.add(intervals.get(i++));
        }

        return res;
    }

    public static void main(String[] args) {
        /*Interval int1 = new Interval(3, 5);
        Interval int2 = new Interval(8, 9);
        Interval intIns = new Interval(6, 7);

        System.out.println(insert(Arrays.asList(int1, int2), intIns));*/

        Interval int1 = new Interval(1, 2);
        Interval int2 = new Interval(3, 5);
        Interval int3 = new Interval(6, 7);
        Interval int4 = new Interval(8, 10);
        Interval int5 = new Interval(12, 16);

        Interval intIns = new Interval(4, 9);

        System.out.println(insert(Arrays.asList(int1, int2, int3, int4, int5), intIns));
    }
}