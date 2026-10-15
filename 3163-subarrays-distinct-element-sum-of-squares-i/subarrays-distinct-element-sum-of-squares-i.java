class Solution {
    public int sumCounts(List<Integer> nums) {
        int n = nums.size();
        int totalSum = 0;
        for (int i = 0; i < n; i++) {
            boolean[] seen = new boolean[101];
            int distinctCount = 0;
            for (int j = i; j < n; j++) {
                int currentVal = nums.get(j);
                if (!seen[currentVal]) {
                    seen[currentVal] = true;
                    distinctCount++;
                }
                totalSum += distinctCount * distinctCount;
            }
        }        
        return totalSum;
    }
}