import java.util.HashMap;
import java.util.Map;

class Solution {
    private Map<Long, Integer> memo = new HashMap<>();

    public String smallestNumber(String num, long t) {
        long temp = t;
        for (int p : new int[]{2, 3, 5, 7}) {
            while (temp % p == 0) {
                temp /= p;
            }
        }
        if (temp > 1) {
            return "-1";
        }

        int n = num.length();
        long[] reqR = new long[n + 1];
        reqR[0] = t;
        int z = n;
        for (int i = 0; i < n; i++) {
            if (num.charAt(i) == '0') {
                z = i;
                break;
            }
            reqR[i + 1] = reqR[i] / gcd(reqR[i], num.charAt(i) - '0');
        }
        if (z == n && reqR[n] == 1) {
            return num;
        }
        int startI = Math.min(n - 1, z);
        for (int i = startI; i >= 0; i--) {
            int startD = (i == z) ? 1 : (num.charAt(i) - '0' + 1);
            for (int d = startD; d <= 9; d++) {
                long newR = reqR[i] / gcd(reqR[i], d);
                int rem = n - 1 - i;
                if (rem >= getMinLen(newR)) {
                    StringBuilder ans = new StringBuilder();
                    ans.append(num, 0, i);
                    ans.append(d);
                    ans.append(fill(newR, rem));
                    return ans.toString();
                }
            }
        }
        int targetLen = Math.max(n + 1, getMinLen(t));
        return fill(t, targetLen);
    }
    private int getMinLen(long R) {
        if (R == 1) return 0;
        if (memo.containsKey(R)) return memo.get(R);
        
        int min = 1000000;
        for (int d = 2; d <= 9; d++) {
            long g = gcd(R, d);
            if (g > 1) {
                min = Math.min(min, 1 + getMinLen(R / g));
            }
        }
        
        memo.put(R, min);
        return min;
    }
    private String fill(long R, int len) {
        StringBuilder sb = new StringBuilder();
        for (int step = 0; step < len; step++) {
            for (int d = 1; d <= 9; d++) {
                long nextR = R / gcd(R, d);
                if (len - 1 - step >= getMinLen(nextR)) {
                    sb.append(d);
                    R = nextR;
                    break;
                }
            }
        }
        return sb.toString();
    }
    private long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }
}