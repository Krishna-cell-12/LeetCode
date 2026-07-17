class Solution {
    public int[] gcdValues(int[] nums, long[] queries) {
        int maxVal = 0;
        for (int num : nums) {
            if (num > maxVal) {
                maxVal = num;
            }
        }
        
        int[] count = new int[maxVal + 1];
        for (int num : nums) {
            count[num]++;
        }

        long[] exactGcd = new long[maxVal + 1];
        for (int i = maxVal; i >= 1; i--) {
            long multiples = 0;
            for (int j = i; j <= maxVal; j += i) {
                multiples += count[j];
            }

            long pairs = multiples * (multiples - 1) / 2;
            exactGcd[i] = pairs;

            for (int j = 2 * i; j <= maxVal; j += i) {
                exactGcd[i] -= exactGcd[j];
            }
        }

        long[] prefix = new long[maxVal + 1];
        for (int i = 1; i <= maxVal; i++) {
            prefix[i] = prefix[i - 1] + exactGcd[i];
        }

        int[] answer = new int[queries.length];
        for (int k = 0; k < queries.length; k++) {
            long q = queries[k];
            int low = 1;
            int high = maxVal;
            int res = maxVal;
            
            while (low <= high) {
                int mid = low + (high - low) / 2;
                if (prefix[mid] > q) {
                    res = mid;
                    high = mid - 1;
                } else {
                    low = mid + 1;
                }
            }
            answer[k] = res;
        }
        
        return answer;
    }
}