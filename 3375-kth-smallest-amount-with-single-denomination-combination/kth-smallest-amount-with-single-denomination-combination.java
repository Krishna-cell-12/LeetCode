class Solution {
    public long findKthSmallest(int[] coins, int k) {
        long low = 1;
        long minCoin = coins[0];
        for (int coin : coins) {
            minCoin = Math.min(minCoin, coin);
        }
        long high = minCoin * (long) k;
        long ans = high;
        while (low <= high) {
            long mid = low + (high - low) / 2;
            if (countMultiples(mid, coins) >= k) {
                ans = mid;
                high = mid - 1;
            } else {
                low = mid + 1;
            }
        }
        
        return ans;
    }
    private long countMultiples(long limit, int[] coins) {
        long totalCount = 0;
        int n = coins.length;
        int totalSubsets = 1 << n; 
        for (int i = 1; i < totalSubsets; i++) {
            long currentLcm = 1;
            int bitsSet = 0;
            
            for (int j = 0; j < n; j++) {
                if ((i & (1 << j)) != 0) { 
                    currentLcm = lcm(currentLcm, coins[j]);
                    bitsSet++;
                    if (currentLcm > limit) break; 
                }
            }
            
            if (currentLcm <= limit) {
                long multiples = limit / currentLcm;
                if (bitsSet % 2 == 1) {
                    totalCount += multiples;
                } else {
                    totalCount -= multiples;
                }
            }
        }
        return totalCount;
    }
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
    private long lcm(long a, long b) {
        return (a / gcd(a, b)) * b;
    }
}