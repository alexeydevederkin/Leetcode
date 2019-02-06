package leetcode;

/*
Suppose an array sorted in ascending order is rotated at some pivot unknown to you beforehand.

(i.e., [0,1,2,4,5,6,7] might become [4,5,6,7,0,1,2]).

Find the minimum element.

The array may contain duplicates.
*/

public class FindMinInRotatedSortedArrayII {

    private static int findMin(int[] nums) {
        return findMinInternal(nums, 0, nums.length - 1);
    }

    private static int findMinInternal(int[] nums, int left, int right) {
        // less then 16 elements - brute force
        if (right + 1 - left < 16) {
            int min = nums[left];
            for (int i = left + 1; i <= right; i++) {
                min = Math.min(min, nums[i]);
            }
            return min;
        }

        while (left < right) {
            // case: strictly increasing sequence: [3, 4, 5, 6, 7]
            if (nums[left] < nums[right]) {
                return nums[left];
            }

            int middle = left + (right-left) / 2;

            if (nums[left] == nums[middle] && nums[middle] == nums[right]) {
                // case: [8, 8, 1, (8), 8, 8, 8]
                //        l         m         r
                // search in both parts
                return Math.min(findMinInternal(nums, left, middle - 1), findMinInternal(nums, middle + 1, right));
            } else if (nums[middle] >= nums[left]) {
                // case: [4, 5, 6, (7), 0, 1, 2]
                //        l         m         r
                left = middle + 1;
            } else {
                // case: [4, 5, 0, (1), 2, 3, 4]
                //        l         m         r
                right = middle;
            }
        }

        return nums[left];
    }

    public static void main(String[] args) {
        System.out.println(findMin(new int[]{4, 5, 6, 7, 0, 1, 2}));
        //System.out.println(findMin(new int[]{10, 1, 10, 10, 10}));
    }
}
