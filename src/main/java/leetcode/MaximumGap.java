package leetcode;

import java.util.Arrays;


/*
    Given an unsorted array, find the maximum difference between the successive elements in its sorted form.

    Return 0 if the array contains less than 2 elements.

    Input: [3,6,9,1]
    Output: 3
    Explanation: The sorted form of the array is [1,3,6,9], either (3,6) or (6,9) has the maximum difference 3.

    Input: [10]
    Output: 0
    Explanation: The array contains less than 2 elements, therefore return 0.
 */

public class MaximumGap {

    private static int maximumGap(int[] nums) {
        if (nums == null || nums.length < 2)
            return 0;

        // get the max and min value of the array
        int min = nums[0];
        int max = nums[0];
        for (int i : nums) {
            min = Math.min(min, i);
            max = Math.max(max, i);
        }

        // the minimum possible max-gap, ceiling of the integer division
        // (reducing gap until other gaps become larger)
        // [1, 3] -> gap = ceil((3-1)/1) = 2
        // [1, 2, 7] -> gap = ceil((7-1)/2) = 3 (case: [1, 4, 7])
        // [1, 5, 8, 9] -> gap = ceil((9-1)/3) = 3 (case: [1, 4, 7, 9])
        int gap = (int) Math.ceil( (double)(max - min) / (nums.length - 1) );

        // divide all numbers in the array into n-1 buckets,
        // where k-th bucket contains all numbers in [min + (k-1)*gap, min + k*gap)
        // storing only min and max value in each bucket
        int[] bucketsMIN = new int[nums.length - 1];
        int[] bucketsMAX = new int[nums.length - 1];
        Arrays.fill(bucketsMIN, Integer.MAX_VALUE);
        Arrays.fill(bucketsMAX, Integer.MIN_VALUE);

        // put numbers into buckets
        for (int i : nums) {
            if (i == min || i == max)
                continue;
            int bucketIndex = (i - min) / gap;
            bucketsMIN[bucketIndex] = Math.min(i, bucketsMIN[bucketIndex]);
            bucketsMAX[bucketIndex] = Math.max(i, bucketsMAX[bucketIndex]);
        }

        // scan the buckets for the max gap
        int maxGap = Integer.MIN_VALUE;
        int previous = min;

        for (int i = 0; i < nums.length - 1; i++) {
            // skip empty bucket
            if (bucketsMIN[i] == Integer.MAX_VALUE && bucketsMAX[i] == Integer.MIN_VALUE)
                continue;

            // current gap = min of this bucket - max of previous bucket
            maxGap = Math.max(maxGap, bucketsMIN[i] - previous);

            previous = bucketsMAX[i];
        }

        // update the final max value gap
        maxGap = Math.max(maxGap, max - previous);

        return maxGap;
    }

    public static void main(String[] args) {
        System.out.println(maximumGap(new int[] {3, 3, 3, 3, 3, 3, 3}));
        System.out.println(maximumGap(new int[] {3, 5, 7, 1, 0, 9, 4, 6, 8, 8, 5}));
        System.out.println(maximumGap(new int[] {3, 9, 6, 1}));
        System.out.println(maximumGap(new int[] {10}));
    }
}