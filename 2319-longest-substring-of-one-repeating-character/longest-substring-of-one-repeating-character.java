class Solution {
    int[] maxLen;
    int[] prefLen;
    int[] suffLen;
    char[] prefChar;
    char[] suffChar;

    public int[] longestRepeating(String s, String queryCharacters, int[] queryIndices) {
        int n = s.length();
        maxLen = new int[4 * n + 1];
        prefLen = new int[4 * n + 1];
        suffLen = new int[4 * n + 1];
        prefChar = new char[4 * n + 1];
        suffChar = new char[4 * n + 1];
        build(1, 0, n - 1, s);
        
        int k = queryIndices.length;
        int[] ans = new int[k];
        for (int i = 0; i < k; i++) {
            update(1, 0, n - 1, queryIndices[i], queryCharacters.charAt(i));
            ans[i] = maxLen[1]; 
        }
        
        return ans;
    }

    private void build(int node, int start, int end, String s) {
        if (start == end) {
            maxLen[node] = 1;
            prefLen[node] = 1;
            suffLen[node] = 1;
            prefChar[node] = s.charAt(start);
            suffChar[node] = s.charAt(start);
            return;
        }
        
        int mid = (start + end) / 2;
        int left = 2 * node;
        int right = 2 * node + 1;
        
        build(left, start, mid, s);
        build(right, mid + 1, end, s);
        
        merge(node, left, right, mid - start + 1, end - mid);
    }

    private void update(int node, int start, int end, int idx, char c) {
        if (start == end) {
            prefChar[node] = c;
            suffChar[node] = c;
            return;
        }
        
        int mid = (start + end) / 2;
        int left = 2 * node;
        int right = 2 * node + 1;
        
        if (idx <= mid) {
            update(left, start, mid, idx, c);
        } else {
            update(right, mid + 1, end, idx, c);
        }
        
        merge(node, left, right, mid - start + 1, end - mid);
    }

    private void merge(int node, int left, int right, int lenLeft, int lenRight) {
        prefChar[node] = prefChar[left];
        prefLen[node] = prefLen[left];
        if (prefLen[left] == lenLeft && prefChar[left] == prefChar[right]) {
            prefLen[node] += prefLen[right];
        }
        suffChar[node] = suffChar[right];
        suffLen[node] = suffLen[right];
        if (suffLen[right] == lenRight && suffChar[right] == suffChar[left]) {
            suffLen[node] += suffLen[left];
        }
        maxLen[node] = Math.max(maxLen[left], maxLen[right]);
        if (suffChar[left] == prefChar[right]) {
            maxLen[node] = Math.max(maxLen[node], suffLen[left] + prefLen[right]);
        }
    }
}