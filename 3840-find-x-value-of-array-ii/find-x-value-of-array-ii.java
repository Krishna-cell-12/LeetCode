import java.util.Arrays;

class Solution {
    private int[] tree_tot;
    private int[][] tree_pref;

    private class NodeState {
        int tot;
        int[] pref;
        
        NodeState(int k) {
            pref = new int[k];
        }
    }

    public int[] resultArray(int[] nums, int k, int[][] queries) {
        int n = nums.length;
        tree_tot = new int[4 * n];
        tree_pref = new int[4 * n][k];

        build(1, 0, n - 1, nums, k);

        int[] result = new int[queries.length];
        for (int i = 0; i < queries.length; i++) {
            int index = queries[i][0];
            int value = queries[i][1];
            int start = queries[i][2];
            int x = queries[i][3];

            // 1. Update the value at index
            update(1, 0, n - 1, index, value, k);
            
            // 2. Query the remaining suffix nums[start ... n-1]
            NodeState res = query(1, 0, n - 1, start, n - 1, k);
            
            // 3. Store the number of prefixes satisfying the product modulo k == x
            result[i] = res.pref[x];
        }

        return result;
    }

    private void build(int node, int l, int r, int[] nums, int k) {
        if (l == r) {
            tree_tot[node] = nums[l] % k;
            tree_pref[node][nums[l] % k] = 1;
            return;
        }
        int mid = l + (r - l) / 2;
        build(node * 2, l, mid, nums, k);
        build(node * 2 + 1, mid + 1, r, nums, k);
        merge(node, node * 2, node * 2 + 1, k);
    }

    private void update(int node, int l, int r, int idx, int val, int k) {
        if (l == r) {
            tree_tot[node] = val % k;
            Arrays.fill(tree_pref[node], 0);
            tree_pref[node][val % k] = 1;
            return;
        }
        int mid = l + (r - l) / 2;
        if (idx <= mid) {
            update(node * 2, l, mid, idx, val, k);
        } else {
            update(node * 2 + 1, mid + 1, r, idx, val, k);
        }
        merge(node, node * 2, node * 2 + 1, k);
    }

    private void merge(int node, int left, int right, int k) {
        tree_tot[node] = (tree_tot[left] * tree_tot[right]) % k;
        
        for (int i = 0; i < k; i++) {
            tree_pref[node][i] = tree_pref[left][i];
        }
        
        for (int w = 0; w < k; w++) {
            if (tree_pref[right][w] > 0) {
                int v = (tree_tot[left] * w) % k;
                tree_pref[node][v] += tree_pref[right][w];
            }
        }
    }

    private NodeState query(int node, int l, int r, int ql, int qr, int k) {
        if (ql <= l && r <= qr) {
            NodeState res = new NodeState(k);
            res.tot = tree_tot[node];
            System.arraycopy(tree_pref[node], 0, res.pref, 0, k);
            return res;
        }
        
        int mid = l + (r - l) / 2;
        
        if (qr <= mid) {
            return query(node * 2, l, mid, ql, qr, k);
        } else if (ql > mid) {
            return query(node * 2 + 1, mid + 1, r, ql, qr, k);
        } else {
            NodeState left = query(node * 2, l, mid, ql, qr, k);
            NodeState right = query(node * 2 + 1, mid + 1, r, ql, qr, k);
            
            NodeState res = new NodeState(k);
            res.tot = (left.tot * right.tot) % k;
            
            for (int i = 0; i < k; i++) {
                res.pref[i] = left.pref[i];
            }
            
            for (int w = 0; w < k; w++) {
                if (right.pref[w] > 0) {
                    int v = (left.tot * w) % k;
                    res.pref[v] += right.pref[w];
                }
            }
            
            return res;
        }
    }
}