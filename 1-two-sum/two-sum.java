import java.util.HashMap;
import java.util.Map;

class Solution {
    public int[] twoSum(int[] nums, int target) {
        // Map to store the number and its index
        Map<Integer, Integer> numMap = new HashMap<>();
        
        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];
            
            // Check if we have already seen the complement
            if (numMap.containsKey(complement)) {
                return new int[] { numMap.get(complement), i };
            }
            
            // Otherwise, put the current number and its index into the map
            numMap.put(nums[i], i);
        }
        
        // Return an empty array if no solution is found 
        // (Though the problem guarantees exactly one solution)
        return new int[] {};
    }
}