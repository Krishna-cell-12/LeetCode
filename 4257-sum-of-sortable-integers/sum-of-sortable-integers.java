class Solution {
    public int sortableIntegers(int[] nums) {
        int n = nums.length;
        
        // Quick check: if the array is already sorted, all divisors k of n are sortable.
        boolean alreadySorted = true;
        for (int i = 0; i < n - 1; i++) {
            if (nums[i] > nums[i + 1]) {
                alreadySorted = false;
                break;
            }
        }
        
        int totalSum = 0;
        
        // Iterate through all divisors k of n
        for (int k = 1; k <= n; k++) {
            if (n % k != 0) continue;
            
            if (alreadySorted || isSortable(nums, n, k)) {
                totalSum += k;
            }
        }
        
        return totalSum;
    }
    
    private boolean isSortable(int[] nums, int n, int k) {
        int prevMax = Integer.MIN_VALUE;
        
        for (int start = 0; start < n; start += k) {
            // Check if subarray from [start, start + k - 1] can be rotated to be non-decreasing
            int dropCount = 0;
            int dropIdx = -1;
            
            for (int i = 0; i < k; i++) {
                int curr = nums[start + i];
                int next = nums[start + (i + 1) % k];
                if (curr > next) {
                    dropCount++;
                    dropIdx = i; // drop occurs between i and (i+1)%k
                }
            }
            
            // A single circular array can be sorted by rotation iff dropCount <= 1
            if (dropCount > 1) {
                return false;
            }
            
            int blockMin, blockMax;
            
            if (dropCount == 0) {
                // Already non-decreasing without rotation
                blockMin = nums[start];
                blockMax = nums[start + k - 1];
            } else {
                // Drop occurs at dropIdx. 
                // The rotated sorted block starts at (dropIdx + 1) % k
                blockMin = nums[start + (dropIdx + 1) % k];
                blockMax = nums[start + dropIdx];
            }
            
            // Check if this block's min is greater than or equal to previous block's max
            if (blockMin < prevMax) {
                return false;
            }
            
            prevMax = blockMax;
        }
        
        return true;
    }
}