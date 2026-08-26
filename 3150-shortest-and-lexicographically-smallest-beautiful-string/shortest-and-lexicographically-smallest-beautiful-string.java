class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        int n = s.length();
        int j = 0;
        int ones = 0;
        String ans = "";
        for(int i = 0; i < n; i++){
            if(s.charAt(i) == '1'){
                ones++;
            }

            while(ones > k){
                if(s.charAt(j) == '1'){
                ones--;
                }
                j++;
            }

            if(ones == k){
                while(s.charAt(j) == '0'){
                    j++;
                }
                String current = s.substring(j, i+1);
                if(ans.equals("") || current.length() < ans.length() || (current.length() == ans.length() && current.compareTo(ans) < 0)){
                    ans = current;
                }
            }
        }
    return ans;    
    }
}