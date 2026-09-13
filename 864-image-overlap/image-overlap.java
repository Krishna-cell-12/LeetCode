class Solution {
    public int largestOverlap(int[][] img1, int[][] img2) {
        int n = img1.length;
        List<int[]> onesA = new ArrayList<>();
        List<int[]> onesB = new ArrayList<>();

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (img1[i][j] == 1) onesA.add(new int[]{i, j});
                if (img2[i][j] == 1) onesB.add(new int[]{i, j});
            }
        }

        Map<String, Integer> shiftCount = new HashMap<>();
        int maxOverlap = 0;

        for (int[] a : onesA) {
            for (int[] b : onesB) {
                int dx = b[0] - a[0];
                int dy = b[1] - a[1];
                String key = dx + "," + dy;
                int count = shiftCount.merge(key, 1, Integer::sum);
                maxOverlap = Math.max(maxOverlap, count);
            }
        }

        return maxOverlap;
    }
}