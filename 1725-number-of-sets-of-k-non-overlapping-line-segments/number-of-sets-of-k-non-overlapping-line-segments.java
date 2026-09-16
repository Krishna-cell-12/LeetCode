class Solution {
    private static final int MOD = 1000000007;

    public int numberOfSets(int n, int k) {
        // We want to calculate nCr(n + k - 1, 2 * k) % MOD
        return nCr(n + k - 1, 2 * k);
    }

    private int nCr(int n, int k) {
        if (k > n || k < 0) {
            return 0;
        }
        
        long num = 1;
        long den = 1;
        
        for (int i = 0; i < k; i++) {
            num = (num * (n - i)) % MOD;
            den = (den * (i + 1)) % MOD;
        }
        
        // nCr = num / den (mod 10^9 + 7)
        // Division under modulo requires multiplying by the modular inverse
        return (int) ((num * modInverse(den, MOD)) % MOD);
    }

    private long modInverse(long n, int p) {
        return power(n, p - 2, p);
    }

    private long power(long base, int exp, int mod) {
        long res = 1;
        base = base % mod;
        
        while (exp > 0) {
            if (exp % 2 == 1) {
                res = (res * base) % mod;
            }
            base = (base * base) % mod;
            exp /= 2;
        }
        
        return res;
    }
}