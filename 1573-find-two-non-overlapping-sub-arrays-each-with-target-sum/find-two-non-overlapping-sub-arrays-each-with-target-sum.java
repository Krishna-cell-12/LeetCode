class Solution {
    public int minSumOfLengths(int[] arr, int target) {
        int n = arr.length;
        int[] dp = new int[n]; 
        int left = 0, sum = 0;
        int ans = Integer.MAX_VALUE;

        for (int right = 0; right < n; right++) {
            sum += arr[right];

            while (sum > target) {
                sum -= arr[left];
                left++;
            }
            dp[right] = (right > 0) ? dp[right - 1] : Integer.MAX_VALUE;

            if (sum == target) {
                int currLen = right - left + 1;
                if (left > 0 && dp[left - 1] != Integer.MAX_VALUE) {
                    ans = Math.min(ans, dp[left - 1] + currLen);
                }

                dp[right] = Math.min(dp[right], currLen);
            }
        }

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }
}