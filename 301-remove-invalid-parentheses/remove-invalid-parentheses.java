import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

class Solution {
    public List<String> removeInvalidParentheses(String s) {
        List<String> result = new ArrayList<>();
        if (s == null) return result;
        
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new LinkedList<>();
        
        queue.add(s);
        visited.add(s);
        
        boolean foundValid = false;
        
        while (!queue.isEmpty()) {
            String current = queue.poll();
            
            // If the current string is valid, add it to the result
            if (isValid(current)) {
                result.add(current);
                foundValid = true;
            }
            
            // If we have found a valid string at this level, we don't need to 
            // explore further removals (shorter strings). We just finish checking 
            // the remaining strings at the current queue level.
            if (foundValid) {
                continue;
            }
            
            // Generate all possible substrings by removing one parenthesis
            for (int i = 0; i < current.length(); i++) {
                char c = current.charAt(i);
                // Skip if it's a letter
                if (c != '(' && c != ')') {
                    continue;
                }
                
                // Create a new string with the character at index i removed
                String next = current.substring(0, i) + current.substring(i + 1);
                
                if (!visited.contains(next)) {
                    queue.add(next);
                    visited.add(next);
                }
            }
        }
        
        return result;
    }
    
    // Helper method to check if a string of parentheses is valid
    private boolean isValid(String s) {
        int count = 0;
        
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                count++;
            } else if (c == ')') {
                count--;
                // If closing parenthesis exceeds opening ones, it's invalid
                if (count < 0) {
                    return false;
                }
            }
        }
        
        return count == 0;
    }
}