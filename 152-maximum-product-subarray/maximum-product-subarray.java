class Solution {
    public int maxProduct(int[] nums) {
        int n = nums.length;
        double maxProduct = nums[0];
        double prefix = 1;
        double suffix = 1;

        for ( int i = 0; i < n; i++){
            if ( prefix == 0){
                prefix = 1;
            }
            if ( suffix == 0){
                suffix = 1;
            }

            prefix *= (double) nums[i];
            suffix *= (double) nums[n-1-i];

            maxProduct = Math.max(maxProduct, Math.max(prefix,suffix));
        }
        return (int)maxProduct;
    }
}