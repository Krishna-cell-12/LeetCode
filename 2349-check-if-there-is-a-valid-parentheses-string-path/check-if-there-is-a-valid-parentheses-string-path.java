class Solution {
    public boolean hasValidPath(char[][] grid) {
        int m = grid.length, n = grid[0].length;
        
        // A valid parentheses string must have an even length.
        // It must also start with '(' and end with ')'.
        if ((m + n - 1) % 2 != 0 || grid[0][0] == ')' || grid[m - 1][n - 1] == '(') {
            return false;
        }
        
        // The maximum possible balance of open parentheses is bounded by the path length.
        int maxBal = m + n + 1;
        
        // dp[j][k] will be true if a balance of k is achievable at the current row, column j.
        // We only need a 1D array of states to represent the current row for space optimization.
        boolean[][] dp = new boolean[n][maxBal];
        
        // Base case: Starting cell
        dp[0][1] = true;
        
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                if (i == 0 && j == 0) continue;
                
                boolean[] nextDp = new boolean[maxBal];
                int diff = grid[i][j] == '(' ? 1 : -1;
                
                for (int k = 0; k < maxBal; k++) {
                    // 1. Evaluate paths coming from the top (previous row, same column)
                    if (i > 0 && dp[j][k]) {
                        int newBal = k + diff;
                        if (newBal >= 0 && newBal < maxBal) {
                            nextDp[newBal] = true;
                        }
                    }
                    
                    // 2. Evaluate paths coming from the left (same row, previous column)
                    if (j > 0 && dp[j - 1][k]) {
                        int newBal = k + diff;
                        if (newBal >= 0 && newBal < maxBal) {
                            nextDp[newBal] = true;
                        }
                    }
                }
                // Update the state for the current cell
                dp[j] = nextDp;
            }
        }
        
        // The path is valid if we can reach the bottom-right cell with exactly 0 balance.
        return dp[n - 1][0];
    }
}