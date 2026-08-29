import java.util.*;

class Solution {
    public int[] lexicographicallySmallestArray(int[] nums, int limit) {
        int n = nums.length;
        Integer[] idx = new Integer[n];
        for (int i = 0; i < n; i++) idx[i] = i;
        Arrays.sort(idx, (a, b) -> nums[a] - nums[b]);

        int[] result = new int[n];
        List<Integer> groupIndices = new ArrayList<>();
        List<Integer> groupValues = new ArrayList<>();

        for (int k = 0; k < n; k++) {
            int i = idx[k];
            if (k > 0 && nums[idx[k]] - nums[idx[k - 1]] > limit) {
                assignGroup(groupIndices, groupValues, result);
                groupIndices.clear();
                groupValues.clear();
            }
            groupIndices.add(i);
            groupValues.add(nums[i]);
        }
        assignGroup(groupIndices, groupValues, result);

        return result;
    }

    private void assignGroup(List<Integer> indices, List<Integer> values, int[] result) {
        List<Integer> sortedIndices = new ArrayList<>(indices);
        Collections.sort(sortedIndices);
        for (int m = 0; m < sortedIndices.size(); m++) {
            result[sortedIndices.get(m)] = values.get(m);
        }
    }
}