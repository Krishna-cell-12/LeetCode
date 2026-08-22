class Solution {
    public boolean checkDivisibility(int n) {
        int originalN = n;
        int sum = 0;
        int product = 1;   
        while (n > 0) {
            int digit = n % 10; 
            sum += digit;
            product *= digit;
            n /= 10;            
        }
        
        int totalSum = sum + product;
        return originalN % totalSum == 0;
    }    
}