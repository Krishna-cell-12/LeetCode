class Solution {
    public int missingInteger(int[] nums) {
        int s = nums[0], i = 1;
        while (i < nums.length && nums[i] == nums[i - 1] + 1) {
            s += nums[i++];
        }
        boolean[] v = new boolean[2000];
        for (int n : nums) {
            v[n] = true;
        }
        while (v[s]) {
            s++;
        }       
        return s;
    }
}