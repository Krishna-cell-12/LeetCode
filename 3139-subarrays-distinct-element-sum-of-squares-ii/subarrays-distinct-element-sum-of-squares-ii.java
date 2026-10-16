class Solution {
    private static final int MOD = 1_000_000_007;
    private long[] sumC;
    private long[] sumSq;
    private long[] lazy;

    public int sumCounts(int[] nums) {
        int n = nums.length;
        sumC = new long[4 * n];
        sumSq = new long[4 * n];
        lazy = new long[4 * n];

        int[] lastSeen = new int[100001];
        // Initialize to -1 to represent elements not seen before (1-indexed for the segment tree limits)
        for (int i = 0; i < lastSeen.length; i++) {
            lastSeen[i] = -1;
        }

        long totalSumOfSquares = 0;

        for (int j = 0; j < n; j++) {
            int val = nums[j];
            int prev = lastSeen[val];
            
            // Add 1 to the distinct counts for all subarrays starting in the range [prev + 1, j]
            update(1, 0, n - 1, prev + 1, j, 1);
            
            // Record the latest occurrence of the current value
            lastSeen[val] = j;

            // The root of the segment tree always contains the total sum of squares 
            // for all subarrays ending at the current index j
            totalSumOfSquares = (totalSumOfSquares + sumSq[1]) % MOD;
        }

        return (int) totalSumOfSquares;
    }

    private void apply(int node, int left, int right, long val) {
        long len = right - left + 1;
        // (c + val)^2 = c^2 + 2 * c * val + val^2
        long valSq = (val * val) % MOD;
        sumSq[node] = (sumSq[node] + 2 * val * sumC[node] % MOD + valSq * len % MOD) % MOD;
        sumC[node] = (sumC[node] + val * len % MOD) % MOD;
        lazy[node] = (lazy[node] + val) % MOD;
    }

    private void pushDown(int node, int left, int right) {
        if (lazy[node] != 0) {
            int mid = left + (right - left) / 2;
            apply(2 * node, left, mid, lazy[node]);
            apply(2 * node + 1, mid + 1, right, lazy[node]);
            lazy[node] = 0;
        }
    }

    private void pushUp(int node) {
        sumC[node] = (sumC[2 * node] + sumC[2 * node + 1]) % MOD;
        sumSq[node] = (sumSq[2 * node] + sumSq[2 * node + 1]) % MOD;
    }

    private void update(int node, int left, int right, int ql, int qr, long val) {
        if (ql <= left && right <= qr) {
            apply(node, left, right, val);
            return;
        }
        
        pushDown(node, left, right);
        int mid = left + (right - left) / 2;
        
        if (ql <= mid) {
            update(2 * node, left, mid, ql, qr, val);
        }
        if (qr > mid) {
            update(2 * node + 1, mid + 1, right, ql, qr, val);
        }
        
        pushUp(node);
    }
}