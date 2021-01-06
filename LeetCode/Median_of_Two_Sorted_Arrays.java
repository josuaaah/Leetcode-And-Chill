class Solution {
    
    public static int[] makeSorted(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int[] sorted = new int[m + n];
        int left = 0;
        int right = 0;
        int idx = 0;
        
        while (left < m && right < n) {
            if (nums1[left] < nums2[right]) {
                sorted[idx] = nums1[left];
                idx++;
                left++;
            } else {
                sorted[idx] = nums2[right];
                idx++;
                right++;
            }            
        }
        
        while (left < m) {
            sorted[idx] = nums1[left];
            idx++;
            left++;
        }
        
        while (right < n) {
            sorted[idx] = nums2[right];
            idx++;
            right++;
        }
        return sorted;
    }
    
    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int m = nums1.length;
        int n = nums2.length;
        int total = m + n;
        boolean isEven = total % 2 == 0;
        
        int[] sorted = makeSorted(nums1, nums2);
        
        if (isEven) {
            int leftIdx = ((total - 2) / 2);
            int rightIdx = leftIdx + 1;
            int leftMedian = sorted[leftIdx];
            int rightMedian = sorted[rightIdx];
            return ((double) (leftMedian + rightMedian)) / 2;
            
        } else {
            int idx = total / 2;
            return (double) sorted[idx];
        }
    }
}
