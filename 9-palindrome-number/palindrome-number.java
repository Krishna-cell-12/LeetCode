class Solution {
    public boolean isPalindrome(int x) {
        int temp = x;
        int rev = 0;
        int lastdigit = 0;

        while ( x > 0 ){
            lastdigit = x % 10;
            rev = rev * 10 + lastdigit;
            x = x/ 10;
        }

        if (temp == rev){
            return true;
        }else{
            return false;
        }
    }
} 