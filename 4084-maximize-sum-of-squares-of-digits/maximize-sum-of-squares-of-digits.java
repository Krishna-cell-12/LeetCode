class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > (long) num * 9) {
            return "";
        }
        
        char[] digits = new char[num];
        for (int i = 0; i < num; i++) {
            int d = Math.min(9, sum);
            digits[i] = (char) ('0' + d);
            sum -= d;
        }
        
        return new String(digits);
    }
}