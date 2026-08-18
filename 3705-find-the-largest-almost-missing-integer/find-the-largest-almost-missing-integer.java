class Solution {
    public int largestInteger(int[] nums, int k) {
        int n = nums.length;
        int[] count = new int[51];

        for(int num : nums){
            count[num]++;
        }
        for(int i = 50; i >= 0; i--){
            if(count[i] == 0){
                continue;
            }
            if (k == n || (k == 1 && count[i] == 1) || (count[i] == 1 && (nums[0] == i || nums[n - 1] == i))) {
                return i;
            }
        }
        return -1;
    }
}