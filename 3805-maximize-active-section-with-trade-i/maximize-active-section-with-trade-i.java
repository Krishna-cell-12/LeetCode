class Solution {
    public int maxActiveSectionsAfterTrade(String s) {
        int n = s.length();
        int totalOnes = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                totalOnes++;
            }
        }
        int[] Z = new int[n];
        int[] O = new int[n];
        int k = 0; 
        
        int idx = 0;
        while (idx < n) {
            char c = s.charAt(idx);
            int count = 0;
            while (idx < n && s.charAt(idx) == c) {
                count++;
                idx++;
            }
            
            if (c == '0') {
                Z[k++] = count;
            } else {
                if (k > 0) {
                    O[k - 1] = count;
                }
            }
        }
        if (k < 2) {
            return totalOnes;
        }
        int[] pref = new int[k];
        int[] suff = new int[k];
        
        pref[0] = Z[0];
        for (int j = 1; j < k; j++) {
            pref[j] = Math.max(pref[j - 1], Z[j]);
        }
        
        suff[k - 1] = Z[k - 1];
        for (int j = k - 2; j >= 0; j--) {
            suff[j] = Math.max(suff[j + 1], Z[j]);
        }
        
        int maxActive = totalOnes;

        for (int j = 0; j < k - 1; j++) {
            int mergedBlock = Z[j] + O[j] + Z[j + 1];
            int otherMax = 0;
            if (j - 1 >= 0) {
                otherMax = Math.max(otherMax, pref[j - 1]);
            }
            if (j + 2 < k) {
                otherMax = Math.max(otherMax, suff[j + 2]);
            }

            int bestZeroBlock = Math.max(mergedBlock, otherMax);
            int currentActive = totalOnes - O[j] + bestZeroBlock;
            maxActive = Math.max(maxActive, currentActive);
        }
        
        return maxActive;
    }
}