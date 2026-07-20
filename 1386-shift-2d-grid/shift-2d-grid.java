import java.util.ArrayList;
import java.util.List;

class Solution {
    public List<List<Integer>> shiftGrid(int[][] grid, int k) {
        int m = grid.length;
        int n = grid[0].length;
        int totalElements = m * n;
        k = k % totalElements;
        List<List<Integer>> result = new ArrayList<>();
        
        for (int r = 0; r < m; r++) {
            List<Integer> row = new ArrayList<>();
            for (int c = 0; c < n; c++) {
                int newIndex = r * n + c;
                int oldIndex = (newIndex - k + totalElements) % totalElements;

                int oldR = oldIndex / n;
                int oldC = oldIndex % n;
                
                row.add(grid[oldR][oldC]);
            }
            result.add(row);
        }
        
        return result;
    }
}