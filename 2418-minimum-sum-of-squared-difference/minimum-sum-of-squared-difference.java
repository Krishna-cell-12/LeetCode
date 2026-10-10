class Solution {
    public long minSumSquareDiff(int[] nums1, int[] nums2, int k1, int k2) {
        long k = (long) k1 + k2;
        int maxDiff = 100000;
        long[] count = new long[maxDiff + 1];
        for (int i = 0; i < nums1.length; i++) {
            int diff = Math.abs(nums1[i] - nums2[i]);
            count[diff]++;
        }
        for (int i = maxDiff; i > 0 && k > 0; i--) {
            if (count[i] > 0) {
                long reduce = Math.min(count[i], k);
                count[i] -= reduce;
                count[i - 1] += reduce;
                k -= reduce;
            }
        }
        long ans = 0;
        for (long i = 1; i <= maxDiff; i++) {
            if (count[(int)i] > 0) {
                ans += count[(int)i] * i * i;
            }
        }        
        return ans;
    }
}