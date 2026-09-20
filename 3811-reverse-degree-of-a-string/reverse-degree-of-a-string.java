class Solution {
    public int reverseDegree(String s) {
        int Degree = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            int reversePosition = 26 - (c - 'a');
            int stringPosition = i + 1;
            
            Degree += reversePosition * stringPosition;
        }
        
        return Degree;
    }
}