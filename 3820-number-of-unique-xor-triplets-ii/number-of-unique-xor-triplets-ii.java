class Solution {
    public int uniqueXorTriplets(int[] nums) {
        boolean[] present = new boolean[2048];
        int uniqueCount = 0;
        for (int num : nums) {
            if (!present[num]) {
                present[num] = true;
                uniqueCount++;
            }
        }
        
        int[] U = new int[uniqueCount];
        int idx = 0;
        for (int i = 0; i < 2048; i++) {
            if (present[i]) {
                U[idx++] = i;
            }
        }
        boolean[] pairXor = new boolean[2048];
        for (int i = 0; i < uniqueCount; i++) {
            for (int j = i + 1; j < uniqueCount; j++) {
                pairXor[U[i] ^ U[j]] = true;
            }
        }
        boolean[] ansSet = new boolean[2048];
        for (int u : U) {
            ansSet[u] = true;
        }
        for (int v = 0; v < 2048; v++) {
            if (pairXor[v]) {
                for (int u : U) {
                    ansSet[v ^ u] = true;
                }
            }
        }
        int count = 0;
        for (boolean isPossible : ansSet) {
            if (isPossible) {
                count++;
            }
        }
        
        return count;
    }
}