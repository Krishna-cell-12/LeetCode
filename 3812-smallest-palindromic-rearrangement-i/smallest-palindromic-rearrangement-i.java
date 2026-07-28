class Solution {
    public String smallestPalindrome(String s) {
        int[] letterCounts = new int[26]; 
        
        for (int i = 0; i < s.length(); i++) {
            char currentLetter = s.charAt(i);
            letterCounts[currentLetter - 'a']++; 
        }
        StringBuilder leftSide = new StringBuilder();
        String middleLetter = "";
        for (int i = 0; i < 26; i++) {
            if (letterCounts[i] > 0) {
                char letter = (char) (i + 'a'); 
                if (letterCounts[i] % 2 != 0) {
                    middleLetter = String.valueOf(letter);
                }
                int halfAmount = letterCounts[i] / 2;
                for (int j = 0; j < halfAmount; j++) {
                    leftSide.append(letter);
                }
            }
        }
        StringBuilder rightSide = new StringBuilder(leftSide).reverse();
        return leftSide.toString() + middleLetter + rightSide.toString();
    }
}