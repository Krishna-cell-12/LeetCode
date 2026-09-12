import java.util.Arrays;
import java.util.List;

class Solution {
    private static class Interval {
        int start, end, weight, id;

        Interval(int start, int end, int weight, int id) {
            this.start = start;
            this.end = end;
            this.weight = weight;
            this.id = id;
        }
    }

    public int[] maximumWeight(List<List<Integer>> intervals) {
        int n = intervals.size();
        Interval[] arr = new Interval[n];
        for (int i = 0; i < n; i++) {
            List<Integer> list = intervals.get(i);
            arr[i] = new Interval(list.get(0), list.get(1), list.get(2), i);
        }

        // Sort intervals by starting time ascending
        Arrays.sort(arr, (a, b) -> Integer.compare(a.start, b.start));

        // Precompute the next valid non-overlapping interval using binary search
        int[] nextValid = new int[n];
        for (int i = 0; i < n; i++) {
            int left = i + 1, right = n - 1, ans = n;
            while (left <= right) {
                int mid = left + (right - left) / 2;
                if (arr[mid].start > arr[i].end) {
                    ans = mid;
                    right = mid - 1;
                } else {
                    left = mid + 1;
                }
            }
            nextValid[i] = ans;
        }

        long[][] dpWeight = new long[n + 1][5];
        int[][][] dpIndices = new int[n + 1][5][];

        // Initialize DP arrays
        for (int i = 0; i <= n; i++) {
            for (int k = 0; k <= 4; k++) {
                dpIndices[i][k] = new int[0];
            }
        }

        // Process DP from right to left
        for (int i = n - 1; i >= 0; i--) {
            for (int k = 1; k <= 4; k++) {
                // Option 1: Skip current interval
                long skipWeight = dpWeight[i + 1][k];
                int[] skipIndices = dpIndices[i + 1][k];

                // Option 2: Take current interval
                int next = nextValid[i];
                long takeWeight = arr[i].weight + dpWeight[next][k - 1];
                int[] prevIndices = dpIndices[next][k - 1];
                
                int[] takeIndices = new int[prevIndices.length + 1];
                System.arraycopy(prevIndices, 0, takeIndices, 0, prevIndices.length);
                takeIndices[takeIndices.length - 1] = arr[i].id;
                Arrays.sort(takeIndices); // Ensure the selected indices are sorted for comparison

                // Evaluate options based on max weight, then lexicographical order
                if (takeWeight > skipWeight) {
                    dpWeight[i][k] = takeWeight;
                    dpIndices[i][k] = takeIndices;
                } else if (skipWeight > takeWeight) {
                    dpWeight[i][k] = skipWeight;
                    dpIndices[i][k] = skipIndices;
                } else {
                    if (isLexicographicallySmaller(takeIndices, skipIndices)) {
                        dpWeight[i][k] = takeWeight;
                        dpIndices[i][k] = takeIndices;
                    } else {
                        dpWeight[i][k] = skipWeight;
                        dpIndices[i][k] = skipIndices;
                    }
                }
            }
        }

        return dpIndices[0][4];
    }

    private boolean isLexicographicallySmaller(int[] a, int[] b) {
        for (int i = 0; i < Math.min(a.length, b.length); i++) {
            if (a[i] < b[i]) return true;
            if (a[i] > b[i]) return false;
        }
        return a.length < b.length;
    }
}