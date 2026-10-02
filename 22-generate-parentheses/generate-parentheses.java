class Solution {
    public List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        backtrack(result, new StringBuilder(), 0, 0, n);
        return result;
    }

    private void backtrack(List<String> result, StringBuilder currentString, int openCount, int closeCount, int max) {
        if (currentString.length() == max * 2) {
            result.add(currentString.toString());
            return;
        }
        if (openCount < max) {
            currentString.append("(");
            backtrack(result, currentString, openCount + 1, closeCount, max);
            currentString.deleteCharAt(currentString.length() - 1);
        }
        if (closeCount < openCount) {
            currentString.append(")");
            backtrack(result, currentString, openCount, closeCount + 1, max);
            currentString.deleteCharAt(currentString.length() - 1); 
        }
    }
}