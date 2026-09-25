import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

class Solution {
    private int i = 0;
    private String s;

    public List<String> braceExpansionII(String expression) {
        this.s = expression;
        this.i = 0;
        
        // Parse the entire expression to get the unique set of words
        Set<String> set = parseExpr();
        
        // Convert to a List and sort it lexicographically as required
        List<String> result = new ArrayList<>(set);
        Collections.sort(result);
        return result;
    }

    // Handles Union (Addition)
    private Set<String> parseExpr() {
        Set<String> res = new HashSet<>();
        res.addAll(parseTerm());
        
        // Continue merging terms as long as they are separated by commas
        while (i < s.length() && s.charAt(i) == ',') {
            i++; // skip ','
            res.addAll(parseTerm());
        }
        return res;
    }

    // Handles Concatenation (Multiplication)
    private Set<String> parseTerm() {
        Set<String> res = new HashSet<>();
        res.add(""); // Start with an empty string as the multiplication identity
        
        // As long as we are not hitting a comma or a closing brace, 
        // the factors are adjacent and should be concatenated.
        while (i < s.length() && s.charAt(i) != ',' && s.charAt(i) != '}') {
            Set<String> factor = parseFactor();
            Set<String> nextRes = new HashSet<>();
            
            // Cartesian product of the current accumulated result and the new factor
            for (String prefix : res) {
                for (String suffix : factor) {
                    nextRes.add(prefix + suffix);
                }
            }
            res = nextRes;
        }
        return res;
    }

    // Handles Base Cases and Grouping (Parentheses)
    private Set<String> parseFactor() {
        Set<String> res = new HashSet<>();
        if (i < s.length() && s.charAt(i) == '{') {
            i++; // skip '{'
            res = parseExpr(); // evaluate the inner expression
            i++; // skip '}'
        } else {
            // Greedily consume all adjacent lowercase letters
            StringBuilder sb = new StringBuilder();
            while (i < s.length() && Character.isLowerCase(s.charAt(i))) {
                sb.append(s.charAt(i));
                i++;
            }
            res.add(sb.toString());
        }
        return res;
    }
}