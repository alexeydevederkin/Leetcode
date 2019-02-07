package leetcode;

import java.util.ArrayDeque;
import java.util.Deque;


/*
    Given n non-negative integers representing an elevation map where the width of each bar is 1,
    compute how much water it is able to trap after raining.

    Example:
    Input: [0,1,0,2,1,0,1,3,2,1,2,1]
    Output: 6
 */

public class TrappingRainWater {

    private static int trap(int[] height) {
        if (height == null || height.length < 3) {
            return 0;
        }

        int volume = 0;
        int size = height.length;

        // maximum height of bar on the left of the i-th position (including)
        int[] maximumsLeft = new int[size];
        maximumsLeft[0] = height[0];
        for (int i = 1; i < size; i++) {
            maximumsLeft[i] = Math.max(maximumsLeft[i-1], height[i]);
        }

        // maximum height of bar on the right of the i-th position (including)
        int[] maximumsRight = new int[size];
        maximumsRight[size-1] = height[size-1];
        for (int i = size-2; i >= 0; i--) {
            maximumsRight[i] = Math.max(maximumsRight[i+1], height[i]);
        }

        for (int i = 1; i < size - 1; i++) {

            // height of water in position i:
            // minimum of maximums on the sides minus height of floor at the position
            //           #
            // ~ # ~ ~ ~ #
            // # # ~ # ~ #
            // # # # # # #
            //   l i     r
            // maxLeft = 3, maxRight = 4, height[i] = 1: waterHeight = min(3, 4) - 1 = 2

            int waterHeight = Math.min(maximumsLeft[i], maximumsRight[i]) - height[i];

            // volume += height of water * width of bar (1) == height of water
            volume += waterHeight;
        }

        return volume;
    }


    /*private static int trap(int[] height) {
        if (height == null || height.length < 2) {
            return 0;
        }

        Deque<Integer> stack = new ArrayDeque<>();

        int totalVolume = 0, lastHeight = 0;
        int currentHeight, previousHeight, h;
        int length, volume, previousIndex;

        for (int i = 0; i < height.length; i++) {

            if (stack.isEmpty()) {
                stack.push(i);
                continue;
            }

            currentHeight = height[i];
            previousHeight = height[stack.peek()];

            if (currentHeight < previousHeight && i != height.length - 1) {
                stack.push(i);
                continue;
            }

            while (!stack.isEmpty()) {

                previousIndex = stack.pop();
                previousHeight = height[previousIndex];

                length = i - previousIndex - 1;

                h = Math.min(height[i], previousHeight);
                h = Math.max(h - lastHeight, 0);

                volume = length * h;
                totalVolume += volume;

                lastHeight = previousHeight;

                if (previousHeight > currentHeight) {
                    stack.push(previousIndex);
                    break;
                }
            }

            stack.push(i);
            lastHeight = 0;
        }

        return totalVolume;
    }*/

    public static void main(String[] args) {
        // 6
        System.out.println(trap(new int[] {0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1}));

        // 4
        System.out.println(trap(new int[] {3, 1, 0, 1, 2}));

        // 1
        System.out.println(trap(new int[] {3, 1, 2}));

        // 1
        System.out.println(trap(new int[] {2, 1, 3}));

        // 14
        System.out.println(trap(new int[] {5, 2, 1, 2, 1, 5}));

        // 9
        System.out.println(trap(new int[] {4, 2, 0, 3, 2, 5}));
    }
}
