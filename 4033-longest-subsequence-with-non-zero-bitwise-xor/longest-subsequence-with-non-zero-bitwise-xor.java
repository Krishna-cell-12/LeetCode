class Solution {
    public int longestSubsequence(int[] nums) {
        int totalXor = 0;
        boolean allZeros = true;
        
        for (int num : nums) {
            totalXor ^= num;
            if (num != 0) {
                allZeros = false;
            }
        }
        
        if (totalXor != 0) {
            return nums.length;
        }
        
        if (allZeros) {
            return 0;
        }
        
        return nums.length - 1;
    }
}