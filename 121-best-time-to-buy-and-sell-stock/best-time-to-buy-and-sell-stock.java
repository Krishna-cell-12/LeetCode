class Solution {
    public int maxProfit(int[] prices) {
        if (prices == null || prices.length == 0) {
            return 0;
        }
        int lowestPriceSeenSoFar = prices[0]; 
        int maxProfit = 0; 

        for (int i = 1; i < prices.length; i++) {
            if (prices[i] < lowestPriceSeenSoFar) {
                lowestPriceSeenSoFar = prices[i];
            } 
            else {
                int currentProfit = prices[i] - lowestPriceSeenSoFar;
                if (currentProfit > maxProfit) {
                    maxProfit = currentProfit;
                }
            }
        }

        return maxProfit;
    }
}