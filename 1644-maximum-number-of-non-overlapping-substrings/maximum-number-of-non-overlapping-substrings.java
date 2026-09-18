import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class Solution {
    public List<String> maxNumOfSubstrings(String s) {
        int[] first = new int[26];
        int[] last = new int[26];
        Arrays.fill(first, -1);
        for (int i = 0; i < s.length(); i++) {
            int c = s.charAt(i) - 'a';
            if (first[c] == -1) {
                first[c] = i;
            }
            last[c] = i;
        }
        
        List<int[]> intervals = new ArrayList<>();
        for (int c = 0; c < 26; c++) {
            if (first[c] == -1) continue;
            
            int L = first[c];
            int R = last[c];
            boolean isValid = true;
            
            for (int i = L; i <= R; i++) {
                int charIdx = s.charAt(i) - 'a';

                if (first[charIdx] < L) {
                    isValid = false;
                    break;
                }
                R = Math.max(R, last[charIdx]);
            }
            
            if (isValid) {
                intervals.add(new int[]{L, R});
            }
        }
        intervals.sort((a, b) -> a[1] == b[1] ? b[0] - a[0] : a[1] - b[1]);
        
        List<String> result = new ArrayList<>();
        int prevR = -1;
        
        for (int[] interval : intervals) {
            if (interval[0] > prevR) {
                result.add(s.substring(interval[0], interval[1] + 1));
                prevR = interval[1];
            }
        }
        
        return result;
    }
}