import java.util.ArrayList;
import java.util.List;

class Solution {
    class Block {
        int start, end, size;
        Block(int s, int e, int sz) {
            this.start = s;
            this.end = e;
            this.size = sz;
        }
    }

    public List<Integer> maxActiveSectionsAfterTrade(String s, int[][] queries) {
        int n = s.length();
        int total1 = 0;
        
        // Count total '1's in the entire string
        for (int i = 0; i < n; i++) {
            if (s.charAt(i) == '1') {
                total1++;
            }
        }

        // Extract all '0' blocks with their start and end indices
        List<Block> zBlocks = new ArrayList<>();
        int idx = 0;
        while (idx < n) {
            if (s.charAt(idx) == '0') {
                int start = idx;
                while (idx < n && s.charAt(idx) == '0') {
                    idx++;
                }
                zBlocks.add(new Block(start, idx - 1, idx - start));
            } else {
                idx++;
            }
        }

        int K = zBlocks.size();
        
        // Precompute logarithm values for Sparse Tables
        int[] log2 = new int[Math.max(2, K + 1)];
        for (int i = 2; i < log2.length; i++) {
            log2[i] = log2[i / 2] + 1;
        }

        // Sparse Table 1: Max '0' block size (stZ)
        int[][] stZ = new int[K][20];
        for (int i = 0; i < K; i++) stZ[i][0] = zBlocks.get(i).size;
        for (int j = 1; (1 << j) <= K; j++) {
            for (int i = 0; i + (1 << j) <= K; i++) {
                stZ[i][j] = Math.max(stZ[i][j - 1], stZ[i + (1 << (j - 1))][j - 1]);
            }
        }

        int oLen = Math.max(0, K - 1);
        int[] oArr = new int[oLen];
        int[] mzArr = new int[oLen];
        
        // Fill gaps: lengths of internal '1' blocks and adjacent merged '0' blocks
        for (int k = 0; k < K - 1; k++) {
            oArr[k] = zBlocks.get(k + 1).start - zBlocks.get(k).end - 1;
            mzArr[k] = zBlocks.get(k).size + zBlocks.get(k + 1).size;
        }

        // Sparse Table 2: Min internal '1' block size (stO)
        int[][] stO = new int[oLen][20];
        for (int i = 0; i < oLen; i++) stO[i][0] = oArr[i];
        for (int j = 1; (1 << j) <= oLen; j++) {
            for (int i = 0; i + (1 << j) <= oLen; i++) {
                stO[i][j] = Math.min(stO[i][j - 1], stO[i + (1 << (j - 1))][j - 1]);
            }
        }

        // Sparse Table 3: Max adjacent merged '0' blocks size (stMZ)
        int[][] stMZ = new int[oLen][20];
        for (int i = 0; i < oLen; i++) stMZ[i][0] = mzArr[i];
        for (int j = 1; (1 << j) <= oLen; j++) {
            for (int i = 0; i + (1 << j) <= oLen; i++) {
                stMZ[i][j] = Math.max(stMZ[i][j - 1], stMZ[i + (1 << (j - 1))][j - 1]);
            }
        }

        List<Integer> ans = new ArrayList<>();
        
        // Process each query
        for (int[] q : queries) {
            int L = q[0], R = q[1];

            // Binary search the first '0' block overlapping with the query range
            int left = 0, right = K - 1;
            int firstZ = K;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (zBlocks.get(mid).end >= L) {
                    firstZ = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }

            // Binary search the last '0' block overlapping with the query range
            left = 0; right = K - 1;
            int lastZ = -1;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (zBlocks.get(mid).start <= R) {
                    lastZ = mid;
                    left = mid + 1;
                } else {
                    right = mid - 1;
                }
            }

            int i = firstZ;
            int j = lastZ;

            // If strictly fewer than 2 '0' blocks exist/overlap, no valid trade can be made
            if (i >= K || j < 0 || i > j || i == j) {
                ans.add(total1);
                continue;
            }

            // Sizes of boundaries intersecting the query L/R bounds
            int z1 = Math.min(R, zBlocks.get(i).end) - Math.max(L, zBlocks.get(i).start) + 1;
            int zm = Math.min(R, zBlocks.get(j).end) - Math.max(L, zBlocks.get(j).start) + 1;

            // 1. Calculate maxZ (maximum independent 0s block available)
            int maxZ = Math.max(z1, zm);
            if (j - i >= 2) {
                int qL = i + 1, qR = j - 1;
                int p = log2[qR - qL + 1];
                maxZ = Math.max(maxZ, Math.max(stZ[qL][p], stZ[qR - (1 << p) + 1][p]));
            }

            // 2. Calculate minO (minimum internal 1s block inside to convert)
            int oL = i, oR = j - 1;
            int p = log2[oR - oL + 1];
            int minO = Math.min(stO[oL][p], stO[oR - (1 << p) + 1][p]);

            // 3. Calculate maxMergedZ (maximum two consecutive 0s blocks that can be linked)
            int maxMergedZ = -1;
            if (j == i + 1) {
                maxMergedZ = z1 + zm;
            } else {
                maxMergedZ = Math.max(z1 + zBlocks.get(i + 1).size, zBlocks.get(j - 1).size + zm);
                if (j - i >= 3) {
                    int mzL = i + 1, mzR = j - 2;
                    int pMZ = log2[mzR - mzL + 1];
                    maxMergedZ = Math.max(maxMergedZ, Math.max(stMZ[mzL][pMZ], stMZ[mzR - (1 << pMZ) + 1][pMZ]));
                }
            }

            // Net gain evaluates swapping to a naturally existing larger 0's block versus taking advantage of the new merged block
            int maxGain = Math.max(maxMergedZ, maxZ - minO);
            
            ans.add(total1 + maxGain);
        }

        return ans;
    }
}