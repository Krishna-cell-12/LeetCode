class Solution {
    public String lexGreaterPermutation(String s, String target) {
       int n = s.length();
        int[] freq = new int[26];
        for (char c : s.toCharArray()) {
            freq[c - 'a']++;
        }

        int maxMatch = 0;
        while (maxMatch < n && freq[target.charAt(maxMatch) - 'a'] > 0) {
            freq[target.charAt(maxMatch) - 'a']--;
            maxMatch++;
        }
        int startIdx = Math.min(maxMatch, n - 1);
        if (maxMatch == n) {
            freq[target.charAt(n - 1) - 'a']++;
        }
        for (int i = startIdx; i >= 0; i--) {
            char targetChar = target.charAt(i);
            char chosenChar = 0;
            for (int c = targetChar - 'a' + 1; c < 26; c++) {
                if (freq[c] > 0) {
                    chosenChar = (char) (c + 'a');
                    break;
                }
            }

            if (chosenChar != 0) {
                StringBuilder result = new StringBuilder();
                for (int j = 0; j < i; j++) {
                    result.append(target.charAt(j));
                }
                result.append(chosenChar);
                freq[chosenChar - 'a']--;
                for (int c = 0; c < 26; c++) {
                    while (freq[c] > 0) {
                        result.append((char) (c + 'a'));
                        freq[c]--;
                    }
                }
                return result.toString();
            }
            if (i > 0) {
                freq[target.charAt(i - 1) - 'a']++;
            }
        }
        
        return "";
    }
}