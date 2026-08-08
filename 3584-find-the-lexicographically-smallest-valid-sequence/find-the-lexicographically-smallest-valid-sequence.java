class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();
        int[] R = new int[m];
        int curr = n - 1;
        for (int j = m - 1; j >= 0; j--) {
            while (curr >= 0 && word1.charAt(curr) != word2.charAt(j)) {
                curr--;
            }
            if (curr >= 0) {
                R[j] = curr;
                curr--;
            } else {
                R[j] = -1;
            }
        }

        int[] ans = new int[m];
        int lastPicked = -1;
        boolean changed = false;
        for (int j = 0; j < m; j++) {
            boolean found = false;
            
            for (int i = lastPicked + 1; i < n; i++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    ans[j] = i;
                    lastPicked = i;
                    found = true;
                    break; 
                } 
                else if (!changed && (j + 1 == m || R[j + 1] >= i + 1)) {
                    ans[j] = i;
                    lastPicked = i;
                    changed = true;
                    found = true;
                    break;
                }
            }
            if (!found) {
                return new int[0];
            }
        }

        return ans;
    }
}