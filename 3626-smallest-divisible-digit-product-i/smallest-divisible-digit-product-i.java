class Solution {
    public int smallestNumber(int n, int t) {
        int current = n;
        while (true) {
            if (product(current) % t == 0) {
                return current;
            }
            current++;
        }
    }
    private int product(int num) {
        int mul = 1;
        while (num > 0) {
            int digit = num % 10; 
            mul *= digit;     
            num /= 10;            
        }
        
        return mul;
    }
}