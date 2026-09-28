class Solution {
    public int maxDepth(String s) {
        int ans = 0;
        int curr = 0;
        for(char c : s.toCharArray()){
            if(c == '('){
                curr++;
                ans = Math.max(ans,curr);
            }else if(c == ')'){
                curr--;
            }
        }
        return ans;
    }
}