class Solution {
    public long[] resultArray(int[] nums, int k) {
        long[] result = new long[k];
        long[] dp = new long[k];
        
        for (int num : nums) {
            long[] nextDp = new long[k];
            int v = num % k;
            nextDp[v] += 1;
            for (int m = 0; m < k; m++) {
                if (dp[m] > 0) {
                    nextDp[(m * v) % k] += dp[m];
                }
            }
            dp = nextDp;
            for (int m = 0; m < k; m++) {
                result[m] += dp[m];
            }
        }
        
        return result;
    }
}