class Solution {
    public boolean sumGame(String num) {
        int n = num.length();
        int diff = 0;
        int p = 0, q = 0;

        for(int i = 0; i < n/2; i++){
            if(num.charAt(i) == '?')
               p++;
            else
               diff += num.charAt(i) - '0';
        }
        for(int i = n/2; i < n; i++){
            if(num.charAt(i) == '?')
               q++;
            else
               diff -= num.charAt(i) - '0';
        }
        if ((p + q) % 2 != 0){
            return true;
        }
        return (diff * 2) != (9 * (q - p));
    }
}