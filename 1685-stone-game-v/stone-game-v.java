class Solution {
    public int stoneGameV(int[] stoneValue) {
        int n = stoneValue.length;
        int[][] memo = new int[n][n];
        int[] prefix = new int[n + 1];
        for (int i = 0; i < n; i++) {
            prefix[i + 1] = prefix[i] + stoneValue[i];
        }
        
        return dfs(0, n - 1, prefix, memo);
    }
    
    private int dfs(int i, int j, int[] prefix, int[][] memo) {
        if (i == j) return 0;
        if (memo[i][j] > 0) return memo[i][j];
        
        int maxScore = 0;
        for (int k = i; k < j; k++) {
            int leftSum = prefix[k + 1] - prefix[i];
            int rightSum = prefix[j + 1] - prefix[k + 1];
            
            if (leftSum < rightSum) {
                maxScore = Math.max(maxScore, leftSum + dfs(i, k, prefix, memo));
            } else if (leftSum > rightSum) {
                maxScore = Math.max(maxScore, rightSum + dfs(k + 1, j, prefix, memo));
            } else {
                maxScore = Math.max(maxScore, leftSum + Math.max(dfs(i, k, prefix, memo), dfs(k + 1, j, prefix, memo)));
            }
        }
        
        return memo[i][j] = maxScore;
    }
}