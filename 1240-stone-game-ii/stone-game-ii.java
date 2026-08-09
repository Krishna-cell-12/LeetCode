class Solution {
    public int stoneGameII(int[] p) {
        int n = p.length;
        int[][] dp = new int[n][n + 1]; 
        int[] suf = new int[n]; 
        
        suf[n - 1] = p[n - 1];
        for (int i = n - 2; i >= 0; i--) {
            suf[i] = suf[i + 1] + p[i];
        }
        
        return dfs(p, dp, suf, 0, 1);
    }
    
    private int dfs(int[] p, int[][] dp, int[] suf, int i, int m) {
        int n = p.length;
        if (i == n) return 0;
        if (i + 2 * m >= n) return suf[i];
        if (dp[i][m] != 0) return dp[i][m];
        
        int max = 0;
        for (int x = 1; x <= 2 * m; x++) {
            int cur = suf[i] - dfs(p, dp, suf, i + x, Math.max(m, x));
            max = Math.max(max, cur);
        }
        dp[i][m] = max;
        return max;
    }
}