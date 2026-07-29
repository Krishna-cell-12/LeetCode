class Solution {
    public String smallestPalindrome(String s, int k) {
        int[] letterCounts = new int[26]; 
        
        for (int i = 0; i < s.length(); i++) {
            char currentLetter = s.charAt(i);
            letterCounts[currentLetter - 'a']++; 
        }
        
        int[] halfCounts = new int[26];
        String middleLetter = "";
        int halfLen = 0;
        for (int i = 0; i < 26; i++) {
            if (letterCounts[i] > 0) {
                char letter = (char) (i + 'a'); 
                if (letterCounts[i] % 2 != 0) {
                    middleLetter = String.valueOf(letter);
                }
                halfCounts[i] = letterCounts[i] / 2;
                halfLen += halfCounts[i];
            }
        }
        if (k > countPermutations(halfCounts, halfLen)) {
            return "";
        }
        StringBuilder leftSide = new StringBuilder();
        for (int i = 0; i < halfLen; i++) {
            for (int j = 0; j < 26; j++) {
                if (halfCounts[j] > 0) {
                    halfCounts[j]--;
                    
                    long perms = countPermutations(halfCounts, halfLen - 1 - i);
                    
                    if (k <= perms) {
                        leftSide.append((char) (j + 'a'));
                        break; 
                    } else {
                        k -= perms; 
                        halfCounts[j]++; 
                    }
                }
            }
        }
        StringBuilder rightSide = new StringBuilder(leftSide).reverse();
        return leftSide.toString() + middleLetter + rightSide.toString();
    }
    private long countPermutations(int[] counts, int length) {
        long perms = 1;
        int currentLen = 1;
        
        for (int count : counts) {
            for (int i = 1; i <= count; i++) {
                perms = (perms * currentLen) / i;
                currentLen++;
                if (perms > 1000000) {
                    return 1000001; 
                }
            }
        }
        return perms;
    }
}