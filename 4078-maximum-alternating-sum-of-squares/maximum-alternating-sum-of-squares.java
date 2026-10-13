class Solution {
    public long maxAlternatingSum(int[] nums) {
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            nums[i] = Math.abs(nums[i]);
        }
        
        Arrays.sort(nums);
        
        long maxScore = 0;
        int mid = n / 2;
        for (int i = 0; i < mid; i++) {
            maxScore -= (long) nums[i] * nums[i];
        }
        for (int i = mid; i < n; i++) {
            maxScore += (long) nums[i] * nums[i];
        }        
        return maxScore;
    }
}